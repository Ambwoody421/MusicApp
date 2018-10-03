import Config from './config'

export function status(response) {
  if (response.status >= 200 && response.status < 300) {
    return Promise.resolve(response)
  } else if (response.status === 403) {
    window.location.assign('http://'+Config.ip+':3000/Login')
  } else {
    return Promise.reject(new Error(response.statusText))
  }
}

export function json(response) {
  return response.json()
}

export function text(response) {
  return response.text()
}


