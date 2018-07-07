import React, { Component } from 'react';
import './App.css';
import {Table, Column, Cell} from 'fixed-data-table-2';
import 'fixed-data-table-2/dist/fixed-data-table.css';

class UserDetails extends Component {


  constructor(props) {
    super(props);
    this.state = { rows: []};
    const that = this;
  fetch('/api/users')
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
                    {this.state.rows[rowIndex]['salaryPrecision']}
                  </Cell>
                )}
                width={150}
              />
              <Column
                header={<Cell>Col 3</Cell>}
                cell={({rowIndex, ...props}) => (
                  <Cell {...props}>
                    {this.state.rows[rowIndex]['salaryStructure']}
                  </Cell>
                )}
                width={150}
              />
              <Column
                header={<Cell>Col 3</Cell>}
                cell={({rowIndex, ...props}) => (
                  <Cell {...props}> {
                  this.state.rows && this.state.rows[rowIndex]['salaries'] && Object.keys(this.state.rows[rowIndex]['salaries']).map((item) => {return (
                    <span>{item + ': ' + this.state.rows[rowIndex]['salaries'][item].map((x) => {return x + ', '})}</span>
                  );
                  })
                  }
                  </Cell>
                )}
                width={350}
              />
            </Table>
    );
  }
}

export default UserDetails;
