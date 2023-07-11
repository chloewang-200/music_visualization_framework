import React from 'react';
import ReactEcharts from "echarts-for-react"; 
import './App.css'; // import the css file to enable your styles.
import Dropdown from 'react-bootstrap/Dropdown';
import ReactLoading from 'react-loading'
import DropdownButton from 'react-bootstrap/DropdownButton';

/**
 * Define the type of the props field for a React component
 */
//interface Props { }

/**
 * Using generics to specify the type of props and state.
 * props and state is a special field in a React component.
 * React will keep track of the value of props and state.
 * Any time there's a change to their values, React will
 * automatically update (not fully re-render) the HTML needed.
 * 
 * props and state are similar in the sense that they manage
 * the data of this component. A change to their values will
 * cause the view (HTML) to change accordingly.
 * 
 * Usually, props is passed and changed by the parent component;
 * state is the internal value of the component and managed by
 * the component itself.
 */
class App extends React.Component {
  initialized = false;
  /**
   * @param props has type Props
   */
  constructor(props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
    // this.state = { grids: [], winner: null, currPlayer: null, gameWin: false, msg: '' }
    this.state = { setDataPlugin: false, setVisualPlugin: false, 
      dataPlugin: null, userSubmitted: false, visualPlugin: null, chartJSON: null,
    userInput: null, instruction: "", dataPlugins: [], visualPlugins: [], songNum: 0,
    invalidSongs: []}
    this.handleChange = this.handleChange.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }
  

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */

  async start(){
    const response = await fetch("/start");
    
    const json = await response.json();
    this.setState(json)
  }

  barChart = async () => {
    const response = await fetch('/barChart');
    const json = await response.json();
    this.setState( prevState => {
      return { 
        ...prevState,
        setVisualPlugin: true,
      };
    });
  }

  stackedLineChart = async () => {
    const response = await fetch('/stackedLineChart');
    const json = await response.json();
    this.setState(json);
  }

  pieChart = async () => {
    const response = await fetch('/pieChart');
    const json = await response.json();
    this.setState(json);
  }

  txtInput = async () => {
    const response = await fetch('/manualInput');
    const json = await response.json();
    this.setState(json);
  }

  billboardInput = async () => {
    const response = await fetch('/billboardInput');
    const json = await response.json();
    this.setState(json);
  }

  spotifyInput = async () => {
    const response = await fetch('/spotifyInput');
    const json = await response.json();
    this.setState(json);
  }

  musicbedInput = async () => {
    const response = await fetch('/musicbedInput');
    const json = await response.json();
    this.setState(json);
  }


  handleChange = event => {
    this.setState({userInput: event.target.value});
  }

  handleSubmit = async () =>  {
    this.setState( prevState => {
      return { 
        ...prevState,
        userSubmitted: true,
      };
    });
    var input;
    if (this.state.userInput != null) {
      input = this.state.userInput
    }
    else {
      input = ""
    }
    const response = await fetch(`/input?s=${input}`);
    const json = await response.json();
    this.setState(json);
    
    //alert('A name was submitted: ' + this.state.userInput);
    
  }
  createDataDropdown() {
    if (this.state.dataPlugins.length === 0) {
      return (
        <span>No dataPlugins loaded</span>
      )
    } else {
      return (
        <div>
          {this.state.dataPlugins.map((plugin, index) => this.createDataPlugin(plugin.name, index))}
        </div>
      )
    }
  }

  createVisualDropdown() {
      if (this.state.visualPlugins.length === 0) {
        return (
          <span>No dataPlugins loaded</span>
        )
      } else {
        return (
          <div>
              {this.state.visualPlugins.map((plugin, index) => this.createVisualPlugin(plugin.name, index))}
          </div>
        );
      }
  }


  createDataPlugin(name, index) {
    return (
      <div key={index}>
        <Dropdown.Item id = "dropItem" href="/" onClick={this.chooseDataPlugin(index)}>{name}</Dropdown.Item>
      </div>
    )
  }

  createVisualPlugin(name, index) {
    return (
      <div key={index}>
        <Dropdown.Item id = "dropItem" href="/" onClick={this.chooseVisualPlugin(index)}>{name}</Dropdown.Item>
      </div>
    )
  }



  chooseDataPlugin(i) {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/dataPlugin?i=${i}`)
      const json = await response.json();

      this.setState(json)
    }
  }

  chooseVisualPlugin(i) {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/visualPlugin?i=${i}`)
      const json = await response.json();
      this.setState( prevState => {
        return { 
          ...prevState,
          setVisualPlugin : true
        };
      })
      this.setState(json);
   }

  }



  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */
  componentDidMount() {
    /**
     * setState in DidMount() will cause it to render twice which may cause
     * this function to be invoked twice. Use initialized to avoid that.
     */
    if (!this.initialized) {
      this.start();
      this.initialized = true;
    }
  }


  choosePlugin(i) {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/plugin?i=${i}`)
      const json = await response.json();

      this.setState(json)
    }
  }

  handleGoBack = async () => {
    this.state.userSubmitted = false;
    this.setState( prevState => {
      return { 
        ...prevState,
        chartJSON: "",
        userInput: null
      };
    });
    this.setState( prevState => {
      return { 
        ...prevState,
        setDataPlugin: false,
        setVisualPlugin: false,
        userInput: null
      };
    });
    this.start()   
  }

  // startFramework = async () => {
  //   const response = await fetch(`/startFramework`);
  //   const json = await response.json();
  //   this.setState(json);
  // }

  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render() {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    if (!this.state.setDataPlugin && !this.state.setVisualPlugin) {
      if (this.state.dataPlugins.length === 0) {
        return (
          <div id="intruction">No data Plugins loaded</div>
        )
      }
      return (
        <div id ="dropdown">
          <div id="intruction">Please choose a data plugin to load</div>
        <DropdownButton id="data-dropdown" title="Data Plugin">
          {this.createDataDropdown()}</DropdownButton>
      </div>
      );
    } else 
    if (this.state.setDataPlugin === true && (this.state.setVisualPlugin === false)) {
      if (this.state.dataPlugins.length === 0) {
        return (
          <div id="intruction">No visual Plugins loaded</div>
        )
      }
      return (
        <div id ="dropdown">
          <div id="intruction">Please choose a visual plugin to load</div>
            <DropdownButton id="visual-dropdown" title="Visual Plugin">
          {this.createVisualDropdown()}</DropdownButton>
      </div>
      );
    } else if (this.state.setDataPlugin === true && this.state.setVisualPlugin === true) {
        if (this.state.userSubmitted == false) {

          return (
            <div>
            {this.state.instruction}
            <form onSubmit={this.handleSubmit}>
            <label>
              Name:
              <input type="text" value={this.state.value} onChange={this.handleChange} />
            </label>
            <input type="submit" value="Submit" />
          </form>
          </div>
        );
      }
      else {
        if (this.state.chartJSON != "") {
          var msg1
          var msg2
          
          if (this.state.invalidSongs.length != 0) {
            msg1 =  <div id="intruction">We found {this.state.songNum} songs successfully!</div>
            msg2 = <div id="intruction">Songs we cannot find : {this.state.invalidSongs.toString()} </div>
          }
          else {

            if (this.state.songNum == 0) {
              msg1 = <div id="intruction">Please enter correct input</div>
            } else {
              msg1 = <div id="intruction">We found all songs successfully!</div>
            }
          
          }
          return (
            <div>
            {msg1}
            {msg2}
            <ReactEcharts
              option={JSON.parse(this.state.chartJSON)}
              notMerge={true}
              lazyUpdate={true}
              theme={"theme_name"}
              style={{height: '500px'}}
            />
            <button className="btn btn-dark btn-lg download-button" type="button" 
                        onClick={this.handleGoBack}>go back</button>
            </div>
          );
        } else {
            return (
              <div>
                <ReactLoading className="center" type={"bars"} color={"grey"} height={'20%'} width={'20%'} />
                <div id="intruction">sentiment analysis is slow... please give us some time :\</div>
              </div>
            );
          }
      }
    
  }
}
}
export default App;