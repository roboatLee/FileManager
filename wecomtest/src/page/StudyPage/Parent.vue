<template>
  <Child :msg="parentMsg" @change="parentMsg = $event" />
</template>

<script setup>
import Child from './Child.vue'
import { ref } from 'vue'

const parentMsg = ref('原始内容')
</script>
