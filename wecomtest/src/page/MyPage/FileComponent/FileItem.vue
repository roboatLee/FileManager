<template>
  <li
    :class="['file-item', file.type, { active: isSelected }]"
    @click="$emit('select', file.name)"
    @dblclick="$emit('open', file)"
  >
    <span class="icon">{{ isDir(file) ? '📁' : '📄' }}</span>
    <span class="name">{{ file.name }}</span>

    <!-- ⭐ 收藏按钮 -->
    <FavoriteButton
      :favorite="isFavorite(file)"
      @toggle="$emit('toggle-favorite', file)"
    />
  </li>
</template>

<script setup>
import { computed } from 'vue'
import { isDir } from '@/page/MyPage/FileComponent/fileUtils.js'
import FavoriteButton from '@/page/MyPage/Favorite/FavoriteButton.vue'

const props = defineProps({
  file: {
    type: Object,
    required: true
  },
  selected: String,

  // 🔑 父组件传进来的函数
  isFavorite: {
    type: Function,
    required: true
  }
})

const isSelected = computed(() => props.selected === props.file.name)
</script>

<style scoped>
.file-item {
  display: flex;
  align-items: center;
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

.name {
  flex: 1;
}
</style>
