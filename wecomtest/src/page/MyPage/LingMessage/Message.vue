<template>
  <div>
    <div v-for="msg in messages" :key="msg">{{ msg }}</div>
    <input v-model="inputMsg" @keyup.enter="sendMessage" placeholder="输入消息"/>
    <button @click="sendMessage">发送</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const messages = ref([])
const inputMsg = ref('')

const fetchMessages = async () => {
  const res = await axios.get('http://localhost:8080/chat/messages')
  messages.value = res.data
}

const sendMessage = async () => {
  if (!inputMsg.value) return
  await axios.post('http://localhost:8080/chat/send', null, { params: { msg: inputMsg.value } })
  inputMsg.value = ''
  fetchMessages()
}

onMounted(() => {
  fetchMessages()
  setInterval(fetchMessages, 1000) // 每秒轮询
})
</script>
