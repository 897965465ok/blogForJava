<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, toRefs, watchEffect} from 'vue';
import {useStore} from '@/stores'
import {useRoute, useRouter} from 'vue-router';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import Prism from 'prismjs';
import 'prismjs/components/prism-clojure.js';
import Editor, {EditorCore, type EditorOptions} from '@toast-ui/editor';
import * as BlogApi from "@/api/BlogApi";
import {ElMessage} from "element-plus";

import {api} from "@/api";

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

let currentArticle:any ;
let options: EditorOptions;
let editor: EditorCore;
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
    height: '75vh',
    initialValue: '',
    theme: 'white',
    plugins: [[codeSyntaxHighlight, {highlighter: Prism}]]
  })
  editor = new Editor(options);
})


async function getOneMarkdown(id: any) {

  let {message, code, result} = await BlogApi.queryOneArticle(id);
  currentArticle = result;
  console.log(result)
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: "打开文章成功",
      type: "success",
    });
    let {data} = await api.get(result.articlePath);
    editor.setMarkdown(data);
  } else {
    ElMessage.error({
      message: "打开文章失败",
      type: "error",
    });
  }


}

async function changeArticle() {
   const file = new File([editor.getMarkdown()], currentArticle.name, { type: "text/plain" });
   return await BlogApi.updateOneArticle(currentArticle,file);
}


watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 
defineExpose({
  ...toRefs(data),
  changeArticle,
  getOneMarkdown
})


</script>
<template>
  <div class="viewer"></div>
</template>

<style lang='scss' scoped>
// :deep(.toastui-editor-contents) {
//   font-size: 18px;
//   background: #ffffff;

//   h1,
//   h2,
//   h3,
//   h4,
//   h5,
//   h6 {
//     padding: 10px;
//     border-bottom: none;
//   }

//   pre {
//     margin: 0px;
//     padding: 12px;
//     background-color: transparent;
//   }

// }
</style>