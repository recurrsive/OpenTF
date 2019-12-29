import {Actions} from './Actions'
import {fetchJson, stateActionMap, handleAction, handleActions} from './Utility'

const defaultBoard = {
  loading: false,
  board: []
}

export const boardReducer = stateActionMap({
  loading: handleActions({
    [Actions.FETCH_BOARD]: () => true,
    [Actions.FETCH_BOARD_SUCCESS]: () => false,
    [Actions.FETCH_BOARD_FAILURE]: () => false
  }, defaultBoard.loading),

  board: handleAction(Actions.FETCH_BOARD_SUCCESS, (_state, {board}) => board, defaultBoard.board),
})

export const fetchBoardSuccess = board => ({type: Actions.FETCH_BOARD_SUCCESS, board})

export const fetchBoard = (dispatch) => {
  fetchJson('/greeting').then(result => dispatch(fetchBoardSuccess(result)))
}