import React, {Component} from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Header from "../../components/Header";
// import 'bootstrap/dist/css/bootstrap.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import Certificate from "../../entity/Certificate";
import axios from "axios";
import AuthorizationHandleService from "../../services/AuthorizationHandleService";
import Pagination from "../../components/Pagination";

interface IProps extends RouteComponentProps<any> {
}

interface IState {
    items: Certificate[];

    tagNames: string[];
    sort?: string;

    partName: string;
    partDescription: string;
    pageSize: number;
    pageNumber: number;

    itemCount?: number;
    totalPageCount: number;
}

class CertificatesAdminPage extends Component<IProps, IState> {

    private columns = [{
        dataField: '_lastUpdateDate',
        text: 'Update date',
        sort: true,
        formatter: CertificatesAdminPage.dateFormatter,
        onSort: (field, order) => {
            this.setState({sort: 'LAST_UPDATE:' + order}, () => this.filter())
        }
    }, {
        dataField: '_name',
        text: 'Name',
        sort: true,
        onSort: (field, order) => {
            this.setState({sort: 'NAME:' + order}, () => this.filter())
        }
    }, {
        dataField: '_tags',
        text: 'Tags',
        formatter: CertificatesAdminPage.tagsFormatter
    }, {
        dataField: '_description',
        text: 'Description',
    }, {
        dataField: '_price',
        text: 'Price',
    }, {
        text: 'Actions',
        formatter: (cell, row) => {
            return (
                <div>
                    <button className={'btn btn-primary'}>View</button>
                    <button className={'btn btn-success'}>Edit</button>
                    <button className={'btn btn-secondary'}>Delete</button>
                </div>
            )
        }
    }];

    constructor(props: IProps) {
        super(props);
        this.state = {
            items: [],
            partName: '',
            partDescription: '',
            pageNumber: 1,
            pageSize: 5,
            totalPageCount: 1000,
            tagNames: [],
            sort: 'LAST_UPDATE:asc'
        }
    }


    componentDidMount() {
        this.loadResources(this.props.location.search);
        this.buildSearch(this.props.location.search);
    }


    private static tagsFormatter(cell: string[], row) {
        return (
            <div className={'d-flex flex-wrap'}>{
                cell.map((el, i) => (
                    <span key={i} className={'mr-1'}>
                #{el}
            </span>
                ))
            }</div>
        )
    }

    private static dateFormatter(cell: Date, row) {
        return (
            <div>{'' + cell.getFullYear() + '-' + (cell.getMonth() + 1) + '-' + cell.getDay() + ' ' + cell.getHours() + ':' + cell.getMinutes()}</div>
        )
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

    render() {
        return (
            <div>
                <Header/>
                <main className={'mt-5 pt-5'}>
                    <div className={'filter__part'}>

                    </div>

                    <div className="table__content m-5">
                        <BootstrapTable bootstrap4={true} keyField='id' data={this.state.items}
                                        columns={this.columns}/>
                    </div>

                    <div className={'row m-5 align-middle'}>
                        <Pagination pageNumber={this.state.pageNumber} pageSize={this.state.pageSize}
                                    totalPageCount={this.state.totalPageCount}
                                    onClickPagination={(event, input) => this.onClickPagination(event, input)}/>

                        <div className="form-group w-10 col-1">
                            <label>
                                <select className="form-control btn-lg" value={this.state.pageSize}
                                        onChange={event => this.changePageSize(event)}>
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

                </main>
            </div>
        );
    }

    private changePageSize(event: React.ChangeEvent<HTMLSelectElement>) {
        this.setState(() => (({pageNumber: 1, pageSize: Number.parseInt(event.target.value)})),
            () => this.filter());
    }

    private onClickPagination(event: React.MouseEvent<HTMLAnchorElement>, input: number) {
        event.preventDefault();
        console.log("onclick pagination = " + input)
        this.setState({pageNumber: input}, () => this.filter());
    }

    private filter() {
        const query = new URLSearchParams(this.props.location.search);
        CertificatesAdminPage.setParamQuery(query, this.state.partName, 'partName');
        CertificatesAdminPage.setParamQuery(query, this.state.partDescription, 'partDescription');
        CertificatesAdminPage.setParamQuery(query, this.state.pageNumber, 'pageNumber');
        CertificatesAdminPage.setParamQuery(query, this.state.pageSize, 'pageSize');
        CertificatesAdminPage.setParamQuery(query, this.state.sort, 'sort');
        CertificatesAdminPage.setParamQueryArray(query, this.state.tagNames, 'tagNames');
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

}

export default withRouter(CertificatesAdminPage);