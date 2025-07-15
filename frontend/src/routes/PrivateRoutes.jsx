import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function PrivateRoute({ children }) {
    const { isAuthenticated, loading } = useAuth();
    
    if (loading) {
        return <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#1a0033] to-[#2d0036] text-white text-xl">Carregando...</div>;
    }
    
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    return children;
}