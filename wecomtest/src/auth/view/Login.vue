<template>
    <div class="login-container">
        <el-card class="login-card">
            <h2 style="text-align:center;margin-bottom:20px;">用户登录</h2>

            <el-form :model="form" @keyup.enter="handleLogin">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" placeholder="请输入用户名" />
                </el-form-item>

                <el-form-item label="密码">
                    <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" style="width:100%" @click="handleLogin">
                        登录
                    </el-button>
                </el-form-item>
            </el-form>
            <div style="text-align:center;">
                没有账号？
                <el-link type="primary" @click="router.push('/register')">
                    去注册
                </el-link>
            </div>
        </el-card>
    </div>

</template>

<script setup>
import { reactive } from "vue"
import { useRouter } from "vue-router"
import { ElMessage } from "element-plus"
import { useAuthStore } from "@/auth/store/authStore"
import { authApi } from "@/api/client"

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
        const res = await authApi.login(form)

        const token = res.data.token   // ⭐ 正确取值
        const username = res.data.username   // ⭐ 取出来
        const userId= res.data.userId // ⭐ 取出来
        const refreshToken= res.data.refreshToken // ⭐ 取出来

        console.log(res.data)

        localStorage.setItem("token", token)
        localStorage.setItem("username", username)   // ⭐ 加这一行
        localStorage.setItem("userId", userId)
        localStorage.setItem('refreshToken',refreshToken)
        
        authStore.loginSuccess(res.data)  // ⭐ 传 data

        ElMessage.success("登录成功")

        router.push("/")
    } catch (e) {
        ElMessage.error("登录失败")
        console.log(e)
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
