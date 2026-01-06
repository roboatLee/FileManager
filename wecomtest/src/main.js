import {createRouter,createWebHistory} from 'vue-router'
// import About  from './page/About.vue'
// import Register from './page/Register.vue'
// import MyPage from './page/MyPage.vue'
// import Patner from './page/Patner.vue'
// import PatnerHistore from './page/PatnerHistore.vue'
import Home from './page/MyPage/Home.vue'
import File from './page/MyPage/File.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


const router = createRouter({
	history:createWebHistory(),
	routes: [
		{path: "/",name: "Home",component: Home},
		// {path: "/about",name: "About",component: About},
		// {path: "/register",name: "Register",component: Register},
		// {path: "/mypage",name: "mypage",component: MyPage},
		// {path: "/patner",name: "Patner",component: Patner},
		// {path: "/history",name: "PatnerHistore",component: PatnerHistore},
		{path: "/file",name: "Json",component: File},


	]
})


import { createApp } from 'vue'
import App from './App.vue'

createApp(App)
    .use(router)
	.use(ElementPlus)
    .mount('#app')
