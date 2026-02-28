<template>
  <div class="div">
    <RouterLink to="/file">file</RouterLink>
    <RouterLink to="/vditor">vditor</RouterLink>
    <RouterLink to="/study">study</RouterLink>
    <RouterLink to="/chat">chat</RouterLink>
  </div>
  <RouterView></RouterView>
</template>


<script setup>
import { ref } from 'vue'


</script>




<style scoped>
.div{
  display: flex;
  gap: 16px;

}

</style>
