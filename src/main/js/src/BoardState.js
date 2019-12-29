import React from 'react'
import { fetchBoard } from './stores/Board'
import { connect } from 'react-redux'

export class UnconnectedBoardState extends React.Component {
  componentDidMount() {
    this.props.fetchBoard()
  }

  render() {
    return <p>Board has {this.props.board.length} els</p>
  }
}

export const BoardState = connect(
  ({board}) => ({board}),
  dispatch => ({fetchBoard: () => fetchBoard(dispatch)})
)(UnconnectedBoardState)

