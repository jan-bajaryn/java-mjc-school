import React, {Component} from "react";
import '../styles/login.css'
import axios from "axios";
import {Link, Redirect} from "react-router-dom";


interface IProps {
}

interface IState {
    redirect?: boolean;
}

export default class LoginPage extends Component<IProps, IState> {
    private username: React.RefObject<HTMLInputElement>;
    private password: React.RefObject<HTMLInputElement>;

    // private state: IState;

    constructor(props: IProps) {
        super(props);
        // this.handleFormSubmit = this.handleFormSubmit.bind(this);
        this.username = React.createRef<HTMLInputElement>();
        this.password = React.createRef<HTMLInputElement>();

        this.state = {
            redirect: false
        }

    }

    private handleFormSubmit = async (
        event: any
    ) => {
        event.preventDefault();

        const endpoint = "http://localhost:9000/oauth/token";
        let username: string = '';
        if (this.username.current) {
            username = this.username.current.value;
        }
        let password: string = '';
        if (this.password.current) {
            password = this.password.current.value;
        }
        const user_object = {
            grant_type: 'password',
            username: username,
            password: password
        };

        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Basic Y2xpZW50aWQ6cGFzc3dvcmQ='
        }

        axios.post(
            endpoint, user_object,
            {
                headers: headers
            }
        ).then(res => {
            localStorage.setItem("authorization", res.data.token);
            localStorage.setItem("refresh_token", res.data.refresh_token);
            this.setState({
                redirect: true
            });
        }).catch((error) => {
            console.log(error);
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
                    {/*<form>*/}
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
                        <button onSubmit={() => this.handleFormSubmit(this)}>Login</button>
                    </div>
                    {
                        this.state.redirect &&
                        <Redirect to={"/login"}/>
                    }
                    {/*</form>*/}
                </div>
            </main>
        )
    }
}