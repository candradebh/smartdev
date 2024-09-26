<template>
  <v-container>
    <v-row>
      <v-col v-for="status in statuses" :key="status" cols="4">
        <v-card>
          <v-card-title class="d-flex justify-space-between">
            {{ status }}
            <v-btn icon @click="openTaskForm(status)">
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text>
            <draggable v-model="tasks[status]" :data-id="status" :group="'tasks'" @end="updateTaskStatus">
              <v-list dense>
                <v-list-item v-for="task in tasks[status]" :key="task.id">
                  <v-list-item-content>
                    <v-list-item-title>{{ task.title }}</v-list-item-title>
                    <v-list-item-subtitle>{{ task.description }}</v-list-item-subtitle>
                  </v-list-item-content>

                  <v-btn icon @click="editTask(task)">
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                  <v-btn icon @click="deleteTask(task.id)">
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>

                </v-list-item>
              </v-list>
            </draggable>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Modal de formulário para criar/editar task -->
    <v-dialog v-model="dialog" max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ task.id ? 'Edit Task' : 'Create Task' }}</span>
        </v-card-title>
        <v-card-text>
          <v-form ref="form" v-model="valid">
            <v-text-field v-model="task.title" :rules="[rules.required]" label="Title" required></v-text-field>

            <v-textarea v-model="task.description" :rules="[rules.required]" label="Description" required></v-textarea>

            <v-select v-model="task.status" :items="statuses" :rules="[rules.required]" label="Status"
                      required></v-select>

            <v-select v-model="task.project" :items="projects" :rules="[rules.required]" item-text="name"
                      item-value="id" label="Project"
                      required></v-select>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn text @click="closeDialog">Cancel</v-btn>
          <v-btn :disabled="!valid" @click="saveTask">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import draggable from 'vuedraggable';

export default {
  name: 'TasksIndex',
  components: {draggable},
  data() {
    return {
      statuses: ['TODO', 'IN_PROGRESS', 'DONE'],
      projects: [],
      tasks: {
        TODO: [],
        IN_PROGRESS: [],
        DONE: [],
      },
      dialog: false,
      valid: false,
      task: {
        id: null,
        title: '',
        description: '',
        status: 'TODO',
        project: null
      },
      rules: {
        required: (value) => !!value || 'Obrigatório.',
      },
    };
  },
  methods: {
    fetchTasks() {
      this.$api.get('/tasks').then((response) => {
        this.statuses.forEach((status) => {
          this.tasks[status] = response.data.filter(
              (task) => task.status === status
          );
        });
      });
    },
    fetchProjects() {
      this.$api.get('/projects/isActive').then((response) => {
        this.projects = response.data;
      });
    },

    // Atualizar o status da task ao mover entre colunas
    updateTaskStatus(evt) {
      //const oldStatus = evt.from.dataset.id; // Status antigo
      const newStatus = evt.to.dataset.id; // Novo status

      // Obtenha a task diretamente a partir do índice
      const movedTask = this.tasks[newStatus][evt.newIndex];

      if (movedTask && movedTask.id) {
        movedTask.status = newStatus;

        // Enviar a cópia "limpa" da task para o backend
        this.$api.put(`/tasks/${movedTask.id}`, movedTask).then(() => {
          this.fetchTasks(); // Atualiza as tasks
        });
      } else {
        console.error('Task not found');
      }
    },

    openTaskForm(status) {
      // Abrir modal para criar nova task
      this.task = {
        id: null,
        title: '',
        description: '',
        status: status, // O status da coluna
        project: null
      };
      this.dialog = true;
    },
    editTask(task) {
      // Abrir modal para editar task
      this.task = {...task};
      console.log(this.task);
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
      this.task = {
        id: null,
        title: '',
        description: '',
        status: 'TODO',
        project: null
      };
    },
    saveTask() {
      const extraParameter = {
        headers: {
          'Content-Type': 'application/json'
        }
      };
      if (this.task.id) {
        // Atualizar task existente
        this.$api.put(`/tasks/${this.task.id}`, this.task, extraParameter).then(() => {
          this.fetchTasks();
          this.closeDialog();
        });
      } else {
        // Criar nova task
        this.$api.post('/tasks', this.task, extraParameter).then(() => {
          this.fetchTasks();
          this.closeDialog();
        });
      }
    },
    deleteTask(taskId) {
      if (taskId) {
        this.$api.delete(`/tasks/${taskId}`).then(() => {
          this.fetchTasks();  // Atualizar a lista de tasks
        });
      } else {
        console.error("Invalid task ID");
      }
    },
  },
  mounted() {
    this.fetchTasks();
    this.fetchProjects();
  },
};
</script>
<style>


/* Efeito de hover nativo */
.v-list-item:hover {
  background-color: #5a5c5c; /* Cor de fundo ao passar o mouse */
  cursor: pointer;
}
</style>