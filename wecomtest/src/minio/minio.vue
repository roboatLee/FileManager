<template>
    <div>
        <!-- 选择文件 -->
        <input type="file" @change="handleFileChange" />

        <!-- 预览 -->
        <div v-if="previewUrl">
            <p>预览：</p>
            <img :src="previewUrl" style="max-width: 200px;" />
        </div>

        <!-- 上传按钮 -->
        <button @click="upload">上传</button>

        <!-- 返回URL -->
        <div v-if="imageUrl">
            <p>上传成功：</p>
            <a :href="imageUrl" target="_blank">{{ imageUrl }}</a>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue"
import axios from "axios"

const file = ref(null)
const previewUrl = ref("")
const imageUrl = ref("")

// 选择文件
function handleFileChange(e) {
    file.value = e.target.files[0]

    // 本地预览
    previewUrl.value = URL.createObjectURL(file.value)
}

// 上传
async function upload() {
    if (!file.value) {
        alert("请选择文件")
        return
    }

    const formData = new FormData()
    formData.append("file", file.value)

    const res = await axios.post("/api/minio/upload", formData)
    imageUrl.value = res.data
}
</script>