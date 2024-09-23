<template>
  <v-responsive class="border rounded">
    <v-app>
      <v-layout>
        <!-- Barra Superior, visível apenas se a rota não tiver meta: { hideNavbar: true } -->
        <v-app-bar v-if="!$route.meta.hideNavbar" app>
          <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
          <v-toolbar-title>MyApp</v-toolbar-title>

          <v-spacer></v-spacer>

          <template>
            <v-btn icon @click="toggleTheme">
              <v-icon>{{ isDarkTheme ? 'mdi-weather-night' : 'mdi-weather-sunny' }}</v-icon>
            </v-btn>

            <v-btn icon>
              <v-icon>mdi-magnify</v-icon>
            </v-btn>

            <v-btn icon>
              <v-icon>mdi-filter</v-icon>
            </v-btn>

            <!-- Menu Adicionado ao Avatar -->
            <v-menu offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon v-bind="attrs" v-on="on">
                  <v-avatar size="32">
                    <v-icon>mdi-account</v-icon>
                  </v-avatar>
                </v-btn>
              </template>
              <v-list>
                <v-list-item @click="goToProfile">
                  <v-list-item-title>Profile</v-list-item-title>
                </v-list-item>
                <v-list-item @click="goToSettings">
                  <v-list-item-title>Settings</v-list-item-title>
                </v-list-item>
                <v-list-item @click="logout">
                  <v-list-item-title>Sair</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </template>
        </v-app-bar>

        <!-- Menu Lateral, visível apenas se a rota não tiver meta: { hideNavbar: true } -->
        <v-navigation-drawer v-if="!$route.meta.hideNavbar" v-model="drawer" app>
          <v-list>
            <v-list-item v-for="item in items" :key="item.title" :to="item.path" link router>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>

        <!-- Conteúdo Principal -->
        <v-main class="mt-5 ml-4">
          <router-view/>
        </v-main>
      </v-layout>
    </v-app>
  </v-responsive>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
    isDarkTheme: false,
    drawer: false,
    items: [
      {title: 'Home', path: '/'},
      {title: 'Serviços', path: '/services'},
      {title: 'Destinatários', path: '/recipients'},
      {title: 'Log Notificação', path: '/notificationlogs'},
    ],
  }),
  methods: {
    toggleTheme() {
      this.isDarkTheme = !this.isDarkTheme;
      this.$vuetify.theme.dark = this.isDarkTheme;
      localStorage.setItem('isDarkTheme', this.isDarkTheme);
    },
    loadTheme() {
      const savedTheme = localStorage.getItem('isDarkTheme');
      if (savedTheme !== null) {
        this.isDarkTheme = savedTheme === 'true';
        this.$vuetify.theme.dark = this.isDarkTheme;
      }
    },
    goToProfile() {
      this.$router.push('/profile');
    },
    goToSettings() {
      this.$router.push('/settings');
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      this.$router.push("/login");
    },
  },
  created() {
    this.loadTheme();
  },
};
</script>
