<template>
    <div class="file-browser">
        <!-- 工具栏 -->
        <div class="toolbar">
            <button @click="goParent" :disabled="!current.path" class="back-btn">⬅</button>
            <button @click="parseJson" :disabled="!selected" class="parse-btn">解析 JSON</button>
            <button @click="printStructure" :disabled="!current.path" class="print-btn">打印结构</button>
            <button @click="openInWorkspace" :disabled="!selected">
                📝 在 Workspace 打开
            </button>
            <select v-model="selectedDisk" @change="switchDisk">
                <option v-for="disk in disks" :key="disk" :value="disk">💽 {{ disk }}</option>
            </select>

            <span class="path" :title="current.path">{{ current.path || '根目录' }}</span>
        </div>

        <!-- 文件列表 -->
        <FileList :files="current.files" :selected="selected" @select="selected = $event" @open="open" />

        <!-- JSON / 字幕显示 -->
        <ContentViewer :content="subtitle" title="🎬 文件内容" />

        <!-- 目录结构显示 -->
        <ContentViewer :content="treeText" title="📂 目录结构" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import FileList from './FileList.vue'
import ContentViewer from './ContentViewer.vue'
import { getChildFiles, getDefaultFiles, getParentFiles, getContentTxt, getDisk } from '@/api/fileApi.js'
import { isDir, joinPath, filterFiles } from '@/page/MyPage/FileComponent/fileUtils.js'
import { useRouter } from 'vue-router'
const router = useRouter()


const current = ref({ path: '', files: [] })
const selected = ref(null)
const subtitle = ref('')
const treeText = ref('')
const disks = ref([])
const selectedDisk = ref('')
const MAX_DEPTH = 10

onMounted(async () => {
    try {
        current.value = (await getDefaultFiles()).data
        disks.value = (await getDisk()).data
    } catch (err) { alert(err.message) }
})

const open = async (item) => {
    if (!isDir(item)) return
    try {
        const nextPath = joinPath(current.value.path, item.name)
        current.value = (await getChildFiles(nextPath)).data
        selected.value = null
    } catch (err) { alert(err.message) }
}

const goParent = async () => {
    try {
        current.value = (await getParentFiles(current.value.path)).data
        selected.value = null
    } catch (err) { alert(err.message) }
}

const switchDisk = async () => {
    if (!selectedDisk.value) return
    try {
        current.value = (await getChildFiles(selectedDisk.value)).data
        selected.value = null
    } catch (err) { alert(err.message) }
}

const parseJson = async () => {
    const file = current.value.files.find(f => f.name === selected.value && !isDir(f))
    if (!file) return alert('请选择文件')
    try {
        subtitle.value = (await getContentTxt(current.value.path, file.name)).data
    } catch (err) { alert(err.message) }
}

// ===== 打印目录结构 =====
const printStructure = async () => {
    treeText.value = ''
    try {
        const lines = await printLevel(current.value.path, '', 0)
        treeText.value = lines.join('\n')
    } catch (err) { alert(err.message) }
}

const printLevel = async (path, prefix, depth) => {
    if (depth > MAX_DEPTH) return []
    const res = await getChildFiles(path)
    const files = filterFiles(res.data.files)
    const lines = []

    for (let i = 0; i < files.length; i++) {
        const file = files[i]
        const isLast = i === files.length - 1
        lines.push(prefix + (isLast ? '└── ' : '├── ') + (isDir(file) ? '📁 ' : '📄 ') + file.name)
        if (isDir(file)) {
            const nextPrefix = prefix + (isLast ? '    ' : '│   ')
            lines.push(...await printLevel(joinPath(path, file.name), nextPrefix, depth + 1))
        }
    }
    return lines
}

const openInWorkspace = () => {
    const file = current.value.files.find(
        f => f.name === selected.value && !isDir(f)
    )
    if (!file) return alert('请选择文件')

    router.push({
        name: 'workspace',
        query: {
            path: current.value.path,
            name: file.name
        }
    })
}

</script>

<style scoped>
.file-browser {
    width: 1000px;
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

.back-btn {
    background: #f0f2f5;
    border: none;
    border-radius: 6px;
    padding: 6px 10px;
    cursor: pointer;
}

.parse-btn {
    background: #1677ff;
    color: #fff;
    border: none;
    border-radius: 6px;
    padding: 6px 10px;
    cursor: pointer;
}

.print-btn {
    background: #52c41a;
    color: #fff;
    border: none;
    border-radius: 6px;
    padding: 6px 10px;
    cursor: pointer;
}

.path {
    flex: 1;
    font-size: 13px;
    color: #555;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>
