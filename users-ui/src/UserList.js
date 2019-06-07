import React, { Component } from 'react';
import './App.css';
import 'react-table/react-table.css';
import ReactTable from 'react-table';
import { Route, Link, Switch } from "react-router-dom";
import UserDetails from './UserDetails.js';

class UsersList extends Component {


  constructor(props) {
    super(props);
    this.state = { rows: []};
    const that = this;
  fetch('/api/users-without-salaries')
    .then(function(response) {
      if (response.status >= 400) {
        throw new Error("Bad response from server");
      }
      return response.json();
    })
    .then(function(data) {
      that.setState = that.setState.bind(that);
      that.setState({ rows: data });
    });
  }

  render() {
  const columns = [{
      Header: 'Name',
      accessor: 'name'
    }, {
      Header: 'Links',
      accessor: 'id',
      Cell: props => {
      console.log(props)
      return (<Link to={`${this.props.match.url}/${props.row['id']}`}>Details</Link>)
      }
    }]


    return (
        <div>
        <Switch>
        <Route path={`${this.props.match.url}/:userId`} component={UserDetails} />
        <Route
          exact
          path={this.props.match.url}
          render={() =>
          <ReactTable columns={columns} data={this.state.rows}/>
            }/>
            </Switch>
        </div>
    );
  }
}

export default UsersList;
