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
        <FileList :files="current.files" :selected="selected" :is-favorite="isFavorite"
            @select="fileName => selected = fileName" @open="open" @toggle-favorite="toggleFavorite" />
        <!-- JSON / 字幕显示 -->
        <ContentViewer :content="subtitle" title="🎬 文件内容" />

        <!-- 目录结构显示 -->
        <ContentViewer :content="treeText" title="📂 目录结构" />
    </div>
</template>

<script setup>
import FileList from './FileList.vue'
import ContentViewer from './ContentViewer.vue'
import { useFileExplorer } from './useFileExplorer'

const {
    current,
    selected,
    subtitle,
    treeText,
    disks,
    selectedDisk,
    open,
    goParent,
    switchDisk,
    parseJson,
    printStructure,
    openInWorkspace,
    isFavorite,
    toggleFavorite
} = useFileExplorer()

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
