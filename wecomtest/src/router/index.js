import Login from "@/auth/view//Login.vue";
import { createRouter, createWebHistory } from "vue-router";
import Parent from "@/page/StudyPage/EmitAndProps/Parent.vue";
import vditor from "@/page/MyPage/vditor/vditor.vue";
import FileBrowser from "@/page/MyPage/FileComponent/FileBrowser.vue";
import { useAuthStore } from "@/auth/store/authStore";

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
    component: () => import("@/page/MyPage/Chat/ChatPage.vue"),
  },
  {
    path: "/register",
    component: () => import("@/views/Register.vue"),
  },
  {
    path: "/private/:username",
    component: () => import("@/views/PrivateChat.vue"),
  },
  {
    path: "/blog",
    component: () => import("@/page/MyPage/letBlog/blog.vue"),
  },
  {
    path: "/addblog",
    component: () => import("@/page/MyPage/letBlog/addBlog.vue"),
  },
  {
    path: "/todo",
    component: () => import("@/todoList/ToDoList.vue"),
  },
  {
    path: "/minio",
    component: () => import("@/minio/minio.vue"),
  },
  {
    path: "/blog/detail/:id",
    name: "BlogDetail",
    component: () => import("@/page/MyPage/letBlog/BlogDetail.vue"),
  },
  {
    path: "/myprofile",
    name: "UserCenter",
    component: () => import("@/usercenter/UserCenter.vue"),
  },
    {
    path: "/letstudy",
    name: "letstudy",
    component: () => import("@/letstudy/letstudy.vue"),
  },
      {
    path: "/letstudydetail",
    name: "letstudydetail",
    component: () => import("@/letstudy/letstudydetail.vue"),
  },
        {
    path: "/addquetion",
    name: "addquetion",
    component: () => import("@/letstudy/addquestion.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  const token = localStorage.getItem("token");

  // 白名单页面
  const whiteList = ["/login", "/register"];

  if (!token && !whiteList.includes(to.path)) {
    next("/login");
    return;
  }

  // 已登录访问登录页，自动跳首页
  if (token && to.path === "/login") {
    next("/");
    return;
  }

  next();
});

export default router;
