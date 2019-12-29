import React from 'react'
import './App.css'
import { Hexagons } from './Hexagons'
import { BoardState } from './BoardState'

export const App = () => (
  <div className="App">
    <header className="App-header">
      <BoardState />
      <Hexagons />
    </header>
  </div>
)
