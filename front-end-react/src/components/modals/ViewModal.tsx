import React, {Component} from "react";
import Parser from "../../services/Parser";
import {Link, RouteComponentProps, withRouter} from "react-router-dom";
import Certificate from "../../entity/Certificate";
import {Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";


interface IProps extends RouteComponentProps<any> {
    currentItem?: Certificate | null;
    showModal: boolean;
    toggle: any;
}

class ViewModal extends Component<IProps, any> {
    render() {
        return (

            <Modal isOpen={this.props.showModal} toggle={() => this.props.toggle()}>
                <ModalHeader toggle={() => this.props.toggle()}>
                    <h5 className="modal-title" id="viewModalLabel">
                        View certificate
                    </h5>
                </ModalHeader>
                <ModalBody>
                    <table className="table">
                        <tbody>
                        <tr>
                            <th scope="row">Name</th>
                            <td>{this.props.currentItem?.name}</td>
                        </tr>
                        <tr>
                            <th scope="row">Description</th>
                            <td>{this.props.currentItem?.description}</td>
                        </tr>
                        <tr>
                            <th scope="row">Price</th>
                            <td>{this.props.currentItem?.price}</td>
                        </tr>
                        <tr>
                            <th scope="row">Duration</th>
                            <td>{this.props.currentItem?.duration}</td>
                        </tr>

                        <tr>
                            <th scope="row">Created</th>
                            <td>
                                {
                                    this.props.currentItem &&
                                    <span>{Parser.dateParseString(this.props.currentItem.createDate)}</span>
                                }
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Updated</th>
                            <td>
                                {
                                    this.props.currentItem &&
                                    <span>{Parser.dateParseString(this.props.currentItem.lastUpdateDate)}</span>
                                }
                            </td>
                        </tr>

                        <tr>
                            <th scope="row">Tags</th>
                            <td className={'d-flex flex-wrap'}>
                                {
                                    this.props.currentItem &&
                                    this.props.currentItem.tags.map((value, index) => (
                                        <span className={'ml-1'}>
                                                            <Link to={'/?tagNames=' + value}>
                                                                #{value}
                                                            </Link>
                                                        </span>
                                    ))
                                }
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </ModalBody>
                <ModalFooter>
                    <button type="button" className="btn btn-secondary" onClick={() => this.props.toggle()}>
                        Close
                    </button>
                </ModalFooter>
            </Modal>
        )
    }
}

export default withRouter(ViewModal);