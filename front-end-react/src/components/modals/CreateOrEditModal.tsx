import React, {Component} from "react";
import {Alert, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import ChipInput from "material-ui-chip-input";
import {RouteComponentProps, withRouter} from "react-router-dom";


interface IProps extends RouteComponentProps<any> {
    showCreateOrEditModal: boolean;
    toggle: any;
    writeOrEditCaption?: string
    editName: string;
    editDescription: string;
    editPrice: number;
    editDuration: number;
    editTags: string[];
    onNameChange: any;
    onDescriptionChange: any;
    onPriceChange: any;
    onDurationChange: any;
    onTagAdd: any;
    onTagDelete: any;
    handleCreateOrEdit: any;
    errText?: string | null;
    wrongEditName: boolean;
    wrongEditDescription: boolean;
}


interface IState {
}

class CreateOrEditModal extends Component<IProps, IState> {
    render() {
        return (
            <Modal isOpen={this.props.showCreateOrEditModal} toggle={() => this.props.toggle()}>
                <ModalHeader toggle={() => this.props.toggle()}>
                    <h5 className="modal-title" id="editModalLabel">
                        {
                            this.props.writeOrEditCaption &&
                            <span>{this.props.writeOrEditCaption}</span>
                        } certificate
                    </h5>
                </ModalHeader>
                <ModalBody>
                    {
                        this.props.errText &&
                        <Alert color="warning">
                            {this.props.errText}
                        </Alert>
                    }
                    <div className="form-group text-left">
                        <label htmlFor="name">Name</label>
                        {
                            this.props.wrongEditName ?
                                <input type="text" className="form-control is-invalid" placeholder="Enter Name"
                                       value={this.props.editName}
                                       onChange={event => this.props.onNameChange(event)}
                                       id="name"/>
                                :
                                <input type="text" className="form-control" placeholder="Enter Name"
                                       value={this.props.editName}
                                       onChange={event => this.props.onNameChange(event)}
                                       id="name"/>
                        }
                    </div>
                    <div className="form-group text-left">
                        <label htmlFor="description">Description:</label>
                        {
                            this.props.wrongEditDescription ?
                                <textarea className="form-control is-invalid" rows={5} id="description"
                                          value={this.props.editDescription}
                                          onChange={event => this.props.onDescriptionChange(event)}
                                          placeholder={"Enter Description"}/>
                                :
                                <textarea className="form-control" rows={5} id="description"
                                          value={this.props.editDescription}
                                          onChange={event => this.props.onDescriptionChange(event)}
                                          placeholder={"Enter Description"}/>
                        }
                    </div>

                    <div className="form-group text-left">
                        <label htmlFor="price">Price</label>
                        {
                            isNaN(this.props.editPrice) ?
                                <input type="number" step={'any'} className="form-control is-invalid"
                                       value={this.props.editPrice}
                                       placeholder="Enter Price"
                                       min={0}
                                       onChange={event => this.props.onPriceChange(event)}
                                       id="price"/>
                                :
                                <input type="number" step={'any'} className="form-control"
                                       value={this.props.editPrice}
                                       placeholder="Enter Price"
                                       min={0}
                                       onChange={event => this.props.onPriceChange(event)}
                                       id="price"/>
                        }

                    </div>

                    <div className="form-group text-left">
                        <label htmlFor="duration">Duration</label>
                        {
                            isNaN(this.props.editDuration) ?
                                <input type="number" className="form-control is-invalid" placeholder="Enter Duration"
                                       value={this.props.editDuration}
                                       min={0}
                                       onChange={event => this.props.onDurationChange(event)}
                                       id="duration"/>
                                :
                                <input type="number" className="form-control" placeholder="Enter Duration"
                                       value={this.props.editDuration}
                                       min={0}
                                       onChange={event => this.props.onDurationChange(event)}
                                       id="duration"/>
                        }

                    </div>

                    <div className="form-group text-left">
                        <ChipInput
                            label={'Tags'}
                            value={this.props.editTags}
                            onAdd={chip => this.props.onTagAdd(chip)}
                            onDelete={chip => this.props.onTagDelete(chip)}
                        />
                    </div>
                </ModalBody>
                <ModalFooter>
                    <button type="button" className="btn btn-secondary" onClick={() => this.props.toggle()}>
                        Close
                    </button>
                    <button type="button" className="btn btn-primary"
                            onClick={event => this.props.handleCreateOrEdit(event)}>
                        {
                            this.props.writeOrEditCaption &&
                            <span>{this.props.writeOrEditCaption}</span>
                        }
                    </button>
                </ModalFooter>
            </Modal>
        )
    }
}

export default withRouter(CreateOrEditModal);