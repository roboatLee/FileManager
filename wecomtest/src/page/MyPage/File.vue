<template>
  <div class="file-browser">
    <!-- 工具栏 -->
    <div class="toolbar">
      <button class="back-btn" @click="goParent" :disabled="!current.path">
        ⬅
      </button>

      <button class="parse-btn" @click="parseJson" :disabled="!selected">
        解析 JSON
      </button>

      <button class="print-btn" @click="printStructure" :disabled="!current.path">
        打印结构
      </button>

      <select v-model="selectedDisk" @change="switchDisk">
        <option v-for="disk in disks" :key="disk" :value="disk">
          💽 {{ disk }}
        </option>
      </select>

      <span class="path" :title="current.path">
        {{ current.path || '根目录' }}
      </span>
    </div>

    <!-- 文件列表 -->
    <ul class="file-list">
      <li
        v-for="item in current.files"
        :key="item.name"
        :class="['file-item', item.type, { active: item.name === selected }]"
        @click="selected = item.name"
        @dblclick="open(item)"
      >
        <span class="icon">
          {{ item.type === 'DIR' ? '📁' : '📄' }}
        </span>
        <span class="name">{{ item.name }}</span>
      </li>

      <li v-if="!current.files.length" class="empty">
        📭 当前目录为空
      </li>
    </ul>
  </div>

  <!-- JSON / 字幕内容 -->
  <div v-if="subtitle" class="subtitle-box">
    <div class="subtitle-title">🎬 文件内容</div>
    <pre class="subtitle-content">{{ subtitle }}</pre>
  </div>

  <!-- 目录结构打印 -->
  <div v-if="treeText" class="subtitle-box">
    <div class="subtitle-title">📂 目录结构</div>
    <pre class="subtitle-content">{{ treeText }}</pre>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import {
  getChildFiles,
  getDefaultFiles,
  getParentFiles,
  getContentTxt,
  getDisk
} from '@/api/fileApi.js'

const current = ref({ path: '', files: [] })
const selected = ref(null)

const subtitle = ref('')
const treeText = ref('')

const disks = ref([])
const selectedDisk = ref('')

/**
 * 初始化
 */
onMounted(async () => {
  current.value = (await getDefaultFiles()).data
  disks.value = (await getDisk()).data
})

/**
 * 进入目录
 */
const open = async (item) => {
  if (item.type === 'FILE') return

  const nextPath = current.value.path + '\\' + item.name
  current.value = (await getChildFiles(nextPath)).data
  selected.value = null
}

/**
 * 返回上一级
 */
const goParent = async () => {
  current.value = (await getParentFiles(current.value.path)).data
  selected.value = null
}

/**
 * 切换磁盘
 */
const switchDisk = async () => {
  if (!selectedDisk.value) return
  current.value = (await getChildFiles(selectedDisk.value)).data
  selected.value = null
}

/**
 * 解析文件
 */
const parseJson = async () => {
  const file = current.value.files.find(
    f => f.name === selected.value && f.type === 'FILE'
  )
  if (!file) return alert('请选择文件')

  subtitle.value = (await getContentTxt(
    current.value.path,
    file.name
  )).data
}

/**
 * ========= 逐层打印文件结构 =========
 */
const MAX_DEPTH = 5

const printStructure = async () => {
  treeText.value = ''
  await printLevel(current.value.path, '', 0)
}

const printLevel = async (path, prefix, depth) => {
  if (depth > MAX_DEPTH) return

  const res = await getChildFiles(path)

  
  const files = res.data.files.filter(f => !f.name.startsWith('.' ) && f.name !== 'node_modules')

  for (let i = 0; i < files.length; i++) {
    const file = files[i]
    const isLast = i === files.length - 1

    treeText.value +=
      prefix +
      (isLast ? '└── ' : '├── ') +
      (file.type === 'DIR' ? '📁 ' : '📄 ') +
      file.name +
      '\n'

    if (file.type === 'DIR') {
      const nextPrefix = prefix + (isLast ? '    ' : '│   ')
      const nextPath = path + '\\' + file.name
      await printLevel(nextPath, nextPrefix, depth + 1)
    }
  }
}
</script>

<style scoped>
.file-browser {
  width: 520px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  padding: 14px;
  font-family: ui-monospace, SFMono-Regular, Menlo, monospace;
}

.toolbar {
  display: flex;
  align-items: center;
  gap: 8px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.back-btn,
.print-btn,
.parse-btn {
  border: none;
  border-radius: 6px;
  padding: 6px 10px;
  cursor: pointer;
}

.back-btn {
  background: #f0f2f5;
}

.print-btn {
  background: #52c41a;
  color: #fff;
}

.parse-btn {
  background: #1677ff;
  color: #fff;
}

.path {
  flex: 1;
  font-size: 13px;
  color: #555;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-list {
  list-style: none;
  padding: 6px 0;
  margin: 0;
  max-height: 300px;
  overflow-y: auto;
}

.file-item {
  display: flex;
  gap: 10px;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
}

.file-item:hover {
  background: #f5f7fa;
}

.file-item.active {
  background: #e6f2ff;
  color: #1677ff;
}

.icon {
  width: 20px;
  text-align: center;
}

.empty {
  padding: 20px;
  text-align: center;
  color: #999;
}

.subtitle-box {
  margin-top: 12px;
  border-top: 1px solid #eee;
  padding-top: 10px;
}

.subtitle-title {
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 6px;
}

.subtitle-content {
  max-height: 600px;
  overflow-y: auto;
  background: #f6f8fa;
  padding: 10px;
  border-radius: 6px;
  font-size: 12px;
  white-space: pre;
}
</style>
