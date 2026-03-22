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
      <div id="preview" class="content"></div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleById } from './myApi/articleApi'

// ✅ 引入 Vditor
import Vditor from 'vditor'
import 'vditor/dist/index.css'

const route = useRoute()
const blog = ref({})

const getDetail = async () => {
  const id = route.params.id
  const res = await getArticleById(id)

  blog.value = res.data

  // ⚠️ 等 DOM 渲染完成
  await nextTick()

  // ✅ 用 Vditor 渲染 Markdown
  Vditor.preview(
    document.getElementById('preview'),
    blog.value.markdownContent,   // ⚠️ 注意这里是 Markdown，不是 html
    {
      hljs: {
        style: 'github'
      },
      math: {
        engine: 'KaTeX'
      }
    }
  )
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

/* 让代码块更好看 */
.content pre {
  padding: 12px;
  border-radius: 8px;
  overflow-x: auto;
}

/* 图片自适应 */
.content img {
  max-width: 100%;
}
</style>