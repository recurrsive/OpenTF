import React from 'react'
import connect from 'react-redux/es/connect/connect'

const hexSize = 800
const hexCount = 5
const maxC = hexCount - 1

const sqrt3 = Math.sqrt(3)
const cellSize = hexSize / (2 * (hexCount * 2 - 1)) * 2 / sqrt3 - 1
const centerX = hexSize / 2
const centerY = hexSize / 2

const axialToCenter = (q, r) => {
  const x = cellSize * (sqrt3 * q + sqrt3 / 2 * r)
  var y = cellSize * (3. / 2 * r)
  return [x, y]
}

function hexagon(ctx, x, y, r) {
  ctx.beginPath()
  let angle = Math.PI / 6
  for (let i = 0; i < 6; i++) {
    ctx.lineTo(Math.cos(angle) * r + x, Math.sin(angle) * r + y)
    angle += Math.PI / 3 // 60 degrees
  }
  ctx.closePath()
  ctx.stroke()
}


const tileColors = {
  Plants: 'green',
  Ocean: 'blue',
  City: 'grey'
}

function hexpoly(x, y, r) {
  const points = []
  let angle = Math.PI / 6
  for (let i = 0; i < 6; i++) {
    points.push(`${Math.cos(angle) * r + x},${Math.sin(angle) * r + y}`)
    angle += Math.PI / 3 // 60 degrees
  }
  return points.join(',')
}


export class UnconnectedBoard extends React.Component {

  state = {
    lastClick: ''
  }

  drawBoard = (canvas) => {
    if (!canvas) return

    // Note - this is only called once, doesn't respond to state

    const ctx = canvas.getContext('2d')


    ctx.fillStyle = 'black'
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    ctx.fillStyle = '#d2b48c'
    ctx.beginPath()
    ctx.arc(centerX, centerY, centerX, 0, 2 * Math.PI, false)
    ctx.fill()

    ctx.strokeStyle = 'white'

    for (let q = -maxC; q <= maxC; q++) {
      for (let r = -maxC; r <= maxC; r++) {
        const z = 0 - r - q
        if (Math.abs(q) + Math.abs(r) + Math.abs(z) <= 2 * maxC) {
          const [x, y] = axialToCenter(q, r)
          hexagon(ctx, x + centerX, y + centerY, cellSize)
        }
      }
    }


    // ctx.strokeStyle = 'green'
    // ctx.beginPath()
    // ctx.moveTo(0, 0)
    // ctx.lineTo(400, 400)
    // ctx.stroke()

  }

  render = () => {

    const els = []
    // if (this.props.board) {
    //   for (let row = 0; row < this.props.board.length; row++) {
    //     const boardRow = this.props.board[row]
    //     for (let col = 0; col < boardRow.length; col++) {
    //       if (boardRow[col].tileSlotType) {
    //         const [x, y] = axialToCenter(col-3, row-3)
    //         const style = {position: 'absolute', top: `${y+centerX}px`, left: `${x+centerX}px`}
    //         els.push(<div style={style}>{`${row},${col}`}</div>)
    //       }
    //     }
    //   }

    if (this.props.board) {
      for (let row = 0; row < this.props.board.length; row++) {
        const boardRow = this.props.board[row]
        for (let col = 0; col < boardRow.length; col++) {
          const {tileSlotType, bonuses, tile, q, r} = boardRow[col]
          if (tileSlotType) {
            const [x, y] = axialToCenter(q, r)
            let fill = 'rgba(0, 0, 0, 0.0)'
            if (tile) {
              fill = tileColors[tile.tileType] || 'orange'
            } else if (tileSlotType === 'Ocean') {
              fill = 'rgba(0,100,250,0.2)'
            }

            const style = {stroke: 'white', ['stroke-width']: 2, fill}
            els.push(<polygon points={hexpoly(x+centerX, y+centerY, cellSize)} style={style} onClick={() => this.setState({lastClick: `${q},${r}`})} />)
            if (tile) {
              els.push(<rect x={x+centerX-12} y={y+centerY-12} width='24' height='24' rx='6' fill='rgb(0, 0, 255, 0.8)'/>)
            }
            else  {
              let bx = -10
              let by = -40
              bonuses.forEach(bonus => {
                if (bonus.bonusType) {
                  for (let i = 0; i < bonus.count; i++) {
                    els.push(<image x={x+centerX+bx} y={y+centerY+by} width='20' height='20' href={`/${bonus.bonusType}-bonus.png`} />)
                    bx -= 15
                    by += 15
                  }
                }
              })
            }
          }
        }
      }
    }

    const polys = []

    for (let q = -maxC; q <= maxC; q++) {
      for (let r = -maxC; r <= maxC; r++) {
        const z = 0 - r - q
        if (Math.abs(q) + Math.abs(r) + Math.abs(z) <= 2 * maxC) {
          const [x, y] = axialToCenter(q, r)
          polys.push(<polygon points={hexpoly(x+centerX, y+centerY, cellSize)} style={{stroke: 'white', ['stroke-width']: 2}} />)
        }
      }
    }

    return (
      <div>
        <p>{this.state.lastClick}</p>
        <div id='outerid' style={{ position: 'relative', width: hexSize, height: hexSize }} >
          <svg height={hexSize} width={hexSize}>
            <image x='0' y='0' width={hexSize} height={hexSize} href='/board_bg_planet.png'></image>
            {els}
          </svg>
        </div>
      </div>
    )
  }
}

export const Board = connect(({ gamestate }) => ({ board: gamestate.board }))(UnconnectedBoard)
