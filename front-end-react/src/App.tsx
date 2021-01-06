import React from 'react';
import logo from './logo.svg';
import './App.css';
import Header from "./components/Header";
import {BrowserRouter, Route} from "react-router-dom";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";


function App() {
    return (
        <div className="App">
            <Header/>
            <BrowserRouter>
                <Route exact path="/">
                    <MainPage/>
                </Route>
                <Route exact path="/login">
                    <LoginPage/>
                </Route>
            </BrowserRouter>
        </div>
    );
}

export default App;
