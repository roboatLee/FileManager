import { ArticleControllerApi } from "@/api/generated/apis/article-controller-api";
import { http } from '@/api/http.ts'

const api = new ArticleControllerApi(undefined, undefined, http)

export async function getAllArtile() {
  return api.getAllArtile()
}