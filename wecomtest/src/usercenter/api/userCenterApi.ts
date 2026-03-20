import { AuthControllerApi } from "@/api/generated/apis/auth-controller-api"
import { http } from "@/api/http"
import { UserVo } from "../moudle/user"

const api = new AuthControllerApi(undefined, undefined, http)


export async function getUserByToken() {
  return api.getUserByToken()
}
