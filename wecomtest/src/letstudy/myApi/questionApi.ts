import { QuestionControllerApi } from '@/api/generated'; 
import { http } from '@/api/http'
import { QuestionDto } from '@/api/generated';
import axios from "axios"

const api = new QuestionControllerApi(undefined, undefined, http)


// export async function addQuestion(authorization: string,dto: QuestionDto) {
//   return api.addQuestion(dto,authorization)
// }

export const addQuestion = (token: string, questionDto: QuestionDto) => {
  return axios.post("/api/question/addQuestion", questionDto, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json"
    }
  })
}