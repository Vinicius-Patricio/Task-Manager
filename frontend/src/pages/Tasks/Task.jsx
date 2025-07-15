import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../../api/axiosConfig";
import { MessageService, Messages, withLoading } from "../../services/messageService";

const Task = () => {
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [filter, setFilter] = useState('TODAS');
    const navigate = useNavigate();

    // Função para marcar tarefa como concluída
    const handleCompleteTask = async (taskId) => {
        try {
            await withLoading("Atualizando tarefa...", async () => {
                await axios.patch(`/tasks/${taskId}`, { status: "CONCLUIDA" });
                MessageService.success(Messages.tasks.updateSuccess);
                // Recarrega a lista de tarefas
                const response = await axios.get("/tasks");
                setTasks(response.data);
            });
        } catch (error) {
            console.error("Erro ao atualizar tarefa:", error);
        }
    };

    // Função para excluir tarefa
    const handleDeleteTask = async (taskId) => {
        const confirmed = window.confirm("Tem certeza que deseja excluir esta tarefa?");
        
        if (!confirmed) {
            MessageService.info("Operação cancelada.");
            return;
        }

        try {
            await withLoading("Excluindo tarefa...", async () => {
                await axios.delete(`/tasks/${taskId}`);
                MessageService.success(Messages.tasks.deleteSuccess);
                // Recarrega a lista de tarefas
                const response = await axios.get("/tasks");
                setTasks(response.data);
            });
        } catch (error) {
            console.error("Erro ao excluir tarefa:", error);
        }
    };

    // Função para editar tarefa (redireciona para página de edição)
    const handleEditTask = (taskId) => {
        // Por enquanto, apenas mostra uma mensagem
        MessageService.info("Funcionalidade de edição em desenvolvimento!");
        // TODO: Implementar redirecionamento para página de edição
        // navigate(`/tasks/edit/${taskId}`);
    };

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                await withLoading("Carregando tarefas...", async () => {
                    const response = await axios.get("/tasks");
                    console.log("Tarefas recebidas:", response.data);
                    setTasks(response.data);
                });
            } catch (error) {
                // O erro já é tratado pelo withLoading
                console.error("Erro ao buscar tarefas:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchTasks();
    }, []);

    if (loading) return <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#1a0033] to-[#2d0036] text-white text-xl">Carregando tarefas...</div>;

    // Filtrar tarefas baseado no filtro selecionado
    const filteredTasks = tasks.filter(task => {
        if (filter === 'TODAS') return true;
        return task.status?.toUpperCase() === filter;
    });

    // Função para obter a cor do status
    const getStatusColor = (status) => {
        switch (status?.toUpperCase()) {
            case 'ALTA':
                return 'bg-red-500 text-white';
            case 'MEDIA':
                return 'bg-yellow-500 text-yellow-900';
            case 'BAIXA':
                return 'bg-green-500 text-white';
            default:
                return 'bg-green-500 text-white';
                
        }
    };

    // Função para obter a cor da prioridade
    const getPriorityColor = (priority) => {
        switch (priority?.toUpperCase()) {
            case 'PENDENTE':
                return 'bg-yellow-500 text-yellow-900';
            case 'EM_ANDAMENTO':
                return 'bg-blue-500 text-blue-900';
            case 'CONCLUIDA':
                return 'bg-green-500 text-green-900';
            case 'CANCELADA':
                return 'bg-red-500 text-red-900';
            default:
                return 'bg-gray-500 text-gray-900';
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-[#1a0033] to-[#2d0036] p-6">
            <div className="max-w-7xl mx-auto">
                <div className="bg-black bg-opacity-80 rounded-2xl shadow-2xl p-8">
                    <div className="flex justify-between items-center mb-8">
                        <h2 className="text-3xl text-purple-200 font-bold tracking-wide font-['Roboto',Arial,Helvetica,sans-serif]">
                            Minhas Tarefas
                        </h2>
                        <div className="flex items-center space-x-4">
                            <div className="text-purple-200 text-sm">
                                {filter === 'TODAS' ? `Total: ${tasks.length} tarefas` : `${filteredTasks.length} de ${tasks.length} tarefas`}
                            </div>
                            <button
                                onClick={() => navigate("/tasks/create")}
                                className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg font-semibold transition-colors flex items-center space-x-2"
                            >
                                <span>+ Nova Tarefa</span>
                            </button>
                        </div>
                    </div>

                    {/* Filtros */}
                    <div className="mb-6 flex flex-wrap gap-2">
                        {['TODAS', 'PENDENTE', 'EM ANDAMENTO', 'CONCLUIDA', 'CANCELADA'].map((status) => (
                            <button
                                key={status}
                                onClick={() => setFilter(status)}
                                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                                    filter === status
                                        ? 'bg-purple-600 text-white'
                                        : 'bg-purple-800 text-purple-200 hover:bg-purple-700'
                                }`}
                            >
                                {status === 'TODAS' ? 'Todas' : status.replace('_', ' ')}
                            </button>
                        ))}
                    </div>

                    {filteredTasks.length === 0 ? (
                        <div className="text-center py-12">
                            <p className="text-purple-200 text-lg">
                                {tasks.length === 0 ? 'Nenhuma tarefa encontrada.' : `Nenhuma tarefa com status "${filter}".`}
                            </p>
                            <p className="text-purple-300 text-sm mt-2">
                                {tasks.length === 0 ? 'Crie sua primeira tarefa para começar!' : 'Tente outro filtro ou crie uma nova tarefa!'}
                            </p>
                        </div>
                    ) : (
                        <div className="overflow-x-auto">
                            <table className="w-full text-left">
                                <thead className="bg-gradient-to-r from-purple-900 to-purple-800 text-white">
                                    <tr>
                                        <th className="px-6 py-4 font-semibold">Título</th>
                                        <th className="px-6 py-4 font-semibold">Descrição</th>
                                        <th className="px-6 py-4 font-semibold">Status</th>
                                        <th className="px-6 py-4 font-semibold">Prioridade</th>
                                        <th className="px-6 py-4 font-semibold">Criado por</th>
                                        <th className="px-6 py-4 font-semibold">Data Criação</th>
                                        <th className="px-6 py-4 font-semibold">Ações</th>
                                    </tr>
                                </thead>
                                <tbody className="text-white">
                                    {filteredTasks.map((task, index) => (
                                        <tr 
                                            key={task.id} 
                                            className={`border-b border-purple-800 hover:bg-purple-900/30 transition-colors ${
                                                index % 2 === 0 ? 'bg-purple-900/10' : 'bg-purple-900/5'
                                            }`}
                                        >
                                            <td className="px-6 py-4">
                                                <div className={`font-semibold text-purple-200 ${task.status === 'CONCLUIDA' ? 'line-through' : 'no-underline'}`}>
                                                    {task.title}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4">
                                                <div className="text-gray-300 max-w-xs truncate" title={task.description}>
                                                    {task.description || 'Sem descrição'}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4">
                                                <span className={`px-3 py-1 rounded-full text-xs font-semibold no-underline ${getPriorityColor(task.priority)}`}>
                                                    {task.priority || 'N/A'}
                                                </span>
                                            </td>
                                            <td className="px-6 py-4">
                                                <span className={`px-3 py-1 rounded-full text-xs font-semibold ${task.status === 'EM_ANDAMENTO' ? 'no-underline' : ''} ${getStatusColor(task.status)}`}>
                                                    {task.status || 'N/A'}
                                                </span>
                                            </td>
                                            <td className="px-6 py-4">
                                                <div className="text-gray-300">{task.userName || 'N/A'}</div>
                                            </td>
                                            <td className="px-6 py-4">
                                                <div className="text-gray-300 text-sm">
                                                    {task.createdAt ? new Date(task.createdAt).toLocaleDateString('pt-BR') : 'N/A'}
                                                </div>
                                            </td>
                                            <td className="px-6 py-4">
                                                <div className="flex space-x-2">
                                                    <button 
                                                        onClick={() => handleEditTask(task.id)}
                                                        className="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 rounded text-sm transition-colors"
                                                        title="Editar"
                                                    >
                                                        Editar
                                                    </button>
                                                    {task.status !== 'CONCLUIDA' && (
                                                        <button 
                                                            onClick={() => handleCompleteTask(task.id)}
                                                            className="bg-green-600 hover:bg-green-700 text-white px-3 py-1 rounded text-sm transition-colors"
                                                            title="Marcar como concluída"
                                                        >
                                                            Concluir
                                                        </button>
                                                    )}
                                                    <button 
                                                        onClick={() => handleDeleteTask(task.id)}
                                                        className="bg-red-600 hover:bg-red-700 text-white px-3 py-1 rounded text-sm transition-colors"
                                                        title="Excluir"
                                                    >
                                                        Excluir
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Task;
