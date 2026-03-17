<template>
  <Child :msg="parentMsg" @change= "handleChange"/>
</template>

<script setup>
import Child from './Child.vue'
import { ref } from 'vue'

const parentMsg = ref('原始内容')
function handleChange(newValue) { parentMsg = newValue }
</script>
