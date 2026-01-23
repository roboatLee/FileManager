<template>
    <li :class="['file-item', file.type, { active: isSelected }]" @click="$emit('select', file.name)"
        @dblclick="$emit('open', file)">
        <span class="icon">{{ isDir(file) ? '📁' : '📄' }}</span>
        <span class="name">{{ file.name }}</span>
    </li>
</template>
<script setup>
import { isDir } from '@/page/MyPage/FileComponent/fileUtils.js'
import { computed } from 'vue'

const props = defineProps({
  file: Object,
  selected: String
})

const isSelected = computed(() => props.selected === props.file.name)
</script>

<style lang="css" scoped>

.file-item {
  display: flex;
  gap: 10px;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
}
.file-item:hover { background: #f5f7fa; }
.file-item.active { background: #e6f2ff; color: #1677ff; }
.icon { width: 20px; text-align: center; }


</style>