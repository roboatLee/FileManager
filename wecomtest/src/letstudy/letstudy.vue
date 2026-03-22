<template>
  <div class="question-list">
    <div 
      class="question-card" 
      v-for="q in list" 
      :key="q.id"
    >
      <!-- 标题 -->
      <div class="title" v-html="q.title"></div>

      <!-- 标签区 -->
      <div class="meta">
        <span class="type">{{ formatType(q.type) }}</span>
        <span class="difficulty">难度: {{ q.difficulty }}</span>
        <span class="accuracy">正确率: {{ getAccuracy(q) }}</span>
      </div>

      <!-- 标签 -->
      <div class="tags">
        <span v-for="tag in q.tags" :key="tag" class="tag">
          {{ tag }}
        </span>
      </div>

      <!-- 操作 -->
      <div class="actions">
        <button @click="goDetail(q.id)">开始做题</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
// import { getQuestionList } from '@/api/question'
import { Question } from './moudle/question'


// const list = ref<Question[]>([])
const list = ref<Question[]>([
  {
    id: 1,
    title: "已知矩阵 A，求其行列式",
    type: "single_choice",
    difficulty: 3,
    tags: ["线性代数", "矩阵"],
    submitCount: 120,
    correctCount: 80,
    isPublic: true
  },
  {
    id: 2,
    title: "函数 f(x)=x² 在 x=1 处的导数是？",
    type: "single_choice",
    difficulty: 2,
    tags: ["高数", "导数"],
    submitCount: 200,
    correctCount: 150,
    isPublic: true
  },
  {
    id: 3,
    title: "判断：向量点乘结果是向量",
    type: "judge",
    difficulty: 1,
    tags: ["线性代数"],
    submitCount: 90,
    correctCount: 60,
    isPublic: true
  }
])

onMounted(async () => {
//   const res = await getQuestionList()
//   list.value = res.data
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
  return map[type]
}

function getAccuracy(q: Question) {
  if (!q.submitCount) return '0%'
  return ((q.correctCount / q.submitCount) * 100).toFixed(1) + '%'
}

function goDetail(id: number) {
  console.log('跳转到题目:', id)
}
</script>

<style scoped>
.question-card {
  padding: 16px;
  border: 1px solid #eee;
  border-radius: 8px;
  margin-bottom: 12px;
  transition: 0.2s;
}

.question-card:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

.meta {
  font-size: 13px;
  color: #666;
}

.tag {
  background: #f0f0f0;
  padding: 2px 8px;
  margin-right: 6px;
  border-radius: 4px;
}
</style>