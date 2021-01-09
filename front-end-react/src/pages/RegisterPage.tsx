import React, {Component, RefObject} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import '../styles/register.css'
import axios from "axios";

interface IProps extends RouteComponentProps<any> {
}


class RegisterPage extends Component<IProps, any> {

    private username = React.createRef<HTMLInputElement>();
    private password = React.createRef<HTMLInputElement>();
    private repeat_password = React.createRef<HTMLInputElement>();
    private first_name = React.createRef<HTMLInputElement>();

    private handleSubmit = async (
        e: React.FormEvent<HTMLFormElement>
    ): Promise<void> => {
        e.preventDefault();
        const endpoint = "http://localhost:8080/users";

        let username: string = this.exctractRef(this.username);
        let password: string = this.exctractRef(this.password);
        let repeat_password: string = this.exctractRef(this.repeat_password);
        let first_name: string = this.exctractRef(this.first_name);

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
        ).then(res => {
            this.props.history.push("/login");
        }).catch((error) => {
            console.log("login error = " + error);
        });

    };

    private isFormValid(username: string, password: string, repeat_password: string, first_name: string) {
        return username !== '' && password !== '' && repeat_password !== '' && first_name !== '' && password === repeat_password;
    }


    private exctractRef(ref: RefObject<HTMLInputElement>) {
        if (ref.current) {
            return ref.current.value;
        }
        return '';
    }

    render() {
        return (
            <main>
                <div className="register_container">
                    <div className="logo_container">
                        <span>Register</span>
                    </div>
                    <div className="inputs_container">
                        <form onSubmit={this.handleSubmit} noValidate={true}>
                            <div className="first_column">
                                <div className="item">
                                    <label htmlFor="login_name">Login Name</label>
                                    <input type="text" id="login_name" ref={this.username}/>
                                </div>
                                <div className="item">
                                    <label htmlFor="password">Password</label>
                                    <input type="password" id="password" ref={this.password}/>
                                </div>
                            </div>

                            <div className="second_column">
                                <div className="item">
                                    <label htmlFor="first_name">First Name</label>
                                    <input type="text" id="first_name" ref={this.first_name}/>
                                </div>
                                <div className="item">
                                    <label htmlFor="repeat_password">Repeat Password</label>
                                    <input type="password" id="repeat_password" ref={this.repeat_password}/>
                                </div>
                                <div className="item">
                                    <div className="btn_holder">
                                        <button className="transparent_bg">Cancel</button>
                                        <button className="green_bg">Sign Up</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </main>
        )
    }
}

export default withRouter(RegisterPage);