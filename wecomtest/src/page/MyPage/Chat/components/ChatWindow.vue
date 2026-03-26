<template>
    <div class="chat-window">

        <div class="message-container" ref="chatBox">
            <div v-if="!messages || messages.length === 0" class="empty">
                暂无消息，快来聊两句吧 👋
            </div>
            <!-- 有消息 -->
            <div v-else>
                <div v-for="(msg, index) in messages" :key="index" :class="[
                    'message',
                    msg.type === 'system'
                        ? 'system-message'
                        : String(msg.senderId) === currentUser
                            ? 'self'
                            : 'other'
                ]">
                    <!-- 系统消息 -->
                    <div v-if="msg.type === 'system'" class="system-text">
                        {{ msg.content }}
                        
                    </div>

                    <!-- 普通聊天消息 -->
                    <div v-else class="message-row">

                        <!-- 左侧头像（别人） -->
                        <img v-if="String(msg.senderId) !== currentUser" :src="msg.senderAvatar" class="avatar" />

                        <div class="bubble">
                            <div class="name">
                                {{ msg.senderName }}
                            </div>

                            <div class="text">
                                {{ msg.content }}
                            </div>
                        </div>

                        <!-- 右侧头像（自己） -->
                        <img v-if="String(msg.senderId) === currentUser" :src="msg.senderAvatar" class="avatar" />
                    </div>
                </div>
            </div>
        </div>




        <div class="input-bar">
            <input v-model="inputText" @keyup.enter="emitSend" placeholder="输入消息..." />
            <button @click="emitSend">发送</button>
        </div>

    </div>
</template>

<script setup>
import { ref, watch, nextTick } from "vue"

const { messages, currentUser } = defineProps({
    messages: Array,
    currentUser: String 
})

const emit = defineEmits(["send"])

const inputText = ref("")
const chatBox = ref(null)

function emitSend() {
    if (!inputText.value.trim()) return
    emit("send", inputText.value)
    inputText.value = ""
}

watch(
    () => messages,
    async () => {
        await nextTick()
        if (chatBox.value) {
            chatBox.value.scrollTop = chatBox.value.scrollHeight
        }
    },
    { deep: true }
)
</script>

<style scoped>
.system-message {
    text-align: center;
    color: #999;
    font-size: 13px;
    margin: 8px 0;
}

/* 整体布局 */
.chat-window {
    height: 100%;
    display: flex;
    flex-direction: column;
}

/* 聊天区域 */
.chat-box {
    flex: 1;
    overflow-y: auto;
    padding: 10px;
    background: #f5f5f5;
    min-height: fit-content;
}

/* 每条消息一行（关键） */
.message {
    display: flex;
    margin: 10px 0;
}

/* 自己的消息在右边 */
.self {
    justify-content: flex-end;
}

/* 别人的消息在左边 */
.other {
    justify-content: flex-start;
}

/* 气泡 */
.bubble {
    max-width: 60%;
    padding: 10px 12px;
    border-radius: 12px;
    background: white;
    word-break: break-word;
}

/* 自己的气泡颜色 */
.self .bubble {
    background: #95ec69;
}

/* 用户名 */
.name {
    font-size: 12px;
    color: #666;
    margin-bottom: 4px;
}

/* 内容 */
.text {
    font-size: 15px;
}

/* 底部输入框 */
.input-bar {
    height: 60px;
    display: flex;
    gap: 10px;
    padding: 10px;
    border-top: 1px solid #ddd;
}

.input-bar input {
    flex: 1;
    padding: 8px;
}

.input-bar button {
    padding: 8px 16px;
}

.empty {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #999;
    font-size: 14px;
}

.message-container {
    flex: 1;
    /* ⭐关键 */
    overflow-y: auto;
    /* ⭐防止撑开 */
}

.message-row {
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;
}

.avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    margin: 0 8px;
}

.self {
    justify-content: flex-end;
}
</style>