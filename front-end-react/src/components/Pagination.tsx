import React, {Component} from "react";

interface IProps {
    pageNumber: number,
    pageSize: number,
    totalPageCount: number,
    onClickPagination: any
}

interface IState {

}

export default class Pagination extends Component<IProps, IState> {

    render() {
        return (
            <ul className="pagination justify-content-center pagination-lg col-11">
                {
                    (!this.props.pageNumber || this.props.pageNumber <= 1) ?
                        <li className="page-item disabled"><a className="page-link"
                                                              href="?">Previous</a></li>
                        :
                        <li className="page-item">
                            <a className="page-link" href="?"
                               onClick={event => this.props.onClickPagination(event, this.props.pageNumber - 1)}>
                                Previous
                            </a>
                        </li>
                }
                {
                    this.props.pageNumber !== 1 &&
                    <li className="page-item">
                        <a className="page-link"
                           href="?" onClick={event => this.props.onClickPagination(event, 1)}>
                            1
                        </a>
                    </li>
                }
                {
                    [this.props.pageNumber - 3, this.props.pageNumber - 2, this.props.pageNumber - 1].map((value, index) => (
                            value > 1 &&
                            <li key={index} className="page-item">
                                <a className="page-link"
                                   href="?" onClick={event => this.props.onClickPagination(event, value)}>
                                    {value}
                                </a>
                            </li>
                        )
                    )
                }

                <li className="page-item active">
                    <a className="page-link"
                       href="?" onClick={event => this.props.onClickPagination(event, this.props.pageNumber)}>
                        {this.props.pageNumber}
                    </a>
                </li>

                {
                    [this.props.pageNumber + 1, this.props.pageNumber + 2, this.props.pageNumber + 3].map((value, index) => (
                            this.props.totalPageCount > value &&
                            <li key={index} className="page-item">
                                <a className="page-link"
                                   href="?" onClick={event => this.props.onClickPagination(event, value)}>
                                    {value}
                                </a>
                            </li>
                        )
                    )
                }
                {
                    this.props.totalPageCount !== this.props.pageNumber &&
                    <li className="page-item">
                        <a className="page-link"
                           href="?" onClick={event => this.props.onClickPagination(event, this.props.totalPageCount)}>
                            {this.props.totalPageCount}
                        </a>
                    </li>
                }
                {
                    this.props.totalPageCount > this.props.pageNumber ?
                        <li className="page-item">
                            <a className="page-link" href="?"
                               onClick={event => this.props.onClickPagination(event, this.props.pageNumber + 1)}>
                                Next
                            </a>
                        </li>
                        :
                        <li className="page-item disabled">
                            <a className="page-link" href="?">
                                Next
                            </a>
                        </li>
                }
            </ul>
        )
    }
}