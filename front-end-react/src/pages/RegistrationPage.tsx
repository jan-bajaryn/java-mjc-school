import {Component} from "react";
import {withRouter} from "react-router-dom";

class RegistrationPage extends Component<any, any> {
    render() {
        return (
            <main>

                <div className="register_container">
                    <div className="logo_container">
                        <span>Register</span>
                    </div>
                    <div className="inputs_container">
                        <div className="first_column">
                            <div className="item">
                                <label htmlFor="login_name">Login Name</label>
                                <input type="text" id="login_name"/>
                            </div>
                            <div className="item">
                                <label htmlFor="password">Password</label>
                                <input type="password" id="password"/>
                            </div>
                            <div className="item">
                                <label htmlFor="email">Email</label>
                                <input type="email" id="email"/>
                            </div>
                        </div>

                        <div className="second_column">
                            <div className="item">
                                <label htmlFor="first_name">First Name</label>
                                <input type="text" id="first_name"/>
                            </div>
                            <div className="item">
                                <label htmlFor="repeat_password">Repeat Password</label>
                                <input type="text" id="repeat_password"/>
                            </div>
                            <div className="item">
                                <label htmlFor="address">Address</label>
                                <input type="text" id="address"/>
                            </div>
                            <div className="item">
                                <div className="btn_holder">
                                    <button className="transparent_bg">Cancel</button>
                                    <button className="green_bg">Sign Up</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </main>
        )
    }
}

export default withRouter(RegistrationPage);