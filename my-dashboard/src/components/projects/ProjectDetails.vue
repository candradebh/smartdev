<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h2>{{ project.name }}</h2>

        <!-- Botão para abrir o modal de adição de tag -->
        <v-btn color="primary" @click="openModal">Adicionar Tecnologias</v-btn>

        <!-- Data Table para Exibir Features -->
        <v-data-table :headers="headers" :items="features" class="mt-5">
          <!-- eslint-disable-next-line -->
          <template v-slot:item.tag="{ item }">
            {{ item.tag }}
          </template>
          <!-- eslint-disable-next-line -->
          <template v-slot:item.featureValue="{ item }">
            {{ item.featureValue }}
          </template>
          <!-- eslint-disable-next-line -->
          <template v-slot:item.actions="{ item }">
            <!-- Botão para Editar a Tag -->
            <v-icon small @click="editFeature(item)" color="blue">mdi-pencil</v-icon>
            <!-- Botão para Deletar a Tag -->
            <v-icon small @click="confirmDelete(item)" color="red">mdi-delete</v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <!-- Modal para Adicionar/Editar Feature -->
    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span v-if="editMode">Editar Tag</span>
          <span v-else>Adicionar Tag</span>
        </v-card-title>

        <v-card-text>
          <v-form @submit.prevent="saveFeature">
            <v-row>
              <v-col cols="6">
                <v-text-field
                  v-model="newFeature.tag"
                  label="Tag"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-text-field
                  v-model="newFeature.featureValue"
                  label="Value"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="saveFeature">Salvar</v-btn>
          <v-btn color="red darken-1" text @click="dialog = false">Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal para Confirmar Delete -->
    <v-dialog v-model="dialogDelete" max-width="400px">
      <v-card>
        <v-card-title class="text-h5">Atenção</v-card-title>
        <v-card-text>Você tem certeza que deseja deletar esta tag?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="deleteFeature">Sim</v-btn>
          <v-btn color="red darken-1" text @click="dialogDelete = false">Não</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      project: {},
      features: [],
      newFeature: {
        id: null,
        tag: '',
        featureValue: ''
      },
      selectedFeature: null,
      dialog: false,
      dialogDelete: false,
      editMode: false,
      headers: [
        { text: 'Tag', value: 'tag' },
        { text: 'Value', value: 'featureValue' },
        { text: 'Ações', value: 'actions', sortable: false }
      ]
    };
  },
  created() {
    this.fetchProject();
    this.fetchFeatures();
  },
  methods: {
    // Fetch project details
    fetchProject() {
      const projectId = this.$route.params.projectId;
      this.$api.get(`/projects/${projectId}`).then(response => {
        this.project = response.data;
      });
    },

    // Fetch features related to the project
    fetchFeatures() {
      const projectId = this.$route.params.projectId;
      this.$api.get(`/projects/${projectId}/features`).then(response => {
        this.features = response.data;
      });
    },

    // Open the modal for adding a new feature
    openModal() {
      this.resetForm();
      this.dialog = true;
      this.editMode = false;
    },

    // Reset form fields
    resetForm() {
      this.newFeature = {
        id: null,
        tag: '',
        featureValue: ''
      };
    },

    // Save feature (add or update)
    saveFeature() {
      const projectId = this.$route.params.projectId;
      this.$api.post(`/projects/${projectId}/features`, this.newFeature).then(() => {
          this.fetchFeatures();
          this.dialog = false;
        });
      
    },

    // Edit an existing feature
    editFeature(feature) {
      this.newFeature = { ...feature }; // Clona os dados da feature selecionada
      this.dialog = true;
      this.editMode = true;
    },

    // Confirm deletion of a feature
    confirmDelete(feature) {
      this.selectedFeature = feature;
      this.dialogDelete = true;
    },

    // Delete feature
    deleteFeature() {
      this.$api.delete(`/features/${this.selectedFeature.id}`).then(() => {
        this.fetchFeatures(); // Atualiza a lista de features
        this.dialogDelete = false;
      });
    }
  }
};
</script>
