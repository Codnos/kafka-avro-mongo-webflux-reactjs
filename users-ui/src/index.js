import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import {Table, Column, Cell} from 'fixed-data-table-2';
import 'fixed-data-table-2/dist/fixed-data-table.css';

//ReactDOM.render(<App />, document.getElementById('root'));

// Table data as a list of array.
const rows = [
  {
  'name': "first row",
  'salaryPrecision': "salaries",
  'salaryStructure': [1, 2, 3],
  "salaries":{"Google":[45001200000000000,56235777400000000,2345000000000000000],"Facebook":[45001200000000000,56235777400000000,2345000000000000000]}
  },
  {
  'name': "second row",
  'salaryPrecision': 'other',
  'salaryStructure': [1, 2, 3],
  "salaries":{"Google":[45001200000000000,56235777400000000,2345000000000000000],"Facebook":[45001200000000000,56235777400000000,2345000000000000000]}
  },
  {
  'name': "third row",
  'salaryPrecision': 'different',
  'salaryStructure': [1, 2, 3],
  "salaries":{"Google":[45001200000000000,56235777400000000,2345000000000000000],"Facebook":[45001200000000000,56235777400000000,2345000000000000000]}
  }
  // .... and more
];
// Custom cell implementation with special prop
const MyCustomCell = ({ mySpecialProp }) =>
  <Cell>
    {mySpecialProp === "column2" ? "I'm column 2" : "I'm not column 2"}
  </Cell>;

// Render your table
ReactDOM.render(
  <Table
    rowHeight={50}
    rowMaxHeight={150}
    rowsCount={rows.length}
    width={800}
    maxHeight={5000}
    headerHeight={50}>
    <Column
      header={<Cell>Col 3</Cell>}
      cell={({rowIndex, ...props}) => (
        <Cell {...props}>
          {rows[rowIndex]['name']}
        </Cell>
      )}
      width={150}
    />
    <Column
      header={<Cell>Col 3</Cell>}
      cell={({rowIndex, ...props}) => (
        <Cell {...props}>
          {rows[rowIndex]['salaryPrecision']}
        </Cell>
      )}
      width={150}
    />
    <Column
      header={<Cell>Col 3</Cell>}
      cell={({rowIndex, ...props}) => (
        <Cell {...props}>
          {rows[rowIndex]['salaryStructure']}
        </Cell>
      )}
      width={150}
    />
    <Column
      header={<Cell>Col 3</Cell>}
      cell={({rowIndex, ...props}) => (
        <Cell {...props}> {

        Object.keys(rows[rowIndex]['salaries']).map(function(item){return (
          <span>{item + ':' + rows[rowIndex]['salaries'][item]}</span>
        );
        })
        }
        </Cell>
      )}
      width={350}
    />
  </Table>,
  document.getElementById('root')
);

registerServiceWorker();
