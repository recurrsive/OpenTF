import React from 'react'
import {connect} from 'react-redux'
import {Tabs, Tab} from 'react-bootstrap'

import {fetchBoard} from './stores/Gamestate'
import PlayerDisplay from './PlayerDisplay'
import './App.css'
import { Board } from './Board'


class UnconnectedApp extends React.Component {

  componentDidMount = () => {
    this.props.fetchBoard()
  }

  render = () => (
    <div className="App">
      <header className="App-header">
        <Tabs defaultActiveKey="board" id="top-tabs">
          <Tab eventKey="board" title="Board">
            <Board />
          </Tab>
          <Tab eventKey="player" title="Player">
            <PlayerDisplay />
          </Tab>
        </Tabs>
      </header>
    </div>
  )
}

export const App = connect(undefined, dispatch => ({fetchBoard: () => fetchBoard(dispatch)}))(UnconnectedApp)
