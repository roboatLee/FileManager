<template>
    <div class="container">
        <h2>我的任务</h2>

        <!-- 输入区域 -->
        <div class="input-box">
            <input v-model="newTodo.title" placeholder="标题（可选）" />
            <input v-model="newTodo.content" placeholder="内容" @keyup.enter="handleAddTodo" />
            <input type="datetime-local" v-model="deadlineInput" />
            <button @click="handleAddTodo">添加</button>
        </div>

        <!-- 任务列表 -->
        <ul>
            <li v-for="todo in todos" :key="todo.id">
                <div class="left">
                    <input type="checkbox" :checked="todo.done" @change="todoStore.toggleDone(todo.id!)" />

                    <div class="text">
                        <span class="title">{{ todo.title || '无标题' }}</span>
                        <span :class="{ completed: todo.done }">
                            {{ todo.content }}
                        </span>

                        <span class="deadline" :class="{ expired: isExpired(todo.deadline) }">
                            {{ formatRemaining(todo.deadline) }}
                        </span>
                    </div>
                </div>

                <button class="delete" @click="todoStore.removeTodo(todo.id!)">
                    ×
                </button>
            </li>
        </ul>

        <p v-if="todos.length === 0" class="empty">还没有任务哦～</p>
    </div>
</template>

<script setup lang="ts">
import { useTodoStore } from './stores/todo'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import { createTodo } from './moudle/todoDto'
import { onMounted,onUnmounted } from 'vue'
const todoStore = useTodoStore()
const { todos } = storeToRefs(todoStore)

// 表单数据
const newTodo = ref(createTodo({}))
const deadlineInput = ref("")

const now = ref(Date.now())
let timer: any

onMounted(() => {
  timer = setInterval(() => {
    now.value = Date.now()
  }, 60000) // 每分钟刷新
})

onUnmounted(() => {
  clearInterval(timer)
})


function handleAddTodo() {
    if (!newTodo.value.content.trim()) return

    const deadline = deadlineInput.value
        ? new Date(deadlineInput.value).getTime()
        : Date.now() + 86400000 // 默认一天后

    todoStore.addTodo(
        createTodo({
            ...newTodo.value,
            deadline
        })
    )

    // 重置
    newTodo.value = createTodo({})
    deadlineInput.value = ""
}

// 是否过期
function isExpired(deadline: number) {
    return Date.now() > deadline
}

// 剩余时间
function formatRemaining(deadline: number) {
    const diff = deadline - Date.now()

    if (diff <= 0) return "已过期"

    const h = Math.floor(diff / 1000 / 60 / 60)
    const m = Math.floor((diff / 1000 / 60) % 60)

    return `剩余 ${h}小时 ${m}分钟`
}
</script>


<style scoped>
.container {
    max-width: 500px;
    margin: 40px auto;
    font-family: Arial, sans-serif;
}

h2 {
    text-align: center;
}

.input-box {
    display: flex;
    gap: 8px;
    margin-bottom: 16px;
}

input {
    padding: 6px;
    border: 1px solid #ddd;
    border-radius: 6px;
}

button {
    padding: 6px 10px;
    border: none;
    border-radius: 6px;
    background: #409eff;
    color: white;
    cursor: pointer;
}

button:hover {
    background: #66b1ff;
}

ul {
    list-style: none;
    padding: 0;
}

li {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    margin-bottom: 8px;
    border-radius: 8px;
    background: #f9f9f9;
}

.left {
    display: flex;
    align-items: center;
    gap: 10px;
}

.text {
    display: flex;
    flex-direction: column;
}

.title {
    font-weight: bold;
}

.completed {
    text-decoration: line-through;
    color: #999;
}

.deadline {
    font-size: 12px;
    color: #666;
}

.expired {
    color: red;
}

.delete {
    background: none;
    color: #e74c3c;
    font-size: 18px;
}

.empty {
    text-align: center;
    color: #999;
}
</style>
