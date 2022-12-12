<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, toRefs, watchEffect} from 'vue';
import {useStore} from '@/stores'
import {useRoute, useRouter} from 'vue-router';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
// Step 1. Import prismjs
import Prism from 'prismjs';

// Step 2. Import language files of prismjs that you need
import 'prismjs/components/prism-clojure.js';


import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import Editor, {type EditorOptions} from '@toast-ui/editor';


let options: EditorOptions

/**
 * 仓库
 */
const store = useStore();
/**
 * 路由对象
 */
const route = useRoute();

/**
 * 路由实例
 */
const router = useRouter();
//console.log('1-开始创建组件-setup')
/**
 * 数据部分
 */
const data = reactive({})
onBeforeMount(() => {
  //console.log('2.组件挂载页面之前执行----onBeforeMount')
})


onMounted(() => {
  // let articlerPath = route.query.articlerPath.replace("http://localhost:3800","");
  // let {data} = await this.$api.get(articlerPath);
  // viewer.invoke("setMarkdown", data);
  // // 请求评论 // 先不写
  // let articleId = this.$route.query.uuid;
  // this.comments = await getComments(articleId);

  options = reactive({
    el: document.querySelector(".viewer") as HTMLElement,
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '',
    theme: 'white',
    plugins: [[codeSyntaxHighlight, {highlighter: Prism}]]
  })
  const editor = new Editor(options);
  editor.setMarkdown(`## 内容`);

  console.log(editor.getMarkdown())
})

watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 
defineExpose({
  ...toRefs(data)
})
</script>
<template>
  <div class="viewer"></div>
</template>

<style lang='scss' scoped>
:deep(.toastui-editor-contents) {
  font-size: 18px;
  background: #ffffff;

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    padding: 10px;
    border-bottom: none;
  }

  pre {
    margin: 0px;
    padding: 12px;
    background-color: transparent;
  }

}
</style>