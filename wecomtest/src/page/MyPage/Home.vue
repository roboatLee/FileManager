<template>
  <h1>首页</h1>
  <div class="nav">
    <RouterLink to="/chat">chat</RouterLink>
    <RouterLink to="/blog">blog</RouterLink>
    <RouterLink to="/todo">todo</RouterLink>
    <RouterLink to="/minio">minio</RouterLink>
    <RouterLink to="/myprofile">user</RouterLink>
    <RouterLink to="/letstudy">letstdudy</RouterLink>
    <RouterLink to="/letstudydetail">letstudydetail</RouterLink>
    <RouterLink to="/addquetion">addquetion</RouterLink>


  </div>


</template>
<script setup>

</script>
<style scoped>
.nav {
  display: flex;
  gap: 10px;
  color: #000;
}

.nav a {
  text-decoration: none;
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>