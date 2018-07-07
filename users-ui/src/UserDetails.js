import React, { Component } from 'react';
import './App.css';
import {Table, Column, Cell} from 'fixed-data-table-2';
import 'fixed-data-table-2/dist/fixed-data-table.css';

class UserDetails extends Component {


  constructor(props) {
    super(props);
    console.log(props);
    this.state = { userData: []};
    const that = this;
  fetch(`/api/user/${this.props.match.params.userId}`)
    .then(function(response) {
      if (response.status >= 400) {
        throw new Error("Bad response from server");
      }
      return response.json();
    })
    .then(function(data) {
      that.setState = that.setState.bind(that);
      console.log(data);
      that.setState({ userData: data });
    });
  }

  render() {
    return (
    <div>
        <p>Id: {this.state.userData.id}</p>
        <p>Name: {this.state.userData.name}</p>
          <Table
              rowHeight={50}
              rowMaxHeight={150}
              rowsCount={0}
              width={800}
              maxHeight={5000}
              headerHeight={50}>

              <Column
                header={<Cell>Col 3</Cell>}
                cell={({rowIndex, ...props}) => (
                  <Cell {...props}>
                    Val
                  </Cell>
                )}
                width={150}
              />
            </Table>
    </div>
    );
  }
}

export default UserDetails;
