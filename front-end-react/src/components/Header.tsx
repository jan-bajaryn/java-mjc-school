import React, {Component} from 'react';
import '../styles/header.css'
import {RouteComponentProps, withRouter} from "react-router-dom";

interface PieceProps extends RouteComponentProps<any> {
}

class Header extends Component<PieceProps, any> {

    render() {
        return (
            <header>
                <div className="logo">
                    <i className="material-icons">menu</i>
                    <span>Logo</span>
                </div>
                <div className="search_inputs">
                    <label htmlFor="search"/>
                    <label htmlFor="category"/>
                    <input type="text" id="search" placeholder="Search by item name or description"/>
                    <select id="category">
                        <option value="" disabled selected>All categories</option>
                        <option value="new year">New Year</option>
                        <option value="new year">Alcohol</option>
                        <option value="new year">Another category</option>
                    </select>
                </div>

                <div className="navigation">
                    <i className="material-icons">favorite</i>
                    <i className="material-icons">shopping_cart</i>
                    <div className="links">
                        <a href="?">Login</a>
                        <span> | </span>
                        <a href="?">Sign Up</a>
                    </div>
                </div>
            </header>
        );
    }

}

export default withRouter(Header);