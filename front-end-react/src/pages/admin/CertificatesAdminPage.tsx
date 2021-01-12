import {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Certificate from "../../entity/Certificate";
import Header from "../../components/Header";


interface IProps extends RouteComponentProps<any> {
}

interface IState {
}


class CertificatesAdminPage extends Component<IProps, IState> {

    render() {
        return (
            <div>
                <Header/>
                <main>

                </main>
            </div>
        );
    }

}

export default withRouter(CertificatesAdminPage);