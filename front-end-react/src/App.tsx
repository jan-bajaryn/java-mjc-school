import React from 'react';
import logo from './logo.svg';
import './App.css';
import Header from "./components/Header";
import {BrowserRouter, Route} from "react-router-dom";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";


function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Header/>
                <Route exact path="/">
                    <MainPage/>
                </Route>
                <Route exact path="/login">
                    <LoginPage/>
                </Route>
                <Route exact path="/register">
                    <RegisterPage/>
                </Route>
            </BrowserRouter>
        </div>
    );
}

export default App;
