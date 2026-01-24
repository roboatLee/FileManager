<template>
  <ul class="file-list">
    <li
      v-for="file in files"
      :key="file.name"
      :class="{ active: file.name === active }"
      @click="$emit('open', file.name)"
    >
      📄 {{ file.name }}
    </li>
  </ul>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getChildFiles } from '@/api/fileApi.js'

const props = defineProps({
  path: String,
  active: String
})

const files = ref([])

const loadFiles = async () => {
  if (!props.path) return
  const res = await getChildFiles(props.path)
  files.value = res.data.files.filter(f => f.type === 'file')
}

onMounted(loadFiles)
watch(() => props.path, loadFiles)
</script>

<style scoped>
.file-list {
  flex: 1;
  list-style: none;
  padding: 6px;
  margin: 0;
  overflow-y: auto;
}

.file-list li {
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 13px;
}

.file-list li:hover {
  background: #f0f2f5;
}

.file-list li.active {
  background: #e6f4ff;
  color: #1677ff;
}
</style>
