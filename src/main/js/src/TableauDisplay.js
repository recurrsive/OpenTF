import React, {Component} from 'react'
import CardDisplay from './CardDisplay'
import CardPreview from './CardPreview'

export default class TableauDisplay extends Component {
  state = {}

  componentDidMount() {
    this.setState({selected: 0})
  }

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

  displayPreviews() {
    let result = [this.props.tableau.length / 4 + 1]
    let row = []
    for (let i = 0; i < this.props.tableau.length; ++i) {
      if (i % 4 === 0) {
        result[i/4] = row
        row = []
      }
      const card = this.props.tableau[i]
      row[i%4] = <CardPreview card={card} displayReq={true} inHand={false}/>
    }
    return result
  }

  displaySelectedCard() {
    if (this.props.tableau.length === 0) {
      return <div/>
    }
    else if (this.state.selected >= this.props.tableau.length) {
      this.setState({selected: 0})
    }
    return <CardDisplay card={this.props.tableau[this.state.selected]} displayReq={true} inHand={false}/>
  }
}