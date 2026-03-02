import Login from "@/views/Login.vue";
import { createRouter, createWebHistory } from "vue-router";
import Parent from "@/page/StudyPage/Parent.vue";
import vditor from "@/page/MyPage/vditor/vditor.vue";
import FileBrowser from "@/page/MyPage/FileComponent/FileBrowser.vue";
import { useAuthStore } from "@/auth/authStore"


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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// router.beforeEach((to, from, next) => {
//   const authStore = useAuthStore()

//   if (to.path !== "/login" && !authStore.isLogin) {
//     next("/login")
//   } else {
//     next()
//   }
// })

export default router;
