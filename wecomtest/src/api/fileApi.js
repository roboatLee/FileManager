// import axios from 'axios'
import { JsonControllerApi } from './generated'
import { http } from './http'
const api = new JsonControllerApi(undefined, undefined, http)


export function getDefaultFiles() {
  return api.getDefaultFiles()
}

export function getChildFiles(path) {
  return api.getChild(path)

}

export function getParentFiles(path) {
  return api.getParetn(path)
}

export function getContentTxt(path, name) {
  return api.getJsonFile2txt(path,name)
}

export function getDisk() {
  return api.getDisks()
}

export function readFileContent(path,name){
  return api.readFileContent(path,name)
}