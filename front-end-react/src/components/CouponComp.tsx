import React, {Component} from "react";
import Certificate from "../entity/Certificate";


interface IProps {
    certificate: Certificate;
    size: number;
    onMinus: any;
    onPlus: any;
}

interface IState {
}

export default class CouponComp extends Component<IProps, IState> {


    render() {

        if (this.props.certificate) {
            return (
                <div className="coupon row mb-3">
                    <div className="details col-8">
                        <div className="coupon_name">{this.props.certificate.name}</div>
                        <div className="coupon_description">{this.props.certificate.description.substr(0, 100)}</div>
                    </div>
                    <div className={'row col-4'}>
                        <div className={'minus__plus d-flex align-items-center mr-5 justify-content-end'}>
                            <button className={'btn btn-outline-secondary'}
                                    onClick={() => this.props.onMinus()}>
                                -
                            </button>
                            <span className={'mx-2'}>{this.props.size}</span>
                            <button className={'btn btn-outline-secondary'}
                                    onClick={() => this.props.onPlus()}>
                                +
                            </button>
                        </div>
                        <div className="d-flex justify-content-end align-items-center">
                            <div
                                className="price">${Math.round((this.props.certificate.price * this.props.size) * 100) / 100}</div>
                        </div>
                    </div>
                </div>
            )
        } else {
            return (
                <div className="coupon">
                    <div className="details">
                        <div className="coupon_name"/>
                        <div className="coupon_description"/>
                        <div className={'minus__plus d-flex align-items-center mr-5'}>
                            <button className={'btn btn-outline-secondary'}>-</button>
                            <span className={'mx-2'}>{this.props.size}</span>
                            <button className={'btn btn-outline-secondary'}>+</button>
                        </div>
                    </div>
                    <div className="d-flex justify-content-end align-items-center">
                        <div className="price"/>
                    </div>
                </div>
            )
        }


    }
}