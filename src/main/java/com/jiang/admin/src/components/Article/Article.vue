<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect } from 'vue';
import { useStore } from '@/stores';
import { useRoute, useRouter } from 'vue-router';
import { deleteArticle } from "@/api/BlogApi";
import TableVue from './Table.vue';
import MarkdownVue from './Markdown.vue';
import { createArticle } from '@/api/BlogApi';
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
  //console.log('3.-组件挂载到页面之后执行-------onMounted')
})
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



const form:formType = reactive({
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


</script>
<template>
  <div class="button-wrapper">
    <el-button @click="creatArticle">新增</el-button>
    <el-button :disabled="(ArticleList.length < 1)" @click="deleteSelectorArticleList">删除</el-button>
    <el-button :disabled="(ArticleList.length != 1)" @click="changeArticle">修改</el-button>
  </div>
  <TableVue @check="checkButton"></TableVue>
  <el-dialog v-model="dialogTableVisible" destroy-on-close title="修改文章">
    <template #default>
      <el-form :model="form" label-width="100px">
        <MarkdownVue></MarkdownVue>
        <div class=" article-title el-upload__text">
          <span>文章封面图</span>
          <span>文章段落</span>
        </div>
        <div class="form-header">
          <div class="header-upload">
            <el-upload action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" drag multiple>
              <el-icon>
                <upload-filled />
              </el-icon>
              <div class="el-upload__text">
                <em>图片拖放</em><br>
                <em>点击上传</em>
              </div>
            </el-upload>
          </div>
          <div class="header-textarea">
            <el-input v-model="form.paragraph" resize="none" type="textarea" />
          </div>
        </div>
        <div class="form-body">
          <div class="form-switch">
            <el-form-item label="热门">
              <el-switch v-model="form.hot" />
            </el-form-item>
            <el-form-item label="侧边栏文章">
              <el-switch v-model="form.sideArticle" />
            </el-form-item>
            <el-form-item label="文章名">
              <el-input v-model="form.name" />
            </el-form-item>
            <el-form-item label="分类名">
              <el-input v-model="form.tag" />
            </el-form-item>
            <el-form-item label="文章路径">
              <el-input v-model="form.articlePath" />
            </el-form-item>
          </div>
        </div>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">修改</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </template>

  </el-dialog>
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