import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json', 
    },
});


api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
api.interceptors.response.use(
    (response) => response,
    (error) => {
        // Só redireciona se não estiver na página de login
        if (error.response?.status === 401 && window.location.pathname !== '/login') {
            window.location.href = '/login';
            localStorage.removeItem('jwtToken');
        }
        return Promise.reject(error);
    }
);

export default api;