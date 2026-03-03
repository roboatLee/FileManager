<template>
  <div class="private-chat">

    <div class="header">
      <button @click="goBack">返回</button>
      <div>正在和 {{ targetUser }} 私聊</div>
    </div>

    <div class="chat-box" ref="chatBox">
      <div
        v-for="(m, index) in messages"
        :key="index"
        :class="['msg', m.sender === currentUser ? 'me' : 'other']"
      >
        <div class="bubble">
          <div class="name">{{ m.sender }}</div>
          <div>{{ m.content }}</div>
        </div>
      </div>
    </div>

    <div class="input-bar">
      <input
        v-model="text"
        @keyup.enter="send"
        placeholder="输入消息..."
      />
      <button @click="send">发送</button>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const router = useRouter()

const token = localStorage.getItem("token")
const currentUser = localStorage.getItem("username")

const targetUser = route.params.username

const ws = ref(null)
const messages = ref([])
const text = ref("")
const chatBox = ref(null)

onMounted(() => {
  connect()
})

function connect() {

  ws.value = new WebSocket(
    `ws://10un4gz933676.vicp.fun:80/chat?token=${token}`
  )

  ws.value.onmessage = async (e) => {

    const msg = JSON.parse(e.data)

    if (msg.type === "chat") {

      // 只接收属于当前私聊的消息
      if (
        msg.data.conversationId.includes(currentUser) &&
        msg.data.conversationId.includes(targetUser)
      ) {
        messages.value.push(msg.data)
      }

    }

    await nextTick()
    scrollToBottom()
  }
}

function send() {

  if (!text.value.trim()) return

  ws.value.send(JSON.stringify({
    type: "chat",
    data: {
      content: text.value,
      toUser: targetUser
    }
  }))

  text.value = ""
}

function goBack() {
  router.push("/chat")
}

function scrollToBottom() {
  if (!chatBox.value) return
  chatBox.value.scrollTop = chatBox.value.scrollHeight
}
</script>

<style scoped>
.private-chat {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  height: 50px;
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 0 10px;
  background: #07c160;
  color: white;
}

.chat-box {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  background: #f5f5f5;
}

.msg {
  display: flex;
  margin: 10px 0;
}

.msg.me {
  justify-content: flex-end;
}

.bubble {
  max-width: 60%;
  padding: 10px;
  border-radius: 10px;
  background: white;
}

.msg.me .bubble {
  background: #95ec69;
}

.input-bar {
  height: 60px;
  display: flex;
  gap: 10px;
  padding: 10px;
  border-top: 1px solid #ddd;
}
</style>