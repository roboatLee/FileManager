<template>
  <div class="app">

    <!-- 顶部栏 -->
    <div class="top-bar">
      <div class="title">聊天系统</div>

      <div class="user-info">
        当前用户：{{ currentUser }}
        <button @click="disconnect" v-if="connected">
          断开
        </button>
      </div>
    </div>
    <button class="mobile-menu" @click="showUsers = true">
      ☰
    </button>

    <div class="main-area">
      <!-- 左侧会话列表 -->
      <div class="user-panel">
        <div class="user-title">会话列表</div>
        <UserPanel :users="users" :currentUser="currentUser" :activeUser="activeUser" @selectUser="selectUser" />
        <!-- 公共聊天室 -->
        <div class="user-item" :class="{ active: activeUser === GLOBAL }" @click="selectUser(GLOBAL)">
          🌍 公共聊天室
        </div>
      </div>

      <!-- 右侧聊天区 -->
      <div class="chat-area">
        <div class="chat-header">
          <span v-if="activeUser === GLOBAL">
            🌍 公共聊天室
          </span>
          <span v-else>
            💬 正在和 {{ activeUser }} 私聊
          </span>
        </div>
        <ChatWindow :messages="conversations[activeUser] || []" :currentUser="currentUser" @send="handleSend" />
      </div>
    </div>

    <div v-if="showUsers" class="mobile-user-panel">
      <div class="mobile-user-header">
        会话列表
        <button @click="showUsers = false">关闭</button>
      </div>

      <div class="mobile-user-item" :class="{ active: activeUser === GLOBAL }"
        @click="selectUser(GLOBAL); showUsers = false">
        🌍 公共聊天室
      </div>

      <div v-for="u in users" :key="u" class="mobile-user-item" @click="selectUser(u); showUsers = false">
        {{ u }}
      </div>
    </div>


  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue"
import { nextTick } from "vue"

import ChatWindow from "./components/ChatWindow.vue"
import UserPanel from "./components/UserPanel.vue"

import { connect, send, close } from "./services/websocket"
import { handleMessage } from "./services/messageHandler"

const token = localStorage.getItem("token")
const currentUser = localStorage.getItem("username")
const connected = ref(false)
const GLOBAL = "global"
const activeUser = ref(GLOBAL)
const users = ref([])
const showUsers = ref(false)

/* =====================
   所有会话
===================== */
const conversations = reactive({
  [GLOBAL]: []
})

/* =====================
   自动连接
===================== */
onMounted(() => {
  if (!token || !currentUser) {
    alert("请先登录")
    return
  }
  connectSocket()
})


function connectSocket() {
  connect(token, {
    onOpen() {
      connected.value = true
    },
    onMessage(msg) {
      handleMessage(msg)
    },
    onClose() {
      connected.value = false
    }

  })

}


/* =====================
   发送消息
===================== */
function handleSend(content) {
  if (!connected.value) return

  const toUser = activeUser.value === GLOBAL
    ? null
    : activeUser.value

  send({
    type: "chat",
    data: {
      content,
      toUser
    }
  })
}

/* =====================
   切换会话
===================== */
function selectUser(u) {

  if (u === currentUser) return

  activeUser.value = u
  if (!conversations[u]) {
    conversations[u] = []
  }
}

/* =====================
   断开连接
===================== */
function disconnect() {
  close()
}

nextTick(() => {
  const box = document.querySelector(".chat-box")
  if (box) {
    box.scrollTop = box.scrollHeight
  }
})

</script>

<style scoped src="./style/chat.css">

</style>