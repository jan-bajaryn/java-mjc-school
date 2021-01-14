import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../styles/header.css'
import {Link, RouteComponentProps, withRouter} from "react-router-dom";
import LocalStorageHelper from "../services/LocalStorageHelper";

interface PieceProps extends RouteComponentProps<any> {
    cartItems?: number;
}

interface IState {
    role: null | string,
    username: string | null
}

class Header extends Component<PieceProps, IState> {

    constructor(props: PieceProps) {
        super(props);
        this.state = {
            role: null,
            username: null
        }

    }

    componentDidMount() {
        let item = localStorage.getItem('role');
        let username = localStorage.getItem('username');
        console.log("Header: role = " + item);
        this.setState({role: item, username: username});
    }

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light justify-content-center bg-white fixed-top">
                <Link to={'/'} className="navbar-brand" href="#">Home</Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        {
                            this.state.role === 'ADMIN' &&
                            <li className="nav-item active">
                                <Link className="nav-link"
                                      to={'/admin/certificates'}>
                                    Certificates
                                    <span className="sr-only">(current)</span>
                                </Link>
                            </li>
                        }
                    </ul>

                    <ul className="navbar-nav">

                        <li className={'nav-item'}>
                            <Link to={'/cabinet'} className="nav-link disabled">
                                {
                                    this.state.username &&
                                    <span>{this.state.username}</span>
                                }
                            </Link>
                        </li>

                        <li className={'nav-item'}>
                            <Link to={'/basket'} className="nav-link disabled">
                                <i className="material-icons">shopping_cart</i>
                                {
                                    this.props.cartItems &&
                                    <small>{this.props.cartItems}</small>
                                }
                            </Link>
                        </li>
                        <li className={'nav-item'}>
                            <Link to={"/login"} className="nav-link">Login</Link>
                        </li>
                        <li className={'nav-item'}>
                            <Link to={"/register"} className="nav-link">Sign Up</Link>
                        </li>
                        {
                            LocalStorageHelper.isLogged() &&
                            <li className={'nav-item'}>
                                <Link to={"/logout"} onClick={(event) => {
                                    event.preventDefault();
                                    LocalStorageHelper.logout();
                                    this.props.history.push('/login');
                                }} className="nav-link">
                                    Log out
                                </Link>
                            </li>
                        }

                    </ul>
                </div>
            </nav>
        );
    }


}

export default withRouter(Header);