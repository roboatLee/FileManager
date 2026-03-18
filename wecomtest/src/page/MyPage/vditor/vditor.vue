<template>
  <div ref="editorRef"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import Vditor from 'vditor'
import 'vditor/dist/index.css'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits([
  'update:modelValue',   // Markdown 源码
  'update:html'          // ← 新增：HTML 格式内容
])

const editorRef = ref(null)
let vditor = null

onMounted(() => {
  vditor = new Vditor(editorRef.value, {
    height: 500,
    mode: 'sv',
    cache: { enable: false },

    after: () => {
      vditor.setValue(props.modelValue)
      emit('update:html', vditor.getHTML())
    },

    input: (value) => {
      emit('update:modelValue', value)
      emit('update:html', vditor.getHTML())
    }

  })
})

watch(
  () => props.modelValue,
  (val) => {
    if (vditor && val !== vditor.getValue()) {
      vditor.setValue(val)
      emit('update:html', vditor.getHTML())
    }
  }
)

onBeforeUnmount(() => {
  vditor?.destroy()
})


</script>
