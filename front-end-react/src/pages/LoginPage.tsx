import React, {Component} from "react";
import '../styles/login.css'
import axios from "axios";
import {withRouter, RouteComponentProps} from 'react-router-dom';


interface IProps extends RouteComponentProps<any> {
}

interface IState {
}

class LoginPage extends Component<IProps, IState> {
    private username = React.createRef<HTMLInputElement>();
    private password = React.createRef<HTMLInputElement>();


    constructor(props: IProps) {
        super(props);
        this.state = {
            redirect: false
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

            this.props.history.push("/");

        }).catch((error) => {
            console.log("login error = " + error);
        });
    };


    render() {
        return (
            <main>
                <div className="form-login">
                    <div className="logo_container">
                        <div className="logo_place">
                            <span>Logo</span>
                        </div>
                    </div>
                    <form onSubmit={this.handleSubmit} noValidate={true}>
                        <div>
                            <label>
                                <input type="text" placeholder="Login" ref={this.username}/>
                            </label>
                        </div>
                        <div>
                            <label>
                                <input type="password" placeholder="Password" ref={this.password}/>
                            </label>
                        </div>
                        <div>
                            <button>Login</button>
                        </div>
                    </form>
                </div>
            </main>
        )
    }

}

export default withRouter(LoginPage);