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
import ChipInput from "material-ui-chip-input";

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
    currentItem?: Certificate;
    err_msg?: string;
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
                    <button className={'btn btn-primary'}
                            onClick={() => this.setState({currentItem: row})}
                            data-toggle="modal" data-target="#viewModal">
                        View
                    </button>
                    <button className={'btn btn-success'}
                            onClick={() => this.setState({currentItem: row})}
                            data-toggle="modal" data-target="#editModal">
                        Edit
                    </button>
                    <button type="button" onClick={() => this.setState({currentItem: row})}
                            className="btn btn-primary"
                            data-toggle="modal" data-target="#deleteModal">
                        Delete
                    </button>
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
                    {
                        this.state.err_msg &&
                        <div className="alert alert-warning" role="alert">
                            {this.state.err_msg}
                        </div>
                    }

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

                    <div className="modal fade" id="deleteModal" tabIndex={-1} role="dialog"
                         aria-labelledby="deleteModalLabel" aria-hidden="true">
                        <div className="modal-dialog" role="document">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="deleteModalLabel">Delete certificate</h5>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <span>Are you sure you want to delete certificate with name </span>
                                    {
                                        this.state.currentItem &&
                                        <span>{this.state.currentItem.name}</span>
                                    }
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" className="btn btn-primary" data-dismiss="modal"
                                            onClick={event => this.handleDeleteItem(event)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="modal fade" id="editModal" tabIndex={-1} role="dialog"
                         aria-labelledby="editModalLabel" aria-hidden="true">
                        <div className="modal-dialog" role="document">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="editModalLabel">Edit certificate</h5>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <div className="form-group text-left">
                                        <label htmlFor="name">Name</label>
                                        <input type="text" className="form-control" placeholder="Enter Name"
                                               id="name"/>
                                    </div>
                                    <div className="form-group text-left">
                                        <label htmlFor="description">Description:</label>
                                        <textarea className="form-control" rows={5} id="description"
                                                  placeholder={"Enter Description"}/>
                                    </div>

                                    <div className="form-group text-left">
                                        <label htmlFor="price">Price</label>
                                        <input type="number" step={'any'} className="form-control"
                                               placeholder="Enter Price"
                                               id="price"/>
                                    </div>

                                    <div className="form-group text-left">
                                        <label htmlFor="duration">Duration</label>
                                        <input type="number" className="form-control" placeholder="Enter Duration"
                                               id="duration"/>
                                    </div>

                                    <div className="form-group text-left">
                                        <ChipInput
                                            label={'Tags'}
                                            defaultValue={[]}
                                            // value={this.state.tagNames}
                                            // onAdd={chip => this.handleAddTag(chip)}
                                            // onDelete={chip => this.handleDeleteTag(chip)}
                                        />
                                    </div>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" className="btn btn-primary" data-dismiss="modal"
                                            onClick={event => this.handleDeleteItem(event)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        );
    }

    private handleDeleteItem(event: React.MouseEvent<HTMLButtonElement>) {
        if (this.state.currentItem) {

            const endpoint = "http://localhost:8080/certificates/" + this.state.currentItem.id;

            axios.delete(endpoint).then(() => {
                this.filter();
            }).catch((error) => {
                if (error.response.data.errorCode === '4044') {
                    this.setState({err_msg: 'Certificate not already deleted'})
                }
                console.log("error = " + error);
                AuthorizationHandleService.handleTokenExpired(
                    error,
                    () => this.handleDeleteItem(event),
                    () => window.location.reload()
                )
            });
        }
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