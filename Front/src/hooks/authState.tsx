import { useState, useEffect } from 'react';
import { loginUser } from '../api/userApi';

const useAuth = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [token, setToken] = useState('');

    useEffect(() => {
        const storedToken = sessionStorage.getItem('token');
        if (storedToken) {
            setToken(storedToken);
            setIsAuthenticated(true);
        }
    }, []);

    const login = (token: string) => {
        setToken(token)
        setIsAuthenticated(true)
    };

    const logout = () => {
        // Implement logout logic here
        // For example, remove user data from local storage
        sessionStorage.removeItem('token');
        setToken('');
        setIsAuthenticated(false);
    };

    return { isAuthenticated, token, login, logout };
};

export default useAuth;
