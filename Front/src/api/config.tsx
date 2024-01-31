import axios from 'axios';
import useAuth from '../hooks/authState';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_URL,
    timeout: 5000,
});

api.interceptors.response.use(response => {
    return response;
 }, error => {
   if (error.response.status === 401) {
        console.log('Unauthorized');
        useAuth().logout();
   }
   return error;
 });

export default api;