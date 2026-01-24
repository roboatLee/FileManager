<template>
  <div ref="editorRef"></div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import Vditor from 'vditor'
import 'vditor/dist/index.css'

const props = defineProps({
  modelValue: String
})

const emit = defineEmits(['update:modelValue'])

const editorRef = ref()
let vditor

onMounted(() => {
  vditor = new Vditor(editorRef.value, {
    height: '100%',
    mode: 'ir',
    cache: { enable: false },

    after: () => {
      vditor.setValue(props.modelValue || '')
    },

    input: (value) => {
      emit('update:modelValue', value)
    }
  })
})

watch(
  () => props.modelValue,
  (val) => {
    if (vditor && val !== vditor.getValue()) {
      vditor.setValue(val || '')
    }
  }
)
</script>
