import Login from "@/views/Login.vue";
import { createRouter, createWebHistory } from "vue-router";
import Parent from "@/page/StudyPage/Parent.vue";
import vditor from "@/page/MyPage/vditor/vditor.vue";
import FileBrowser from "@/page/MyPage/FileComponent/FileBrowser.vue";
import { useAuthStore } from "@/auth/authStore";

const routes = [
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/",
    component: () => import("@/page/MyPage/Home.vue"),
  },
  { path: "/file", name: "Json", component: FileBrowser },
  { path: "/vditor", name: "vditor", component: vditor },
  { path: "/study", name: "study", component: Parent },
  {
    path: "/workspace",
    name: "workspace",
    component: () => import("@/page/MyPage/EditFile/Workspace.vue"),
  },
  {
    path: "/chat",
    name: "chat",
    component: () => import("@/page/MyPage/Chat/Chat.vue"),
  },
  {
    path: "/register",
    component: () => import("@/views/Register.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  const token = localStorage.getItem("token")

  // 白名单页面
  const whiteList = ["/login", "/register"]

  if (!token && !whiteList.includes(to.path)) {
    next("/login")
    return
  }

  // 已登录访问登录页，自动跳首页
  if (token && to.path === "/login") {
    next("/")
    return
  }

  next()
})

export default router;
