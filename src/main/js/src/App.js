import React from 'react'
import PlayerDisplay from './PlayerDisplay'
import './App.css'
import { Hexagons } from './Hexagons'
import { BoardState } from './BoardState'
export const App = () => (
  <div className="App">
    <header className="App-header">
      <PlayerDisplay/>
      <Hexagons />
    </header>
  </div>
)
