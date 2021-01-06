import React, {Component} from "react";
import '../styles/login.css'
import axios from "axios";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;

export default class LoginPage extends Component<any> {
    private username: React.RefObject<HTMLInputElement>;
    private password: React.RefObject<HTMLInputElement>;

    constructor() {
        super();
        // this.handleFormSubmit = this.handleFormSubmit.bind(this);
        this.username = React.createRef();
        this.password = React.createRef();
    }

    private handleFormSubmit = async (
        event: React.FormEvent<HTMLFormElement>
    ) => {
        event.preventDefault();

        const endpoint = "http://localhost:9000/oauth/token";
        const username = this.username.current.value;
        const password = this.password.current.value;
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
            // console.log("Authorities = ", res.data.authorities)
            // console.log("Authority = ", res.data.authorities[0])
            // localStorage.setItem("role", res.data.authorities[0])
            // console.log("Role = ", localStorage.getItem("role"))
            // return this.handleDashboard();
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
                    {/*</form>*/}
                </div>
            </main>
        )
    }
}