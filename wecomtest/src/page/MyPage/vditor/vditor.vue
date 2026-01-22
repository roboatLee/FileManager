<template>
  <div ref="editorRef"></div>
  <button @click="getValue">刷新</button>
  <div> {{ vditorValue  }}</div>
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

const emit = defineEmits(['update:modelValue'])

const editorRef = ref(null)
let vditor = null
const vditorValue = ref()

onMounted(() => {
  vditor = new Vditor(editorRef.value, {
    height: 500,
    mode: 'sv',
    cache: { enable: false },

    after: () => {
      vditor.setValue(props.modelValue)
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
      vditor.setValue(val)
    }
  }
)

onBeforeUnmount(() => {
  vditor?.destroy()
})

function getValue(){
  vditorValue.value =  vditor.getValue()
  console.log(vditorValue.value)
}

</script>
