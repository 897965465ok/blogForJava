import {fileURLToPath, URL} from 'node:url'
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue(), vueJsx(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),],
    base:'./', // 解决首页白屏问题
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        port: 3000,
        open: false, //自动打开
        base: "./ ", //生产环境路径
        proxy: { // 本地开发环境通过代理实现跨域，生产环境使用 nginx 转发
            // 正则表达式写法
            '^/v1': {
                target: 'http://localhost:8888', // 后端服务实际地址
                changeOrigin: true, //开启代理
                rewrite: (path) => path.replace(/^\/v1/, '')
            }
        }
    }

})




