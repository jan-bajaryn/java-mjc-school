import React from 'react';
import logo from './logo.svg';
import './App.css';
import Header from "./components/Header";
import {BrowserRouter, Route} from "react-router-dom";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import CertificatesAdminPage from "./pages/admin/CertificatesAdminPage";
import './Interceptors'

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Route exact path="/">
                    <MainPage/>
                </Route>
                <Route exact path="/login">
                    <LoginPage/>
                </Route>
                <Route exact path={'/admin/certificates'}>
                    <CertificatesAdminPage/>
                </Route>
                <Route exact path="/register">
                    <RegisterPage/>
                </Route>
            </BrowserRouter>
        </div>
    );
}

export default App;
