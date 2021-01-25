import React, {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Header from "../../components/Header";
import BootstrapTable from 'react-bootstrap-table-next';
import Order from "../../entity/Order";
import axios from "axios";
import AuthorizationHandleService from "../../services/AuthorizationHandleService";
import LocalStorageHelper from "../../services/LocalStorageHelper";
import Parser from "../../services/Parser";
import QueryUrlParamHelper from "../../services/QueryUrlParamHelper";
import Pagination from "../../components/Pagination";

interface IProps extends RouteComponentProps<any> {
}

interface IState {
    orders: Order[];
    totalPageCount: number;
    pageSize: number;
    pageNumber: number;
}

class UserOrdersPage extends Component<IProps, IState> {


    constructor(props) {
        super(props);
        this.state = {
            orders: [],
            totalPageCount: 0,
            pageNumber: 1,
            pageSize: 5,
        }
    }

    componentDidMount() {
        this.loadResource(this.props.location.search);
        this.buildSearch(this.props.location.search);
    }

    private columns = [{
        dataField: 'createDate',
        text: 'Date of purchase',
        formatter: (cell: Date, row) => {
            return (
                <div>{Parser.dateParseString(cell)}</div>
            )
        }
    }, {
        dataField: 'price',
        text: 'Price',
        formatter: (cell: Date, row) => {
            return (
                <div>${cell}</div>
            )
        }
    },];
    private expandRow = {
        renderer: row => (
            <div className={'container text-center'}>
                <h2 className={'mb-3'}>Certificates:</h2>
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Count</th>
                        <th scope="col">Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        row.purchases.map((el, index) => (
                            <tr>
                                <td>{el.name}</td>
                                <td>{el.count}</td>
                                <td>${el.priceForOne * el.count}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        ),

        showExpandColumn: true,
        expandHeaderColumnRenderer: ({isAnyExpands}) => {
            if (isAnyExpands) {
                return <b>-</b>;
            }
            return <b>+</b>;
        },
        expandColumnRenderer: ({expanded}) => {
            if (expanded) {
                return (
                    <b>-</b>
                );
            }
            return (
                <b>+</b>
            );
        }
    };

    render() {
        return (
            <div>
                <Header/>
                <div className={'mt-5 pt-5'}/>
                <main>
                    <div className="container">
                        <div className={'text-center mb-3'}>
                            <h1>Orders</h1>
                        </div>
                        <BootstrapTable bootstrap4={true} keyField='id'
                                        data={this.state.orders}
                                        columns={this.columns}
                                        expandRow={this.expandRow}
                        />
                        <Pagination pageNumber={this.state.pageNumber} pageSize={this.state.pageSize}
                                    totalPageCount={this.state.totalPageCount}
                                    onClickPagination={(event, input) => this.onClickPagination(event, input)}
                                    changePageSize={(event) => this.changePageSize(event)}/>
                    </div>
                </main>
            </div>
        )
    }


    private buildSearch(location: string) {
        const query = new URLSearchParams(location);
        let pageSize = query.get('pageSize');
        if (pageSize) {
            this.setState({pageSize: Number.parseInt(pageSize)})
        }
        let pageNumber = query.get('pageNumber');
        if (pageNumber) {
            this.setState({pageNumber: Number.parseInt(pageNumber)})
        }
    }

    private loadResource(search) {
        let role = LocalStorageHelper.getRole();
        if (role === null) {
            this.props.history.push('/login');
        } else if (role === 'ADMIN') {
            this.loadAdmin(search);
        }
        if (role === 'USER') {
            this.loadUser(search);
        }
    }

    private loadAdmin(search: any) {
        console.log('loadAdmin')

        const endpoint = "http://localhost:8080/orders?userId=" + LocalStorageHelper.getUserId() + '&' + search;

        axios.get(endpoint).then(res => {
            let data = res.data.items;
            let totalPageCount = res.data.totalPageCount;
            let arr: Order[] = Order.parseList(data);
            this.setState({orders: arr, totalPageCount: totalPageCount});
            console.log('list = ', arr)
            console.log('totalPageCount = ', totalPageCount)
            console.log('success');
        }).catch((error) => {
            console.log("error = " + error);
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.loadResource(this.props.location.search),
                () => window.location.reload()
            )
        });
    }

    private loadUser(search: any) {
        const endpoint = "http://localhost:8080/orders?" + search;

        axios.get(endpoint).then(res => {
            let data = res.data.items;
            let totalPageCount = res.data.totalPageCount;
            let arr: Order[] = Order.parseList(data);
            this.setState({orders: arr, totalPageCount: totalPageCount});
        }).catch((error) => {
            console.log("error = " + error);
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.loadResource(this.props.location.search),
                () => window.location.reload()
            )
        });
    }

    private changePageSize(event: React.ChangeEvent<HTMLSelectElement>) {
        this.setState(() => (({pageNumber: 1, pageSize: Number.parseInt(event.target.value)})),
            () => this.filter());
    }

    private filter() {
        const query = new URLSearchParams(this.props.location.search);
        QueryUrlParamHelper.setParamQuery(query, this.state.pageNumber, 'pageNumber');
        QueryUrlParamHelper.setParamQuery(query, this.state.pageSize, 'pageSize');
        let path = query.toString();
        this.props.history.replace("/orders?" + path);
        this.loadResource(path);
        this.buildSearch('?' + path);
    }


    private onClickPagination(event: React.MouseEvent<HTMLAnchorElement>, input: number) {
        event.preventDefault();
        console.log("onclick pagination = " + input)
        this.setState({pageNumber: input}, () => this.filter());
    }
}

export default withRouter(UserOrdersPage);