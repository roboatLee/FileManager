import { defineStore } from "pinia"
import { getToken, setToken, removeToken } from "./token"

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: getToken(),
    userId: null,
    username: null,
    role: null
  }),

  getters: {
    isLogin: (state) => !!state.token
  },

  actions: {
    loginSuccess(data) {
      this.token = data.token
      this.userId = data.userId
      this.username = data.username
      this.role = data.role

      setToken(data.token)
    },

    logout() {
      this.token = null
      this.userId = null
      this.username = null
      this.role = null

      removeToken()
    }
  }
})