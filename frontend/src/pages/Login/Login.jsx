import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { MessageService, Messages } from "../../services/messageService";
import api from "../../api/axiosConfig";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (!email || !password) {
            MessageService.warning("Por favor, preencha todos os campos.");
            return;
        }

        setLoading(true);

        try {
            const response = await api.post("/auth/login", { email, password });
            login(response.data.token, response.data.user);
            navigate("/tasks");
        } catch (error) {

            console.log("Status do erro:", error.response?.status);
            
            // Tratamento manual do erro
            if (error.response) {
                const { status, data } = error.response;
                
                switch (status) {
                    case 400:
                        MessageService.error(data.message || "Dados inválidos");
                        break;
                    case 401:
                        MessageService.error("Credenciais inválidas");
                        break;
                    case 500:
                        MessageService.error("Erro no servidor");
                        break;
                    default:
                        MessageService.error("Erro inesperado");
                }
            } else if (error.request) {
                MessageService.error("Erro de conexão. Verifique sua internet.");
            } else {
                MessageService.error("Erro inesperado");
            }
            
            // Garante que não há redirecionamento automático
            return false;
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#1a0033] to-[#2d0036]">
            <div className="bg-black bg-opacity-80 rounded-2xl shadow-2xl p-8 w-full max-w-md">
                <h1 className="text-3xl text-purple-200 font-bold mb-8 text-center tracking-wide font-['Roboto',Arial,Helvetica,sans-serif]">Login</h1>
                <form onSubmit={handleSubmit} className="flex flex-col gap-5">
                    <input
                        type="email"
                        placeholder="E-mail"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        className="rounded-lg px-4 py-3 bg-[#2d0036] text-white placeholder-purple-300 focus:outline-none focus:ring-2 focus:ring-purple-600 transition"
                    />
                    <input
                        type="password"
                        placeholder="Senha"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        className="rounded-lg px-4 py-3 bg-[#2d0036] text-white placeholder-purple-300 focus:outline-none focus:ring-2 focus:ring-purple-600 transition"
                    />
                    <button
                        type="submit"
                        disabled={loading}
                        className="bg-gradient-to-r from-purple-800 to-purple-600 text-white font-bold py-3 rounded-lg shadow hover:from-purple-700 hover:to-purple-500 transition disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                        {loading ? "Entrando..." : "Entrar"}
                    </button>
                </form>
            </div>
        </div>
    );
}