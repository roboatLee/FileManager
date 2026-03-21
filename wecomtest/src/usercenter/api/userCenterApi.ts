import { AuthControllerApi } from "@/api/generated/apis/auth-controller-api";
import { UserControllerApi } from "@/api/generated/apis/user-controller-api";

import { http } from "@/api/http";
import { UserVo } from "../moudle/user";

const api = new AuthControllerApi(undefined, undefined, http);
const userapi = new UserControllerApi(undefined, undefined, http);

export async function getUserByToken() {
  return api.getUserByToken();
}

export async function uploadAvater(file: File) {
  const formData = new FormData()
  formData.append("file", file)

  return http.post("/user/upload", formData)
}
