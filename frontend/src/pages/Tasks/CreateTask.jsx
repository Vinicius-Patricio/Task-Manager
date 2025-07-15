import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../../api/axiosConfig";

export default function CreateTask() {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [status, setStatus] = useState("PENDENTE");
    const [priority, setPriority] = useState("MEDIA");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const userId = 7;

            await axios.post("/tasks", {
                title,
                description,
                status,
                priority,
                userId
            });

            navigate("/tasks");
        } catch (err) {
            setError("Erro ao criar tarefa");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#1a0033] to-[#2d0036]">
            <div className="bg-black bg-opacity-80 rounded-2xl shadow-2xl p-8 w-full max-w-md">
                <h1 className="text-3xl text-purple-200 font-bold mb-8 text-center tracking-wide">
                    Nova Tarefa
                </h1>
                
                {error && (
                    <p className="text-pink-400 text-center font-semibold mb-4">
                        {error}
                    </p>
                )}

                <form onSubmit={handleSubmit} className="flex flex-col gap-5">
                    <input
                        type="text"
                        placeholder="Título da tarefa"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                        className="rounded-lg px-4 py-3 bg-[#2d0036] text-white placeholder-purple-300 focus:outline-none focus:ring-2 focus:ring-purple-600 transition"
                    />

                    <textarea
                        placeholder="Descrição da tarefa"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        rows="4"
                        className="rounded-lg px-4 py-3 bg-[#2d0036] text-white placeholder-purple-300 focus:outline-none focus:ring-2 focus:ring-purple-600 transition resize-none"
                    />

                    <select
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                        className="rounded-lg px-4 py-3 bg-[#2d0036] text-white focus:outline-none focus:ring-2 focus:ring-purple-600 transition"
                    >
                        <option value="PENDENTE">Pendente</option>
                        <option value="EM_ANDAMENTO">Em Andamento</option>
                        <option value="CONCLUIDA">Concluída</option>
                    </select>

                    <select
                        value={priority}
                        onChange={(e) => setPriority(e.target.value)}
                        className="rounded-lg px-4 py-3 bg-[#2d0036] text-white focus:outline-none focus:ring-2 focus:ring-purple-600 transition"
                    >
                        <option value="BAIXA">Baixa</option>
                        <option value="MEDIA">Média</option>
                        <option value="ALTA">Alta</option>
                    </select>

                    <div className="flex gap-3">
                        <button
                            type="button"
                            onClick={() => navigate("/tasks")}
                            className="flex-1 bg-gray-600 hover:bg-gray-700 text-white font-semibold py-3 rounded-lg transition"
                        >
                            Cancelar
                        </button>
                        <button
                            type="submit"
                            disabled={loading}
                            className="flex-1 bg-gradient-to-r from-green-600 to-green-700 text-white font-semibold py-3 rounded-lg shadow hover:from-green-700 hover:to-green-800 transition disabled:opacity-50"
                        >
                            {loading ? "Criando..." : "Criar Tarefa"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}