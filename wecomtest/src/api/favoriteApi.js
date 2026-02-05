import { FavoriteControllerApi } from "./generated";
import { http } from './http'

const api = new FavoriteControllerApi(undefined, undefined, http)

export function getFavorites() {
  return api.get('/api/favorite')
}

export function addFavorite(data) {
  return api.post('/api/favorite', data)
}

export function deleteFavorite(id) {
  return api.delete(`/api/favorite/${id}`)
}
