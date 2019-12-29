import React, {Component} from 'react'
import URLMap from './URLMap'
import connect from 'react-redux/es/connect/connect'

class ProductionDisplay extends Component {
  state = {}

  render() {
    if (!this.props.resources) return <div>Loading</div>
    return <div>{this.resourceDisplay(this.props.resources)}</div>
  }

  icon(resource) {
    return <img src={URLMap.mapResourceIcon(resource)} width="20px" height="20px"/>
  }

  resourceDisplay(player) {
    return <div className="res-table">
      <span className="res-display">{this.icon('score')} Terraforming Rating: {this.props.score}</span>
      <span className="res-display">{this.icon('mc')} Mega Credits: {player.mc.amount}
        {' (' + (player.mc.production >= 0 ? '+' : '') + player.mc.production})</span>
      <span className="res-display">{this.icon('steel')} Steel: {player.steel.amount} {' (+' + player.steel.production})</span>
      <span className="res-display">{this.icon('titanium')} Titanium: {player.titanium.amount} (+{player.titanium.production})</span>
      <span className="res-display">{this.icon('plants')} Plants: {player.plants.amount} {' (+' + player.plants.production})</span>
      <span className="res-display">{this.icon('energy')} Energy: {player.energy.amount} {' (+' + player.energy.production})</span>
      <span className="res-display">{this.icon('heat')} Heat: {player.heat.amount} {' (+' + player.heat.production})</span>
    </div>
  }
}

const mapStateToProps = state => {
  return {
    player: state.players && state.active ? state.players[state.active].resources : null
  }
}

const ProductionDisplayConnected = connect(mapStateToProps)(ProductionDisplay)

export default ProductionDisplayConnected