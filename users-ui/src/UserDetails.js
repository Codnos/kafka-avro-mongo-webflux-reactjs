import React, { Component } from 'react';
import './App.css';
import ReactEcharts from "echarts-for-react";
import 'echarts-gl';

class UserDetails extends Component {


constructor(props) {
    super(props);
    this.state = {
      data: [],
      pages: null,
      loading: true
    };
    const that = this;
    fetch(`/api/user/${this.props.match.params.userId}`)
    .then(function(response) {
        if (response.status >= 400) {
            throw new Error("Bad response from server");
        }
        return response.json();
    })
    .then(res => {
        console.log(res);
        this.setState({
            data: res,
            loading: false
        });
    });
  }

  getOption() {

   return {
      xAxis: {
          data: ["2000-06-05", "2000-06-06", "2000-06-07", "2000-06-08", "2000-06-09", "2000-06-10", "2000-06-11"]
      },
      yAxis: {
          type: 'value'
      },
      tooltip: {
              trigger: 'axis',
              position: function (pt) {
                  return [pt[0], '20%'];
              }
          },
       legend : {
           data : ['3M', '6M']
       },
      series: [{
          name: '3M',
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: 'line'
      },{
          name: '6M',
          data: [20, 32, 1, 34, 290, 330, 320],
          type: 'line'
      }]
    };
  }

  getOptionSurface() {

   return {
              tooltip: {},
              backgroundColor: '#fff',
              visualMap: {
                  show: false,
                  dimension: 2,
                  min: -1,
                  max: 1,
                  inRange: {
                      color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
                  }
              },
              xAxis3D: {
                  type: 'value'
              },
              yAxis3D: {
                  type: 'value'
              },
              zAxis3D: {
                  type: 'value'
              },
              grid3D: {
                  viewControl: {
                      // projection: 'orthographic'
                  }
              },
              series: [{
                  type: 'surface',
                  wireframe: {
                      // show: false
                  },
                  equation: {
                      x: {
                          step: 0.05
                      },
                      y: {
                          step: 0.05
                      },
                      z: function (x, y) {
                          if (Math.abs(x) < 0.1 && Math.abs(y) < 0.1) {
                              return '-';
                          }
                          return Math.sin(x * Math.PI) * Math.sin(y * Math.PI);
                      }
                  }
              }]
          };
  }

  render() {
    const { data, pages, loading } = this.state;
    return (
    <div>
        <ReactEcharts
          option={this.getOption()}
          notMerge={true}
          lazyUpdate={true}/>
        <ReactEcharts
          option={this.getOptionSurface()}
          notMerge={true}
          lazyUpdate={true}/>
    </div>
        );
  }
}

export default UserDetails;
