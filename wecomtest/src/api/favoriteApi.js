import { FavoriteControllerApi } from "./generated";
import { http } from './http'

const api = new FavoriteControllerApi(undefined, undefined, http)

export function getFavorites() {
  return api.list()
}

export function addFavorite(data) {
  return api.add(data)
}

export function deleteFavorite(id) {
  return api._delete(id)
}
