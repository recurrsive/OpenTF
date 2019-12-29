export const handleAction = (actionType, reducer, defaultState) => (state, action) => 
  (action.type === actionType) ? reducer(state, action) : (state || defaultState)

export const handleActions = (actionMap, defaultState) => (state, action) => 
  actionMap[action.type] ? actionMap[action.type](state, action) : (state || defaultState)

export const stateActionMap = actionMap => (state, action) => {
  const result = {}
  Object.keys(actionMap).forEach((key) => {
    result[key] = actionMap[key](state, action)
  })
  return result
}

class HttpError extends Error {
  constructor(code, message) {
    super(message)
    this.name = 'HttpError'
    this.code = code
  }
}

const checkStatus = (response) => {
  if (response.status >= 200 && response.status < 300) return response 

  throw new HttpError(response.status, response.statusText)
}

const fetchOptions = {credentials: 'include'}

export const fetchJson = (url) => fetch(url, fetchOptions).then(checkStatus).then(response => response.json())
