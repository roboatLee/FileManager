<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 style="text-align:center;margin-bottom:20px;">用户登录</h2>

      <el-form :model="form" @keyup.enter="handleLogin">
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

        <el-form-item>
          <el-button type="primary" style="width:100%" @click="handleLogin">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from "vue"
import { useRouter } from "vue-router"
import { ElMessage } from "element-plus"
import { useAuthStore } from "@/auth/authStore"

// ⭐ 如果你 openapi 自动生成了 login API
// 改成你生成的实际方法名
// import { login } from "@/api/user"   // ← 这里按你实际生成路径改

const router = useRouter()
const authStore = useAuthStore()

const form = reactive({
  username: "",
  password: ""
})

const handleLogin = async () => {
  if (!form.username || !form.password) {
    ElMessage.error("请输入用户名和密码")
    return
  }

  try {
    const res = await login(form)

    // 假设后端返回：
    // {
    //   token,
    //   userId,
    //   username,
    //   role
    // }

    authStore.loginSuccess(res)

    ElMessage.success("登录成功")

    router.push("/")
  } catch (e) {
    ElMessage.error("登录失败")
  }
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