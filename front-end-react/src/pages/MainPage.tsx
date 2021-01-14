import React, {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import axios from "axios";
import Certificate from "../entity/Certificate";
import Header from "../components/Header";
import 'bootstrap/dist/css/bootstrap.css';
import ChipInput from 'material-ui-chip-input'
import AuthorizationHandleService from "../services/AuthorizationHandleService";
import Pagination from "../components/Pagination";
import LocalStorageHelper from "../services/LocalStorageHelper";
import '../styles/main-page.css'


interface IProps extends RouteComponentProps<any> {
}

interface IState {
    items: Certificate[];

    tagNames: string[];
    sort?: string;

    partName: string;
    partDescription: string;
    displayFilters: boolean;
    pageSize: number;
    pageNumber: number;

    itemCount?: number;
    totalPageCount: number;
}


class MainPage extends Component<IProps, IState> {


    constructor(props: IProps) {
        super(props);

        this.state = ({
            items: [],
            displayFilters: true,
            partName: '',
            partDescription: '',
            itemCount: LocalStorageHelper.calcItemCount(),
            pageNumber: 1,
            pageSize: 5,
            totalPageCount: 1000,
            tagNames: [],
            // sort: new Map([['NAME', 'asc'], ['LAST_UPDATE', 'DE']])
            sort: 'LAST_UPDATE:asc'
        })
        console.log('before build search')
    }

    private buildSearch(location: string) {
        const query = new URLSearchParams(location);
        let tagNames = query.get('tagNames');
        if (tagNames) {
            this.setState({tagNames: tagNames.split(',')})
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
            let data = res.data.items;
            let totalPageCount = res.data.totalPageCount;
            console.log("data = " + data)
            console.log("totalPageCount = " + totalPageCount)
            let list: Certificate[] = Certificate.parseCertificateList(data);
            this.setState({items: list, totalPageCount: totalPageCount});
        }).catch((error) => {
            console.log("error = " + error);
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.loadResources(location),
                () => window.location.reload()
            )
        });
    }

    handleAddTag = (chip) => {
        this.setState(prev => ({
            tagNames: [...prev.tagNames, chip]
        }));
    }
    handleDeleteTag = (chip) => {
        this.setState({
            tagNames: this.state.tagNames.filter((e) => e !== chip)
        });
    }

    render() {
        return (

            <div>
                <Header cartItems={this.state.itemCount}/>
                <div className={'container my-3 pt-5'}>
                    {
                        this.state.displayFilters ?
                            <button className={'btn btn-primary'} onClick={() => this.toggleFilter()}>Hide
                                filters</button>
                            :
                            <button className={'btn btn-primary'} onClick={() => this.toggleFilter()}>Show
                                Filters</button>
                    }
                </div>
                {
                    this.state.displayFilters &&
                    <div className={'d-flex justify-content-center'}>
                        <div className={'form__container mx-5 mb-5 w-50'}>
                            <div className={'row text-center'}>
                                <div className="form-group col-6">
                                    <input type="text" className="form-control" placeholder="Enter name" id="partName"
                                           value={this.state.partName}
                                           onChange={event => this.setState({partName: event.target.value})}/>
                                </div>
                                <div className="form-group col-6">
                                    <input type="text" className="form-control" placeholder="Enter description"
                                           id="partDescription" value={this.state.partDescription}
                                           onChange={event => this.setState({partDescription: event.target.value})}/>
                                </div>
                            </div>
                            <div className="form-group text-center row">
                                <div className={'col-6 text-left chip__array'}>
                                    <ChipInput
                                        label={'Tags'}
                                        value={this.state.tagNames}
                                        onAdd={chip => this.handleAddTag(chip)}
                                        onDelete={chip => this.handleDeleteTag(chip)}/>
                                </div>
                                <div className={'col-6 text-center'}>
                                    <div className={'radio text-left'}>
                                        <div className="form-check">
                                            <input className="form-check-input" type="radio" name="sort"
                                                   id="exampleRadios1" value="LAST_UPDATE:asc"
                                                   checked={this.state.sort === 'LAST_UPDATE:asc'}
                                                   onChange={() => this.setState({sort: 'LAST_UPDATE:asc'})}/>
                                            <label className="form-check-label" htmlFor="exampleRadios1">
                                                Sort by last update
                                            </label>
                                        </div>
                                        <div className="form-check">
                                            <input className="form-check-input" type="radio" name="sort"
                                                   id="exampleRadios2" value="NAME:asc"
                                                   checked={this.state.sort === 'NAME:asc'}
                                                   onChange={() => this.setState({sort: 'NAME:asc'})}/>
                                            <label className="form-check-label" htmlFor="exampleRadios2">
                                                Sort by name
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <button onClick={() => this.filter()} className="btn btn-primary">Submit</button>
                        </div>
                    </div>
                }
                <main className={'main-page'}>
                    <div className="coupon_list">
                        {
                            this.state.items && this.state.items.map((el, i) => (
                                <div className="card mx-2 my-4">
                                    <img
                                        src="data:image/png;base64,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"
                                        className="card-img-top" alt="..."/>
                                    <div className="card-body-header p-3">
                                        <h5 className="card-title">
                                            {el.name}
                                        </h5>
                                        <p className="card-text">
                                            {el.description.substr(0, 41)}
                                        </p>
                                    </div>
                                    <ul className="list-group list-group-flush">
                                        <li className="list-group-item">
                                            <span>Expires in 4 days</span>
                                        </li>
                                        <li className="list-group-item d-flex justify-content-between align-items-center">
                                            <span>${el.price}</span>
                                            <span>
                                                <button className={'btn btn-secondary'}
                                                        onClick={() => this.addCard(el.id)}>Add to Cart</button>
                                            </span>
                                        </li>
                                    </ul>
                                    <div className="card-body text-left">
                                        {
                                            el.tags.map((value, index) => (
                                                <span key={index} className={'mr-1'}>#{value}</span>
                                            ))
                                        }
                                    </div>
                                </div>
                            ))
                        }
                    </div>
                </main>

                <Pagination pageNumber={this.state.pageNumber} pageSize={this.state.pageSize}
                            totalPageCount={this.state.totalPageCount}
                            onClickPagination={(event, input) => this.onClickPagination(event, input)}
                            changePageSize={(event) => this.changePageSize(event)}/>

            </div>
        )
    }

    private changePageSize(event: React.ChangeEvent<HTMLSelectElement>) {
        this.setState(() => (({pageNumber: 1, pageSize: Number.parseInt(event.target.value)})),
            () => this.filter());
    }

    private filter() {
        console.log("this.state.partName = " + this.state.partName)
        console.log("this.state.partDescription = " + this.state.partDescription)
        console.log("this.state.pageSize = " + this.state.pageSize)
        console.log("this.state.pageNumber = " + this.state.pageNumber)
        const query = new URLSearchParams(this.props.location.search);
        MainPage.setParamQuery(query, this.state.partName, 'partName');
        MainPage.setParamQuery(query, this.state.partDescription, 'partDescription');
        MainPage.setParamQuery(query, this.state.pageNumber, 'pageNumber');
        MainPage.setParamQuery(query, this.state.pageSize, 'pageSize');
        MainPage.setParamQuery(query, this.state.sort, 'sort');
        MainPage.setParamQueryArray(query, this.state.tagNames, 'tagNames');
        let path = "?" + query.toString();
        this.props.history.replace(path);
        this.loadResources(path);
        this.buildSearch(path);
    }

    private static setParamQueryArray(query: URLSearchParams, values: string[], name: string) {
        console.log("enter method setParamQueryArray")
        if (values.length !== 0) {
            console.log("set something")
            query.set(name, values.join(','));
        } else {
            query.delete(name);
        }
    }

    private static setParamQuery(query: URLSearchParams, value: any, name: string) {
        if (value) {
            query.set(name, value);
        } else {
            query.delete(name);
        }
    }

    private addCard(id: number) {
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

    private toggleFilter(): void {
        this.setState(prevState => ({
            displayFilters: !prevState.displayFilters
        }));
    }

    private onClickPagination(event: React.MouseEvent<HTMLAnchorElement>, input: number) {
        event.preventDefault();
        console.log("onclick pagination = " + input)
        this.setState({pageNumber: input}, () => this.filter());
    }

    // private parseSort(sort: string): Map<string, string> {
    //     let map: Map<string, string> = new Map<string, string>();
    //     if (sort) {
    //         sort.split(',').forEach(value => {
    //             let strings = value.split(':');
    //             map.set(strings[0], strings[1]);
    //         })
    //     }
    //     return map;
    // }
}

export default withRouter(MainPage);