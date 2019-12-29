import React from 'react'

const hexSize = 800
const hexCount = 5
const maxC = hexCount - 1

const sqrt3 = Math.sqrt(3)
const cellSize = hexSize / (2*(hexCount * 2 - 1)) * 2 / sqrt3 - 1
const centerX = hexSize/2
const centerY = hexSize/2

const axialToCenter = (q, r) => {
  const x = cellSize * (sqrt3 * q  +  sqrt3/2 * r)
  var y = cellSize * (                3./2 * r)
  return [x, y]
}

function hexagon(ctx, x, y, r) {
  ctx.beginPath()
  let angle = Math.PI / 6
  for (let i = 0; i < 6; i ++) {
    ctx.lineTo(Math.cos(angle) * r + x, Math.sin(angle) * r + y)
    angle += Math.PI / 3 // 60 degrees
  }
  ctx.closePath()
  ctx.stroke()
}


export class Hexagons extends React.Component {

  drawBoard(canvas) {
    if (!canvas) return

    const ctx = canvas.getContext('2d')


    ctx.fillStyle = 'black'
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    ctx.fillStyle = '#d2b48c'
    ctx.beginPath();
    ctx.arc(centerX, centerY, centerX, 0, 2 * Math.PI, false)
    ctx.fill()
     
    ctx.strokeStyle = 'white'

    for (let q = -maxC; q <= maxC; q++) {
      for (let r = -maxC; r <= maxC; r++) {
        const z = 0 - r - q
        if (Math.abs(q) + Math.abs(r) + Math.abs(z) <= 2*maxC) {
          const [x, y] = axialToCenter(q, r)
          hexagon(ctx, x + centerX, y + centerY, cellSize)
        }
      }
    }
  }

  render() {
    return (
      <div>
        <canvas ref={this.drawBoard} width={hexSize} height={hexSize} />
      </div>
    )
  }
}