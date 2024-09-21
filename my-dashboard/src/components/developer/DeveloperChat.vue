<template>
  <v-container>
    <!-- Caixa de Seleção para Projetos e Modelos (em linha) -->
    <v-row>
      <v-col cols="12" md="6">
        <v-select v-model="selectedProject" :items="projects" item-text="name" item-value="id"
          label="Selecione o Projeto" outlined dense clearable @change="fetchChatHistory"></v-select>
      </v-col>

      <v-col cols="12" md="6">
        <v-select v-model="selectedModel" :items="models" item-text="name" item-value="id" label="Selecione o Modelo"
          outlined dense></v-select>
      </v-col>
    </v-row>

    <!-- Histórico das conversas -->
    <v-row>
      <v-col>
        <v-list dense>
          <v-list-item v-for="(message, index) in chatHistory" :key="index">
            <v-list-item-content>
              <v-list-item-title :class="message.sender === 'user' ? 'user-message' : 'bot-message'">
                
                <strong :style="message.sender === 'user' ? 'color:green' : 'color:red'">
                  {{ message.sender === 'user' ? 'Você:' : 'Bot:' }}
                </strong>
                
                <template v-if="message.isCode">
                  <v-card class="pa-3 mb-2">
                    <pre><code>{{ message.message }}</code></pre>
                  </v-card>
                </template>
                <template v-else>
                  <span class="message-text">{{ message.message }}</span>
                </template>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>

    <!-- Input de mensagem e botão enviar -->
    <v-row v-if="selectedModel" class="mt-5">
      <v-col>
        <v-textarea v-model="userMessage" label="Digite sua mensagem" outlined rows="2"></v-textarea>
      </v-col>

      <v-col cols="auto">
        <v-btn color="primary" @click="sendMessage">Enviar</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "DeveloperChat",
  data() {
    return {
      projects: [],
      models: [], // Lista de modelos para o projeto selecionado
      selectedProject: null,
      selectedModel: null, // Modelo selecionado
      userMessage: "",
      chatHistory: [], // Histórico vai ser carregado do backend
    };
  },
  methods: {
    fetchProjects() {
      this.$api.get('/projects/isActive').then(response => {
        this.projects = response.data;
      });
    },
    fecthModels() {
      this.$api.get(`/chat/models`).then(response => {
        this.models = response.data;

        // Pré-seleciona o primeiro modelo da lista
        if (this.models.length > 0 && this.selectedModel == null) {
          this.selectedModel = this.models[0].id;
        }
      });
    },
    fetchChatHistory() {

      const messageData = {
        projectId: this.selectedProject,
        modelId: this.selectedModel, // Envia o modelo selecionado junto
        message: this.userMessage,
      };



      // Carrega o histórico de chat do projeto selecionado
      this.$api.post(`/chat/history`, messageData).then(response => {
        this.chatHistory = response.data.map(message => {
          // Verifica se a mensagem contém código e define `isCode`
          const isCode = message.message.includes("```");
          return {
            ...message,
            isCode: isCode, // Adiciona a propriedade `isCode` ao histórico
            message: isCode ? message.message.replace(/```/g, "") : message.message,
          };
        });
      });

    },
    sendMessage() {
      if (this.userMessage.trim() !== "") {
        const messageData = {
          projectId: this.selectedProject,
          modelId: this.selectedModel, // Envia o modelo selecionado junto
          message: this.userMessage,
        };

        // Envia a mensagem ao backend
        this.$api.post(`/chat/message`, messageData).then(response => {
          // Adiciona a mensagem do usuário no histórico
          this.chatHistory.push({
            sender: "user",
            message: this.userMessage,
          });

          // Adiciona a resposta do bot ao histórico, verificando se contém código
          const botMessage = response.data;
          const isCode = botMessage.includes("```");

          this.chatHistory.push({
            sender: "bot",
            message: isCode ? botMessage.replace(/```/g, "") : botMessage,
            isCode: isCode,
          });

          // Limpa o campo de mensagem
          this.userMessage = "";
        });
      }
    },
  },
  created() {
    this.fetchProjects();
    this.fecthModels();
    this.fetchChatHistory();
  },
};
</script>

<style scoped>
.v-list {
  height: 300px;
  /* altura desejada */
  overflow-y: auto;
  /* permite rolagem vertical */
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 8px;
}

.message-text {
  white-space: pre-wrap;
  /* permite quebra de linha */
  word-wrap: break-word;
  /* quebra palavras longas */
}
</style>
