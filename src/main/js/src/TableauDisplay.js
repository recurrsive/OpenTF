import React, {Component} from 'react'
import CardDisplay from './CardDisplay'

export default class TableauDisplay extends Component {
  render() {
    const tableau = this.props.tableau

    return (
        <div className="table">
          <div className="row">
            {tableau.map(card => <CardDisplay card={card} displayReq={true} inHand={false}/>)}
          </div>
        </div>
    )
  }
}