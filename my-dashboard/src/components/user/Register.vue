<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>
            Registro de Usuário
          </v-card-title>

          <v-card-text>
            <v-form @submit.prevent="registerUser">
              
              <v-text-field label="Name" v-model="name" required></v-text-field>
              <v-text-field label="Username" v-model="username" required></v-text-field>
              <v-text-field label="Email" v-model="email" required></v-text-field>
              <v-text-field label="Password" v-model="password" type="password" required></v-text-field>

              <v-btn type="submit" color="primary">Registrar</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'RegisterPage',
  data() {
    return {
      name: '',
      username: '',
      email: '',
      password: '',
    };
  },
  methods: {
    async registerUser() {
      try {
        const response = await this.$api.post('/users/register', {
          name: this.name,
          username: this.username,
          email: this.email,
          password: this.password, // Certifique-se de que está enviando a senha
        });
        console.log('Usuário registrado com sucesso:', response.data);
        this.$router.push('/login');
      } catch (error) {
        console.error('Erro ao registrar usuário:', error.response.data);
        alert('Falha no registro: ' + error.response.data);
      }
    }
  },
};
</script>