<template>
  <div class="question-create">

    <h2>🚀 题目创建系统</h2>

    <div class="layout">

      <!-- 左侧 -->
      <div class="left">

        <!-- 类型 -->
        <select v-model="form.type" class="select">
          <option value="single_choice">单选题</option>
          <option value="multiple_choice">多选题</option>
          <option value="judge">判断题</option>
          <option value="fill">填空题</option>
          <option value="math">数学题</option>
          <option value="subjective">主观题</option>
        </select>

        <!-- 难度 -->
        <input type="number" v-model="form.difficulty" min="1" max="5" class="input" />

        <!-- 标题 -->
        <div id="title-editor"></div>

        <!-- 选项 -->
        <div v-if="isChoice" class="option-box">
          <h3>选项</h3>

          <div v-for="(opt, index) in form.options" :key="index" class="option-item">
            <span class="option-key">
              {{ String.fromCharCode(65 + index) }}
            </span>

            <input v-model="opt.content" class="option-input" placeholder="请输入选项内容" />

            <button class="btn danger" @click="removeOption(index)">
              删除
            </button>
          </div>

          <button class="btn add" @click="addOption">+ 添加选项</button>
        </div>

        <!-- 答案 -->
        <div class="answer-box">
          <h3>答案</h3>

          <input v-if="form.type === 'single_choice'" v-model="form.answer" class="input" placeholder="A" />
          <input v-if="form.type === 'multiple_choice'" v-model="multiAnswer" class="input" placeholder="A,B" />

          <select v-if="form.type === 'judge'" v-model="form.answer" class="input">
            <option :value="true">正确</option>
            <option :value="false">错误</option>
          </select>

          <input v-if="form.type === 'fill'" v-model="fillAnswer" class="input" placeholder="用 | 分隔" />

          <textarea v-if="isTextAnswer" v-model="form.answer" class="textarea"></textarea>
        </div>

        <!-- 解析 -->
        <div id="analysis-editor"></div>

        <!-- 标签 -->
        <input v-model="tagsInput" class="input" placeholder="标签：算法,数学" />

        <label class="checkbox">
          <input type="checkbox" v-model="form.isPublic" />
          公开
        </label>

        <button class="btn submit" @click="submit">
          🚀 提交题目
        </button>
      </div>

      <!-- 右侧预览 -->
      <div class="right">
        <h3>📖 实时预览</h3>

        <div class="preview">

          <div id="preview-title"></div>

          <ul v-if="isChoice">
            <li v-for="(opt, i) in previewOptions" :key="i">
              {{ opt.key }}. {{ opt.content }}
            </li>
          </ul>

          <div class="analysis">
            <h4>解析：</h4>
            <div id="preview-analysis"></div>
          </div>

        </div>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, computed, onMounted, nextTick } from "vue"
import type { QuestionDto } from "@/api/generated"
import { addQuestion } from "./myApi/questionApi"


import Vditor from "vditor"
import "vditor/dist/index.css"

const data = ref<QuestionDto | null>(null)

const form = reactive({
  type: "single_choice",
  difficulty: 3,
  options: [] as Array<{ content: string }>,
  answer: "",
  isPublic: false
})

const tagsInput = ref("")
const multiAnswer = ref("")
const fillAnswer = ref("")

let titleEditor = null
let analysisEditor = null

const isChoice = computed(() =>
  ["single_choice", "multiple_choice"].includes(form.type)
)

const isTextAnswer = computed(() =>
  ["subjective", "math"].includes(form.type)
)

const previewOptions = computed(() =>
  form.options.map((o, i) => ({
    key: String.fromCharCode(65 + i),
    content: o.content
  }))
)

// 🔥 核心：正确预览
const updatePreview = async () => {
  await nextTick()

  if (titleEditor) {
    Vditor.preview(
      document.getElementById("preview-title"),
      titleEditor.getValue(),
      { math: { engine: "KaTeX" } }
    )
  }

  if (analysisEditor) {
    Vditor.preview(
      document.getElementById("preview-analysis"),
      analysisEditor.getValue(),
      { math: { engine: "KaTeX" } }
    )
  }
}

onMounted(() => {
  titleEditor = new Vditor("title-editor", {
    height: 200,
    mode: "sv",
    input: updatePreview,
    preview: { math: { engine: "KaTeX" } }
  })

  analysisEditor = new Vditor("analysis-editor", {
    height: 200,
    mode: "sv",
    input: updatePreview,
    preview: { math: { engine: "KaTeX" } }
  })
})

// 操作
const addOption = () => form.options.push({ content: "" })
const removeOption = (i) => form.options.splice(i, 1)

// 答案处理
const formatAnswer = () => {
  switch (form.type) {
    case "multiple_choice":
      return multiAnswer.value.split(",")
    case "fill":
      return fillAnswer.value.split("|")
    default:
      return form.answer
  }
}

const submit = async () => {

  const dto: QuestionDto = {
    title: titleEditor.getValue(),
    type: form.type,
    difficulty: form.difficulty,
    categoryId: 1,
    options: form.options.map((opt, index) => ({
      key: String.fromCharCode(65 + index),
      content: opt.content
    })),
    answer: formatAnswer() as any,
    analysis: analysisEditor.getValue(),
    tags: tagsInput.value
      ? tagsInput.value.split(/[,，]/).map(tag => tag.trim()).filter(tag => tag !== "")
      : [], isPublic: form.isPublic
  }

  console.log("提交数据：", dto)

  try {
    const token = localStorage.getItem("token") // 或 pinia
    const res = await addQuestion(token, dto)
    console.log("接口返回：", res)
    alert("提交成功！")
  } catch (e) {
    console.error(e)
    alert("提交失败")
  }
}

</script>

<style scoped>
.question-create {
  padding: 30px;
  background: #f5f7fa;
  min-height: 100vh;
}

/* 整体布局 */
.layout {
  display: flex;
  gap: 24px;
}

/* 左右卡片 */
.left,
.right {
  flex: 1;
  background: #ffffff;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
}

/* 标题 */
h2 {
  margin-bottom: 20px;
  font-weight: 600;
}

/* 输入框统一 */
.input,
.textarea,
.select {
  width: 100%;
  margin-top: 12px;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #e5e6eb;
  transition: 0.2s;
}

.input:focus,
.textarea:focus {
  border-color: #409eff;
  outline: none;
}

/* 选项区域 */
.option-box {
  margin-top: 20px;
}

.option-item {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 10px;
}

/* A B C 圆圈 */
.option-key {
  width: 34px;
  height: 34px;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  color: white;
  border-radius: 50%;
  text-align: center;
  line-height: 34px;
  font-weight: bold;
  box-shadow: 0 2px 6px rgba(64, 158, 255, 0.4);
}

/* 输入框 */
.option-input {
  flex: 1;
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #e5e6eb;
}

/* 按钮系统 */
.btn {
  padding: 8px 14px;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 14px;
}

.btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.btn.add {
  background: linear-gradient(135deg, #409eff, #66b1ff);
  color: white;
}

.btn.danger {
  background: linear-gradient(135deg, #ff4d4f, #ff7875);
  color: white;
}

.btn.submit {
  background: linear-gradient(135deg, #67c23a, #95de64);
  color: white;
  width: 100%;
  margin-top: 20px;
  font-size: 16px;
}

/* 预览区 */
.preview {
  background: #fafafa;
  padding: 16px;
  border-radius: 12px;
  border: 1px solid #ebeef5;
}

/* 题目样式 */
.preview h1,
.preview h2,
.preview h3 {
  margin: 10px 0;
}

/* 选项预览 */
.preview ul {
  margin-top: 10px;
  padding-left: 0;
}

.preview li {
  list-style: none;
  padding: 10px;
  border-radius: 8px;
  margin-bottom: 8px;
  background: #ffffff;
  border: 1px solid #e5e6eb;
  transition: 0.2s;
}

.preview li:hover {
  border-color: #409eff;
  background: #ecf5ff;
}

/* 解析 */
.analysis {
  margin-top: 20px;
  padding-top: 10px;
  border-top: 1px solid #eee;
}

/* checkbox */
.checkbox {
  display: flex;
  align-items: center;
  margin-top: 15px;
  gap: 6px;
}
</style>