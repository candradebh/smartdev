<template>
    <v-container>
        <!-- Formulário de Atualização do Perfil -->
        <v-row>
            <v-col cols="12" md="6">
                <v-card>
                    <v-card-title>Dados de Perfil</v-card-title>
                    <v-card-text>
                        <v-form ref="profileForm" v-model="valid">
                            <v-text-field v-model="profile.name" label="Nome" :rules="[rules.required]" required></v-text-field>
                            <v-text-field v-model="profile.email" label="E-mail" :rules="[rules.required, rules.email]" required></v-text-field>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn color="primary" @click="updateProfile" :disabled="!valid">Atualizar Perfil</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>

        <!-- Formulário de Troca de Senha -->
        <v-row>
            <v-col cols="12" md="6">
                <v-card>
                    <v-card-title>Trocar Senha</v-card-title>
                    <v-card-text>
                        <v-form ref="passwordForm" v-model="validPassword">
                            <v-text-field v-model="password.oldPassword" label="Senha Atual" :rules="[rules.required]" type="password" required></v-text-field>
                            <v-text-field v-model="password.newPassword" label="Nova Senha" :rules="[rules.required, rules.min(6)]" type="password" required></v-text-field>
                            <v-text-field v-model="password.confirmNewPassword" label="Confirmar Nova Senha" :rules="[rules.required, matchPassword]" type="password" required></v-text-field>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn color="secondary" @click="changePassword" :disabled="!validPassword">Trocar Senha</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'ProfilePage',
    data() {
        return {
            valid: false,
            validPassword: false,
            profile: {
                name: '',
                email: ''
            },
            password: {
                oldPassword: '',
                newPassword: '',
                confirmNewPassword: ''
            },
            rules: {
                required: value => !!value || 'Campo obrigatório.',
                email: value => {
                    const pattern = /^[^@]+@[^@]+\.[^@]+$/;
                    return pattern.test(value) || 'E-mail inválido.';
                },
                min: length => value => (value.length >= length) || `Mínimo de ${length} caracteres.`,
            }
        };
    },
    mounted() {
        // Tentar carregar os dados do usuário a partir do localStorage
        const storedUser = JSON.parse(localStorage.getItem('user'));

        if (storedUser) {
            this.profile.name = storedUser.name;
            this.profile.email = storedUser.email;
        }

        // Obter os dados do usuário logado ao carregar a página para garantir que estejam sincronizados
        this.$api.get('/auth/profile')
            .then(response => {
                const user = response.data;
                delete user.password;

                this.profile.name = user.name;
                this.profile.email = user.email;
                
                // Atualiza o localStorage com os dados mais recentes do backend
                localStorage.setItem('user', JSON.stringify(user));
            })
            .catch(error => {
                console.error('Erro ao carregar perfil:', error);
            });
    },
    methods: {
        updateProfile() {
            this.$refs.profileForm.validate();
            if (this.valid) {
                this.$api.put('/auth/profile', this.profile)
                    .then(response => {
                        const updatedUser = response.data;
                        delete updatedUser.password;
                        localStorage.setItem('user', JSON.stringify(updatedUser));
                    })
                    .catch(error => {
                        console.error('Erro ao atualizar perfil:', error);
                    });
            }
        },
        changePassword() {
            this.$refs.passwordForm.validate();
            if (this.validPassword && this.password.newPassword === this.password.confirmNewPassword) {
                this.$api.post('/auth/change-password', {
                    oldPassword: this.password.oldPassword,
                    newPassword: this.password.newPassword
                })
                    .then(response => {
                        console.log('Senha alterada com sucesso', response.data);
                    })
                    .catch(error => {
                        console.error('Erro ao trocar senha:', error);
                    });
            }
        },
        matchPassword() {
            return this.password.newPassword === this.password.confirmNewPassword || 'As senhas não coincidem.';
        }
    }
}
</script>
