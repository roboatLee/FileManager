import { QuestionControllerApi } from '@/api/generated'; 
import { http } from '@/api/http'
import { QuestionDto } from '@/api/generated';
const api = new QuestionControllerApi(undefined, undefined, http)


export async function addQuestion(authorization: string,dto: QuestionDto) {
  return api.addQuestion(dto,authorization)
}