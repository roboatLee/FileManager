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

    <div class="main-area">

      <!-- 左侧会话列表 -->
      <div class="user-panel">
        <div class="user-title">会话列表</div>

        <!-- 公共聊天室 -->
        <div
          class="user-item"
          :class="{ active: activeUser === GLOBAL }"
          @click="selectUser(GLOBAL)"
        >
          🌍 公共聊天室
        </div>

        <!-- 在线用户 -->
        <div
          v-for="u in users"
          :key="u"
          class="user-item"
          :class="{ self: u === currentUser, active: u === activeUser }"
          @click="selectUser(u)"
        >
          <span class="dot"></span>
          {{ u }}
        </div>
      </div>

      <!-- 右侧聊天区 -->
      <div class="chat-area">
        <ChatWindow
          :messages="conversations[activeUser] || []"
          :currentUser="currentUser"
          @send="handleSend"
        />
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue"
import ChatWindow from "./components/ChatWindow.vue"

const token = localStorage.getItem("token")
const currentUser = localStorage.getItem("username")

const ws = ref(null)
const connected = ref(false)

const GLOBAL = "global"
const activeUser = ref(GLOBAL)

const users = ref([])

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

  connect()
})

function connect() {

  ws.value = new WebSocket(
    `ws://10un4gz933676.vicp.fun:80/chat?token=${token}`
  )

  ws.value.onopen = () => {
    connected.value = true
  }

  ws.value.onmessage = (e) => {

  const msg = JSON.parse(e.data)

  switch (msg.type) {

    case "history":

      conversations[GLOBAL].splice(
        0,
        conversations[GLOBAL].length,
        ...msg.data
      )

      break

    case "chat":

      const message = msg.data
      const target = message.toUser || GLOBAL

      if (!conversations[target]) {
        conversations[target] = []
      }

      conversations[target].push(message)

      break

    case "join":

      conversations[GLOBAL].push({
        type: "system",
        content: msg.data.user + " 加入了聊天室"
      })

      break

    case "leave":

      conversations[GLOBAL].push({
        type: "system",
        content: msg.data.user + " 离开了聊天室"
      })

      break

    case "users":

      users.value = msg.data
      break
  }
}

  ws.value.onclose = () => {
    connected.value = false
  }
}

/* =====================
   发送消息
===================== */
function handleSend(content) {

  if (!connected.value) return

  const toUser = activeUser.value === GLOBAL
    ? null
    : activeUser.value

  ws.value.send(JSON.stringify({
    type: "chat",
    data: {
      content,
      toUser
    }
  }))
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
  ws.value?.close()
}
</script>

<style>
html,
body,
#app {
  height: 100%;
  margin: 0;
}

.app {
  height: 100%;
  display: flex;
  flex-direction: column;
  font-family: Arial;
  background: #f0f2f5;
}

/* 顶部栏 */
.top-bar {
  height: 60px;
  flex-shrink: 0;
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
}

.name-input {
  height: 36px;
  padding: 0 12px;
  border-radius: 18px;
  border: 1px solid #ddd;
}

.connect-btn {
  height: 36px;
  padding: 0 18px;
  border-radius: 18px;
  border: none;
  background: #07c160;
  color: white;
  cursor: pointer;
}

.connect-btn:disabled {
  background: #ccc;
}

/* 主体区域 */
.main-area {
  flex: 1;
  display: flex;
  overflow: hidden;
  padding: 10px;
}

/* 左侧用户 */
.user-panel {
  width: 180px;
  flex-shrink: 0;
  border: 1px solid #ccc;
  background: #fafafa;
  border-radius: 6px;
  margin-right: 10px;
  display: flex;
  flex-direction: column;
}

.user-title {
  padding: 10px;
  font-weight: bold;
  border-bottom: 1px solid #ddd;
  text-align: center;
  background: #f0f0f0;
}

.user-list {
  flex: 1;
  overflow-y: auto;
  padding: 5px;
}

.user-item {
  padding: 8px;
  margin: 4px 0;
  border-radius: 6px;
  display: flex;
  align-items: center;
}

.user-item.self {
  background: #d9f7be;
}

.dot {
  width: 8px;
  height: 8px;
  background: #52c41a;
  border-radius: 50%;
  margin-right: 6px;
}

/* 聊天区域 */
.chat-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-box {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  background: #f5f5f5;
  border: 1px solid #ccc;
  border-radius: 6px;
}

/* 消息 */
.msg-row {
  display: flex;
  margin: 10px 0;
}

.msg-row.me {
  justify-content: flex-end;
}

.bubble {
  max-width: 60%;
  padding: 10px 14px;
  border-radius: 12px;
  background: white;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.msg-row.me .bubble {
  background: #95ec69;
}

.name {
  font-size: 12px;
  color: #666;
  margin-bottom: 3px;
}

.text {
  font-size: 16px;
  word-break: break-all;
}

/* 输入框 */
.input-bar {
  height: 60px;
  flex-shrink: 0;
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.input-bar input {
  flex: 1;
  padding: 8px;
  font-size: 16px;
}

.input-bar button {
  width: 80px;
}

/* 系统消息 */
.system-msg {
  text-align: center;
  margin: 10px 0;
}

.system-text {
  display: inline-block;
  padding: 4px 12px;
  font-size: 12px;
  color: #999;
  background: #e5e5e5;
  border-radius: 10px;
}

/* ================= ⭐移动端覆盖 ================= */

@media (max-width:768px) {

  html,
  body,
  #app {
    height: 100%;
  }

  .app {
    background: #ededed;
  }

  /* 顶部栏更像APP */
  .top-bar {
    height: 52px;
    padding: 0 12px;
    background: #07c160;
    color: white;
    border: none;
  }

  .title {
    font-size: 18px;
  }

  .connect-area {
    display: flex;
    gap: 6px;
  }

  .name-input {
    height: 30px;
    font-size: 14px;
    border-radius: 6px;
  }

  .connect-btn {
    height: 30px;
    font-size: 14px;
    padding: 0 10px;
    border-radius: 6px;
    background: white;
    color: #07c160;
  }

  /* 隐藏用户列表 */
  .user-panel {
    display: none;
  }

  .main-area {
    padding: 0;
  }

  /* 聊天区全屏 */
  .chat-area {
    background: #ededed;
  }

  .chat-box {
    border: none;
    border-radius: 0;
    padding: 10px 8px 70px 8px;
    background: #ededed;
  }

  /* 行间距更紧凑 */
  .msg-row {
    margin: 6px 0;
  }

  /* ⭐聊天气泡优化 */
  .bubble {
    max-width: 75%;
    padding: 10px 12px;
    border-radius: 16px;
    font-size: 16px;
    box-shadow: none;
  }

  .msg-row.me .bubble {
    background: #95ec69;
  }

  .msg-row.other .bubble {
    background: white;
  }

  /* 用户名更小 */
  .name {
    font-size: 11px;
    margin-bottom: 2px;
  }

  .text {
    font-size: 16px;
    line-height: 1.4;
  }

  /* ⭐系统消息更像微信 */
  .system-text {
    background: #dcdcdc;
    font-size: 12px;
  }

  /* ⭐底部输入栏（重点优化） */
  .input-bar {

    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;

    height: 55px;

    padding: 6px 8px;

    background: #f7f7f7;

    border-top: 1px solid #ddd;

    display: flex;
    gap: 8px;

    padding-bottom:
      calc(env(safe-area-inset-bottom));
  }

  /* 输入框像微信 */
  .input-bar input {

    border-radius: 20px;

    border: 1px solid #ddd;

    padding: 8px 14px;

    font-size: 16px;

    background: white;
  }

  /* 发送按钮像APP */
  .input-bar button {

    width: 70px;

    border: none;

    border-radius: 20px;

    background: #07c160;

    color: white;

    font-size: 15px;
  }

}
</style>