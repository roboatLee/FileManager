<template>
  <div class="page">
    <!-- 加载中 -->
    <div v-if="loading" class="loading">
      <div class="loader"></div>
      <p>加载中...</p>
    </div>

    <!-- 题目内容 -->
    <div v-else-if="question" class="detail-card">
      <!-- 标题 -->
      <div id="detail-title-preview" class="title-area"></div>

      <!-- 元信息 -->
      <div class="meta">
        <span class="type-badge" :class="`type-${question.type}`">{{ formatType(question.type) }}</span>
        <span class="difficulty">
          <span v-for="i in 5" :key="i" class="star" :class="{ active: i <= question.difficulty }">★</span>
        </span>
        <span class="stats">📊 {{ question.submitCount }}次作答 | 正确率 {{ getAccuracy() }}</span>
      </div>

      <!-- 选项区 -->
      <div class="options-area">
        <!-- 单选 -->
        <template v-if="question.type === 'single_choice'">
          <label v-for="opt in question.options" :key="opt.key" class="option-row">
            <input type="radio" :value="opt.key" v-model="singleAnswer" :disabled="showResult" />
            <span class="opt-key">{{ opt.key }}.</span>
            <span :id="`opt-preview-${opt.key}`" class="opt-content"></span>
          </label>
        </template>

        <!-- 多选 -->
        <template v-if="question.type === 'multiple_choice'">
          <label v-for="opt in question.options" :key="opt.key" class="option-row">
            <input type="checkbox" :value="opt.key" v-model="multiAnswer" :disabled="showResult" />
            <span class="opt-key">{{ opt.key }}.</span>
            <span :id="`opt-preview-${opt.key}`" class="opt-content"></span>
          </label>
        </template>

        <!-- 判断 -->
        <template v-if="question.type === 'judge'">
          <label class="option-row">
            <input type="radio" value="true" v-model="singleAnswer" :disabled="showResult" />
            正确
          </label>
          <label class="option-row">
            <input type="radio" value="false" v-model="singleAnswer" :disabled="showResult" />
            错误
          </label>
        </template>

        <!-- 填空 -->
        <template v-if="question.type === 'fill'">
          <input
            class="text-input"
            v-model="textAnswer"
            placeholder="多个答案用 | 分隔，如：答案1|答案2"
            :disabled="showResult"
          />
        </template>

        <!-- 主观题 / 数学题 -->
        <template v-if="isTextType">
          <textarea
            class="text-input textarea"
            v-model="textAnswer"
            rows="8"
            placeholder="请输入你的答案"
            :disabled="showResult"
          ></textarea>
        </template>
      </div>

      <!-- 提交按钮 -->
      <div class="submit-area" v-if="!showResult">
        <button class="btn-submit" @click="submit">📝 提交答案</button>
      </div>

      <!-- 结果区 -->
      <div v-if="showResult" class="result-area">
        <div v-if="resultMessage" class="result-message" :class="{ correct: isCorrect, wrong: !isCorrect }">
          {{ resultMessage }}
        </div>

        <div class="correct-answer" v-if="!isCorrect && question.type !== 'subjective'">
          <span class="label">正确答案：</span>
          <span>{{ formatCorrectAnswer() }}</span>
        </div>

        <div class="analysis" v-if="question.analysis">
          <h4>📖 解析</h4>
          <div id="detail-analysis-preview"></div>
        </div>
      </div>
    </div>

    <!-- 空状态 -->
    <div v-else class="empty">题目不存在 😢</div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, watch } from 'vue'
import { useRoute } from 'vue-router'
import Vditor from 'vditor'
import 'vditor/dist/index.css'
import { getQuestionById } from './myApi/questionApi'

const route = useRoute()
const question = ref(null)
const loading = ref(true)

const singleAnswer = ref('')
const multiAnswer = ref([])
const textAnswer = ref('')

const showResult = ref(false)
const isCorrect = ref(false)
const resultMessage = ref('')

const isTextType = computed(() =>
  ['math', 'subjective'].includes(question.value?.type)
)

onMounted(async () => {
  try {
    const id = Number(route.params.id)
    const res = await getQuestionById(id)
    question.value = res.data
  } catch (e) {
    console.error('加载题目失败', e)
  } finally {
    loading.value = false
  }
})

watch(question, async (val) => {
  if (!val) return
  await nextTick()

  // 渲染标题
  const titleEl = document.getElementById('detail-title-preview')
  if (titleEl) {
    Vditor.preview(titleEl, val.title, { math: { engine: 'KaTeX' } })
  }

  // 渲染选项
  if (val.options) {
    val.options.forEach(opt => {
      const el = document.getElementById(`opt-preview-${opt.key}`)
      if (el) {
        Vditor.preview(el, opt.content, { math: { engine: 'KaTeX' } })
      }
    })
  }
})

watch(showResult, async (val) => {
  if (!val || !question.value?.analysis) return
  await nextTick()
  const el = document.getElementById('detail-analysis-preview')
  if (el) {
    Vditor.preview(el, question.value.analysis, { math: { engine: 'KaTeX' } })
  }
})

function submit() {
  const q = question.value
  if (!q) return

  // 检查是否作答
  if (q.type === 'single_choice' || q.type === 'judge') {
    if (!singleAnswer.value) return
  } else if (q.type === 'multiple_choice') {
    if (multiAnswer.value.length === 0) return
  } else {
    if (!textAnswer.value.trim()) return
  }

  showResult.value = true

  switch (q.type) {
    case 'single_choice':
      isCorrect.value = singleAnswer.value === q.answer
      break

    case 'multiple_choice': {
      const correct = [...q.answer].sort()
      const user = [...multiAnswer.value].sort()
      isCorrect.value = JSON.stringify(correct) === JSON.stringify(user)
      break
    }

    case 'judge': {
      const userVal = singleAnswer.value === 'true'
      isCorrect.value = userVal === q.answer
      break
    }

    case 'fill': {
      const correctArr = Array.isArray(q.answer) ? q.answer : [q.answer]
      const userArr = textAnswer.value.split('|').map(s => s.trim()).filter(Boolean)
      isCorrect.value = JSON.stringify(correctArr) === JSON.stringify(userArr)
      break
    }

    case 'math':
    case 'subjective':
      isCorrect.value = null
      break
  }

  if (q.type === 'math' || q.type === 'subjective') {
    resultMessage.value = '你的答案已提交，以下是参考答案：'
  } else if (isCorrect.value) {
    resultMessage.value = '✔ 回答正确！'
  } else {
    resultMessage.value = '✘ 回答错误'
  }
}

function formatCorrectAnswer() {
  const a = question.value?.answer
  if (Array.isArray(a)) return a.join('，')
  if (typeof a === 'boolean') return a ? '正确' : '错误'
  return String(a ?? '')
}

function getAccuracy() {
  const q = question.value
  if (!q || !q.submitCount) return '0%'
  return ((q.correctCount / q.submitCount) * 100).toFixed(1) + '%'
}

function formatType(type) {
  const map = {
    single_choice: '单选',
    multiple_choice: '多选',
    judge: '判断',
    fill: '填空',
    math: '数学',
    subjective: '主观'
  }
  return map[type] || type
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 30px 0;
}

.detail-card {
  max-width: 800px;
  margin: 0 auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 32px;
}

.title-area {
  font-size: 20px;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 16px;
  line-height: 1.7;
}

.meta {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.type-badge {
  padding: 2px 12px;
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

.star {
  font-size: 14px;
  color: #dcdfe6;
}
.star.active { color: #f7ba2a; }

.stats {
  font-size: 13px;
  color: #909399;
}

.options-area {
  margin-bottom: 28px;
}

.option-row {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  padding: 12px 16px;
  margin-bottom: 8px;
  background: #fafbfc;
  border-radius: 8px;
  border: 1px solid #ebeef5;
  cursor: pointer;
  transition: background 0.2s;
}

.option-row:hover {
  background: #f0f2f5;
}

.opt-key {
  font-weight: 600;
  color: #409eff;
  min-width: 24px;
}

.opt-content {
  flex: 1;
}

.text-input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  font-size: 15px;
  box-sizing: border-box;
}

.text-input:focus {
  outline: none;
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.textarea {
  resize: vertical;
  min-height: 120px;
}

.submit-area {
  text-align: center;
  margin: 8px 0 20px;
}

.btn-submit {
  padding: 10px 40px;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.15s, box-shadow 0.15s;
}

.btn-submit:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.35);
}

.result-area {
  border-top: 1px solid #ebeef5;
  padding-top: 20px;
}

.result-message {
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 12px;
}

.result-message.correct {
  color: #67c23a;
}

.result-message.wrong {
  color: #f56c6c;
}

.correct-answer {
  padding: 10px 16px;
  background: #fef0f0;
  border-radius: 8px;
  margin-bottom: 16px;
  font-size: 14px;
  color: #f56c6c;
}

.correct-answer .label {
  font-weight: 600;
}

.analysis {
  background: #f6ffed;
  border-radius: 8px;
  padding: 16px 20px;
}

.analysis h4 {
  margin: 0 0 10px;
  font-size: 15px;
  color: #67c23a;
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
  100% { transform: rotate(360deg); }
}

/* 空状态 */
.empty {
  text-align: center;
  margin-top: 100px;
  font-size: 18px;
  color: #999;
}
</style>