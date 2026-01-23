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
      <select v-model="selectedDisk" @change="switchDisk">
        <option v-for="disk in disks" :key="disk" :value="disk">💽{{ disk }}</option>
      </select>

      <span class="path" :title="current.path">
        {{ current.path || '根目录' }}
      </span>
    </div>

    <!-- 文件列表 -->
    <ul class="file-list">
      <li v-for="item in current.files" :key="item.name"
        :class="['file-item', item.type, { active: item.name === selected }]" @click="selected = item.name"
        @dblclick="open(item)">
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

  <div v-if="subtitle" class="subtitle-box">
    <div class="subtitle-title">🎬 字幕内容</div>
    <pre class="subtitle-content">
      {{ subtitle }}
  </pre>
  </div>
</template>



<script setup>
import { ref, onMounted } from 'vue'
import { getChildFiles, getDefaultFiles, getParentFiles, getContentTxt,getDisk } from '@/api/fileApi.js'

const current = ref({
  path: '',
  files: []
})

const selected = ref(null)
const subtitle = ref('')     // 字幕内容
const loading = ref(false)   // 加载状态
const disks = ref([])
const selectedDisk = ref('')

/**
 * 初始化：默认目录
 */
onMounted(async () => {
  const res = await getDefaultFiles()
  current.value = res.data

  disks.value =(await getDisk()).data

})

/**
 * 进入下一级目录（双击目录）
 */
const open = async (item) => {
  if (item.type === 'FILE') {
    console.log('打开文件', item.name)
    return
  }

  const nextPath = current.value.path + '\\' + item.name
  const res = await getChildFiles(nextPath)
  current.value = res.data
  selected.value = null
}

/**
 * 返回上一级
 */
const goParent = async () => {
  const res = await getParentFiles(current.value.path)

  current.value = res.data
  selected.value = null
}

const parseJson = async () => {
  // 找到当前选中的文件对象
  const file = current.value.files.find(
    f => f.name === selected.value && f.type === 'file'
  )

  if (!file) {
    alert('请先选择一个文件')
    return
  }

  try {
    loading.value = true

    const res = await getContentTxt(
      current.value.path, // 目录路径
      file.name            // 文件名
    )

    subtitle.value = res.data
  } finally {
    loading.value = false
  }
}

const switchDisk = async () => {
  if (!selectedDisk.value) return
  const res = await getChildFiles(selectedDisk.value)
  current.value = res.data
  selected.value = null
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

/* 工具栏 */
.toolbar {
  display: flex;
  align-items: center;
  gap: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.back-btn {
  border: none;
  background: #f0f2f5;
  border-radius: 6px;
  padding: 6px 10px;
  cursor: pointer;
  transition: all 0.2s;
}

.back-btn:hover:not(:disabled) {
  background: #e6f4ff;
}

.back-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.path {
  flex: 1;
  font-size: 13px;
  color: #555;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 文件列表 */
.file-list {
  list-style: none;
  padding: 6px 0;
  margin: 0;
  max-height: 300px;
  overflow-y: auto;
}

/* 文件项 */
.file-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.15s, color 0.15s;
}

.file-item:hover {
  background: #f5f7fa;
}

.file-item.active {
  background: #e6f2ff;
  color: #1677ff;
}

/* 图标 */
.icon {
  width: 20px;
  text-align: center;
  font-size: 16px;
}

/* 名称 */
.name {
  flex: 1;
  font-size: 14px;
}

/* 目录/文件区分 */
.file-item.dir .name {
  font-weight: 600;
}

.file-item.file .name {
  color: #444;
}

/* 空目录 */
.empty {
  padding: 20px;
  text-align: center;
  color: #999;
  font-size: 13px;
}

/* 滚动条美化 */
.file-list::-webkit-scrollbar {
  width: 6px;
}

.file-list::-webkit-scrollbar-thumb {
  background: #d0d7de;
  border-radius: 3px;
}

.file-list::-webkit-scrollbar-track {
  background: transparent;
}

.parse-btn {
  border: none;
  background: #1677ff;
  color: #fff;
  border-radius: 6px;
  padding: 6px 12px;
  cursor: pointer;
}

.parse-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
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
  max-height: 200px;
  overflow-y: auto;
  background: #f6f8fa;
  padding: 10px;
  border-radius: 6px;
  font-size: 12px;
  white-space: pre-wrap;
}
</style>
