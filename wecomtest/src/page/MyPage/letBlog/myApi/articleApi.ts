import { ArticleControllerApi } from "@/api/generated/apis/article-controller-api";
import { http } from '@/api/http'
import { ArticleDto } from "../moudel/BlogDDto";

const api = new ArticleControllerApi(undefined, undefined, http)

export async function getAllArtile() {
  return api.getAllArtile()
}

export async function addArtile(authorization: string,dto: ArticleDto) {
  return api.addArticle(authorization,dto)
}