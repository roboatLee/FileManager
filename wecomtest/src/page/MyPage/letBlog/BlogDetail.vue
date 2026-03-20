<template>
  <div class="blog-detail">
    <div class="container">
      <!-- 标题 -->
      <h1 class="title">{{ blog.title }}</h1>

      <!-- 作者信息 -->
      <div class="meta">
        <span class="author">作者：{{ blog.userName }}</span>
        <span class="time">{{ blog.createTime }}</span>
      </div>

      <hr />

      <!-- 内容 -->
      <div class="content" v-html="blog.htmlContent"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleById } from './myApi/articleApi' 


const route = useRoute()
const blog = ref({})

const getDetail = async () => {
  const id = route.params.id
  const res = await getArticleById(id)
  console.log(res.data)
  blog.value = res.data
}

onMounted(() => {
  getDetail()
})
</script>

<style scoped>
.blog-detail {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.container {
  width: 800px;
  background: #fff;
  padding: 30px;
  border-radius: 10px;
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
}

.meta {
  color: #888;
  font-size: 14px;
  margin-bottom: 20px;
}

.meta span {
  margin-right: 15px;
}

.content {
  line-height: 1.8;
  font-size: 16px;
}
</style>