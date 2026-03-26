<template>
  <!-- 在线用户 -->
  <div
    v-for="u in users"
    :key="u.id"
    class="user-item"
    :class="{
      self: u.id === currentUser,
      active: u.id === activeUser
    }"
    @click="selectUser(u)"
  >
    <!-- 头像 -->
    <img class="avatar" :src="u.avatarUrl" alt="avatar" />

    <!-- 用户信息 -->
    <div class="info">
      <div class="name">
        {{ u.username }}
        <span v-if="String(u.id) === currentUser" class="me">(我)</span>
      </div>
    </div>

    <!-- 在线状态 -->
    <span class="dot"></span>
  </div>
</template>

<script setup>
const props = defineProps({
  users: {
    type: Array,
    default: () => []
  },
  currentUser: String, 
  activeUser: Number   
})

const emit = defineEmits(["selectUser"])

function selectUser(user) {
  emit("selectUser", user.id)
}
</script>

<style scoped>
.user-item {
  padding: 10px;
  margin: 6px 0;
  border-radius: 8px;
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: all 0.2s;
}

.user-item:hover {
  background: #f5f5f5;
}

.user-item.self {
  background: #d9f7be;
}

.user-item.active {
  background: #e6f7ff;
}

/* 头像 */
.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  margin-right: 10px;
  object-fit: cover;
}

/* 用户信息 */
.info {
  flex: 1;
}

.name {
  font-size: 14px;
  font-weight: 500;
}

.me {
  font-size: 12px;
  color: #999;
  margin-left: 4px;
}

/* 在线小绿点 */
.dot {
  width: 8px;
  height: 8px;
  background: #52c41a;
  border-radius: 50%;
}
</style>