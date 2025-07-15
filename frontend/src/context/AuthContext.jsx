import { createContext, useContext, useState, useEffect } from 'react';
import { MessageService, Messages } from '../services/messageService';

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Verifica se há um token válido no localStorage
        const token = localStorage.getItem("jwtToken");
        if (token && token.trim() !== "") {
            setIsAuthenticated(true);
            // Aqui você pode decodificar o JWT para obter informações do usuário
            // Por enquanto, vamos apenas definir como autenticado
        }
        setLoading(false);
    }, []);

    const login = (token, userData) => {
        localStorage.setItem("jwtToken", token);
        setIsAuthenticated(true);
        setUser(userData);
        MessageService.success(Messages.auth.loginSuccess);
    };

    const logout = () => {
        localStorage.removeItem("jwtToken");
        setIsAuthenticated(false);
        setUser(null);
        MessageService.success(Messages.auth.logoutSuccess);
    };

    const value = {
        isAuthenticated,
        user,
        loading,
        login,
        logout
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth deve ser usado dentro de um AuthProvider');
    }
    return context;
}
