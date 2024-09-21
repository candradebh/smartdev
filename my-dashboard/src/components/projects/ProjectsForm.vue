<template>
    <v-card>
      <v-card-title>
        <span class="text-h5">{{ itemModel ? 'Editar' : 'Adicionar' }} projeto</span>
      </v-card-title>
  
      <v-card-text>
        <v-form ref="form">
          <v-text-field v-model="form.name" label="Name" required></v-text-field>
          <v-text-field v-model="form.description" label="Description" required></v-text-field>
          <v-text-field v-model="form.workspacePath" label="workspacePath" placeholder="Ex: C:/projetos/teste/" required></v-text-field>
          <v-text-field v-model="form.gitPath" label="gitPath" required></v-text-field>
          <v-checkbox v-model="form.isActive" label="Active"></v-checkbox>
        </v-form>
      </v-card-text>
  
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="cancel">Cancelar</v-btn>
        <v-btn color="blue darken-1" text @click="saveModel">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </template>
  
  <script>
  
  export default {
    props: {
      itemModel: Object
    },
    data() {
      return {
        form: {
          id: null,
          name: '',
          description: '',
          workspacePath: '',
          gitPath: '',
          isActive: true,
          features: [],
          tasks: []
        }
        
      };
    },
    watch: {
      itemModel: {
        handler(newValue) {
          if (newValue) {
            this.form = { ...newValue };
          } else {
            this.resetForm();
          }
        },
        immediate: true
      }
    },
    methods: {
      cancel(){
        this.$router.push('/projects');
      },
      resetForm() {
        this.form = {
          id: null,
          name: '',
          description: '',
          workspacePath: '',
          gitPath: '',
          isActive: true,
          features: [],
          tasks: []
        };
      },
      saveModel() {
        if (this.$refs.form.validate()) {
          const request = this.form.id
            ? this.$api.put(`/projects/${this.form.id}`, this.form)
            : this.$api.post('/projects', this.form);
  
          request.then(() => {
            this.$router.push('/projects');
          });
        }
      }
    }
  };
  </script>
  