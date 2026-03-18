<template>
  <div>
    <input v-model="title"/>
    <Editor v-model="markdownContent" />
    <button @click="addBlog">发送文章</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Editor from '@/page/MyPage/vditor/vditor.vue' // 根据实际情况调整路径
const title = ref()
const markdownContent = ref('# Hello, Vditor!')

function addBlog(){
    console.log(title.value)
}


</script>

<style scoped>
.preview {
  margin-top: 20px;
  border: 1px solid #ccc;
  padding: 10px;
}
</style>
