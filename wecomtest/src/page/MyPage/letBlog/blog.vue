<template>
  <div class="blog-container">
    <header class="hero">
      <h1>欢迎来到 Blog 乐园</h1>
      <p class="subtitle">在这里，每一篇文章都是一个故事</p>
    </header>

    <main class="blog-list">
      <article v-for="blog in blogs" class="blog-card">
        <div class="card-content" @click="goDetail(blog.id)">
          <h2 class="blog-title">{{ blog.title }}</h2>

          <div class="meta">
            <span v-if="blog.userName" class="author">
              <i class="icon-user"></i> {{ blog.userName }}
            </span>
            <time v-if="blog.createTime" class="date">
              <i class="icon-calendar"></i> {{ formatDate(blog.createTime) }}
            </time>
          </div>

          <p class="excerpt" v-if="blog.summary || blog.htmlContent">
            {{ (blog.summary || blog.htmlContent || '').slice(0, 120) + '...' }}
          </p>

          <router-link v-if="blog._id || blog.id" :to="{ name: 'BlogDetail', params: { id: blog._id || blog.id } }"
            class="read-more">
            阅读全文 →
          </router-link>
        </div>
      </article>
    </main>

  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getAllArtile } from './myApi/articleApi'
import { useRouter } from 'vue-router'

// 可选：如果你的路由是 vue-router，可以导入 useRouter
// import { useRouter } from 'vue-router'

const blogs = ref([])
const router = useRouter()

onMounted(async () => {
  try {
    const res = await getAllArtile()
    blogs.value = res.data || []
    console.log('文章列表：', blogs.value)
  } catch (err) {
    console.error('获取文章失败', err)
  }
})

// 格式化日期（可根据需要调整）
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const goDetail = (id) => {
  router.push(`/blog/detail/${id}`)
}

</script>

<style scoped>
.blog-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

.hero {
  text-align: center;
  padding: 4rem 1rem 3rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 16px;
  margin-bottom: 3rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.hero h1 {
  font-size: 2.8rem;
  margin: 0 0 0.8rem;
  font-weight: 700;
  letter-spacing: -0.5px;
}

.subtitle {
  font-size: 1.2rem;
  opacity: 0.9;
  margin: 0;
}

.blog-list {
  display: grid;
  gap: 1.5rem;
}

.blog-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  transition: all 0.25s ease;
  border: 1px solid #f0f0f5;
}

.blog-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.12);
}

.card-content {
  padding: 1.8rem 2rem;
}

.blog-title {
  font-size: 1.5rem;
  margin: 0 0 1rem;
  color: #1a1a2e;
  line-height: 1.4;
}

.meta {
  display: flex;
  gap: 1.5rem;
  color: #64748b;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.meta .icon-user,
.meta .icon-calendar {
  margin-right: 0.4rem;
  font-style: normal;
  /* 如果你没有用 icon 字体，可以删掉 */
}

.excerpt {
  color: #4b5563;
  line-height: 1.6;
  margin: 0 0 1.2rem;
  font-size: 0.98rem;
}

.read-more {
  display: inline-block;
  color: #6366f1;
  font-weight: 600;
  text-decoration: none;
  transition: all 0.2s;
}

.read-more:hover {
  color: #4f46e5;
  transform: translateX(4px);
}

.empty-state {
  text-align: center;
  padding: 5rem 1rem;
  color: #94a3b8;
  font-size: 1.1rem;
}

/* 你也可以添加一些小图标（需要自行引入 iconfont 或 lucide 等） */
.icon-user::before {
  content: "👤 ";
}

.icon-calendar::before {
  content: "📅 ";
}
</style>