<template>

    <h2>聊天系统</h2>

    <div class="top-bar">

        <div class="title">
            💬 WebSocket聊天
        </div>

        <div class="connect-area">

            <input v-model="userName" placeholder="输入用户名" class="name-input" />

            <button @click="connect" :disabled="connected" class="connect-btn">
                {{ connected ? "已连接" : "连接" }}
            </button>

        </div>

    </div>


    <div class="chat-box" ref="chatBox">

        <div v-for="(m, index) in messages" :key="index" :class="[
            'msg-row',
            m.sender === userName ? 'me' : 'other'
        ]">

            <div class="bubble">

                <div class="name">

                    {{ m.sender }}

                </div>

                <div class="text">

                    {{ m.content }}

                </div>

            </div>

        </div>

    </div>


    <div class="input-bar">

        <input v-model="text" @keyup.enter="send" placeholder="输入消息..." />

        <button @click="send" :disabled="!connected">

            发送

        </button>

    </div>

</template>



<script setup>

import { ref, nextTick } from 'vue'

let ws = null

const userName = ref("")

const text = ref("")

const messages = ref([])

const connected = ref(false)

const chatBox = ref(null)



function connect() {

    if (connected.value) return

    if (!userName.value) {

        alert("请输入用户名")

        return

    }


    ws = new WebSocket(

        `ws://10un4gz933676.vicp.fun:80/chat?user=${userName.value}`

    )


    ws.onopen = () => {

        connected.value = true

        console.log("连接成功")

    }


    ws.onmessage = async (e) => {

        const msg = JSON.parse(e.data)

        messages.value.push(msg)


        await nextTick()

        scrollToBottom()

    }


    ws.onclose = () => {

        connected.value = false

        console.log("连接关闭")

    }

}



function send() {

    if (!connected.value) return

    if (!text.value.trim()) return


    ws.send(text.value)

    text.value = ""

}



function scrollToBottom() {

    if (!chatBox.value) return

    chatBox.value.scrollTop =

        chatBox.value.scrollHeight

}

</script>



<style>
body {

    font-family: Arial;

}


/* 顶部 */

.top-bar {

    height: 60px;

    border-bottom: 1px solid #ddd;

    display: flex;

    justify-content: space-between;

    align-items: center;

    padding: 0 20px;

    background: white;
}

.title {

    font-size: 20px;

    font-weight: bold;

    color: #333;

}
.name-input {

    height: 36px;

    padding: 0 12px;

    border-radius: 18px;

    border: 1px solid #ddd;

    outline: none;

    font-size: 14px;

}
.name-input:focus {

    border-color: #07c160;

}
.connect-btn {

    height: 36px;

    padding: 0 18px;

    border-radius: 18px;

    border: none;

    background: #07c160;

    color: white;

    font-size: 14px;

    cursor: pointer;

}
.connect-btn:hover {

    background: #06ad56;

}
.connect-btn:disabled {

    background: #ccc;

    cursor: not-allowed;

}

/* 聊天区域 */

.chat-box {

    height: 450px;

    border: 1px solid #ccc;

    overflow-y: auto;

    padding: 10px;

    background: #f5f5f5;

    border-radius: 6px;

}



/* 每行消息 */

.msg-row {

    display: flex;

    margin: 10px 0;

}



/* 自己 */

.msg-row.me {

    justify-content: flex-end;

}



/* 别人 */

.msg-row.other {

    justify-content: flex-start;

}



/* 气泡 */

.bubble {

    max-width: 60%;

    padding: 10px 14px;

    border-radius: 12px;

    background: white;

    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);

}



/* 自己气泡 */

.msg-row.me .bubble {

    background: #95ec69;

}



/* 名字 */

.name {

    font-size: 12px;

    color: #666;

    margin-bottom: 3px;

}



/* 内容 */

.text {

    font-size: 16px;

    word-break: break-all;

}



/* 输入区域 */

.input-bar {

    margin-top: 10px;

    display: flex;

    gap: 10px;

}



.input-bar input {

    flex: 1;

    padding: 8px;

    font-size: 16px;

    border-radius: 4px;

    border: 1px solid #ccc;

}



.input-bar button {

    width: 80px;

}
</style>