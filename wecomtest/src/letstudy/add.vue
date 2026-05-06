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

                    <div v-for="(opt, index) in form.options" :key="opt.id" class="option-item">
                        <div class="option-item">
                            <div class="option-item-inner">
                                <span class="option-key">
                                    {{ String.fromCharCode(65 + index) }}
                                </span>

                                <div :id="'option-editor-' + opt.id" class="option-editor"></div>

                                <button class="btn danger" @click="removeOption(index)">
                                    删除
                                </button>
                            </div>
                        </div>
                    </div>

                    <button class="btn add" @click="addOption">+ 添加选项</button>
                </div>

                <!-- 答案 -->
                <div class="answer-box">
                    <h3>答案</h3>

                    <input v-if="form.type === 'single_choice'" v-model="form.answer" class="input" placeholder="A" />
                    <input v-if="form.type === 'multiple_choice'" v-model="multiAnswer" class="input"
                        placeholder="A,B" />

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
                        <li v-for="(opt, i) in form.options" :key="opt.id">
                            <span>{{ String.fromCharCode(65 + i) }}.</span>
                            <span :id="'preview-option-' + opt.id"></span>
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
import { reactive, ref, computed, onMounted, nextTick, watch } from "vue"
import type { QuestionDto } from "@/api/generated"
import { addQuestion } from "./myApi/questionApi"

import Vditor from "vditor"
import "vditor/dist/index.css"

// ================= 数据 =================
let optionId = 0

const form = reactive({
    type: "single_choice",
    difficulty: 3,
    options: [] as Array<{ id: number; content: string }>,
    answer: "",
    isPublic: false
})

const tagsInput = ref("")
const multiAnswer = ref("")
const fillAnswer = ref("")

let titleEditor: Vditor | null = null
let analysisEditor: Vditor | null = null

// 🔥 选项编辑器池
const optionEditors = new Map<number, Vditor>()

// ================= 计算属性 =================
const isChoice = computed(() =>
    ["single_choice", "multiple_choice"].includes(form.type)
)

const isTextAnswer = computed(() =>
    ["subjective", "math"].includes(form.type)
)

// ================= 初始化 =================
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

    // 默认两个选项
    addOption()
    addOption()
})

// ================= 动态创建选项编辑器 =================
watch(
    () => form.options.map(o => o.id),
    async () => {
        await nextTick()

        form.options.forEach(opt => {
            if (!optionEditors.has(opt.id)) {
                const editor = new Vditor(`option-editor-${opt.id}`, {
                    height: 300,
                    mode: "sv",
                    cache: { enable: false },

                    input: (val) => {
                        opt.content = val
                        updatePreview()
                    },

                    after: () => {
                        editor.setValue(opt.content || "")
                    },

                    preview: { math: { engine: "KaTeX" } }
                })

                optionEditors.set(opt.id, editor)
            }
        })
    },
    { immediate: true }
)

// ================= 预览 =================
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

    // 🔥 选项预览
    form.options.forEach(opt => {
        const el = document.getElementById(`preview-option-${opt.id}`)
        if (el) {
            Vditor.preview(el, opt.content, {
                math: { engine: "KaTeX" }
            })
        }
    })
}

// ================= 操作 =================
const addOption = () => {
    if (form.options.length >= 6) return

    form.options.push({
        id: optionId++,
        content: ""
    })
}

const removeOption = (index: number) => {
    const opt = form.options[index]

    const editor = optionEditors.get(opt.id)
    if (editor) {
        editor.destroy()
        optionEditors.delete(opt.id)
    }

    form.options.splice(index, 1)
}

// ================= 答案处理 =================
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

// ================= 提交 =================
const submit = async () => {
    const dto: QuestionDto = {
        title: titleEditor?.getValue() || "",
        type: form.type,
        difficulty: form.difficulty,
        categoryId: 1,

        options: form.options.map((opt, index) => {
            const editor = optionEditors.get(opt.id)

            return {
                key: String.fromCharCode(65 + index),
                content: editor ? editor.getValue() : ""
            }
        }),

        answer: formatAnswer() as any,
        analysis: analysisEditor?.getValue() || "",

        tags: tagsInput.value
            ? tagsInput.value.split(/[,，]/).map(t => t.trim()).filter(Boolean)
            : [],

        isPublic: form.isPublic
    }

    console.log("提交数据：", dto)

    try {
        const token = localStorage.getItem("token")
        await addQuestion(token, dto)
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
    align-items: flex-start;
}

/* 左右卡片 */
.left,
.right {
    flex: 1;
    background: #ffffff;
    border-radius: 16px;
    padding: 20px;
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
    max-height: calc(100vh - 100px);
    overflow-y: auto;
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
    font-size: 14px;
}

.input:focus,
.textarea:focus,
.select:focus {
    border-color: #409eff;
    outline: none;
    box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

/* textarea */
.textarea {
    min-height: 120px;
    resize: vertical;
}

/* 选项区域 */
.option-box {
    margin-top: 20px;
}

/* 单个选项 */
.option-item {
    background: #fafafa;
    padding: 12px;
    border-radius: 10px;
    border: 1px solid #eee;
    position: relative;
    z-index: 0;
}

/* A B C 圆圈 */
.option-key {
    width: 34px;
    height: 34px;
    min-width: 34px;
    background: linear-gradient(135deg, #409eff, #66b1ff);
    color: white;
    border-radius: 50%;
    text-align: center;
    line-height: 34px;
    font-weight: bold;
    box-shadow: 0 2px 6px rgba(64, 158, 255, 0.4);
}

.option-editor {
    flex: 1;
    min-width: 0;
    overflow: hidden;   /* 🔥 防止子元素溢出压到下一个 */
}

/* 🔥 关键修复：让 Vditor 不塌陷 */
.option-editor :deep(.vditor) {
    position: relative;
    /* 🔥 关键：阻止内部 absolute 乱飞 */
    width: 100%;
    z-index: 0;
}

/* 🔥 防止内容重叠 */
.option-editor :deep(.vditor-content) {
    min-height: 120px;
}

.option-editor :deep(.vditor) {
    display: block;
}

.option-editor :deep(.vditor-toolbar) {
    position: sticky;
    /* 🔥 从 absolute 改成可控 */
    top: 0;
    z-index: 1;
}


/* 按钮系统 */
.btn {
    padding: 8px 14px;
    border-radius: 8px;
    border: none;
    cursor: pointer;
    transition: all 0.2s;
    font-size: 14px;
    white-space: nowrap;
}

.btn:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* 添加按钮 */
.btn.add {
    background: linear-gradient(135deg, #409eff, #66b1ff);
    color: white;
    margin-top: 10px;
}

/* 删除按钮 */
.btn.danger {
    background: linear-gradient(135deg, #ff4d4f, #ff7875);
    color: white;
}

/* 提交按钮 */
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

/* 标题样式 */
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
    padding: 12px;
    border-radius: 10px;
    margin-bottom: 10px;
    background: #ffffff;
    border: 1px solid #e5e6eb;
    transition: 0.2s;
    display: flex;
    gap: 8px;
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
    font-size: 14px;
}

/* 滚动条美化 */
.left::-webkit-scrollbar,
.right::-webkit-scrollbar {
    width: 6px;
}

.left::-webkit-scrollbar-thumb,
.right::-webkit-scrollbar-thumb {
    background: #ccc;
    border-radius: 3px;
}

.left::-webkit-scrollbar-thumb:hover,
.right::-webkit-scrollbar-thumb:hover {
    background: #999;
}

.option-item-inner {
    display: flex;
    align-items: flex-start;
    gap: 12px;
}
</style>