import {Component} from "react";
import '../styles/main-page.css'
import {RouteComponentProps, withRouter} from "react-router-dom";
import axios from "axios";
import Certificate from "../entity/Certificate";


interface IProps extends RouteComponentProps<any> {
}

interface IState {
    items: Certificate[];
}

class MainPage extends Component<IProps, IState> {


    constructor(props: IProps) {
        super(props);

        this.state = ({items: []});
    }

    componentDidMount() {
        console.log("this.props.location.search = " + this.props.location.search)
        const endpoint = "http://localhost:8080/certificates" + this.props.location.search;

        axios.get(endpoint).then(res => {
            let data = res.data._embedded.giftCertificateDtoList;
            console.log("data = " + data)
            console.log("1")
            let list: Certificate[] = this.parseCertificateList(data);
            console.log("2")
            this.setState({items: list});
        }).catch((error) => {
            console.log("login error = " + error);
            this.setState({items: []})
        });

    }

    private parseCertificateList(data: any): Certificate[] {
        let list: Certificate[] = [];
        for (let i = 0; i < data.length; i++) {
            let obj = data[i];
            list.push(Certificate.parse(obj));
        }
        return list;
    }


    render() {
        return (
            <main>
                <div className="coupon_list">
                    {
                        this.state.items && this.state.items.map((el, i) => (
                            <div className="coupon_card" key={i}>
                                <div className="image_part">
                                    <img
                                        src="data:image/png;base64,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"
                                        alt="Gift certificate"/>
                                </div>
                                <div className="description_part">
                                    <div className="coupon_name">
                                        {el.name}
                                    </div>
                                    <div className="icon">
                                        <i className="material-icons">
                                            favorite
                                        </i>
                                    </div>
                                    <div className="brief_description">
                                        {el.description}
                                    </div>
                                    <div className="expires_in">
                                        Expires in 3 days
                                    </div>
                                </div>
                                <div className="line"/>
                                <div className="price_part">
                                    <div className="price">
                                        $235
                                    </div>
                                    <div className="add_to_card">
                                        <button>Add to Cart</button>
                                    </div>
                                </div>
                            </div>
                        ))
                    }
                </div>
            </main>
        )
    }
}

export default withRouter(MainPage);