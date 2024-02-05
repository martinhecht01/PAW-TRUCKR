import axios from 'axios';
import { getClaims, refreshToken } from './userApi';

// const API_URL = 'http://localhost:8080/api';
const API_URL = 'http://pawserver.it.itba.edu.ar/paw-2023a-08/api'

const api = axios.create({
    baseURL: API_URL,
    timeout: 5000,
});

api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalConfig = error.config;    

    if (error.response) {
      if ((error.response.status === 401) && !originalConfig._retry && getClaims()) {
        
        const token = localStorage.getItem('refresh')

        if(!token || !tokenValid(token)){
          localStorage.clear();
          window.location.href = '/login'
        }

        originalConfig._retry = true;
        await refreshToken();
        return api(originalConfig);
      }

      if (error.response.status === 500) {
        window.location.href = '/500'
        return;
      }

      if (error.response.status === 404) {
        window.location.href = '/404'
        return;
      }
      
    }
    
    return Promise.reject(error);
  }
);

export function tokenValid(token: string){
  const parts = token.split('.');
  if(parts.length !== 3) {
      throw new Error('Invalid token');
  }

  const payload = parts[1].replace(/-/g, '+').replace(/_/g, '/');
  const decodedPayload = atob(decodeURIComponent(payload));

  const jwtData = JSON.parse(decodedPayload);

  console.log(jwtData.exp)

  if(jwtData.exp < Date.now() / 1000){
    return false;
  }
  
  return true;
}

export default api;