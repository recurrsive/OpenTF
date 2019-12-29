import React, {Component} from 'react'
import URLMap from './URLMap'

class CardDisplay extends Component {
  render() {
    const card = this.props.card
    const displayReq = this.props.displayReq

    return (
        <div className="col col-2">
          <div className="card">
            <div className="card-header">{this.formatName(card.name)}
              <span className="cardcost">{this.formatCost(card.cost)}</span>
              {this.props.displayReq ? this.formatRequirement(card.requirement) : <span/>}
            </div>
            <div className="card-body">{this.tagImages(card.tags)}{this.formatResources(card)}</div>
            <div className="card-body">{card.text}</div>
          </div>
        </div>
    )
  }

  formatName(name) {
    if (!name) return ""
    if (name.length > 17) {
      return <span className="cardname">{name.substring(0, 16) + (name.charAt(16) === ' ' ? '' : name.charAt(16))}
        <span className="ellipsis">...</span></span>
    }
    else {
      return <span className="cardname-spaced" style={{"paddingRight": "" + (20-name.length)*7 + "px"}}>{name}</span>
    }
  }

  formatCost(cost) {
    if (!this.props.inHand) {
      return <span className="cost-neutral">{cost}</span>
    }
    else if (!this.props.resources || this.props.resources.mc.amount < cost) {
      return <span className="cost-unaffordable">{cost}</span>
    }
    else {
      return <span className="cost-affordable">{cost}</span>
    }
  }

  tagImages(tags) {
    return tags.map(tag => <img src={URLMap.mapTag(tag)} width="20px" height="20px"/>)
  }

  formatRequirement(req) {
    if (!req) return null
    return (
        <span>
          <br/>
          <span style={{color: "gray"}}>Needs: </span>
          <span style={{"font-style": "italic", color: "gray"}}>{req}</span>
        </span>
    )
  }

  formatResources(card) {
    if (this.props.inHand || !card.resource_name) return <span/>
    return (
        <span>
          <br/>
          <span style={{color: "gray"}}>{card.resource_name} resources: </span>
          <span style={{color: "blue"}}>{card.resource_count}</span>
        </span>
    )
  }
}

export default CardDisplay