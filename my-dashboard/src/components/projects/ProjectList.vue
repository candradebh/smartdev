<template>
  <v-container>
    <v-data-table :headers="headers" :items="listModel" class="elevation-1">
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Projetos</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="openForm(null)">Criar</v-btn>
        </v-toolbar>
      </template>
      <template v-slot:[`item.date`]="{ item }">
        {{ item.date | formatDate }}
      </template>

      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small @click="openForm(item)">mdi-pencil</v-icon>
        <v-icon small @click="confirmDelete(item)">mdi-delete</v-icon>
        <v-icon small @click="goToFeatures(item)">mdi-tag</v-icon> <!-- Botão para Features -->
      </template>
    </v-data-table>

    <!-- Form Dialog -->
    <v-dialog v-model="dialogForm" max-width="500px">
      <ProjectsForm :itemModel="selectedModel" @close="dialogForm = false" @save="fetchModels"/>
    </v-dialog>

    <!-- Delete Confirmation Dialog -->
    <v-dialog v-model="dialogDelete" max-width="400px">
      <v-card>
        <v-card-title class="text-h5">Atenção</v-card-title>
        <v-card-text>Deseja deletar esse registro?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="deleteModel">Sim</v-btn>
          <v-btn color="red darken-1" text @click="dialogDelete = false">Não</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import ProjectsForm from './ProjectsForm.vue';

export default {
  name: 'ProjectList',
  components: {ProjectsForm},
  data() {
    return {
      listModel: [],
      selectedModel: null,
      dialogForm: false,
      dialogDelete: false,
      headers: [
        {text: 'ID', value: 'id'},
        {text: 'Name', value: 'name'},
        {text: 'Description', value: 'description'},
        {text: 'Data', value: 'date'},
        {text: 'workspacePath', value: 'workspacePath'},
        {text: 'gitPath', value: 'gitPath'},
        {text: 'Ações', value: 'actions', sortable: false}
      ]
    };
  },
  methods: {
    fetchModels() {
      this.$api.get('/projects').then(response => {
        this.listModel = response.data;
      });
    },
    openForm(model) {
      this.$router.push({name: 'ProjectForm', params: {itemModel: model}})
    },
    confirmDelete(model) {
      this.selectedModel = model;
      this.dialogDelete = true;
    },
    deleteModel() {
      this.$api.delete(`/projects/${this.selectedModel.id}`).then(() => {
        this.fetchModels();
        this.dialogDelete = false;
      });
    },
    // Método para navegar para a página de features
    goToFeatures(project) {
      this.$router.push(`/projects/${project.id}/details`);
    }
  },
  created() {
    this.fetchModels();
  }
};
</script>
