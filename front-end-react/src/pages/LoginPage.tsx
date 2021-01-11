import React, {Component} from "react";
import '../styles/login.css'
import axios from "axios";
import {withRouter, RouteComponentProps} from 'react-router-dom';
import Header from "../components/Header";
import 'bootstrap/dist/css/bootstrap.css';

interface IProps extends RouteComponentProps<any> {
}

interface IState {
    itemCount: number;
    bad_credentials: boolean;
    bad_username: boolean;
    bad_password: boolean;
}

class LoginPage extends Component<IProps, IState> {
    private username = React.createRef<HTMLInputElement>();
    private password = React.createRef<HTMLInputElement>();


    constructor(props: IProps) {
        super(props);
        this.state = {
            itemCount: this.calcItemCount(),
            bad_credentials: false,
            bad_username: false,
            bad_password: false
        }

    }

    private calcItemCount() {
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
        const endpoint = "http://localhost:9000/oauth/token";
        let username: string = '';
        if (this.username.current) {
            username = this.username.current.value;
        }
        let password: string = '';
        if (this.password.current) {
            password = this.password.current.value;
        }

        if (!this.validateForm(username, password)) {
            return;
        }
        console.log("username = " + username)
        console.log("password = " + password)

        const params = new URLSearchParams();
        params.append('grant_type', 'password');
        params.append('username', username);
        params.append('password', password);


        const headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic Y2xpZW50aWQ6cGFzc3dvcmQ='
        }

        axios.post(
            endpoint, params,
            {
                headers: headers
            }
        ).then(res => {
            localStorage.setItem("authorization", res.data.access_token);
            localStorage.setItem("refresh_token", res.data.refresh_token);
            localStorage.setItem("role", res.data.role);
            localStorage.setItem("userId", res.data.id);

            this.props.history.push("/");

        }).catch((error) => {
            if (error.response.status === 400 && error.response.data.error === 'invalid_grant') {
                this.setState({bad_credentials: true});
            }
        });
    };

    private validateForm(username: string, password: string): boolean {
        let result = true;
        if (!username || username === '' || username.length < 3 || username.length > 30) {
            this.setState({bad_username: true})
            result = false;
        }

        if (!password || password === '' || password.length < 3 || password.length > 30) {
            this.setState({bad_password: true});
            result = false;
        }
        return result;
    }


    render() {
        return (
            <div>
                <Header cartItems={this.state.itemCount}/>
                <main className={'pt-5 mt-5'}>
                    <div className="form-login">
                        <div className="logo_container">
                            <div className="logo_place">
                                <span>Logo</span>
                            </div>
                        </div>
                        <form onSubmit={this.handleSubmit} noValidate={true}>
                            <div>
                                {
                                    this.state.bad_username ?
                                        <label>
                                            <input type="text" placeholder="Login" ref={this.username}
                                                   className={'is-invalid form-control'}
                                                   onChange={event => this.setState({bad_username: false})}/>
                                            <br/>
                                            <span className={'error_message'}>Incorrect Username</span>
                                        </label>
                                        :
                                        <label>
                                            <input type="text" className={'form-control'} placeholder="Login"
                                                   ref={this.username}/>
                                        </label>
                                }
                            </div>
                            <div>
                                {
                                    this.state.bad_password ?
                                        <label>
                                            <input type="password" placeholder="Password" ref={this.password}
                                                   className={'is-invalid form-control'}
                                                   onChange={event => this.setState({bad_password: false})}/>
                                            <br/>
                                            <span className={'error_message'}>Incorrect Password</span>
                                        </label>
                                        :
                                        <label>
                                            <input type="password" className={'form-control'} placeholder="Password"
                                                   ref={this.password}/>
                                        </label>
                                }

                            </div>
                            {
                                this.state.bad_credentials &&
                                <div className="alert alert-warning" role="alert">
                                    Incorrect Login Or Password
                                </div>
                            }
                            <div>
                                <button>Login</button>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
        )
    }

}

export default withRouter(LoginPage);