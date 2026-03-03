<template>
    <div class="chat-window">

        <div class="chat-box" ref="chatBox">
            <div v-for="(msg, index) in messages" :key="index" :class="[
                'message',
                msg.type === 'system'
                    ? 'system-message'
                    : msg.sender === currentUser
                        ? 'self'
                        : 'other'
            ]">
                <!-- 系统消息 -->
                <div v-if="msg.type === 'system'" class="system-text">
                    {{ msg.content }}
                </div>

                <!-- 普通聊天消息 -->
                <div v-else class="bubble">
                    <div class="name">
                        {{ msg.sender }}
                    </div>

                    <div class="text">
                        {{ msg.content }}
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

</style>