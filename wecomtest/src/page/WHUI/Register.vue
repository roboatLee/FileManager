<template>


</template>
<script setup>

ww.register({
  corpId: 'wwb80449aeb36f41ef',       // 必填，当前用户企业所属企业ID
  jsApiList: ['getExternalContact'], // 必填，需要使用的JSAPI列表
  getConfigSignature                 // 必填，根据url生成企业签名的回调函数
})


async function getConfigSignature(url) {
  // 根据 url 生成企业签名
  // 生成方法参考 https://developer.work.weixin.qq.com/document/14924
  return { timestamp, nonceStr, signature }
}

</script>
