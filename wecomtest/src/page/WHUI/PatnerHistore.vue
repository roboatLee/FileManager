<template>
  <div class="page">
    <el-page-header content="群发历史记录" />

    <el-space direction="vertical" size="large" fill   style="width: 100%"
> 
      <el-card
        v-for="item in historyList"
        :key="item.messageStoreId"
        shadow="hover"
      >
        <!-- 卡片头 -->
        <template #header>
          <div class="card-header">
            <span class="title">群发消息</span>
            <el-button
              type="primary"
              link
              @click="item.show = !item.show"
            >
              {{ item.show ? '收起' : '查看详情' }}
            </el-button>
          </div>
        </template>

        <!-- 消息内容 -->
        <div class="message">
          {{ item.message }}
        </div>

        <!-- 元信息 -->
        <div class="meta">
          <el-tag type="info">
            🕒 {{ item.createTime }}
          </el-tag>

          <el-tag type="success">
            👥 {{ item.companyList.length }} 公司 /
            {{ totalUsers(item) }} 人
          </el-tag>
        </div>

        <!-- 详情 -->
        <el-collapse v-if="item.show" class="detail">
          <el-collapse-item
            v-for="company in item.companyList"
            :key="company.agentId"
            :title="company.cropName + '（' + company.agentId + '）'"
          >
            <el-space wrap>
              <el-tag
                v-for="user in company.userList"
                :key="user.userId"
                type="primary"
                effect="light"
              >
                {{ user.userName }}
              </el-tag>
            </el-space>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </el-space>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const historyList = ref([])

const fetchHistory = async () => {
  const res = await fetch('/api/wecom/message/history')
  const data = await res.json()

  historyList.value = data.map(item => ({
    ...item,
    show: false
  }))
}

const totalUsers = (item) => {
  return item.companyList.reduce(
    (sum, c) => sum + c.userList.length,
    0
  )
}

onMounted(fetchHistory)
</script>

<style scoped>
.page {
  margin: 20px;
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-weight: 600;
  font-size: 16px;
}

.message {
  font-size: 15px;
  margin-bottom: 12px;
}

.meta {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.detail {
  margin-top: 10px;
}
</style>
