import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../styles/header.css'
import {Link, RouteComponentProps, withRouter} from "react-router-dom";

interface PieceProps extends RouteComponentProps<any> {
    cartItems?: number;
}

interface IState {
    role: null | string
}

class Header extends Component<PieceProps, IState> {

    constructor(props: PieceProps) {
        super(props);
        this.state = {
            role: null
        }

    }

    componentDidMount() {
        let item = localStorage.getItem('role');
        console.log("Header: role = " + item);
        this.setState({role: item})
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
                            <Link to={'/basket'} className="nav-link">
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
                    </ul>
                </div>
            </nav>
            //     <div className="logo">
            // <i className="material-icons">menu</i>
            // <span>Logo</span>
            //                 </div>
            //                 <div className="search_inputs">
            //                     <label htmlFor="search"/>
            //                     <label htmlFor="category"/>
            //                     <input type="text" id="search" placeholder="Search by item name or description"/>
            //                     <select id="category">
            //                         <option value="" disabled>All categories</option>
            //                         <option value="new year">New Year</option>
            //                         <option value="new year">Alcohol</option>
            //                         <option value="new year">Another category</option>
            //                     </select>
            //                 </div>
            //
            //                 <div className="navigation">
            //                     <i className="material-icons">favorite</i>
            //                     <i className="material-icons">shopping_cart</i>
            //                     {
            //                         this.props.cartItems &&
            //                         <small>{this.props.cartItems}</small>
            //                     }
            //                     <div className="links">
            //                         {/*<Link href="?">Login</Link>*/}
            //                         <Link to={"/login"}>Login</Link>
            //                         <span> | </span>
            //                         <Link to={"/register"}>Sign Up</Link>
            //                     </div>
            //                 </div>
        );
    }


}

export default withRouter(Header);