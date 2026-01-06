import axios from 'axios'

export function getDefaultFiles() {
  return axios.get('/api/file/getdefaultfiles')
}

export function getChildFiles(path) {
  return axios.get('/api/file/getchild', {
    params: { path }
  })
}

export function getParentFiles(path) {
  return axios.get('/api/file/getparent', {
    params: { path }
  })
}

export function getContentTxt(path, name) {
  return axios.get('/api/file/getcontenttxt', {
    params: { path ,name}
  })
}