<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect } from 'vue';
import {storeToRefs} from 'pinia';
import { useStore  } from '@/stores';
import { useRoute, useRouter } from 'vue-router';
import { deleteArticle } from "@/api/BlogApi";
import TableVue from './RoleTable.vue'
import { createArticle} from '@/api/BlogApi';

/**
 * 仓库
 */
const store = useStore();
const {treeMap} = storeToRefs(store);
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
onMounted(async () =>{})
watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 
defineExpose({
  ...toRefs(data)
})

const ArticleList = ref([]);
const dialogTableVisible = ref(false)


function checkButton(selectorList: any) {
  ArticleList.value = selectorList
}

function deleteSelectorArticleList() {
  // 删除选择中的文章
  deleteArticle
}

function changeArticle() {
  // 修改选中的文章
  dialogTableVisible.value = true
  Object.keys(form).forEach((item) => {
    form[item] = ArticleList.value[0][item]
  })

}


async function creatArticle() {
  // 创建文章
  dialogTableVisible.value = true
  // let result  =   await createArticle(form);
}


const form: formType = reactive({
  name: '',
  paragraph: '',
  tag: '',
  articlePath: '',
  sideArticle: '',
  hot: '',
  picture: ''
})

const onSubmit = () => {
  console.log(form)
}

var menu = {
  "name": "System",
  "path": "/system",
  "hidden": false,
  "redirect": "noRedirect",
  "component": "Layout",
  "alwaysShow": true,
  "meta": {
    "title": "系统管理",
    "icon": "system",
    "noCache": false,
    "link": null
  },
  "children": [
    {
      "name": "User",
      "path": "user",
      "hidden": false,
      "component": "system/user/index",
      "meta": {
        "title": "用户管理",
        "icon": "user",
        "noCache": false,
        "link": null
      }
    }

  ]
}

















</script>
<template>
  <div class="button-wrapper">
    <el-button @click="creatArticle">新增</el-button>
    <el-button :disabled="(ArticleList.length < 1)" @click="deleteSelectorArticleList">删除</el-button>
    <el-button :disabled="(ArticleList.length != 1)" @click="changeArticle">修改</el-button>
  </div>
  <TableVue @check="checkButton">

  </TableVue>
</template>


<style lang='scss' scoped>
.form-header {
  display: flex;

  .header-upload {
    flex: 1 1 30%;

    .el-upload-list {
      margin: 0;
    }
  }

  .header-textarea {
    flex: 1 1 60%;
    margin-left: 15px;

    .el-textarea {
      height: 100%;
    }

    :deep(.el-textarea__inner) {
      height: 100%;
    }
  }
}

:deep(.el-upload-list) {
  margin: 0;
}

.form-body {
  display: flex;
  margin-top: 15px;

  .form-switch {
    display: flex;
  }
}

.article-title {
  width: 100%;
  margin: 16px 0;
  display: flex;
  justify-content: space-between;
  color: #409eff;
}

.button-wrapper {
  margin: 16px 0px;
}
</style>


