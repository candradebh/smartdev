<template>
  <div>
    <v-container>
      <v-row>
        <!-- Card para Total de Projetos -->
        <v-col cols="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="400">
            <v-card-title>Total de Projetos </v-card-title>
            <v-card-text class="d-flex justify-center align-center">
              <span class="display-2">{{ totalProjects }}</span>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Card para Total de Modelos IA -->
        <v-col cols="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="400">
            <v-card-title>Total Modelos IA</v-card-title>
            <v-card-text class="d-flex justify-center align-center">
              <span class="display-2">{{ totalModels }}</span>
            </v-card-text>
          </v-card>
        </v-col>

         <!-- Card para Total de TAsks -->
         <v-col cols="12" sm="6" md="4">
          <v-card class="mx-auto" max-width="400">
            <v-card-title>Total Tasks</v-card-title>
            <v-card-text class="d-flex justify-center align-center">
              <span class="display-2">{{ totalTasks }}</span>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      apiEndpoints: {
        models: '/chat/models',
        projects: '/projects',
        tasks: '/tasks'
      },
      totalModels: 0,
      totalProjects: 0,
      totalTasks: 0
    };
  },
  created() {
    this.fetchTotalProjects();
    this.fetchTotalModels();
    this.fetchTotalTasks();
  },
  methods: {
    async fetchTotalProjects() {
      const objeto = 'projetos';
      try {
        const response = await this.$api.get(this.apiEndpoints.projects);

        if (response && response.data) {
          this.totalProjects = response.data.length;
        } else {
          console.warn('Resposta inesperada ao obter número de '+objeto+':', response);
        }
      } catch (error) {
        console.error('Erro ao obter número de '+objeto+':', error);
        this.showErrorNotification('Ocorreu um erro ao buscar os '+objeto+'. Tente novamente mais tarde.');
      }
    },
    async fetchTotalModels() {
      const objeto = 'Modelos IA';
      try {
        const response = await this.$api.get(this.apiEndpoints.models);

        if (response && response.data) {
          this.totalModels = response.data.length;
        } else {
          console.warn('Resposta inesperada ao obter número de '+objeto+':', response);
        }
      } catch (error) {
        console.error('Erro ao obter número de modelos IA:', error);
        this.showErrorNotification('Ocorreu um erro ao buscar os '+objeto+'. Tente novamente mais tarde.');
      }
    },
    async fetchTotalTasks() {
      const objeto = 'Tasks';
      try {
        const response = await this.$api.get(this.apiEndpoints.tasks);

        if (response && response.data) {
          this.totalTasks = response.data.length;
        } else {
          console.warn('Resposta inesperada ao obter número de '+objeto+':', response);
        }
      } catch (error) {
        console.error('Erro ao obter número de '+objeto+':', error);
        this.showErrorNotification('Ocorreu um erro ao buscar os '+objeto+'. Tente novamente mais tarde.');
      }
    },

  }
};
</script>
