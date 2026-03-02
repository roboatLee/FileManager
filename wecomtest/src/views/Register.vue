<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 style="text-align:center;margin-bottom:20px;">用户注册</h2>

      <el-form :model="form" @keyup.enter="handleRegister">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>

        <el-form-item label="确认密码">
          <el-input
            v-model="confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            style="width:100%"
            :loading="loading"
            @click="handleRegister"
          >
            注册
          </el-button>
        </el-form-item>

        <div style="text-align:center;">
          已有账号？
          <el-link type="primary" @click="goLogin">去登录</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue"
import { useRouter } from "vue-router"
import { ElMessage } from "element-plus"
import { authApi } from "@/api/client"

const router = useRouter()

const form = reactive({
  username: "",
  password: ""
})

const confirmPassword = ref("")
const loading = ref(false)

const handleRegister = async () => {
  if (!form.username || !form.password) {
    ElMessage.error("请输入完整信息")
    return
  }

  try {
    await authApi.register(form)

    ElMessage.success("注册成功，请登录")
    router.push("/login")
  } catch (e) {
    ElMessage.error("注册失败")
  }
}

const goLogin = () => {
  router.push("/login")
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f7fa;
}

.login-card {
  width: 400px;
}
</style>