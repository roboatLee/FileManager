import { UserVo } from "@/auth/moudel/UserVo"

export interface ArticleDetailVo {
  title: string
  markdownContent: string
  htmlContent: string
  userVo: UserVo
  createTime: string
  likeNumber: number
  viewNumber: number
}