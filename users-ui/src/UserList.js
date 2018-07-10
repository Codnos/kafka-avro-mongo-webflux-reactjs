import React, { Component } from 'react';
import './App.css';
import {Table, Column, Cell} from 'fixed-data-table-2';
import 'fixed-data-table-2/dist/fixed-data-table.css';
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
    return (
        <div>
        <Switch>
        <Route path={`${this.props.match.url}/:userId`} component={UserDetails} />
        <Route
          exact
          path={this.props.match.url}
          render={() =>
          <Table
              rowHeight={50}
              rowMaxHeight={150}
              rowsCount={this.state.rows.length}
              width={800}
              maxHeight={5000}
              headerHeight={50}>
              <Column
                header={<Cell>Col 3</Cell>}
                cell={({rowIndex, ...props}) => (
                  <Cell {...props}>
                    {this.state.rows[rowIndex]['name']}
                  </Cell>
                )}
                width={150}
              />
              <Column
                header={<Cell>Col 3</Cell>}
                cell={({rowIndex, ...props}) => (
                  <Cell {...props}>
                    <Link to={`${this.props.match.url}/${this.state.rows[rowIndex]['id']}`}>Details</Link>
                  </Cell>
                )}
                width={150}
              />
            </Table>
            }/>
            </Switch>
        </div>
    );
  }
}

export default UsersList;
