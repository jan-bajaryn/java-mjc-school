import React, {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Header from "../../components/Header";
import '../../styles/checkout.css'
import Certificate from "../../entity/Certificate";
import LocalStorageHelper from "../../services/LocalStorageHelper";
import CouponComp from "../../components/CouponComp";
import axios from "axios";
import AuthorizationHandleService from "../../services/AuthorizationHandleService";
import RefExtractor from "../../services/RefExtractor";


interface IProps extends RouteComponentProps<any> {
}

interface IState {
    // coupons: Map<number, number>;
    coupons: Map<Certificate, number>;
}


class BasketPage extends Component<IProps, IState> {

    constructor(props: IProps) {
        super(props);
        this.state = {
            coupons: new Map([])
        }
    }

    async componentDidMount() {
        let basket = Array.from(LocalStorageHelper.getBasket().entries())
        console.log('basket comp did mount = ' + JSON.stringify(basket))
        let resultBasket: Map<Certificate, number> = new Map([]);
        for (let i = 0; i < basket.length; i++) {
            await this.buildItem(resultBasket, basket[i][0], basket[i][1]);
        }
        this.setState({coupons: resultBasket})
        console.log("resultBasket = " + JSON.stringify(Array.from(resultBasket.entries())))
    }

    async buildItem(resultBasket: Map<Certificate, number>, id: number, size: number) {
        const endpoint = "http://localhost:8080/certificates/" + id;
        await axios.get(endpoint).then(res => {
            let data = res.data;
            resultBasket.set(Certificate.parse(data), size)
        }).catch((error) => {
            console.log("error = " + error);
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.buildItem(resultBasket, id, size),
                () => window.location.reload()
            )
        });
    }

    render() {
        return (
            <div>
                <Header/>
                <div className={'mt-5 pt-2'}/>
                <div className="header_line">
                    <span>
                        Basket
                    </span>
                </div>

                <main className={'checkout'}>
                    <div className="main_container">
                        <div className="coupons mt-5">

                            {
                                this.state.coupons.size === 0 &&
                                <h1 className={'container my-5 py-5'}>
                                    You have not certificates in your basket
                                </h1>
                            }

                            {
                                Array.from(this.state.coupons.entries()).map((value, index) => (
                                    <CouponComp key={index} certificate={value[0]} size={value[1]}
                                                onMinus={() => this.onMinus(value)}
                                                onPlus={() => this.onPlus(value)}/>
                                ))
                            }
                        </div>
                        <div className="line"/>
                        <div className="total_container">
                            <div className="total">
                                <span>Total</span>
                                <span>${
                                    Array.from(this.state.coupons.entries())
                                        .map(value => value[0].price * value[1])
                                        .reduce((p, c) => p + c, 0)
                                }</span>
                            </div>
                        </div>
                        <div className="btn_container">
                            <div className="btn_place">
                                <button className="btn transparent_bg"
                                        onClick={() => {
                                            this.props.history.push('/');
                                        }}>
                                    Back
                                </button>
                                {
                                    this.state.coupons.size === 0 ?
                                        <button className="btn green_bg disabled">
                                            Save
                                        </button>
                                        :
                                        <button className="btn green_bg" onClick={() => this.onFormSubmit()}>
                                            Save
                                        </button>
                                }

                            </div>
                        </div>
                    </div>
                </main>
            </div>
        )
    }

    private onMinus(value: [Certificate, number]) {
        LocalStorageHelper.removeItemBasket(value[0].id);
        let copyMap = new Map(this.state.coupons.entries());
        if (value[1] - 1 < 1) {
            copyMap.delete(value[0])
            this.setState({coupons: copyMap});
        } else {
            copyMap.set(value[0], value[1] - 1)
            this.setState({
                coupons: copyMap
            });
        }
    }

    private onPlus(value: [Certificate, number]) {
        LocalStorageHelper.putItemBasket(value[0].id);
        let copyMap = new Map(this.state.coupons.entries());
        copyMap.set(value[0], value[1] + 1)
        this.setState({
            coupons: copyMap
        })
    }

    private onFormSubmit() {
        const endpoint = "http://localhost:8080/orders";


        let retCerts = Array.from(this.state.coupons.entries())
            .map(value => {
                return {
                    id: value[0].id,
                    count: value[1]
                }
            });
        let data = {
            userId: LocalStorageHelper.getUserId(),
            giftCertificates: retCerts,
        }

        axios.post(endpoint,
            data
        ).then(() => {
            LocalStorageHelper.clearBasket();
            this.props.history.push("/orders");
        }).catch((error) => {
            if (error.response.data.errorCode === '40128') {
                this.props.history.push('/login');
            }
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.onFormSubmit(),
                () => window.location.reload()
            )
        });
    }
}

export default withRouter(BasketPage);
