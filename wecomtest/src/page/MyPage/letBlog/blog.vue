<template>

    <h1>欢迎来到blog乐园</h1>
    <div v-for="blog in blogs">
        <h3>{{ blog.title }}</h3>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { getAllArtile } from './myApi/articleApi';
const blogs = ref([])

onMounted(async()=>{
    const res = await getAllArtile();
    blogs.value = res.data;            
    console.log(blogs.value)
})

</script>

<style scoped>

</style>