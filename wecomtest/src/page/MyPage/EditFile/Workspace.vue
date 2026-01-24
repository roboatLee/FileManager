<template>
  <div class="workspace">
    <!-- 左侧：文件列表 -->
    <aside class="sidebar">
      <div class="sidebar-title">
        📁 {{ dirPath }}
      </div>

      <SidebarFileList
        :path="dirPath"
        :active="fileName"
        @open="openFile"
      />
    </aside>

    <!-- 右侧：编辑区 -->
    <main class="editor">
      <div class="editor-toolbar">
        <span class="filename">
          {{ fileName || '未打开文件' }}
        </span>
      </div>

      <MarkdownEditor v-model="content" />
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SidebarFileList from './SidebarFileList.vue'
import MarkdownEditor from '../vditor/vditor.vue'
import { readFileContent } from '@/api/fileApi.js'

const route = useRoute()

const dirPath = ref('')
const fileName = ref('')
const content = ref('')

onMounted(async () => {
  dirPath.value = route.query.path
  fileName.value = route.query.name

  if (dirPath.value && fileName.value) {
    await openFile(fileName.value)
  }
})

const openFile = async (name) => {
  const res = await readFileContent(dirPath.value, name)
  content.value = res.data
  fileName.value = name
}
</script>

<style scoped>
.workspace {
  display: flex;
  height: 100vh;
  background: #f5f7fa;
}

/* 左侧栏 */
.sidebar {
  width: 260px;
  background: #ffffff;
  border-right: 1px solid #eaeaea;
  display: flex;
  flex-direction: column;
}

.sidebar-title {
  padding: 12px;
  font-size: 13px;
  color: #555;
  border-bottom: 1px solid #eee;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 右侧编辑区 */
.editor {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.editor-toolbar {
  height: 40px;
  padding: 0 12px;
  display: flex;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #eee;
}

.filename {
  font-size: 14px;
  font-weight: 500;
}
</style>
