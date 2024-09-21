<template>
  <v-responsive class="border rounded">
    <v-app>
      <v-layout>
        <v-app-bar app>
          <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
          <v-toolbar-title>MyApp</v-toolbar-title>

          <v-spacer></v-spacer>

          <template>

            <!-- Botão para alternar tema -->
            <v-btn icon @click="toggleTheme">
              <v-icon>{{ isDarkTheme ? 'mdi-weather-sunny' : 'mdi-weather-night' }}</v-icon>
            </v-btn>

            <v-btn icon>
              <v-icon>mdi-magnify</v-icon>
            </v-btn>

            <v-btn icon>
              <v-icon>mdi-filter</v-icon>
            </v-btn>
          </template>
        </v-app-bar>

        <v-navigation-drawer v-model="drawer" app>
          <v-list>
            <v-list-item v-for="item in items" :key="item.title" :to="item.path" router link>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>

        <v-main class="mt-5 ml-4">
          <router-view />
        </v-main>
      </v-layout>
    </v-app>
  </v-responsive>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isDarkTheme: false, // Define o tema inicial como claro
      drawer: false,
      items: [
        { title: 'Home', path: '/' },
        { title: 'Desenvolvedor', path: '/developer' },
        { title: 'Projects', path: '/projects' },
        { title: 'Tasks', path: '/tasks' },
        { title: 'Models', path: '/models' },
        { title: 'Serviços', path: '/services' },
        { title: 'Tabelas', path: '/tables' },
        { title: 'Destinatários', path: '/recipients' },
        { title: 'Log Notificação', path: '/notificationlogs' },
      ],
    };
  },
  methods: {
    toggleTheme() {
      // Alterna entre claro e escuro
      this.isDarkTheme = !this.isDarkTheme;
      this.$vuetify.theme.dark = this.isDarkTheme;

      // Salva a preferência de tema no localStorage
      localStorage.setItem('isDarkTheme', this.isDarkTheme);
    },
    loadTheme() {
      // Carrega a preferência do tema do localStorage
      const savedTheme = localStorage.getItem('isDarkTheme');
      if (savedTheme !== null) {
        this.isDarkTheme = savedTheme === 'true'; // Converte string para booleano
        this.$vuetify.theme.dark = this.isDarkTheme;
      }
    },
  },
  created() {
    // Carrega o tema salvo ao iniciar o componente
    this.loadTheme();
  },
};
</script>
