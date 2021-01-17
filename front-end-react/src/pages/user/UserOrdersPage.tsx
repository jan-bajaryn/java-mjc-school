import React, {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Header from "../../components/Header";
import BootstrapTable from 'react-bootstrap-table-next';
import Order from "../../entity/Order";
import axios from "axios";
import AuthorizationHandleService from "../../services/AuthorizationHandleService";
import LocalStorageHelper from "../../services/LocalStorageHelper";
import Parser from "../../services/Parser";

interface IProps extends RouteComponentProps<any> {
}

interface IState {
    orders: Order[];
    totalPageCount: number;
}

class UserOrdersPage extends Component<IProps, IState> {


    constructor(props) {
        super(props);
        this.state = {
            orders: [],
            totalPageCount: 0
        }
    }

    componentDidMount() {
        this.loadResource();
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
    },];
    private expandRow = {
        renderer: row => (
            <div>
                <p>{`This Expand row is belong to rowKey ${row.price}`}</p>
                <p>You can render anything here, also you can add additional data on every row object</p>
                <p>expandRow.renderer callback will pass the origin row object to you</p>
            </div>
        ),
        showExpandColumn: true,
    };

    render() {
        return (
            <div>
                <Header/>
                <div className={'mt-5 pt-5'}/>
                <main>
                    <div className="container">
                        <BootstrapTable bootstrap4={true} keyField='id'
                                        data={this.state.orders}
                                        columns={this.columns}
                                        expandRow={this.expandRow}
                        />
                    </div>
                </main>
            </div>
        )
    }

    private loadResource() {
        let role = LocalStorageHelper.getRole();
        if (role === null) {
// TODO exception
        } else if (role === 'ADMIN') {
            this.loadAdmin();
        }
        if (role === 'USER') {
            this.loadUser();
        }
    }

    private loadAdmin() {
        console.log('loadAdmin')

        const endpoint = "http://localhost:8080/orders?userId=" + LocalStorageHelper.getUserId();

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
                () => this.loadResource(),
                () => window.location.reload()
            )
        });
    }

    private loadUser() {
        const endpoint = "http://localhost:8080/orders";

        axios.get(endpoint).then(res => {
            let data = res.data.items;
            let totalPageCount = res.data.totalPageCount;
            let arr: Order[] = Order.parseList(data);
            this.setState({orders: arr, totalPageCount: totalPageCount});
        }).catch((error) => {
            console.log("error = " + error);
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.loadResource(),
                () => window.location.reload()
            )
        });
    }
}

export default withRouter(UserOrdersPage);