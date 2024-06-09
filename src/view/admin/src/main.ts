import {createApp, getCurrentInstance} from 'vue'
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import router from './router'
import App from './App.vue'
import './assets/main.css'
import 'element-plus/dist/index.css'
import {ElPagination} from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import VueI18n from 'vue-i18n'
import i18n from '@/i18n'
const app = createApp(App)
app.use(createPinia().use(piniaPluginPersistedstate))
app.use(router)
app.use(i18n)
app.use(ElPagination)
app.mount('#app')

// @ts-ignore
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.provide("global", {
    title: "标题",
})

/*app.config.globalProperties.$global = {
    global: "global"
}
// 获取当前组件实例
const currentInstance = getCurrentInstance();*/



