
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import { createPinia } from "pinia"
import router from "@/router/index.js" 


import { createApp } from "vue";
import App from "./App.vue";


createApp(App).use(router).use(ElementPlus).use(createPinia()).mount("#app");
