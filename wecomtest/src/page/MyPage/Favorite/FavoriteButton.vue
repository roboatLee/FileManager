<template>
  <span
    class="favorite-btn"
    :title="favorite ? '取消收藏' : '加入收藏'"
    @click.stop="toggle"
  >
    {{ favorite ? '⭐' : '☆' }}
  </span>
</template>

<script setup>
const props = defineProps({
  favorite: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['toggle'])

const toggle = () => {
  emit('toggle')
}
</script>

<style scoped>
.favorite-btn {
  cursor: pointer;
  margin-left: 8px;
  font-size: 16px;
  user-select: none;
}

.favorite-btn:hover {
  transform: scale(1.1);
}
</style>
