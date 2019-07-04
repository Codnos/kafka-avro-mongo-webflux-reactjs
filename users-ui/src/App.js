import React, { Component } from 'react';
import './App.css';
import logo from './logo.svg';
import UserList from './UserList.js';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";


const Home = () => (
  <div>
    <h2>Home</h2>
  </div>
);

const About = () => (
  <div>
    <h2>About</h2>
  </div>
);


class App extends Component {
  render() {
    return (
      <div className="App">
         <div className="App-header">
            <img className="App-logo" src={logo}></img>
            <div className="App-title">Users-UI</div>
         </div>
         <Router>
            <div>
              <ul>
                <li>
                  <Link to="/">Home</Link>
                </li>
                <li>
                  <Link to="/about">About</Link>
                </li>
                <li>
                  <Link to="/users">Users</Link>
                </li>
              </ul>

              <hr />

              <Route exact path="/" component={Home} />
              <Route path="/about" component={About} />
              <Route path="/users" component={UserList} />
            </div>
          </Router>
      </div>
    );
  }
}

export default App;