import axios from 'axios';
import useAuth from '../hooks/authState';
import { refreshToken } from './userApi';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_URL,
    timeout: 5000,
});

api.interceptors.response.use((response) => {
  return response
}, async function (error) {
  const originalRequest = error.config;
  
  if (error.response.status === 403 && !originalRequest._retry) {
    originalRequest._retry = true;
    await refreshToken();
    return api(originalRequest);
  } else if (error.response.status === 401) {
    useAuth().logout();
  }

  return Promise.reject(error);
});

export default api;