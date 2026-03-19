<template>
  <div>
    <input v-model="title"/>
    <Editor v-model="markdown" @update:html="handleHtmlChange"/>
    <button @click="save">保存到服务器</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Editor from '@/page/MyPage/vditor/vditor.vue' // 根据实际情况调整路径
import { addArtile } from './myApi/articleApi'
import { ArticleDto } from './moudel/BlogDDto'

const title = ref()
const markdown = ref('')
const html = ref('')

const handleHtmlChange = (newHtml) => {
  html.value = newHtml
  console.log('Markdown:', markdown.value)
  console.log('HTML:', newHtml)
}


const save = () => {
  const articleDto = new ArticleDto(title.value,markdown.value,html.value)
  const token = localStorage.getItem("token")
  addArtile(token,articleDto)
}

</script>

<style scoped>
.preview {
  margin-top: 20px;
  border: 1px solid #ccc;
  padding: 10px;
}
</style>
