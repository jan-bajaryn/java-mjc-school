import React, {Component, RefObject} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import '../styles/register.css'
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';
import Header from "../components/Header";
import AuthorizationHandleService from "../services/AuthorizationHandleService";


interface IProps extends RouteComponentProps<any> {
}

interface IState {
    itemCount: number;
    bad_username: boolean;
    bad_password: boolean;
    bad_name: boolean;
    bad_repeat_password: boolean;
    err_msg?: string
}


class RegisterPage extends Component<IProps, IState> {

    private username = React.createRef<HTMLInputElement>();
    private password = React.createRef<HTMLInputElement>();
    private repeat_password = React.createRef<HTMLInputElement>();
    private first_name = React.createRef<HTMLInputElement>();


    constructor(props: IProps) {
        super(props);
        this.state = {
            itemCount: RegisterPage.calcItemCount(),
            bad_name: false,
            bad_password: false,
            bad_username: false,
            bad_repeat_password: false
        }
    }

    private static calcItemCount() {
        let item = localStorage.getItem('cart');
        if (item == null) {
            return null;
        } else {
            let parse = JSON.parse(item);
            return parse.length
        }
    }

    private handleSubmit = async (
        e: React.FormEvent<HTMLFormElement>
    ): Promise<void> => {
        e.preventDefault();
        const endpoint = "http://localhost:8080/users";

        let username: string = RegisterPage.exctractRef(this.username);
        let password: string = RegisterPage.exctractRef(this.password);
        let repeat_password: string = RegisterPage.exctractRef(this.repeat_password);
        let first_name: string = RegisterPage.exctractRef(this.first_name);

        if (!this.isFormValid(username, password, repeat_password, first_name)) {
            return;
        }

        let data = {
            username: username,
            password: password,
            firstName: first_name
        }

        axios.post(endpoint,
            data
        ).then(() => {
            this.props.history.push("/login");
        }).catch((error) => {
            if (error.response.data.errorCode === '40026') {
                this.setState({err_msg: 'User with so username already exists'})
            }
            if (error.response.data.errorCode === '40019') {
                this.setState({err_msg: error.response.data.message})
            }
            console.log("login error = " + error);
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.handleSubmit(e),
                () => window.location.reload()
            )
        });

    };

    private isFormValid(username: string, password: string, repeat_password: string, first_name: string): boolean {
        let result = true;
        if (!username || username === '' || username.length < 3 || username.length > 30) {
            this.setState({bad_username: true})
            result = false;
        }

        if (!password || password === '' || password.length < 4 || password.length > 30) {
            this.setState({bad_password: true});
            result = false;
        }
        if (!repeat_password || repeat_password === '' || password !== repeat_password) {
            this.setState({bad_repeat_password: true});
            result = false;
        }
        if (first_name && (/\s/.test(first_name) || first_name.length > 30)) {
            this.setState({bad_name: true});
            result = false;
        }
        return result;
    }


    private static exctractRef(ref: RefObject<HTMLInputElement>) {
        if (ref.current) {
            return ref.current.value;
        }
        return '';
    }

    render() {
        return (
            <div>
                <Header cartItems={this.state.itemCount}/>
                <main className={'mt-5 pt-5 register_container'}>
                    <div className="register_container">
                        <div className="logo_container">
                            <span>Register</span>
                        </div>
                        {
                            this.state.err_msg &&
                            <div className="alert alert-warning" role="alert">
                                {this.state.err_msg}
                            </div>
                        }
                        <div className="inputs_container">
                            <form onSubmit={this.handleSubmit} noValidate={true}>
                                <div className="first_column">
                                    <div className="item">
                                        <label htmlFor="login_name">Login Name</label>
                                        {
                                            this.state.bad_username ?
                                                <input type="text" id="login_name" ref={this.username}
                                                       className={'form-control is-invalid'}
                                                       onChange={() => this.setState({bad_username: false})}/>
                                                :
                                                <input type="text" id="login_name" ref={this.username}
                                                       className={'form-control'}/>
                                        }
                                    </div>
                                    <div className="item">
                                        <label htmlFor="password">Password</label>
                                        {
                                            this.state.bad_password ?
                                                <input type="password" id="password" ref={this.password}
                                                       className={'form-control is-invalid'}
                                                       onChange={() => this.setState({bad_password: false})}/>
                                                :
                                                <input type="password" id="password" ref={this.password}
                                                       className={'form-control'}/>
                                        }

                                    </div>
                                </div>

                                <div className="second_column">
                                    <div className="item">
                                        <label htmlFor="first_name">First Name</label>
                                        {
                                            this.state.bad_name ?
                                                <input type="text" id="first_name" ref={this.first_name}
                                                       className={'form-control is-invalid'}
                                                       onChange={() => this.setState({bad_name: false})}/>
                                                :
                                                <input type="text" id="first_name" ref={this.first_name}
                                                       className={'form-control'}/>
                                        }

                                    </div>
                                    <div className="item">
                                        <label htmlFor="repeat_password">Repeat Password</label>
                                        {
                                            this.state.bad_repeat_password ?
                                                <input type="password" id="repeat_password" ref={this.repeat_password}
                                                       className={'form-control is-invalid'}
                                                       onChange={() => this.setState({bad_repeat_password: false})}/>
                                                :
                                                <input type="password" id="repeat_password" ref={this.repeat_password}
                                                       className={'form-control'}/>
                                        }

                                    </div>
                                    <div className="item">
                                        <div className="btn_holder">
                                            <button className="transparent_bg" type={'reset'}>Cancel</button>
                                            <button className="green_bg">Sign Up</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                </main>
            </div>
        )
    }

}

export default withRouter(RegisterPage);