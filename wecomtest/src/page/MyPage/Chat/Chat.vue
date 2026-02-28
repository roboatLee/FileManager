<template>

<h2>聊天</h2>

用户名：

<input v-model="userName">

<br><br>

<button @click="connect">
连接
</button>

<br><br>

<div v-for="m in messages">
{{ m }}
</div>

<input v-model="text">

<button @click="send">
发送
</button>

</template>


<script setup>

import { ref } from 'vue'

let ws = null

const userName = ref("")
const text = ref("")
const messages = ref([])


function connect(){

  ws = new WebSocket(
    "ws://10un4gz933676.vicp.fun:80/chat?user=" + userName.value
  )

  ws.onmessage = e=>{
    messages.value.push(e.data)
  }

}


function send(){

  ws.send(text.value)

}

</script>