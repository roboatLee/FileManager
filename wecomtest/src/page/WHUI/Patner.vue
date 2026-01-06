<template>
  <div class="container">
    <!-- 加载中提示 -->
    <div v-if="loading" class="loading">
      ⏳ 正在加载公司与员工信息，请稍候...
    </div>


    <!-- 公司列表 -->
    <div v-else>
          <!-- 页面统计 -->
      <div class="stat-bar">
        <span>🏢 公司 {{ stat.companyCount }} 家</span>
        <span>👥 用户 {{ stat.userCount }} 人</span>
      </div>

      <div class="company" v-for="company in companyList" :key="company.agentId">
        <!-- 公司标题（可折叠） -->
        <h3 class="company-name" @click="company.expanded = !company.expanded">
          {{ company.cropName }}
          <span class="count">
            （已选 {{ selectedCount(company) }} / {{ company.cropWorker.length }}）
          </span>
          <span class="toggle">
            {{ company.expanded ? '▲' : '▼' }}
          </span>
        </h3>

        <!-- 员工列表 -->
        <div class="worker-list" v-if="company.expanded">
          <label class="worker" v-for="worker in company.cropWorker" :key="worker.UserId">
            <input type="checkbox" v-model="worker.checked" />
            <img class="avatar" :src="worker.UserAvatarPath" />
            <span>{{ worker.UserName }}</span>
          </label>
        </div>
      </div>
    </div>

    <!-- 底部消息发送区 -->
    <div class="message-box">
      <textarea v-model="message" placeholder="请输入群发消息内容"></textarea>

      <div class="actions">
        <span>共选择 {{ totalSelected }} 人</span>
        <button :disabled="sending" @click="sendMessage">
          {{ sending ? '发送中...' : '发送消息' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

/**
 * 页面统计（公司数 / 用户数）
 */
const stat = ref({
  companyCount: 0,
  userCount: 0
})
/**
 * 计算总数
 * 
*/
const calcStat = (list) => {
  stat.value.companyCount = list.length
  stat.value.userCount = list.reduce(
    (sum, company) => sum + company.cropWorker.length,
    0
  )
}
/**
 * 公司 + 员工列表
 */
const companyList = ref([])

/**
 * 群发消息内容
 */
const message = ref('')

/**
 * 页面状态
 */
const loading = ref(false)
const sending = ref(false)

/**
 * 获取公司列表
 * 并且获得总人数
 */
const fetchCompanyList = async () => {
  loading.value = true
  try {
    const res = await fetch('/api/wecom/getPartner')
    if (!res.ok) throw new Error('请求失败')

    const data = await res.json()

    const list = data.map(company => ({
      ...company,
      expanded: false,
      cropWorker: company.cropWorker.map(worker => ({
        ...worker,
        checked: false
      }))
    }))

    companyList.value = list

    // ✅ 获取完成后立刻统计
    calcStat(list)
  } catch (err) {
    alert('❌ 获取公司列表失败')
    console.error(err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCompanyList()
})

/**
 * 单个公司选中人数
 */
const selectedCount = (company) => {
  return company.cropWorker.filter(w => w.checked).length
}

/**
 * 全部选中人数
 */
const totalSelected = computed(() => {
  return companyList.value.reduce((sum, company) => {
    return sum + selectedCount(company)
  }, 0)
})

/**
 * 发送消息
 */
const sendMessage = async () => {
  if (!message.value.trim()) {
    alert('请输入消息内容')
    return
  }

  const companyPayload = companyList.value
    .map(company => {
      const userList = company.cropWorker
        .filter(w => w.checked)
        .map(w => w.UserId)

      return {
        corpid: company.corpid,
        cropName: company.cropName,
        agentId: company.agentId,
        userList
      }
    })
    .filter(c => c.userList.length > 0)

  if (companyPayload.length === 0) {
    alert('请至少选择一个用户')
    return
  }

  const payload = {
    message: message.value,
    companyList: companyPayload
  }

  try {
    sending.value = true

    const res = await fetch('/api/wecom/sendPartnerMessage', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    })

    if (!res.ok) throw new Error('发送失败')

    alert('✅ 群发消息发送成功')

    // 清空输入 & 勾选
    message.value = ''
    companyList.value.forEach(company => {
      company.cropWorker.forEach(worker => {
        worker.checked = false
      })
    })
  } catch (err) {
    alert('❌ 消息发送失败，请稍后重试')
    console.error(err)
  } finally {
    sending.value = false
  }
}
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: 0 auto;
  padding-bottom: 120px;
}

/* 加载中 */
.loading {
  text-align: center;
  padding: 60px;
  font-size: 16px;
  color: #666;
}

/* 公司卡片 */
.company {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 16px;
}

.stat-bar {
  position: sticky;
  top: 0;
  z-index: 10;

  background: #fff;
  padding: 8px 0;
  border-bottom: 1px solid #eee;

  display: flex;
  gap: 16px;
  font-size: 14px;
}

.company-name {
  margin-bottom: 10px;
  cursor: pointer;
  user-select: none;
}

.count {
  font-size: 14px;
  color: #666;
  margin-left: 6px;
}

.toggle {
  float: right;
  font-size: 14px;
  color: #999;
}

/* 员工列表 */
.worker-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.worker {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 4px 6px;
  border-radius: 4px;
}

.worker:hover {
  background: #f5f7fa;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

/* 底部发送栏 */
.message-box {
  position: sticky;
  bottom: 0;
  background: #fff;
  padding-top: 12px;
  border-top: 1px solid #eee;
}

textarea {
  width: 100%;
  height: 90px;
  padding: 8px;
  box-sizing: border-box;
  resize: none;
}

.actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

button {
  padding: 6px 18px;
  cursor: pointer;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
