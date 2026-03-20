<template>
  <div class="tabs">
    <span :class="{active: modelValue==='overview'}" @click="$emit('update:modelValue','overview')">总览</span>
    <span :class="{active: modelValue==='blog'}" @click="$emit('update:modelValue','blog')">我的博客</span>
    <span :class="{active: modelValue==='setting'}" @click="$emit('update:modelValue','setting')">设置</span>
  </div>
</template>

<script setup>
defineProps(["modelValue"]);
</script>

<style scoped>
.tabs span {
  margin-right: 20px;
  cursor: pointer;
}

.active {
  font-weight: bold;
  border-bottom: 2px solid #000;
}
</style>