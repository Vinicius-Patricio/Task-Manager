import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import PrivateRoute from "./PrivateRoutes"
import Login from "../pages/Login/Login";
import Task from "../pages/Tasks/Task";
import CreateTask from "../pages/Tasks/CreateTask";
import Header from "../components/Header";
import Footer from "../components/Footer";


export default function Router() {
    return (
        <BrowserRouter>
                <Routes>
                <Route path="/" element={<Navigate to="/login" replace />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/tasks" element={
                        <PrivateRoute>
                        <Header />
                            <Task />
                        <Footer />
                        </PrivateRoute>
                        } />
                    <Route path="/tasks/create"
                    element= {
                        <PrivateRoute>
                        <Header />
                            <CreateTask />
                        <Footer />
                        </PrivateRoute>
                    } />
                <Route path="*" element={<Navigate to="/login" replace />} />
                </Routes>
        </BrowserRouter>
    );
}