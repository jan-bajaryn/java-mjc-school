import React, {Component} from 'react';
import '../styles/header.css'
import {Link, RouteComponentProps, withRouter} from "react-router-dom";
import Certificate from "../entity/Certificate";

interface PieceProps extends RouteComponentProps<any> {
    cartItems?: number;
}

interface IState {
}

class Header extends Component<PieceProps, IState> {

    constructor(props: PieceProps) {
        super(props);

    }

    componentDidMount() {
        let item = localStorage.getItem('cart');
        if (item == null) {
            this.setState({cartItems: 0})
        } else {
            let parse = JSON.parse(item);
            this.setState({cartItems: parse.length})
        }
    }

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
                        <option value="" disabled>All categories</option>
                        <option value="new year">New Year</option>
                        <option value="new year">Alcohol</option>
                        <option value="new year">Another category</option>
                    </select>
                </div>

                <div className="navigation">
                    <i className="material-icons">favorite</i>
                    <i className="material-icons">shopping_cart</i>
                    {
                        this.props.cartItems &&
                        <small>{this.props.cartItems}</small>
                    }
                    <div className="links">
                        {/*<Link href="?">Login</Link>*/}
                        <Link to={"/login"}>Login</Link>
                        <span> | </span>
                        <Link to={"/register"}>Sign Up</Link>
                    </div>
                </div>
            </header>
        );
    }

}

export default withRouter(Header);