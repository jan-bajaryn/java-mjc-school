import React, {Component} from "react";
import '../styles/main-page.css'
import {BrowserRouter, RouteComponentProps, withRouter} from "react-router-dom";
import axios from "axios";
import Certificate from "../entity/Certificate";
import Header from "../components/Header";
import 'bootstrap/dist/css/bootstrap.css';


interface IProps extends RouteComponentProps<any> {
}

interface IState {
    items: Certificate[];
    tagNames?: string;
    pageSize: number;
    pageNumber: number;
    partName: string;
    partDescription: string;
    sort?: string;
    displayFilters: boolean;

    itemCount?: number;
    totalCount: number;
}

class MainPage extends Component<IProps, IState> {


    constructor(props: IProps) {
        super(props);

        this.state = ({
            items: [],
            displayFilters: true,
            partName: '',
            partDescription: '',
            itemCount: this.calcItemCount(),
            pageNumber: 1,
            pageSize: 5,
            totalCount: 1000
        });
        console.log('before build search')
    }

    private calcItemCount() {
        let item = localStorage.getItem('cart');
        if (item == null) {
            return null;
        } else {
            let parse = JSON.parse(item);
            return parse.length
        }
    }

    private buildSearch(location: string) {
        const query = new URLSearchParams(location);
        let tagNames = query.get('tagNames');
        if (tagNames) {
            this.setState({tagNames: tagNames})
        }
        let pageSize = query.get('pageSize');
        if (pageSize) {
            this.setState({pageSize: Number.parseInt(pageSize)})
        }
        let pageNumber = query.get('pageNumber');
        if (pageNumber) {
            this.setState({pageNumber: Number.parseInt(pageNumber)})
        }
        let partName = query.get('partName');
        console.log('partName in buildSearch = ' + partName)
        if (partName) {
            this.setState({partName: partName})
        }
        let partDescription = query.get('partDescription');
        if (partDescription) {
            this.setState({partDescription: partDescription})
        }
        let sort = query.get('sort');
        if (sort) {
            this.setState({sort: sort})
        }
    }

    componentDidMount() {
        this.loadResources(this.props.location.search);
        this.buildSearch(this.props.location.search);
    }


    private loadResources(location: string) {
        const endpoint = "http://localhost:8080/certificates" + location;
        console.log("loadResources, location = " + location);

        axios.get(endpoint).then(res => {
            let data = res.data._embedded.giftCertificateDtoList;
            console.log("data = " + data)
            let list: Certificate[] = this.parseCertificateList(data);
            this.setState({items: list});
        }).catch((error) => {
            console.log("error = " + error);
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

            <div>
                <Header cartItems={this.state.itemCount}/>
                <div className={'container'}>
                    <button className={'btn btn-primary'} onClick={e => this.toggleFilter(e)}>Filter</button>
                </div>
                {
                    this.state.displayFilters &&
                    <div className={'form__container row text-center mx-5'}>
                        <div className="form-group col-6">
                            <label htmlFor="partName">Name</label>
                            <input type="text" className="form-control" placeholder="Enter name" id="partName"
                                   value={this.state.partName}
                                   onChange={event => this.setState({partName: event.target.value})}/>
                        </div>
                        <div className="form-group col-6">
                            <label htmlFor="partDescription">Description</label>
                            <input type="text" className="form-control" placeholder="Enter description"
                                   id="partDescription" value={this.state.partDescription}
                                   onChange={event => this.setState({partDescription: event.target.value})}/>
                        </div>
                        <button onClick={event => this.filter(event)} className="btn btn-primary">Filter</button>
                    </div>
                }
                <main>
                    <div className="coupon_list">
                        {
                            this.state.items && this.state.items.map((el, i) => (
                                <div className="coupon_card" key={i}>
                                    <div className="image_part">
                                        <img
                                            src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAbFBMVEX////MlyvIjwDKkx3Kkhbp1LX8+vXPnTz79u/Omzjkyp/lzaXhw5LLlSXq17fLlB/o0q7TplLevoju3sTQoEb27d7cun/Rokz58+jixpjJkQ7Vqlz27uDv4MjXsGrz59XatXbXrmbevYXZs3Ex5E5eAAAKHElEQVR4nO2da5+yLBCHC1jtIHnosEUH293v/x0fNc6iYlq3/Z65XlXrEn+EmWFAms0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAmyeJ4XIxbYjB6ic+Tn1KGCth3lI9T4mV1JmWJaJddxylxCJsQYTqvoJjRaD+0wMVaKzFm8WqMWj5PHjJeGQ5G4er4fHlBkiJilkjwZrz69uaEzNo87iTaJc8Noe2PLa8C3Uautj9/rF6dSiRB594Nf70zl7yqY+xeUXsP/oi7Qlzk39a/qPyXsLi5tDh8nYoWTvodpPXmjxk5eBnXSzRHdXlGifj8ajUOcqS+H5HdLpYWUBfZaVyPq9Dxj5ix3XesjUq2fo8qnSWVMqJLS13Rstm4lqbT2S5Z1S6L9RyLD9Hb/f9G9FHyE8gPi/5WH06Fcf1eJy5cpjNmTO/bkegp8eHdCkNeN/Jrfp7/YpdI4qImz2GfNkIiCmZvZc+/OP6p/+16d7q1LiofU5ex4habJG+QpXHiA4S5W3bj9t0t8oqunLjL2j0Kom82p+fH1+Ko6QJH/NVMqzna8hHPXqOkCd51WJsvOK53DktZo9Ol8O9CA+Ld/gTIr10vp3lDZCdvH/ntCgt4f2Ejzc38WDwUUo9o6ipiO2zA7y32mBwdHraZ9IgCh3PkCj1CYt4Y8+XqpLFKqXe1s89QSL/Nj3vcGFD4EhoVBpe9ySVnrQqTxbFA+8OlFoBOSWFwRqzGvE3h/JFyQsKvHorXoeU7pqRwKacCNZoUctgjvP0pDS+1PN+EFCYtvq9D4WN2xKNdax4xIYW/LYmILoWsrH/y8J2Wm52Qwqi5k3YrLHO/G+K6dkIKtcRGb4Wk/JAHg1bKYkIKZxmKaY12hY9rYvRI32/KAlhqlTohhbPtLbX5pm0K6a666HDhH+/v6dme6k5KoYOgPabxWHrJ/C8djWBLvBWOFrXF0aYv2/y55E45e5+/X+E8dqazWmEo7Z/eWWvLCz16qTQqhv3ppfApKOmZTF7szLil8x/ye2v96LlbYnsJ3bBdj2zy0cq7dCQx9xFtW3CpJDJ27zAhuydSk+Z3YO8Uz8LOnrV1suNqiVqCHEXMcNaSg1m0xBG+EonvXay1ZmMSc5F8++TZpEg0P10ailp5NVOHRM8VyLVaMBR9D7l6WLA59856U4zCtaulAzHwY9wbNUKI33YA2V0IlqM/ro3E7e2ppH6V1k/ree9UZOUOUW/+sLwnyEegvIWo8DF/Iq5cGqP4emCOhZlqIm92Nlp8QlxX/phD+8z/jXabbReJuCvEx2fwCPORmhVLM4UxlD41z2KnvDRZBIFc6nh8ms6CwHm3CwemjOtVDgf25MJM3pBFcSFcN18EussKExpt9/vryWE6tdEVmI60mvHOgi/XiC2M6+82319Xai8LXT4nUM6qfVbnRGtg/j5WNcOEMeZYzNUtpOW3KeWfL5yrG7FVInp+J5JYJeteFOAT8PjO3+87/BQjupeTs2NxnzXr1u050YDFQ960pHv/y9dDoVpQu7ZKtCY7wpWSk7RXum8oop82gY2LeB7w3Ar56q+wkNhcLct2iWX/wvWKbE78Z1xxaW4vNGhv2xCFs2PYtGnItgwyQsjN1xqHho6K8bCp7yCF5lxK3+VjxToRb4m43KKm3U+dwJltxcjaCvFuhYXGEBEcUxrHBIXCZVrxqgyeH2NPjElmDn4VFOKqRFqUGEeD99EMVlj01a/sdk5vWXIRltlekb6JRYpT9VbZVbMgsQkJJ9voryjxNxm8W3U2ikKFSBHizPhYKor5ByLks8oSxvl59+5iTIXSQxIzfgjtXql6rXmvz8KljLkxeEyFMnA1/XNSDw2FU7Q2HB1fsQFqRIXSRlr7F6SL10aVsLqWzc2Er7z3EtHKiAplrU0/56z1tkdrDGQ8hSfd5ylkzzM3iKXePXoooym0fJ6kwXrIII2YxdSs0mBGUyg8AGnwAPYcXXoWM2aRnoXORmIshTWfxxFeHNl5RxmktUcHwxlLoZwdbWfBfpOIia+IxBxpx9qfgk2SL2aBc3Y1gJEUyk3R9L5EjBB0f1S64UZVLE2PkaByJSW+7URnGOlBkpEUqpkP9xms6mRiRoRdEwRziObynQCNsxlxHIWOPQrlxsxLQxjHMczsTz1L59j5eI0yMaA3mfmsnHofrDNtQ+4oCo+O+XmZTGtwerV/K1xl4Cqi9m83hDGqxm0QsuKlqkuwlO/3jBTzLmnZRlFob6yo7sDPbCs6XtM2VC3cSVzZAssuz1bVsMZpKaj6SqXjpyoKZWV3r9pVGqpRFIaufA0K5u7gU0MZotRZhGVOuW3CacBfSWskNy1HYoIjI4xRFH67qkeFbtcTCxwZpDnbqDbFkAZbZIqp2JgiR7xcqZD1HEXhSqZYKCZMfIeodNsWdKFMPWJEsMz2xNbGmnpLqh1wtWEsn88Yx5Z+szKrwhA5R1/7rTmk2pIC9jaq+Pe4Pd3mqNKJmd00W1sHkin1zMpjUTmGR/KH63N6KCOSCtPuYNf1kptxMXeBQb6JzmlUdzEnU6IeCt6MdqWqdUbN03CMzRMdkwRjDbs7QWNINGPdm+6TiVpRfoVCfUWjc3050tre49kEbY3O3kTwo0Yp0fr3KxSqZxN9Qi/thncH24G2lcCK5nOtIL2SL1G4kvfFWpxwsVGG2LadNYKlbk2xfr1ps5jKZ75EoRpcPlMgeVs6F8BMgcbjwfbCjgroXqJQxdQ+09jcnclx8GuHdipyrW2Ekbms1ygU25n9thfwNczuHi1ViCEnh6KK/cU1WDTuaxTyEKOWunDDg8rm8FUgbiG7i2jPjtroXPjFcaO2OpsiwKfMd5aelFejbqPEO3/RcNwvqsrwTlO4CS5Rtu6LFM6uZxL6b3ssr/ZYqt+XEyNa1b2SGKuRmzzel47+Vt5gddLEqxS+hjxEaMk3vccIpZpjT9T7CCGtQ3yWwsIRKfdjH0J0DFwvP05hf0ChAhROFVCoAIVTBRQqQOFU6a0wzmbBJyEf7vNWOI/RZyFWzf0VfiigEBROH1CoK6w/cj9pnlAYfha9FeKxdmO9ixNEbRJQOFVAoQIUThVQqACFUwUUKkDhVAGFClA4VUChAhROlSfWLYLFJxHAukVd4YcCCkHh9AGFs//VusXys+itENYtJgfEpQpQOFVAoQIUThVQqACFUwUUKsQZtG//QdCBHLzPoJWnKr+hVmOin2Ddjjw+5F/+Enh/xKPiPgcVysOu3vpjkgM59vixEfmUPcVv/U2bQWzFidlex2mqg45YGK0/gSiUpxn4naapjrBo+AnjqaGOPCeePm7pPAnoA/A+1dZ1ptdH4P9Lu3nLGeXThfY52+4y/7y8MJk3/cCLm6zP78f8eyhGWbcok0U0R4WZ+gQIQfPnjjw/bpPVJ5BsPyn8AgAAAAAAAAAAAAAAAAAAAAAAAADg3/EfJ/3jmOLTQ0IAAAAASUVORK5CYII="
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
                                            {el.description.substr(0, 41)}
                                        </div>
                                        <div className="expires_in">
                                            Expires in 3 days
                                        </div>
                                    </div>
                                    <div className="line"/>
                                    <div className="price_part">
                                        <div className="price">
                                            ${el.price}
                                        </div>
                                        <div className="add_to_card">
                                            <button onClick={event => this.addCard(el.id, event)}>Add to Cart</button>
                                        </div>
                                    </div>
                                </div>
                            ))
                        }
                    </div>
                </main>

                <div className={'row m-5 align-middle'}>
                    <ul className="pagination justify-content-center pagination-lg col-11">
                        {
                            (!this.state.pageNumber || this.state.pageNumber <= 1) ?
                                <li className="page-item disabled"><a className="page-link"
                                                                      href="?">Previous</a></li>
                                :
                                <li className="page-item"><a className="page-link" href="?">Previous</a>
                                </li>
                        }
                        {
                            [this.state.pageNumber - 3, this.state.pageNumber - 2, this.state.pageNumber - 1].map((value, index) => (
                                    value >= 1 &&
                                    <li key={index} className="page-item active"><a className="page-link"
                                                                                    href="?">{value}</a></li>
                                )
                            )
                        }


                        <li className="page-item active"><a className="page-link"
                                                            href="?">{this.state.pageNumber}</a></li>

                        {
                            [this.state.pageNumber + 1, this.state.pageNumber + 2, this.state.pageNumber + 3].map((value, index) => (
                                    value >= 1 &&
                                    <li key={index} className="page-item"><a className="page-link"
                                                                             href="?">{value}</a></li>
                                )
                            )
                        }
                        {
                            (this.state.totalCount / this.state.pageSize) <= this.state.pageNumber ?
                                <li className="page-item disabled"><a className="page-link"
                                                                      href="?">Next</a></li>
                                :
                                <li className="page-item"><a className="page-link" href="?">Next</a></li>
                        }
                    </ul>

                    <div className="form-group w-10 col-1">
                        <label>
                            <select className="form-control btn-lg" value={this.state.pageNumber}
                                    onChange={event => this.setState({pageSize: Number.parseInt(event.target.value)})}>
                                <option>5</option>
                                <option>10</option>
                                <option>15</option>
                                <option>20</option>
                                <option>25</option>
                                <option>30</option>
                                <option>50</option>
                                <option>100</option>
                            </select>
                        </label>
                    </div>
                </div>
            </div>
        )
    }

    private filter(event: React.MouseEvent<HTMLButtonElement>) {
        console.log("this.state.partName = " + this.state.partName)
        console.log("this.state.partDescription = " + this.state.partDescription)
        console.log("this.state.pageSize = " + this.state.pageSize)
        const query = new URLSearchParams(this.props.location.search);
        if (this.state.partName) {
            query.set('partName', this.state.partName);
        } else {
            query.delete('partDescription');
        }

        if (this.state.partDescription) {
            query.set('partDescription', this.state.partDescription);
        } else {
            query.delete('partDescription');
        }
        let path = "?" + query.toString();
        this.props.history.replace(path);
        this.loadResources(path);
        this.buildSearch(path);
    }

    private addCard(id: number, event: React.MouseEvent<HTMLButtonElement>) {
        console.log("executed");
        let item = localStorage.getItem('cart');
        console.log(item);
        if (item == null) {
            localStorage.setItem('cart', JSON.stringify([id]))
        } else {
            let parse: Array<number> = JSON.parse(item);
            parse.push(id);
            this.setState({itemCount: parse.length})
            localStorage.setItem('cart', JSON.stringify(parse));
        }
    }

    private toggleFilter(event: any): void {
        this.setState(prevState => ({
            displayFilters: !prevState.displayFilters
        }));
    }
}

export default withRouter(MainPage);