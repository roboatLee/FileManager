<!-- <template>
<h1>{{ WhereIcome }}</h1>
<h1>{{ Txt2success }}</h1>
<p>失败原因：{{ result1 }}</p>
</template> -->

<template>
<p>{{ userInfo }}</p>
</template>


<script setup>
import { onMounted, ref } from 'vue';


const userInfo = ref();
onMounted(async () => {
    const res = await fetch('/api/user/info')
    userInfo.value = await res.json()
    
})



</script>
