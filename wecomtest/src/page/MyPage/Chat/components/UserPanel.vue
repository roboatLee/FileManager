<template>
    <!-- 在线用户 -->
    <div v-for="u in users" :key="u" class="user-item" :class="{ self: u === currentUser, active: u === activeUser }"
        @click="selectUser(u)">
        <span class="dot"></span>
        {{ u }}
    </div>

</template>
<script setup>

const props = defineProps({
  users: {
    type: Array,
    default: () => []
  },
  currentUser: String,
  activeUser: String
})
const emit = defineEmits(["selectUser"])

function selectUser(user){
  emit("selectUser", user)
}

</script>

<style scoped>
.user-item{
  padding:8px;
  margin:4px 0;
  border-radius:6px;
  display:flex;
  align-items:center;
  cursor:pointer;
}

.user-item.self{
  background:#d9f7be;
}

.user-item.active{
  background:#e6f7ff;
}

.dot{
  width:8px;
  height:8px;
  background:#52c41a;
  border-radius:50%;
  margin-right:6px;
}
</style>