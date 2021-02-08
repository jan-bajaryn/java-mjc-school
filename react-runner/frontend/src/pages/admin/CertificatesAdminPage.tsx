import React, {Component} from "react";
import {Link, RouteComponentProps, withRouter} from "react-router-dom";
import Header from "../../components/Header";
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import Certificate from "../../entity/Certificate";
import axios from "axios";
import AuthorizationHandleService from "../../services/AuthorizationHandleService";
import Pagination from "../../components/Pagination";
import ChipInput from "material-ui-chip-input";
import LocalStorageHelper from "../../services/LocalStorageHelper";
import Parser from "../../services/Parser";
import CreateOrEditModal from "../../components/modals/CreateOrEditModal";
import ViewModal from "../../components/modals/ViewModal";
import QueryUrlParamHelper from "../../services/QueryUrlParamHelper";
import {config} from "../../Configuration";

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
    showCreateOrEditModal: boolean;
    showViewModal: boolean;
    filterString: string[];
    wrongEditName: boolean;
    wrongEditDescription: boolean;
    errModalText?: string | null;
}

const MAX_DESCRIPTION_LENGTH = 500;
const MAX_TAG_LENGTH = 255;
const MAX_CERTIFICATE_NAME_LENGTH = 255;

class CertificatesAdminPage extends Component<IProps, IState> {

    private readonly DEFAULT_PAGE_SIZE = 5;


    private readonly DEFAULT_SORT = 'LAST_UPDATE:desc';
    private readonly LAST_UPDATE_NAME = 'LAST_UPDATE:';
    private readonly NAME_NAME = 'NAME:';

    constructor(props: IProps) {
        super(props);
        this.state = {
            editDescription: "",
            editDuration: 0,
            editName: "",
            editPrice: 0,
            editTags: [],

            items: [],
            partName: '',
            partDescription: '',
            pageNumber: 1,
            pageSize: this.DEFAULT_PAGE_SIZE,
            totalPageCount: 1000,
            tagNames: [],
            sort: this.DEFAULT_SORT,
            filterString: [],
            showCreateOrEditModal: false,
            showViewModal: false,
            wrongEditName: false,
            wrongEditDescription: false,
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
            this.setState({sort: this.LAST_UPDATE_NAME + order}, () => this.filter())
        }
    }, {
        dataField: '_name',
        text: 'Name',
        sort: true,
        onSort: (field, order) => {
            this.setState({sort: this.NAME_NAME + order}, () => this.filter())
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
                            onClick={() => this.setState({currentItem: row, showViewModal: true})}>
                        View
                    </button>
                    <button className={'btn btn-success'}
                            onClick={() => {
                                this.setState({
                                    currentItem: row,
                                    editDescription: row.description,
                                    editName: row.name,
                                    editDuration: row.duration,
                                    editPrice: row.price,
                                    editTags: [...row.tags],
                                    writeOrEditCaption: "Edit",
                                    showCreateOrEditModal: true,
                                    wrongEditName: false,
                                    wrongEditDescription: false,
                                    errModalText: null
                                })
                            }}
                    >
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


    componentWillMount() {
        this.buildSearch(this.props.location.search);
    }

    componentDidMount() {
        this.checkForAdmin();
        // this.loadResources(this.props.location.search);
        this.filter();
    }


    private static tagsFormatter(cell: string[], row) {
        return (
            <div className={'d-flex flex-wrap'}>{
                cell.map((el, i) => (
                    <span key={i} className={'mr-1'}>
                         <Link to={'/?tagNames=' + el}>
                             #{el}
                         </Link>
                    </span>
                ))
            }</div>
        )
    }

    private static dateFormatter(cell: Date, row) {
        return (
            <div>{Parser.dateParseString(cell)}</div>
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
            this.saveSetPageSize(pageSize);
        }
        let pageNumber = query.get('pageNumber');
        if (pageNumber) {
            this.saveSetPageNumber(pageNumber);
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
            this.saveSetSort(sort);
        }
    }


    private saveSetSort(sort: string) {
        if (sort.match(new RegExp('^(NAME:|LAST_UPDATE:)(asc|desc)$'))) {
            this.setState({sort: sort})
        } else {
            this.setState({sort: this.DEFAULT_SORT})
        }

    }
    private saveSetPageNumber(pageNumber: string) {
        let ps = Number.parseInt(pageNumber);
        if (!isNaN(ps) && ps > 1) {
            this.setState({pageNumber: ps})
        } else {
            this.setState({pageNumber: 1})
        }
    }

    private saveSetPageSize(pageSize: string) {
        let ps = Number.parseInt(pageSize);
        if (!isNaN(ps) && ps > 1) {
            this.setState({pageSize: ps})
        } else {
            this.setState({pageSize: this.DEFAULT_PAGE_SIZE})
        }
    }

    private loadResources(location: string) {
        const endpoint = config.urlApi +"certificates" + location;
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
            <div className={'bg-light'}>
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
                                            writeOrEditCaption: "Create",
                                            showCreateOrEditModal: true,
                                            wrongEditName: false,
                                            wrongEditDescription: false,
                                            errModalText: null
                                        })
                                    }}
                            >
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


                    <CreateOrEditModal
                        editTags={this.state.editTags}
                        editDescription={this.state.editDescription}
                        editDuration={this.state.editDuration}
                        editName={this.state.editName}
                        editPrice={this.state.editPrice}
                        showCreateOrEditModal={this.state.showCreateOrEditModal}
                        writeOrEditCaption={this.state.writeOrEditCaption}
                        toggle={() => this.toggleCreateOrEditModal()}
                        onNameChange={event => {
                            if (event.target.value.length <= MAX_CERTIFICATE_NAME_LENGTH) {
                                this.setState({editName: event.target.value, wrongEditName: false})
                            }
                        }}
                        onDescriptionChange={event => {
                            if (event.target.value.length <= MAX_DESCRIPTION_LENGTH) {
                                this.setState({editDescription: event.target.value, wrongEditDescription: false})
                            }
                        }}
                        onPriceChange={event => {
                            console.log("price = ", Number.parseFloat(event.target.value))
                            this.setState({editPrice: Number.parseFloat(event.target.value)})
                        }}
                        onDurationChange={event => this.setState({editDuration: Number.parseFloat(event.target.value)})}
                        onTagAdd={chip => {
                            if (chip.length < MAX_TAG_LENGTH) {
                                this.setState(prev => ({
                                    editTags: [...prev.editTags, chip]
                                }))
                            }
                        }}
                        onTagDelete={chip => this.setState({
                            editTags: this.state.editTags.filter((e) => e !== chip)
                        })}
                        handleCreateOrEdit={event => this.handleCreateOrEdit(event)}
                        wrongEditName={this.state.wrongEditName}
                        wrongEditDescription={this.state.wrongEditDescription}
                        errText={this.state.errModalText}
                    />


                    <ViewModal currentItem={this.state.currentItem} showModal={this.state.showViewModal}
                               toggle={() => this.toggleViewModal()}/>
                </main>
            </div>
        )
            ;
    }

    private toggleCreateOrEditModal() {
        this.setState(prev => ({
            showCreateOrEditModal: !prev.showCreateOrEditModal
        }));
    }

    private toggleViewModal() {
        this.setState(prev => ({
            showViewModal: !prev.showViewModal
        }));
    }

    private handleDeleteItem(event: React.MouseEvent<HTMLButtonElement>) {
        if (this.state.currentItem) {

            const endpoint = config.urlApi +"certificates/" + this.state.currentItem.id;

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

    private handleCreateOrEdit(event: React.MouseEvent<HTMLButtonElement>) {
        if (!this.validateCreateOrEdit()) {
            return;
        }
        if (this.state.currentItem) {
            this.edit(event);
        } else {
            this.create(event);
        }
    }

    private edit(event: React.MouseEvent<HTMLButtonElement>) {
        if (this.state.currentItem) {

            const endpoint = config.urlApi +'certificates/' + this.state.currentItem.id;
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
                this.setState({showCreateOrEditModal: false}, () => this.filter());
            }).catch((error) => {
                AuthorizationHandleService.handleTokenExpired(
                    error,
                    () => this.edit(event),
                    () => window.location.reload()
                )
                if (error.response && error.response.data) {
                    if (error.response.data.errorCode === '4008') {
                        this.setState({errModalText: 'Gift certificate with so name already exists'})
                    }
                }
            });
        }
    }

    private create(event: React.MouseEvent<HTMLButtonElement>) {

        const endpoint = config.urlApi +'certificates';
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
            this.setState({pageNumber: 1, showCreateOrEditModal: false}, () => this.filter());
        }).catch((error) => {
            AuthorizationHandleService.handleTokenExpired(
                error,
                () => this.create(event),
                () => window.location.reload()
            )
            if (error.response && error.response.data) {
                if (error.response.data.errorCode === '4003') {
                    this.setState({errModalText: 'Gift certificate with so name already exists'})
                }
            }
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

    private validateCreateOrEdit(): boolean {
        let result = true;
        if (this.state.editName === '') {
            this.setState({wrongEditName: true})
            result = false;
        }
        if (this.state.editDescription === '') {
            this.setState({wrongEditDescription: true})
            result = false;
        }

        if (isNaN(this.state.editPrice) || isNaN(this.state.editDuration)) {
            result = false;
        }
        return result;
    }
}

export default withRouter(CertificatesAdminPage);