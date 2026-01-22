<template>
  <div>{{ msg }}</div>
  <button @click="changeMsg">修改父组件数据</button>
</template>

<script setup>
const props = defineProps({
  msg: String
})

const emit = defineEmits(['change'])

function changeMsg() {
  emit('change', '这是子组件发回去的新值')
}
</script>
