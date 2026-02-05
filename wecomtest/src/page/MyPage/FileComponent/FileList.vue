<template>
    <ul class="file-list">
        <FileItem v-for="file in files" :key="file.name" :file="file" :selected="selected" :is-favorite="isFavorite"
            @select="$emit('select', $event)" @open="$emit('open', $event)"
            @toggle-favorite="$emit('toggle-favorite', $event)" />

        <li v-if="!filteredFiles.length" class="empty">📭 当前目录为空</li>
    </ul>
</template>
<script setup>
import { computed } from 'vue'
import FileItem from './FileItem.vue'
import { filterFiles } from '@/page/MyPage/FileComponent/fileUtils.js'
const props = defineProps({
  files: {
    type: Array,
    required: true
  },
  selected: String,

  // 🔑 必须是 Function
  isFavorite: {
    type: Function,
    required: true
  }
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