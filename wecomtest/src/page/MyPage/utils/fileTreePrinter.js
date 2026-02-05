import { getChildFiles } from '@/api/fileApi'
import { isDir, joinPath, filterFiles } from '@/page/MyPage/FileComponent/fileUtils'

const MAX_DEPTH = 10

export async function printTree(path, prefix = '', depth = 0) {
  if (depth > MAX_DEPTH) return []

  const res = await getChildFiles(path)
  const files = filterFiles(res.data.files)
  const lines = []

  for (let i = 0; i < files.length; i++) {
    const file = files[i]
    const isLast = i === files.length - 1

    lines.push(
      prefix +
      (isLast ? '└── ' : '├── ') +
      (isDir(file) ? '📁 ' : '📄 ') +
      file.name
    )

    if (isDir(file)) {
      const children = await printTree(
        joinPath(path, file.name),
        prefix + (isLast ? '    ' : '│   '),
        depth + 1
      )
      lines.push(...children)
    }
  }

  return lines
}