// src/api.js
import axios from 'axios';
import router from '@/router'; // Supondo que você use Vue Router

const api = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,  
});


// Interceptor para requisições
api.interceptors.request.use(
  (config) => {
    // Obtém o token do localStorage
    const token = localStorage.getItem('token');
    //console.log(token); // Apenas para verificar se o token está sendo obtido corretamente
    
    // Se o token existir, adiciona-o ao cabeçalho Authorization
    if (token) {
      //config.headers['Authorization'] = `Bearer ${token}`;
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    // Rejeita a promessa em caso de erro
    return Promise.reject(error);
  }
);

// Interceptor para respostas
api.interceptors.response.use((response) => {
  return response;
}, (error) => {
  if (error.response) {
    console.log(error.response.data.message);

    // Caso o erro seja 401 (não autorizado) ou 403 (proibido)
    if (error.response.status === 401 ) {
      if (error.response.data.message === 'Token expirado') {
        // Exemplo de lógica para token expirado
        alert('Sua sessão expirou. Por favor, faça login novamente.');
        localStorage.removeItem('token'); // Remova o token antigo
        // Verifica se o usuário já está na página de login antes de redirecionar
        if (router.currentRoute.path !== '/login') {
          //router.push('/login'); // Redireciona para a página de login se o usuário não estiver nela
          router.replace('/login'); //replace não mantém o histórico da navegação anterior, o que pode ser útil em casos onde você deseja limpar o histórico de navegação
        }
      } else {
        alert('Você não tem permissão para acessar este recurso.');
        router.push('/login'); // Redirecione para a página de login
      }
    }
  }
  return Promise.reject(error);
});
export default api;