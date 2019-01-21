import React, { Component } from 'react';
import './App.css';
import { Stage, Layer, RegularPolygon } from 'react-konva';
import board from './board'

const slotColors = {
  Ocean: '#cceeff',
  Desert: '#ffaa00',
  Noctis: '#ffd480'
}
const tileColors = {
  Ocean: '#0066ff',
  Plants: '#33cc33',
  City: '#e6e6e6',
  Special: '#996600',
  CapitalCity: 'White'
}


class App extends Component {
  render() {
    const polys = []
    let index = 0;
    for (let row=0; row < 9; row++) {
      const cols = (row <= 4) ? row + 5 : 13 - row;
      const ofs = (row <= 4) ? 4 - row : row - 4;

      for (let col=0; col < cols; col++) {
        if (index === board.board.length) {
          throw new Error(`Ran out of slots at ${index} row=${row} col=${col}`)
        }
        const cell = board.board[index++];
        const bg = cell.tile ? tileColors[cell.tile.tileType] : slotColors[cell.slot.slotType]
        polys.push(
          <RegularPolygon x={250 + ofs * 42 + col * 85} y={row*78+100} 
          fill={bg}
          sides = {6} radius={50} stroke='white' />
        )
      }
    }
    return (
      <Stage width={window.innerWidth} height={window.innerHeight}>
        <Layer>
          {polys}
        </Layer>
      </Stage>
    )
  }
}

export default App;
