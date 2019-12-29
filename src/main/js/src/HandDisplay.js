import React, {Component} from 'react'
import CardDisplay from './CardDisplay'

export default class HandDisplay extends Component {
  render() {
    const cards = this.props.cards
    const resources = this.props.resources

    return (
        <div className="table">
          <div className="row">
            {cards.map(card => <CardDisplay card={card} resources={resources} displayReq={true} inHand={true}/>)}
          </div>
        </div>
    )
  }
}