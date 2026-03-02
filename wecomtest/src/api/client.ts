import { Configuration } from "./generated/configuration"
import { AuthControllerApi } from "./generated/apis/auth-controller-api"
import { http } from "./http"

const configuration = new Configuration()

export const authApi = new AuthControllerApi(
  configuration,
  undefined,
  http
)