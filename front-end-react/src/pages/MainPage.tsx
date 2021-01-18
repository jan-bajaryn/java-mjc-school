import React, {Component} from "react";
import {Link, RouteComponentProps, withRouter} from "react-router-dom";
import axios from "axios";
import Certificate from "../entity/Certificate";
import Header from "../components/Header";
import 'bootstrap/dist/css/bootstrap.css';
import ChipInput from 'material-ui-chip-input'
import AuthorizationHandleService from "../services/AuthorizationHandleService";
import Pagination from "../components/Pagination";
import LocalStorageHelper from "../services/LocalStorageHelper";
import '../styles/main-page.css'
import QueryUrlParamHelper from "../services/QueryUrlParamHelper";
import {Redirect} from "react-router-dom";


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

    itemCount: number;
    totalPageCount: number;
}


class MainPage extends Component<IProps, IState> {


    constructor(props: IProps) {
        super(props);

        this.state = ({
            items: [],
            displayFilters: false,
            partName: '',
            partDescription: '',
            itemCount: LocalStorageHelper.calcItemCount(),
            pageNumber: 1,
            pageSize: 5,
            totalPageCount: 1000,
            tagNames: [],
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
                <div className={'container my-3 pt-5 text-center'}>
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
                        <div className={'form__container mx-5 mb-5 w-50 text-center'}>
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
                                <div key={i} className="card mx-2 my-4">
                                    <h2 className="card-title card-header text-center">
                                        {el.name}
                                    </h2>

                                    <div className="card-body-header p-3">
                                        <p className="card-text">
                                            {el.description.substr(0, 100)}
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
                                                <span key={index} className={'mr-1'}>
                                                    <Link to={'/?tagNames=' + value}
                                                          onClick={() => this.setState({
                                                                  tagNames: [value],
                                                                  partName: '',
                                                                  partDescription: '',
                                                                  sort: 'LAST_UPDATE:asc',
                                                                  pageNumber: 1
                                                              },
                                                              () => this.filter())}>
                                                        #{value}
                                                    </Link>
                                                </span>
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
        const query = new URLSearchParams(this.props.location.search);
        QueryUrlParamHelper.setParamQuery(query, this.state.partName, 'partName');
        QueryUrlParamHelper.setParamQuery(query, this.state.partDescription, 'partDescription');
        QueryUrlParamHelper.setParamQuery(query, this.state.pageNumber, 'pageNumber');
        QueryUrlParamHelper.setParamQuery(query, this.state.pageSize, 'pageSize');
        QueryUrlParamHelper.setParamQuery(query, this.state.sort, 'sort');
        QueryUrlParamHelper.setParamQueryArray(query, this.state.tagNames, 'tagNames');
        let path = "?" + query.toString();
        this.props.history.replace(path);
        this.loadResources(path);
        this.buildSearch(path);
    }


    private addCard(id: number) {
        LocalStorageHelper.putItemBasket(id);
        this.setState({itemCount: LocalStorageHelper.calcItemCount()})
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
}

export default withRouter(MainPage);