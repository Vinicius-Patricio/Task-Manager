import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Header() {
    const navigate = useNavigate();
    const { logout } = useAuth();
    
    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    const handleCreateTask = () => {
        navigate("/tasks/create");
    };

    return (
        <header className="w-full bg-gradient-to-r from-[#2d0036] to-[#1a0033] py-4">
            <div className="flex justify-between items-center px-4">
                <div className="text-purple-200 font-semibold text-lg">
                    Task Manager
                </div>
                <div className="flex gap-2">
                    <button
                        onClick={handleCreateTask}
                        className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-lg transition-colors">
                        + Novo
                    </button>
                    <button
                        onClick={handleLogout}
                        className="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-lg transition-colors">
                        Sair
                    </button>
                </div>
            </div>
        </header>
    );
}