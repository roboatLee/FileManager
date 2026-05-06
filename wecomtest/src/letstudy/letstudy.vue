<template>
  <div class="question-list-page">
    <div class="hero">
      <h1>📚 题库</h1>
      <p>练习题目，巩固知识</p>
    </div>

    <div class="card-grid">
      <div
        class="question-card"
        v-for="q in list"
        :key="q.id"
        :class="`card-${q.type}`"
      >
        <div class="card-strip"></div>
        <div class="card-body">
          <div class="title" :id="`title-preview-${q.id}`"></div>

          <div class="meta">
            <span class="type-badge" :class="`type-${q.type}`">
              {{ formatType(q.type) }}
            </span>
            <span class="difficulty">
              <span v-for="i in 5" :key="i" class="star" :class="{ active: i <= q.difficulty }">★</span>
            </span>
          </div>

          <div class="tags" v-if="q.tags && q.tags.length">
            <span v-for="tag in q.tags" :key="tag" class="tag">{{ tag }}</span>
          </div>

          <div class="accuracy">
            <span class="accuracy-bar">
              <span class="accuracy-fill" :style="{ width: getAccuracyPercent(q) }"></span>
            </span>
            <span class="accuracy-text">{{ getAccuracy(q) }}</span>
          </div>

          <div class="actions">
            <button class="btn-start" @click="goDetail(q.id)">✍️ 开始做题</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="list.length === 0 && !loading" class="empty">
      <p>📭 暂无题目</p>
      <p class="sub">去 <a href="/addquetion">添加一道</a> 吧</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import Vditor from 'vditor'
import 'vditor/dist/index.css'
import { getQuestionList } from './myApi/questionApi'
import { Question } from './moudle/question'

const router = useRouter()
const list = ref<Question[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
    const res = await getQuestionList()
    list.value = res.data
    await nextTick()
    list.value.forEach(q => {
      const el = document.getElementById(`title-preview-${q.id}`)
      if (el) {
        Vditor.preview(el, q.title, { math: { engine: 'KaTeX' } })
      }
    })
  } catch (e) {
    console.error('加载题目列表失败', e)
  } finally {
    loading.value = false
  }
})

function formatType(type: string) {
  const map: Record<string, string> = {
    single_choice: '单选',
    multiple_choice: '多选',
    judge: '判断',
    fill: '填空',
    math: '数学',
    subjective: '主观'
  }
  return map[type] || type
}

function getAccuracyPercent(q: Question) {
  if (!q.submitCount) return '0%'
  return ((q.correctCount / q.submitCount) * 100).toFixed(0) + '%'
}

function getAccuracy(q: Question) {
  if (!q.submitCount) return '0%'
  return ((q.correctCount / q.submitCount) * 100).toFixed(1) + '%'
}

function goDetail(id: number) {
  router.push(`/letstudydetail/${id}`)
}
</script>

<style scoped>
.question-list-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.hero {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  padding: 48px 32px 40px;
  text-align: center;
  border-radius: 0 0 24px 24px;
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.25);
}

.hero h1 {
  margin: 0;
  font-size: 28px;
  font-weight: 700;
}

.hero p {
  margin: 8px 0 0;
  opacity: 0.85;
  font-size: 15px;
}

.card-grid {
  max-width: 800px;
  margin: 24px auto 0;
  padding: 0 16px 40px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.question-card {
  display: flex;
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: transform 0.2s, box-shadow 0.2s;
  border: 1px solid #f0f0f5;
}

.question-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.card-strip {
  width: 5px;
  flex-shrink: 0;
}

.card-body {
  flex: 1;
  padding: 20px 24px;
  min-width: 0;
}

/* 左侧色条颜色 */
.card-single_choice  .card-strip { background: #e6a23c; }
.card-multiple_choice .card-strip { background: #409eff; }
.card-judge          .card-strip { background: #67c23a; }
.card-fill           .card-strip { background: #e040fb; }
.card-math           .card-strip { background: #f56c6c; }
.card-subjective     .card-strip { background: #909399; }

.title {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 14px;
  line-height: 1.6;
}

.meta {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 10px;
}

.type-badge {
  padding: 2px 10px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 600;
  color: #fff;
}

.type-single_choice  { background: #e6a23c; }
.type-multiple_choice { background: #409eff; }
.type-judge          { background: #67c23a; }
.type-fill           { background: #e040fb; }
.type-math           { background: #f56c6c; }
.type-subjective     { background: #909399; }

.difficulty {
  display: flex;
  gap: 1px;
}

.star {
  font-size: 14px;
  color: #dcdfe6;
}

.star.active {
  color: #f7ba2a;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 12px;
}

.tag {
  padding: 2px 10px;
  background: #f0f2f5;
  color: #606266;
  border-radius: 12px;
  font-size: 12px;
}

.accuracy {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 16px;
}

.accuracy-bar {
  flex: 1;
  height: 6px;
  background: #ebeef5;
  border-radius: 3px;
  overflow: hidden;
}

.accuracy-fill {
  height: 100%;
  background: linear-gradient(90deg, #67c23a, #85ce61);
  border-radius: 3px;
  transition: width 0.6s ease;
}

.accuracy-text {
  font-size: 13px;
  color: #909399;
  min-width: 44px;
  text-align: right;
}

.actions {
  text-align: right;
}

.btn-start {
  padding: 8px 24px;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.15s, box-shadow 0.15s;
}

.btn-start:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.35);
}

.empty {
  text-align: center;
  padding: 80px 0;
  color: #909399;
  font-size: 16px;
}

.empty .sub {
  margin-top: 8px;
  font-size: 14px;
}

.empty a {
  color: #409eff;
  text-decoration: none;
}
</style>