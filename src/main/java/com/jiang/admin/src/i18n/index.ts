// index.js
import {createI18n} from 'vue-i18n'
import en from './English.js'
import zh from './Chinese.js'

const messages = {
    en,
    zh,
}
const language = (navigator.language || 'en').toLocaleLowerCase() // 这是获取浏览器的语言

const i18n = createI18n({
    locale: 'zh', // 语言
    fallbackLocale: 'en', //备用语言
    messages,
    legacy: false, // 如果要支持compositionAPI，此项必须设置为false;
    globalInjection: true, // 全局注册$t方法
})

export default i18n
