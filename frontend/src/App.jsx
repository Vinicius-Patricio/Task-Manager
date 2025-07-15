import Router from "./routes/Router";
import { AuthProvider } from "./context/AuthContext";
import { Toaster } from "react-hot-toast";

export default function App() {
    return (
        <AuthProvider>
            <Router />
            <Toaster 
                position="top-right"
                toastOptions={{
                    duration: 4000,
                    style: {
                        background: '#363636',
                        color: '#fff',
                    },
                }}
            />
        </AuthProvider>
    );
}