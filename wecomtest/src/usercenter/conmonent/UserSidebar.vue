<template>
  <div class="sidebar">
    <img :src="user.avatar || defaultAvatar" class="avatar" />
    <h3>{{ user.username }}</h3>

    <div class="stats">
      <p>博客：{{ user.blogCount || 0 }}</p>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  user: Object
});

const defaultAvatar = "https://via.placeholder.com/80";

</script>

<style scoped>
.sidebar {
  width: 220px;
  padding: 20px;
  border-right: 1px solid #eee;
}

.avatar {
  width: 80px;
  border-radius: 50%;
}
</style>