<template>
    <ul class="file-list">
        <FileItem v-for="file in filteredFiles" :key="file.name" :file="file" :selected="selected"
            @select="$emit('select', $event)" @open="$emit('open', $event)" />
        <li v-if="!filteredFiles.length" class="empty">📭 当前目录为空</li>
    </ul>
</template>
<script setup>
import { computed } from 'vue'
import FileItem from './FileItem.vue'
import { filterFiles } from '@/page/MyPage/FileComponent/fileUtils.js'

const props = defineProps({
    files: Array,
    selected: String
})

const filteredFiles = computed(() => filterFiles(props.files))
</script>

<style lang="css" scoped>
.file-list {
    list-style: none;
    padding: 6px 0;
    margin: 0;
    max-height: 300px;
    overflow-y: auto;
}

.empty {
    padding: 20px;
    text-align: center;
    color: #999;
}
</style>