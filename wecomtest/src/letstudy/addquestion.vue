<template>
  <div class="question-create">

    <h2>创建题目（模拟版）</h2>

    <!-- 类型 -->
    <select v-model="form.type">
      <option value="single_choice">单选题</option>
      <option value="multiple_choice">多选题</option>
      <option value="judge">判断题</option>
      <option value="fill">填空题</option>
      <option value="math">数学题</option>
      <option value="subjective">主观题</option>
    </select>

    <!-- 难度 -->
    <input type="number" v-model="form.difficulty" min="1" max="5" />

    <!-- 题干 -->
    <div id="title-editor"></div>

    <!-- 选项 -->
    <div v-if="isChoice">
      <h3>选项</h3>
      <div v-for="(opt, index) in form.options" :key="index">
        <span>{{ String.fromCharCode(65 + index) }}</span>
        <input v-model="opt.content" placeholder="选项内容" />
        <button @click="removeOption(index)">删除</button>
      </div>
      <button @click="addOption">+ 添加选项</button>
    </div>

    <!-- 答案 -->
    <div>
      <h3>答案</h3>

      <!-- 单选 -->
      <div v-if="form.type === 'single_choice'">
        <input v-model="form.answer" placeholder="例如 A" />
      </div>

      <!-- 多选 -->
      <div v-if="form.type === 'multiple_choice'">
        <input v-model="multiAnswer" placeholder="例如 A,B" />
      </div>

      <!-- 判断 -->
      <div v-if="form.type === 'judge'">
        <select v-model="form.answer">
          <option :value="true">正确</option>
          <option :value="false">错误</option>
        </select>
      </div>

      <!-- 填空 -->
      <div v-if="form.type === 'fill'">
        <input v-model="fillAnswer" placeholder="用 | 分隔" />
      </div>

      <!-- 主观 / 数学 -->
      <div v-if="isTextAnswer">
        <textarea v-model="form.answer"></textarea>
      </div>

    </div>

    <!-- 解析 -->
    <div id="analysis-editor"></div>

    <!-- 标签 -->
    <input v-model="tagsInput" placeholder="标签（逗号分隔）" />

    <!-- 是否公开 -->
    <label>
      公开
      <input type="checkbox" v-model="form.isPublic" />
    </label>

    <br /><br />
    <button @click="submit">提交</button>

    <hr />

    <!-- 模拟数据库展示 -->
    <h3>📦 模拟题库数据</h3>
    <pre>{{ questionDB }}</pre>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue"
import Vditor from "vditor"
import "vditor/dist/index.css"

// 表单
const form = ref({
  type: "single_choice",
  difficulty: 3,
  options: [],
  answer: "",
  isPublic: false
})

// 模拟数据库
const questionDB = ref([])

// 辅助字段
const tagsInput = ref("")
const multiAnswer = ref("")
const fillAnswer = ref("")

let titleEditor = null
let analysisEditor = null

// 类型判断
const isChoice = computed(() =>
  form.value.type === "single_choice" ||
  form.value.type === "multiple_choice"
)

const isTextAnswer = computed(() =>
  form.value.type === "subjective" ||
  form.value.type === "math"
)

// 初始化编辑器
onMounted(() => {
  titleEditor = new Vditor("title-editor", {
    height: 200
  })

  analysisEditor = new Vditor("analysis-editor", {
    height: 200
  })
})

// 选项操作
const addOption = () => {
  form.value.options.push({ content: "" })
}

const removeOption = (index) => {
  form.value.options.splice(index, 1)
}

// 提交
const submit = () => {
  const data = {
    id: Date.now(),
    title: titleEditor.getValue(),
    type: form.value.type,
    difficulty: form.value.difficulty,
    options: [],
    answer: null,
    analysis: analysisEditor.getValue(),
    tags: tagsInput.value.split(","),
    isPublic: form.value.isPublic
  }

  // options 自动生成 A/B/C
  if (isChoice.value) {
    data.options = form.value.options.map((opt, index) => ({
      key: String.fromCharCode(65 + index),
      content: opt.content
    }))
  }

  // answer 处理
  if (form.value.type === "multiple_choice") {
    data.answer = multiAnswer.value.split(",")
  } else if (form.value.type === "fill") {
    data.answer = fillAnswer.value.split("|")
  } else {
    data.answer = form.value.answer
  }

  // 存入模拟数据库
  questionDB.value.push(data)

  console.log("当前题库：", questionDB.value)

  // 重置表单（关键）
  resetForm()
}

// 重置
const resetForm = () => {
  form.value = {
    type: "single_choice",
    difficulty: 3,
    options: [],
    answer: "",
    isPublic: false
  }

  tagsInput.value = ""
  multiAnswer.value = ""
  fillAnswer.value = ""

  titleEditor.setValue("")
  analysisEditor.setValue("")
}
</script>

<style scoped>
.question-create {
  max-width: 800px;
  margin: auto;
}
</style>