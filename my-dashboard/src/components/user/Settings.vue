<template>
    <v-container>
        <v-row>
            <v-col cols="12" md="8" class="mx-auto">
                <v-card>
                    <v-card-title>Configurações da Conta</v-card-title>
                    <v-card-subtitle>Gerencie suas preferências</v-card-subtitle>

                    <v-card-text>
                        <!-- Configurações de Tema -->
                        <v-divider></v-divider>
                        <v-row class="mb-3">
                            <v-col cols="12">
                                <v-subheader>Tema</v-subheader>
                                <v-switch v-model="isDarkTheme" label="Modo Escuro" @change="toggleDarkMode"></v-switch>
                            </v-col>
                        </v-row>

                        <!-- Configurações de Notificações -->
                        <v-divider></v-divider>
                        <v-row class="mb-3">
                            <v-col cols="12">
                                <v-subheader>Notificações</v-subheader>
                                <v-checkbox v-model="notifications.email"
                                    label="Receber notificações por e-mail"></v-checkbox>
                                <v-checkbox v-model="notifications.sms"
                                    label="Receber notificações por SMS"></v-checkbox>
                                <v-checkbox v-model="notifications.push" label="Receber notificações Push"></v-checkbox>
                            </v-col>
                        </v-row>

                        <!-- Preferências de Layout -->
                        <v-divider></v-divider>
                        <v-row class="mb-3">
                            <v-col cols="12">
                                <v-subheader>Preferências de Layout</v-subheader>
                                <v-radio-group v-model="layoutPreference" row>
                                    <v-radio label="Layout em Grade" value="grid"></v-radio>
                                    <v-radio label="Layout em Lista" value="list"></v-radio>
                                </v-radio-group>
                            </v-col>
                        </v-row>

                        <!-- Dados da Conta -->
                        <v-divider></v-divider>
                        <v-row class="mb-3">
                            <v-col cols="12">
                                <v-subheader>Dados da Conta</v-subheader>
                                <v-text-field v-model="user.name" label="Nome Completo"></v-text-field>
                                <v-text-field v-model="user.email" label="E-mail"></v-text-field>
                            </v-col>
                        </v-row>

                        <v-divider></v-divider>
                        <v-row class="mt-3">
                            <v-col cols="12" class="text-right">
                                <v-btn color="primary" @click="saveSettings">Salvar Configurações</v-btn>
                            </v-col>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'SettingPage',
    data() {
        return {
            isDarkTheme: false,  // Controle para alternar entre tema escuro e claro
            notifications: {
                email: true,  // Notificações por e-mail
                sms: false,   // Notificações por SMS
                push: true    // Notificações Push
            },
            layoutPreference: 'grid',  // Preferências de layout (padrão: grid)
            user: {
                name: 'John Doe',  // Nome fictício do usuário
                email: 'johndoe@example.com'  // E-mail fictício
            }
        };
    },
    methods: {
        loadSettings() {
            const savedTheme = localStorage.getItem('isDarkTheme');
            if (savedTheme !== null) {
                this.isDarkTheme = savedTheme === 'true';
                this.$vuetify.theme.dark = this.isDarkTheme;
            }
        },
        toggleDarkMode() {
            // Alterna entre modo escuro e claro
            if (this.isDarkTheme) {
                this.$vuetify.theme.dark = true;
            } else {
                this.$vuetify.theme.dark = false;
            }
            localStorage.setItem('isDarkTheme', this.isDarkTheme);
        },
        
        saveSettings() {
            // Simulação de salvamento de configurações
            console.log('Configurações Salvas:', {
                darkMode: this.isDarkTheme,
                notifications: this.notifications,
                layoutPreference: this.layoutPreference,
                user: this.user
            });
            this.$vuetify.theme.dark = this.isDarkTheme; // Aplica o tema escuro ou claro
            this.$toast('Configurações salvas com sucesso!', { color: 'success' });
        }
    },
    created() {
        this.loadSettings();
    },
};
</script>

<style scoped>
.v-card-subtitle {
    font-size: 1.2rem;
    color: #607d8b;
}
</style>