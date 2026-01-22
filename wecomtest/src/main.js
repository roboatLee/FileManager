import {createRouter,createWebHistory} from 'vue-router'
import Parent from './page/StudyPage/Parent.vue'
import Home from './page/MyPage/Home.vue'
import File from './page/MyPage/File.vue'
import vditor from './page/MyPage/vditor/vditor.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


const router = createRouter({
	history:createWebHistory(),
	routes: [
		{path: "/",name: "Home",component: Home},
		{path: "/file",name: "Json",component: File},
		{path: "/vditor",name: "vditor",component: vditor},
		{path: "/study",name: "study",component: Parent},


	]
})


import { createApp } from 'vue'
import App from './App.vue'

createApp(App)
    .use(router)
	.use(ElementPlus)
    .mount('#app')
