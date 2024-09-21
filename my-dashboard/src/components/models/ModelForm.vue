<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">{{ itemModel ? 'Editar' : 'Adicionar' }} modelo IA Externo</span>
    </v-card-title>

    <v-card-text>
      <v-form ref="form">
        <v-text-field v-model="form.name" label="Name" required></v-text-field>
        <v-text-field v-model="form.model" label="Model" required></v-text-field>
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
  name: 'ModelForm',
  props: {
    itemModel: Object
  },
  data() {
    return {
      apiIndex: '/chat/models',
      routeIndex: '/models',
      form: {
        id: null,
        name: '',
        model: '',
        modified_at: '',
        size: '',
        digest: ''
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
      this.$router.push(this.routeIndex);
    },
    resetForm() {
      this.form = {
        id: null,
        name: '',
        model: '',
        modified_at: '',
        size: '',
        digest: ''
      };
    },
    saveModel() {
      if (this.$refs.form.validate()) {
        const request = this.form.id
          ? this.$api.put(`${this.apiIndex}/${this.form.id}`, this.form)
          : this.$api.post(`${this.apiIndex}`, this.form);

        request.then(() => {
          this.$router.push(this.routeIndex);
        });
      }
    }
  }
};
</script>