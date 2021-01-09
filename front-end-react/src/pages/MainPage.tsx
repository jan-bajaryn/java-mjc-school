import {Component} from "react";
import '../styles/main-page.css'
import {withRouter} from "react-router-dom";

class MainPage extends Component<any> {
    render() {
        return (
            <main>

                <div className="coupon_list">
                    {
                        [...Array(10)].map((el, i) => (
                            <div className="coupon_card">
                                <div className="image_part">
                                    <img src="https://www.beautycolorcode.com/cdcdcd-2880x1800.png"
                                         alt="Grey color"/>
                                </div>
                                <div className="description_part">
                                    <div className="coupon_name">
                                        Coupon Name
                                    </div>
                                    <div className="icon">
                                        <i className="material-icons">
                                            favorite
                                        </i>
                                    </div>
                                    <div className="brief_description">
                                        Some brief description
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