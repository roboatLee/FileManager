<template>
  <div class="user-center">
    <h1>👤 用户中心</h1>

    <div class="user-card">
      <p><strong>ID：</strong>{{ user.id }}</p>
      <p><strong>用户名：</strong>{{ user.username }}</p>
      <p><strong>角色：</strong>{{ user.role }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { UserVo } from './moudle/user'
import { onMounted, ref } from 'vue'
import { getUserByToken } from './api/userCenterApi'

const user = ref<UserVo>({
  id: 0,
  username: '',
  role: ''
})

onMounted(async () => {
  const res = await getUserByToken()
  user.value = res.data
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
</style>