import React, {Component} from 'react'

import CardDisplay from './CardDisplay'

export default class CardPreview extends Component{
  render() {
    const card = this.props.card
    const index = this.props.index

    return (
        <div className="col col-2">
          <div className="card" style={{border: "2px solid " + CardDisplay.formatBorder(card)}}>
            <div className="card-header" onClick={evt => this.props.selectCard(index)}>{CardDisplay.formatName(card.name)}
              <span className="cardcost">{CardDisplay.formatCost(card.cost, false, this.props.resources)}</span>
              {this.props.displayReq ? CardDisplay.formatRequirement(card) : <span/>}
              <br/>
              {CardDisplay.tagImages(card.tags)}
              <br/>
              {CardDisplay.formatResources(card, false)}
            </div>
          </div>
        </div>
    )
  }
}