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
import LocalStorageHelper from "../../services/LocalStorageHelper";

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
    currentItem?: Certificate | null;
    err_msg?: string;

    editName: string;
    editDescription: string;
    editPrice: number;
    editDuration: number;
    editTags: string[];

    writeOrEditCaption?: string;

    filterString: string[]
}

const MAX_DESCRIPTION_LENGTH = 500;

class CertificatesAdminPage extends Component<IProps, IState> {

    constructor(props: IProps) {
        super(props);
        this.state = {
            editDescription: "", editDuration: 0, editName: "", editPrice: 0, editTags: [],

            items: [],
            partName: '',
            partDescription: '',
            pageNumber: 1,
            pageSize: 5,
            totalPageCount: 1000,
            tagNames: [],
            sort: 'LAST_UPDATE:asc',
            filterString: []
        }
    }


    private columns = [{
        dataField: '_lastUpdateDate',
        text: 'Update date',
        sort: true,
        formatter: CertificatesAdminPage.dateFormatter,
        sortFunc: (a: Date, b: Date, order, dataField, rowA, rowB) => {
            function compareDates(a: Date, b: Date) {
                if (a > b) {
                    return 1;
                }
                if (a < b) {
                    return -1;
                }
                return 0;
            }

            if (order === 'asc') {
                return compareDates(a, b);
            } else {
                return compareDates(b, a);
            }
        },
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
        formatter: (cell, row: Certificate) => {
            return (
                <div>
                    <button className={'btn btn-primary'}
                            onClick={() => this.setState({currentItem: row})}
                            data-toggle="modal" data-target="#viewModal">
                        View
                    </button>
                    <button className={'btn btn-success'}
                            onClick={() => {
                                this.setState({currentItem: row})
                                this.setState({
                                    editDescription: row.description,
                                    editName: row.name,
                                    editDuration: row.duration,
                                    editPrice: row.price,
                                    editTags: [...row.tags],
                                    writeOrEditCaption: "Edit"
                                })
                            }}
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


    componentDidMount() {
        this.checkForAdmin();
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
            <div>{CertificatesAdminPage.dateParseString(cell)}</div>
        )
    }


    private static dateParseString(cell: Date) {
        return '' + cell.getFullYear() + '-' + (cell.getMonth() + 1) + '-' + cell.getDay() + ' ' + cell.getHours() + ':' + cell.getMinutes();
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
                <Header cartItems={LocalStorageHelper.calcItemCount()}/>
                <main className={'mt-5 pt-5'}>
                    <div className="container">
                        <div className="text-center my-5 d-flex align-content-end justify-content-center">
                            <ChipInput
                                label={'Filter'}
                                value={this.filterValue()}
                                onAdd={chip => {
                                    const pattern = new RegExp("^(#\\().+\\)$")
                                    if (chip.match(pattern)) {
                                        this.setState(prev => ({tagNames: [...prev.tagNames, chip.substr(2, chip.length - 3)]}))
                                    } else {
                                        this.setState({partName: chip})
                                    }
                                }}
                                onDelete={chip => {
                                    const pattern = new RegExp("^(#\\().+\\)$");
                                    if (chip.match(pattern)) {
                                        this.setState({
                                            tagNames: this.state.tagNames.filter((e) => e !== chip.substr(2, chip.length - 3))
                                        })
                                    } else {
                                        this.setState({partName: ''})
                                    }

                                }}
                            />
                            <div className={'ml-3 d-flex'}>
                                <button className={'btn btn-primary align-self-end'}
                                        onClick={() => this.filter()}>
                                    Filter
                                </button>
                            </div>
                        </div>
                        <div className="text-center">
                            <button className={'btn btn-primary'}
                                    onClick={() => {
                                        this.setState({currentItem: null})
                                        this.setState({
                                            editDescription: '',
                                            editName: '',
                                            editDuration: 0,
                                            editPrice: 0,
                                            editTags: [],
                                            writeOrEditCaption: "Create"
                                        })
                                    }}
                                    data-toggle="modal" data-target="#editModal">
                                Create
                            </button>
                        </div>
                    </div>

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

                    <Pagination pageNumber={this.state.pageNumber} pageSize={this.state.pageSize}
                                totalPageCount={this.state.totalPageCount}
                                onClickPagination={(event, input) => this.onClickPagination(event, input)}
                                changePageSize={event => this.changePageSize(event)}/>

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
                                    <h5 className="modal-title" id="editModalLabel">
                                        {
                                            this.state.writeOrEditCaption &&
                                            <span>{this.state.writeOrEditCaption}</span>
                                        } certificate
                                    </h5>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <div className="form-group text-left">
                                        <label htmlFor="name">Name</label>
                                        <input type="text" className="form-control" placeholder="Enter Name"
                                               value={this.state.editName}
                                               onChange={event => {
                                                   if (event.target.value.length <= 255) {
                                                       this.setState({editName: event.target.value})
                                                   }
                                               }}
                                               id="name"/>
                                    </div>
                                    <div className="form-group text-left">
                                        <label htmlFor="description">Description:</label>
                                        <textarea className="form-control" rows={5} id="description"
                                                  value={this.state.editDescription}
                                                  onChange={event => {
                                                      if (event.target.value.length <= MAX_DESCRIPTION_LENGTH) {
                                                          this.setState({editDescription: event.target.value})
                                                      }
                                                  }}
                                                  placeholder={"Enter Description"}/>
                                    </div>

                                    <div className="form-group text-left">
                                        <label htmlFor="price">Price</label>
                                        <input type="number" step={'any'} className="form-control"
                                               value={this.state.editPrice}
                                               placeholder="Enter Price"
                                               min={0}
                                               onChange={event => this.setState({editPrice: Number.parseFloat(event.target.value)})}
                                               id="price"/>
                                    </div>

                                    <div className="form-group text-left">
                                        <label htmlFor="duration">Duration</label>
                                        <input type="number" className="form-control" placeholder="Enter Duration"
                                               value={this.state.editDuration}
                                               min={0}
                                               onChange={event => this.setState({editDuration: Number.parseFloat(event.target.value)})}
                                               id="duration"/>
                                    </div>

                                    <div className="form-group text-left">
                                        <ChipInput
                                            label={'Tags'}
                                            value={this.state.editTags}
                                            onAdd={chip => this.setState(prev => ({
                                                editTags: [...prev.editTags, chip]
                                            }))}
                                            onDelete={chip => this.setState({
                                                editTags: this.state.editTags.filter((e) => e !== chip)
                                            })}
                                        />
                                    </div>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" className="btn btn-primary" data-dismiss="modal"
                                            onClick={event => this.handleCreateOrEdit(event)}>
                                        {
                                            this.state.writeOrEditCaption &&
                                            <span>{this.state.writeOrEditCaption}</span>
                                        }
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="modal fade" id="viewModal" tabIndex={-1} role="dialog"
                         aria-labelledby="viewModalLabel" aria-hidden="true">
                        <div className="modal-dialog" role="document">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="viewModalLabel">
                                        View certificate
                                    </h5>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <table className="table">
                                        <tbody>
                                        <tr>
                                            <th scope="row">Name</th>
                                            <td>{this.state.currentItem?.name}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Description</th>
                                            <td>{this.state.currentItem?.description}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Price</th>
                                            <td>{this.state.currentItem?.price}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Duration</th>
                                            <td>{this.state.currentItem?.duration}</td>
                                        </tr>

                                        <tr>
                                            <th scope="row">Created</th>
                                            <td>
                                                {
                                                    this.state.currentItem &&
                                                    <span>{CertificatesAdminPage.dateParseString(this.state.currentItem.createDate)}</span>
                                                }
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Updated</th>
                                            <td>
                                                {
                                                    this.state.currentItem &&
                                                    <span>{CertificatesAdminPage.dateParseString(this.state.currentItem.lastUpdateDate)}</span>
                                                }
                                            </td>
                                        </tr>

                                        <tr>
                                            <th scope="row">Tags</th>
                                            <td className={'d-flex flex-wrap'}>
                                                {
                                                    this.state.currentItem &&
                                                    this.state.currentItem.tags.map((value, index) => (
                                                        <span className={'ml-1'}>#{value}</span>
                                                    ))
                                                }
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close
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
        if (this.checkForAdmin()) {
            return;
        }
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

    private handleCreateOrEdit(event: React.MouseEvent<HTMLButtonElement>) {

        if (this.state.currentItem) {
            this.edit(event);
        } else {
            this.create(event);
        }

        // if (!this.isFormValid(username, password, repeat_password, first_name)) {
        //     return;
        // }
    }

    private edit(event: React.MouseEvent<HTMLButtonElement>) {
        if (this.state.currentItem) {

            const endpoint = 'http://localhost:8080/certificates/' + this.state.currentItem.id;
            let data = {
                name: this.state.editName,
                description: this.state.editDescription,
                price: this.state.editPrice,
                duration: this.state.editDuration,
                tags: this.state.editTags.map(value => {
                    return {
                        name: value
                    }
                })
            }


            axios.patch(endpoint,
                data
            ).then(() => {
                this.filter();
            }).catch((error) => {
                // if (error.response.data.errorCode === '40026') {
                //     this.setState({err_msg: 'User with so username already exists'})
                // }
                // if (error.response.data.errorCode === '40019') {
                //     this.setState({err_msg: error.response.data.message})
                // }
                AuthorizationHandleService.handleTokenExpired(
                    error,
                    () => this.edit(event),
                    () => window.location.reload()
                )
            });
        }
    }

    private create(event: React.MouseEvent<HTMLButtonElement>) {

        const endpoint = 'http://localhost:8080/certificates';
        let data = {
            name: this.state.editName,
            description: this.state.editDescription,
            price: this.state.editPrice,
            duration: this.state.editDuration,
            tags: this.state.editTags.map(value => {
                return {
                    name: value
                }
            })
        }


        axios.post(endpoint,
            data
        ).then(() => {
            this.setState({pageNumber: 1}, () => this.filter());
        }).catch((error) => {
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.create(event),
                () => window.location.reload()
            )
        });

    }

    private filterValue() {
        let tags = this.state.tagNames.map(el => '#(' + el + ')');
        if (this.state.partName && this.state.partName !== '') {
            return [this.state.partName, ...tags];
        } else {
            return [...tags];
        }
    }

    private checkForAdmin(): boolean {
        let item = localStorage.getItem('role');
        if (item !== 'ADMIN') {
            this.props.history.push('/login');
            return true;
        }
        return false;
    }
}

export default withRouter(CertificatesAdminPage);