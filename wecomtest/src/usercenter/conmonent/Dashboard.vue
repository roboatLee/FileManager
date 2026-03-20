<template>
  <div>
    <h3>数据概览</h3>

    <div class="stats">
      <div class="card">博客数：{{ blogs.length }}</div>
    </div>

    <h3>最近博客</h3>

    <BlogCard v-for="b in blogs.slice(0,3)" :key="b.id" :blog="b"/>
  </div>
</template>

<script setup>
import BlogCard from "./BlogCard.vue";

defineProps({
  blogs: Array
});
</script>

<style scoped>
.stats {
  display: flex;
  gap: 10px;
}

.card {
  padding: 10px;
  border: 1px solid #eee;
}
</style>