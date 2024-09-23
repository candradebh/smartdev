// src/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,  
});

api.interceptors.request.use(
  (config) => {
    // Obtém o token do localStorage
    const token = localStorage.getItem('token');
    //console.log(token); // Apenas para verificar se o token está sendo obtido corretamente
    
    // Se o token existir, adiciona-o ao cabeçalho Authorization
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
      //config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    // Rejeita a promessa em caso de erro
    return Promise.reject(error);
  }
);
export default api;