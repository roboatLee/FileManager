<template>
    <div>{{ MessageShow }}</div>
    <div id="ww_login"></div>

</template>
<script setup>
import { onMounted, ref } from 'vue'
const messageShow = ref('')
// 初始化登录组件,把注册信息放进全局变量中
onMounted(() => {
    window.ww.createWWLoginPanel({
        el: '#ww_login',
        params: {
            login_type: 'CorpApp',
            appid: 'wwb80449aeb36f41ef',
            agentid: '1000028',
            redirect_uri: 'http://lcx.u3w.com/about',

        },
        async  onLoginSuccess({ code }) {
            console.log({ code })
        },
        onLoginFail(err) {
            messageShow.value = JSON.stringify(err)
        },
    })
})
</script>