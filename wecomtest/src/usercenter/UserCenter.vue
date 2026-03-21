<template>
  <div class="user-center">
    <h1>👤 用户中心</h1>

    <div class="user-card">
      <!-- 👇 头像 -->
      <div class="avatar-box">
        <img v-if="user.avatarUrl" :src="user.avatarUrl" class="avatar" />
        <div v-else class="avatar placeholder">无头像</div>
      </div>
      <p><strong>ID：</strong>{{ user.id }}</p>
      <p><strong>用户名：</strong>{{ user.username }}</p>
      <p><strong>角色：</strong>{{ user.role }}</p>
    </div>

    <div class="upload-area">
      <!-- 选择文件 -->
      <input type="file" accept="image/*" @change="handleFileChange" />

      <!-- 预览 -->
      <div v-if="previewUrl">
        <p>预览：</p>
        <img :src="previewUrl" class="preview" />
      </div>

      <!-- 上传按钮 -->
      <button @click="upload">上传头像</button>

      <!-- 返回URL -->
      <div v-if="imageUrl">
        <p>上传成功：</p>
        <a :href="imageUrl" target="_blank">{{ imageUrl }}</a>

        <p>服务器头像：</p>
        <img :src="imageUrl" class="preview" />
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import type { UserVo } from './moudle/user'
import { onMounted, ref } from 'vue'
import { getUserByToken, uploadAvater } from './api/userCenterApi'

const user = ref<UserVo>({
  id: 0,
  username: '',
  role: '',
  avatarUrl: ''
})

const file = ref<File | null>(null)
const previewUrl = ref("")
const imageUrl = ref("")

// 选择文件
function handleFileChange(e: Event) {
  const target = e.target as HTMLInputElement
  const f = target.files?.[0]
  if (!f) return

  file.value = f

  // 本地预览
  previewUrl.value = URL.createObjectURL(f)
}

// 上传头像
async function upload() {
  if (!file.value) {
    alert("请选择文件")
    return
  }

  try {
    const res = await uploadAvater(file.value)

    imageUrl.value = res.data   // 返回的URL

    // 👇 关键：更新当前用户头像
    user.value.avatarUrl = res.data

    alert("上传成功 🎉")

  } catch (err) {
    console.error(err)
    alert("上传失败 ❌")
  }
}

onMounted(async () => {
  const res = await getUserByToken()
  user.value = res.data
  console.log(user.value)
})
</script>

<style scoped>
.user-center {
  max-width: 600px;
  margin: 40px auto;
}

.user-card {
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 8px;
  background: #fafafa;
}

.upload-area {
  margin-top: 20px;
}

.preview {
  max-width: 200px;
  border-radius: 6px;
  margin-top: 10px;
}
.avatar-box {
  text-align: center;
  margin-bottom: 15px;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #ddd;
}

.placeholder {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: #eee;
  color: #888;
  font-size: 14px;
}
</style>