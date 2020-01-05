import { Actions } from './Actions'
import { fetchJson, stateActionMap, handleAction, handleActions } from './Utility'

const defaultBoard = {
  loading: false,
  gamestate: {board: undefined}
}

export const boardReducer = stateActionMap({
  loading: handleActions({
    [Actions.FETCH_GAME_STATE]: () => true,
    [Actions.FETCH_GAME_STATE_SUCCESS]: () => false,
    [Actions.FETCH_GAME_STATE_FAILURE]: () => false
  }, defaultBoard.loading),

  gamestate: handleAction(Actions.FETCH_GAME_STATE_SUCCESS, (_state, { gamestate }) => gamestate, defaultBoard.gamestate),
})

export const fetchBoardSuccess = gamestate => ({ type: Actions.FETCH_GAME_STATE_SUCCESS, gamestate: gamestate })

const exCard1 = {
  name: "Test card with a long name",
  cost: 26,
  tags: ["Space", "Plants"],
  text: "Gain two extra corporations.",
  resource_name: "Floater",
  resource_count: 8
}

const exCard2 = {
  name: "Trees",
  cost: 11,
  tags: ["Plants"],
  text: "Increase your plant production two stages and gain a plant.",
  requirement: "3 Oceans"
}

const exCard3 = {
  name: "Trees",
  cost: 11,
  tags: ["Plants"],
  text: "Increase your plant production two stages and gain a plant.",
  requirement: "3 Oceans"
}

const exCard4 = {
  name: "Trees",
  cost: 11,
  tags: ["Plants"],
  text: "Increase your plant production two stages and gain a plant.",
  requirement: "3 Oceans"
}

const exCard5 = {
  name: "Trees",
  cost: 11,
  tags: ["Plants"],
  text: "Increase your plant production two stages and gain a plant.",
  requirement: "3 Oceans"
}

const exPlayers = [{
  resources: {
    mc: { amount: 20, production: -3 },
    steel: { amount: 0, production: 2 },
    titanium: { amount: 1, production: 0 },
    plants: { amount: 3, production: 0 },
    energy: { amount: 3, production: 3 },
    heat: { amount: 16, production: 7 }
  },
  score: 27,
  tableau: [exCard1, exCard2],
  hand: [exCard3, exCard4, exCard5]
}]

export const fetchBoard = (dispatch) => {
  fetchJson('/greeting').then(result => dispatch(fetchBoardSuccess(
    {
      players: exPlayers,
      active: 0,
      board: result
    })))
}