<!-- <template>
<h1>{{ WhereIcome }}</h1>
<h1>{{ Txt2success }}</h1>
<p>失败原因：{{ result1 }}</p>
</template> -->

<template>
  <div>
    <h2>企业微信回调页</h2>
    <p>登录状态：{{ loginStatus }}</p>
    <p>JS-SDK getContext：{{ contextResult }}</p>
    <p>是否执行了result {{ isProcess }}</p>
    <p>执行结果：{{ resultforFuncation }}</p>
    <P>企业微信函数执行结果: {{ enterPrise }}</P>
    <P>应用微信函数执行结果: {{ Application }}</P>
    <P>选择群接口执行结果: {{ MyselectCorpGroupContact }}</P>

  </div>
</template>


<script setup>
import { onMounted, ref } from 'vue';


const loginStatus = ref('初始化')
const contextResult = ref('未调用')
const sdkStatus = ref('未初始化')
const resultforFuncation = ref()
const isProcess = ref()
const enterPrise = ref()
const Application = ref()
const MyselectCorpGroupContact = ref('为成功')



onMounted(async () => {
  const url = new URL(window.location.href)
  const code = url.searchParams.get('code')
  if (!code) {
    loginStatus.value = '❌ URL 没有 code'
    return
  }

  const res = await fetch(window.location.origin + '/api/wecom/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ code })
  })
  loginStatus.value = res.ok ? '✅ 登录成功' : '❌ 登录失败'

  await ww.register({
    corpId: 'wwb80449aeb36f41ef',
    agentId: 1000028,
    jsApiList: ['getContext','selectCorpGroupContact'],

    async getConfigSignature() {
      const r = await fetch(
        window.location.origin + `/api/wecom/getconfigsignature?url=${encodeURIComponent(location.href)}`
      )
      const data = await r.json()
      enterPrise.value = data
      return {
        timestamp: data.timestamp,
        nonceStr: data.nonceStr,
        signature: data.signature
      }
    },

    async getAgentConfigSignature() {
      const r = await fetch(
        window.location.origin + `/api/wecom/getAgentConfigSignature?url=${encodeURIComponent(location.href)}`
      )
      const data = await r.json()
      Application.value = data
      return {
        timestamp: data.timestamp,
        nonceStr: data.nonceStr,
        signature: data.signature
      }
    }
  })

  ww.getContext({
    success(result) {
      resultforFuncation.value = JSON.stringify(result)
    },
    fail(err) {
      resultforFuncation.value = '❌ ' + JSON.stringify(err)
    },
    complete() {
      isProcess.value = '执行了'
    }
  })

  ww.selectCorpGroupContact({
    fromDepartmentId: 0,
    mode: 'single',
    type: ['department', 'user'],
    success(result){
      MyselectCorpGroupContact.value="执行成功"
      console.log(result)
    }
  })
})

</script>
