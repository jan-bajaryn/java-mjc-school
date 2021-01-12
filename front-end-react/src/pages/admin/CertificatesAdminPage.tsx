import {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Certificate from "../../entity/Certificate";
import Header from "../../components/Header";
import 'bootstrap/dist/css/bootstrap.css';


interface IProps extends RouteComponentProps<any> {
}

interface IState {
}


class CertificatesAdminPage extends Component<IProps, IState> {

    render() {
        return (
            <div>
                <Header/>
                <main className={'mt-5 pt-5'}>
                    <div className={'filter__part'}>

                    </div>

                    <div className="table__content">
                        
                    </div>
                </main>
            </div>
        );
    }

}

export default withRouter(CertificatesAdminPage);