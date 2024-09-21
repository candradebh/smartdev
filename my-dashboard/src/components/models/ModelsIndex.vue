<template>
    <v-container>
        <v-data-table :headers="headers" :items="listModel" class="elevation-1">
            <template v-slot:top>
                <v-toolbar flat>
                    <v-toolbar-title>Modelos de IA</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" @click="openForm(null)">Criar</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:[`item.modified_at`]="{ item }">
                {{ item.modified_at | formatDate }}
            </template>
            <template v-slot:[`item.size`]="{ item }">
                {{ formatSize(item.size) }}
            </template>
            <template v-slot:[`item.actions`]="{ item }">
                <v-icon small @click="openForm(item)">mdi-pencil</v-icon>
                <v-icon small @click="confirmDelete(item)">mdi-delete</v-icon>
            </template>
        </v-data-table>

        <!-- Delete Confirmation Dialog -->
        <v-dialog v-model="dialogDelete" max-width="400px">
            <v-card>
                <v-card-title class="text-h5">Atenção</v-card-title>
                <v-card-text>Deseja deletar esse registro?</v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="green darken-1" text @click="deleteModel">Sim</v-btn>
                    <v-btn color="red darken-1" text @click="dialogDelete = false">Não</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>

export default {
    name: 'ModelIndex',
    data() {
        return {
            apiIndex: '/chat/models',
            routeIndex: '/models',
            listModel: [],
            selectedModel: null,
            dialogDelete: false,
            headers: [
                { text: 'ID', value: 'id' },
                { text: 'Name', value: 'name' },
                { text: 'Description', value: 'model' },
                { text: 'Data', value: 'modified_at' },
                { text: 'Tamanho', value: 'size' },
                { text: 'digest', value: 'digest' },
                { text: 'Ações', value: 'actions', sortable: false }
            ]
        };
    },
    methods: {
        fetchModels() {
            this.$api.get(this.apiIndex).then(response => {
                this.listModel = response.data;
            });
        },
        openForm(model) {
            this.$router.push({ name: 'ModelForm', params: { itemModel: model } })
        },
        confirmDelete(model) {
            this.selectedModel = model;
            this.dialogDelete = true;
        },
        deleteModel() {
            this.$api.delete(`${this.apiIndex}/${this.selectedModel.id}`).then(() => {
                this.fetchModels();
                this.dialogDelete = false;
            });
        },
        formatSize(sizeInBytes) {
            if (!sizeInBytes) return '0 GB';
            let sizeInGB = sizeInBytes / (1024 * 1024 * 1024); // Converter bytes para gigabytes
            return sizeInGB.toFixed(2) + ' GB'; // Retornar com 2 casas decimais
        },
        // Método para navegar para a página de features
        goToDetails(model) {
            this.$router.push(`${this.routeIndex}/${model.id}/details`);
        }
    },
    created() {
        this.fetchModels();
    }
};
</script>