import toast from 'react-hot-toast';

// Configurações padrão do toast
const toastConfig = {
    duration: 4000,
    position: 'top-center',
    style: {
        borderRadius: '10px',
        background: '#333',
        color: '#fff',
    },
};

// Configurações específicas para sucesso
const successConfig = {
    ...toastConfig,
    icon: '✅',
    style: {
        ...toastConfig.style,
        background: '#10b981',
        border: '1px solid #059669',
    },
};

// Configurações específicas para erro
const errorConfig = {
    ...toastConfig,
    icon: '❌',
    style: {
        ...toastConfig.style,
        background: '#ef4444',
        border: '1px solid #dc2626',
    },
};

// Configurações específicas para aviso
const warningConfig = {
    ...toastConfig,
    icon: '⚠️',
    style: {
        ...toastConfig.style,
        background: '#f59e0b',
        border: '1px solid #d97706',
    },
};

// Configurações específicas para informação
const infoConfig = {
    ...toastConfig,
    icon: 'ℹ️',
    style: {
        ...toastConfig.style,
        background: '#3b82f6',
        border: '1px solid #2563eb',
    },
};

export const MessageService = {
    // Mensagens de sucesso
    success: (message, options = {}) => {
        toast.success(message, { ...successConfig, ...options });
    },

    // Mensagens de erro
    error: (message, options = {}) => {
        toast.error(message, { ...errorConfig, ...options });
    },

    // Mensagens de aviso
    warning: (message, options = {}) => {
        toast(message, { ...warningConfig, ...options });
    },

    // Mensagens de informação
    info: (message, options = {}) => {
        toast(message, { ...infoConfig, ...options });
    },

    // Mensagem de carregamento
    loading: (message = 'Carregando...') => {
        return toast.loading(message, toastConfig);
    },

    // Dismiss toast específico
    dismiss: (toastId) => {
        toast.dismiss(toastId);
    },

    // Dismiss todos os toasts
    dismissAll: () => {
        toast.dismiss();
    },
};

// Mensagens padrão para operações comuns
export const Messages = {
    // Mensagens de autenticação
    auth: {
        loginSuccess: 'Login realizado com sucesso!',
        loginError: 'Erro ao fazer login. Verifique suas credenciais.',
        logoutSuccess: 'Logout realizado com sucesso!',
        unauthorized: 'Você não tem permissão para acessar este recurso.',
    },

    // Mensagens de tarefas
    tasks: {
        createSuccess: 'Tarefa criada com sucesso!',
        createError: 'Erro ao criar tarefa.',
        updateSuccess: 'Tarefa atualizada com sucesso!',
        updateError: 'Erro ao atualizar tarefa.',
        deleteSuccess: 'Tarefa excluída com sucesso!',
        deleteError: 'Erro ao excluir tarefa.',
        loadError: 'Erro ao carregar tarefas.',
    },

    // Mensagens de usuário
    users: {
        createSuccess: 'Usuário criado com sucesso!',
        createError: 'Erro ao criar usuário.',
        updateSuccess: 'Usuário atualizado com sucesso!',
        updateError: 'Erro ao atualizar usuário.',
        deleteSuccess: 'Usuário excluído com sucesso!',
        deleteError: 'Erro ao excluir usuário.',
    },

    // Mensagens gerais
    general: {
        networkError: 'Erro de conexão. Verifique sua internet.',
        serverError: 'Erro no servidor. Tente novamente mais tarde.',
        validationError: 'Por favor, verifique os dados informados.',
        unexpectedError: 'Erro inesperado. Tente novamente.',
    },
};

// Função helper para tratar erros da API
export const handleApiError = (error) => {
    console.error('API Error:', error);

    if (error.response) {
        // Erro com resposta do servidor
        const { status, data } = error.response;
        
        switch (status) {
            case 400:
                MessageService.error(data.message || Messages.general.validationError);
                break;
            case 401:
                MessageService.error(Messages.auth.unauthorized);
                break;
            case 403:
                MessageService.error('Acesso negado.');
                break;
            case 404:
                MessageService.error('Recurso não encontrado.');
                break;
            case 500:
                MessageService.error(Messages.general.serverError);
                break;
            default:
                MessageService.error(data.message || Messages.general.unexpectedError);
        }
    } else if (error.request) {
        // Erro de rede
        MessageService.error(Messages.general.networkError);
    } else {
        // Erro inesperado
        MessageService.error(Messages.general.unexpectedError);
    }
};

// Função helper para operações com loading
export const withLoading = async (loadingMessage, operation) => {
    const loadingToast = MessageService.loading(loadingMessage);
    
    try {
        const result = await operation();
        MessageService.dismiss(loadingToast);
        return result;
    } catch (error) {
        MessageService.dismiss(loadingToast);
        handleApiError(error);
        throw error;
    }
}; 