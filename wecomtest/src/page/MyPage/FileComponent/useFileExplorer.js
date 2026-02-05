import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {
  getChildFiles,
  getDefaultFiles,
  getParentFiles,
  getContentTxt,
  getDisk
} from '@/api/fileApi'
import { isDir, joinPath } from '@/page/MyPage/FileComponent/fileUtils'
import { printTree } from '../utils/fileTreePrinter'

export function useFileExplorer() {
  const router = useRouter()

  const current = ref({ path: '', files: [] })
  const selected = ref(null)
  const subtitle = ref('')
  const treeText = ref('')
  const disks = ref([])
  const selectedDisk = ref('')
  const favorites = ref(new Set())

  onMounted(async () => {
    current.value = (await getDefaultFiles()).data
    disks.value = (await getDisk()).data
  })

  const open = async (item) => {
    if (!isDir(item)) return
    current.value = (await getChildFiles(
      joinPath(current.value.path, item.name)
    )).data
    selected.value = null
  }

  const goParent = async () => {
    current.value = (await getParentFiles(current.value.path)).data
    selected.value = null
  }

  const switchDisk = async () => {
    if (!selectedDisk.value) return
    current.value = (await getChildFiles(selectedDisk.value)).data
    selected.value = null
  }

  const parseJson = async () => {
    const file = current.value.files.find(
      f => f.name === selected.value && !isDir(f)
    )
    if (!file) return alert('请选择文件')

    subtitle.value = (await getContentTxt(
      current.value.path,
      file.name
    )).data
  }

  const printStructure = async () => {
    treeText.value = (await printTree(current.value.path)).join('\n')
  }

  const openInWorkspace = () => {
    const file = current.value.files.find(
      f => f.name === selected.value && !isDir(f)
    )
    if (!file) return alert('请选择文件')

    router.push({
      name: 'workspace',
      query: {
        path: current.value.path,
        name: file.name
      }
    })
  }

  const isFavorite = (file) =>
    file && favorites.value.has(file.name)

  const toggleFavorite = (file) => {
    if (!file) return
    favorites.value.has(file.name)
      ? favorites.value.delete(file.name)
      : favorites.value.add(file.name)
  }

  return {
    current,
    selected,
    subtitle,
    treeText,
    disks,
    selectedDisk,
    favorites,
    open,
    goParent,
    switchDisk,
    parseJson,
    printStructure,
    openInWorkspace,
    isFavorite,
    toggleFavorite
  }
}
