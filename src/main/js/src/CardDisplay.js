import React, {Component} from 'react'
import URLMap from './URLMap'

class CardDisplay extends Component {
  render() {
    const card = this.props.card
    const displayReq = this.props.displayReq

    return (
        <div className="col col-2">
          <div className="card" style={{border: "2px solid " + CardDisplay.formatBorder(card)}}>
            <div className="card-header">{CardDisplay.formatName(card.name)}
              <span className="cardcost">{CardDisplay.formatCost(card.cost, this.props.inHand, this.props.resources)}</span>
              {this.props.displayReq ? CardDisplay.formatRequirement(card) : <span/>}
            </div>
            <div className="card-body">{CardDisplay.tagImages(card.tags)}{CardDisplay.formatResources(card)}</div>
            <div className="card-body">{CardDisplay.formatText(card.text)}</div>
          </div>
        </div>
    )
  }

  static formatText(text) {
    const sentences = text.split("\n")
    return sentences.map(sentence => <span>{sentence}<br/></span>)
  }

  static formatName(name) {
    if (!name) return ""
    if (name.length > 17) {
      return <span className="cardname">{name.substring(0, 13) + (name.charAt(13) === ' ' ? '' : name.charAt(13))}
        <span className="ellipsis">...</span></span>
    }
    else {
      return <span className="cardname-spaced" style={{"paddingRight": "" + (17-name.length)*7 + "px"}}>{name}</span>
    }
  }

  static formatCost(cost, inHand, resources) {
    if (!inHand) {
      return <span className="cost-neutral">{cost}</span>
    }
    else if (!resources || resources.mc.amount < cost) {
      return <span className="cost-unaffordable">{cost}</span>
    }
    else {
      return <span className="cost-affordable">{cost}</span>
    }
  }

  static tagImages(tags) {
    return tags.map(tag => <img src={URLMap.mapTag(tag)} width="20px" height="20px"/>)
  }

  static formatRequirement(card) {
    if (!card || !card.requirement) return null
    if (!card.requirementsMet) {
      return (
          <span>
            <br/>
            <span style={{color: "red"}}>Needs: {card.requirement}</span>
          </span>
      )
    }
    return (
        <span>
          <br/>
          <span style={{color: "gray"}}>Needs: </span>
          <span style={{"font-style": "italic", color: "gray"}}>{card.requirement}</span>
        </span>
    )
  }

  static formatResources(card, inHand) {
    if (inHand || !card.resource_name) return <span/>
    return (
        <span>
          <br/>
          <span style={{color: "gray"}}>{card.resource_name} resources: </span>
          <span style={{color: "blue"}}>{card.resource_count}</span>
        </span>
    )
  }

  static formatBorder(card) {
    if (card.tags.filter(t => t === "Event").length > 0) {
      return "orange"
    }
    else if (card.text.includes("ACTION") || card.text.includes("WHEN")
        || card.text.includes("cost") || card.text.includes("could")) {
      return "blue"
    }
    else {
      return "green"
    }
  }
}

export default CardDisplay