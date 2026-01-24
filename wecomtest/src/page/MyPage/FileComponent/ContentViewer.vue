<template>
    <div v-if="content" class="content-box">
        <div class="content-title">{{ title }}</div>
        <pre class="content-text">{{ content }}</pre>
    </div>
</template>
<script setup>

const props = defineProps({
    content: String,
    title: { type: String, default: '内容' }
})
</script>

<style lang="css" scoped>
.content-box {
    margin-top: 12px;
    border-top: 1px solid #eee;
    padding-top: 10px;
}

.content-title {
    font-size: 13px;
    font-weight: 600;
    margin-bottom: 6px;
}

.content-text {
    max-height: 600px;
    overflow-y: auto;
    background: #f6f8fa;
    padding: 10px;
    border-radius: 6px;
    font-size: 12px;
    white-space: pre;
}
</style>