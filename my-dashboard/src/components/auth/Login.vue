<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>
            Login
          </v-card-title>

          <v-card-text>
            <v-form @submit.prevent="login">
              <v-text-field label="Username" v-model="username" required></v-text-field>

              <v-text-field label="Password" v-model="password" type="password" required></v-text-field>

              <v-btn type="submit" color="primary">Login</v-btn>
            </v-form>
          </v-card-text>

          <v-card-actions>
            <!-- Botão para redirecionar para a página de registro -->
            <v-btn text color="secondary" @click="goToRegister">Registrar</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { jwtDecode } from "jwt-decode";

export default {
  name: 'LoginPage',
  data() {
    return {
      username: '',
      password: '',
      userInfo: {
        id:'',
        username: '',
        name: '',
        email: '',
        roles: []
      }
    };
  },
  methods: {
    async login() {
      try {
        const response = await this.$api.post('/auth/login', {
          username: this.username,
          password: this.password,
        });
        const token = response.data.token;
        localStorage.setItem('token', token); // Salva o token no localStorage
        this.getUserInfo(token);
        this.$router.push('/'); // Redireciona para a página principal
      } catch (error) {
        console.error('Erro de login:', error);
        alert('Falha ao fazer login. Verifique suas credenciais.');
      }
    },
    // Método para redirecionar para a página de registro
    goToRegister() {
      this.$router.push('/register'); // Redireciona para a rota de registro
    },
    getUserInfo(token) {
      try {
        const decodedToken = jwtDecode(token); // Decodifica o token JWT
        //console.log(decodedToken);

        const id = decodedToken.id;
        const username = decodedToken.sub; // O "subject" (username)
        const name = decodedToken.name; // O "name" (nome)
        const email = decodedToken.email; // O "email"
        const roles = decodedToken.roles; // As "roles"

        // Exemplo de exibição em variáveis de estado
        this.userInfo = {
          id,
          username,
          name,
          email,
          roles,
        };

        localStorage.setItem("user",JSON.stringify(this.userInfo));

      } catch (error) {
        console.error("Erro ao decodificar o token JWT", error);
      }
    }
  }
  }
</script>