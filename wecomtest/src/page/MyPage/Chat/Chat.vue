<template>

    <h2>聊天</h2>

    用户名：

    <input v-model="userName">

    <br><br>

    <button @click="connect">
        连接
    </button>

    <br><br>

    <div v-for="(m, index) in messages" :key="index">
        {{ m.sender }} : {{ m.content }}
    </div>

    <br>

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


function connect() {

    ws = new WebSocket(
        `ws://10un4gz933676.vicp.fun:80/chat?user=${userName.value}`
    )

    ws.onopen = () => {

        console.log("连接成功")

    }

    ws.onmessage = (e) => {

        const msg = JSON.parse(e.data)
        messages.value.push(msg)

    }

}


function send() {

    if (!ws) return

    ws.send(text.value)

    text.value = ""

}

</script>