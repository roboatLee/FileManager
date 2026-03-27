// src/api/http.ts
import axios from 'axios'

export const http = axios.create({
  baseURL: '/api'
})

// ===== 排队队列 & 刷新状态 =====
let isRefreshing = false
let failedQueue: ((token: string) => void)[] = []

// ===== 请求拦截器：自动带 accessToken =====
http.interceptors.request.use(config => {
  const token = localStorage.getItem("accessToken")
  if (token) {
    config.headers.Authorization = "Bearer " + token
  }
  return config
})

// ===== 响应拦截器：401 → 无感刷新 =====
http.interceptors.response.use(
  res => res,
  async err => {
    const originalRequest = err.config

    // 只处理 401 且没有重试过
    if (err.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true

      // 如果正在刷新 token，就排队
      if (isRefreshing) {
        return new Promise(resolve => {
          failedQueue.push((token: string) => {
            originalRequest.headers.Authorization = "Bearer " + token
            resolve(http(originalRequest))
          })
        })
      }

      isRefreshing = true

      try {
        const refreshToken = localStorage.getItem("refreshToken")
        if (!refreshToken) throw new Error("没有 refreshToken，无法刷新")

        // 调用刷新接口（注意：不走拦截器，避免循环）
        const response = await axios.post("/api/refresh", { refreshToken })
        const newToken = response.data.accessToken
        const newRefresh = response.data.refreshToken

        // 保存新 token
        localStorage.setItem("accessToken", newToken)
        localStorage.setItem("refreshToken", newRefresh)

        // 执行排队请求
        failedQueue.forEach(cb => cb(newToken))
        failedQueue = []

        // 重试当前请求
        originalRequest.headers.Authorization = "Bearer " + newToken
        return http(originalRequest)

      } catch (e) {
        // refresh token 也过期 → 清理 token → 跳登录
        localStorage.removeItem("accessToken")
        localStorage.removeItem("refreshToken")
        window.location.href = "/login"
        return Promise.reject(e)
      } finally {
        isRefreshing = false
      }
    }

    return Promise.reject(err)
  }
)