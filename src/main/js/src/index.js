import React from 'react'
import { render } from 'react-dom'
import { createStore } from 'redux'
import { Provider } from 'react-redux'
import {App} from './App'
import {boardReducer} from './stores/Gamestate'

import './index.css'
import 'bootstrap/dist/css/bootstrap.css'

const store = createStore(boardReducer)

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
)

