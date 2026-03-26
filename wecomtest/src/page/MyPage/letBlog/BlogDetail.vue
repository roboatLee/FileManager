<template>
  <div class="page">

    <!-- ✅ 加载动画 -->
    <div v-if="loading" class="loading">
      <div class="loader"></div>
      <p>加载中...</p>
    </div>

    <!-- ✅ 正常内容 -->
    <div v-else-if="blog" class="blog-detail">
      <div class="container">

        <!-- 标题 -->
        <h1 class="title">{{ blog.title }}</h1>

        <!-- 作者信息 -->
        <div class="author-card">
          <img class="avatar" :src="blog.userVo.avatarUrl" />
          <div class="info">
            <div class="name">{{ blog.userVo.username }}</div>
            <div class="time">{{ formatTime(blog.createTime) }}</div>
          </div>
        </div>

        <!-- 数据 -->
        <div class="stats">
          👀 {{ blog.viewNumber }} 阅读
          👍 {{ blog.likeNumber }} 点赞
        </div>

        <hr />

        <!-- 内容 -->
        <div id="preview" class="content"></div>

      </div>
    </div>

    <!-- ❌ 空状态 -->
    <div v-else class="empty">
      博客不存在 😢
    </div>

  </div>
</template>


<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleById } from './myApi/articleApi'
import type { ArticleDetailVo } from '@/api/generated'

// ✅ 引入 Vditor
import Vditor from 'vditor'
import 'vditor/dist/index.css'

const route = useRoute()
const blog = ref<ArticleDetailVo | null>(null)

const getDetail = async () => {
  console.log("getDetail开始启动")

  const id = Number(route.params.id )
  const res = await getArticleById(id)

  blog.value = res.data

  if (!blog.value) {
    console.log("博客不存在")
    return
  }

  console.log(blog.value)

  await nextTick()

  const previewEl = document.getElementById('preview')
  if (!previewEl) return

  Vditor.preview(previewEl as HTMLDivElement, blog.value.markdownContent, {
    mode: 'light',
    hljs: { style: 'github' },
    math: { engine: 'KaTeX' }
  })
}

onMounted(async () => {
  await getDetail()
})

const formatTime = (time: string) => {
  return new Date(time).toLocaleString()
}

</script>


<style scoped>
.page {
  background: #f5f6f7;
  min-height: 100vh;
  padding: 30px 0;
}

/* 卡片 */
.container {
  width: 800px;
  margin: auto;
  background: #fff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

/* 标题 */
.title {
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* 作者 */
.author-card {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.avatar {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  margin-right: 12px;
}

.name {
  font-weight: bold;
}

.time {
  font-size: 12px;
  color: #888;
}

/* 数据 */
.stats {
  font-size: 14px;
  color: #666;
  margin-bottom: 15px;
}

/* 内容 */
.content {
  line-height: 1.8;
  font-size: 16px;
}

/* loading */
.loading {
  text-align: center;
  margin-top: 100px;
}

.loader {
  border: 4px solid #eee;
  border-top: 4px solid #409eff;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  margin: auto;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  100% {
    transform: rotate(360deg);
  }
}

/* 空状态 */
.empty {
  text-align: center;
  margin-top: 100px;
  color: #999;
}
</style>