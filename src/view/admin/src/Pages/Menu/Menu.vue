<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect, provide, type Ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores';
import { useRoute, useRouter } from 'vue-router';
import TableVue from './components/MenuTable.vue'
import MarkdownVue from '../Article/component/Markdown.vue';
import CreateMenu from "@/Pages/Menu/components/CreateMenu.vue";
import { ElMessage } from 'element-plus';
import { deleteManyMenu } from '@/api/BlogApi';
const store = useStore()
const menuList = ref([])

const visible = ref(false)
provide("visible", visible)

const isDelete = ref(false)
const isLoading = ref(false)
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
onBeforeMount(async () => {


})
onMounted(async () => { })
watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 
defineExpose({
  ...toRefs(data)
})


// 保存选中的节点
function checkButton(selectorList: any) {
  menuList.value = selectorList
}

// 删除选择中的节点
async function deleteSelectByMenuList() {
  isLoading.value = !isLoading.value
  let { code, message } = await store.deleteManyMenu(menuList.value);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: '删除成功',
      type: 'success',
    })
  } else {
    ElMessage.error({
      message: '删除失败',
      type: 'error',
    })
  }
  isDelete.value = false
  isLoading.value = !isLoading.value
}

function changeMenu() {
  // 修改选中的文章
  visible.value = true
  Object.keys(form).forEach((item) => {
    form[item] = menuList.value[0][item]
  })

  console.log(menuList.value)



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




</script>
<template>
  <el-row class="button-wrapper">
    <el-button @click="visible = !visible">新增</el-button>
    <el-button :disabled="(menuList.length < 1)" @click="isDelete = !isDelete">删除</el-button>
    <!-- <el-button :disabled="(menuList.length != 1)" @click="changeMenu">修改</el-button> -->
  </el-row>

  <TableVue @check="checkButton"></TableVue>
  <CreateMenu :change="visible"></CreateMenu>

  <el-dialog v-model="isDelete" title="删除菜单" width="30%" align-center>
    <span>是否删除菜单？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="isDelete = false">
          取消
        </el-button>
        <el-button :loading="isLoading" @click="deleteSelectByMenuList">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style lang='scss' scoped>
.container {
  display: grid;
  // 区域一个名称算一个单元
  grid-template-areas: 'header header'
    'main aside'
    'footer footer';
  //grid-template-columns: 200px 1fr 2fr;
  //grid-template-rows: minmax(100px,200px) repeat(5 ,1fr);
  //grid-gap:10px;
  height: 700px;
  width: 100%;
  //控制横线
  grid-template-rows: 15% 75% 15%;
  // 控制纵线
  grid-template-columns: 70% 30%;
  // 控制线大小
  grid-column-gap: 5px;
  grid-row-gap: 5px;


  // 设置多余的单元
  // 横排还是纵排
  grid-auto-flow: column;
  // 设置多余的单元
  grid-auto-rows: 50px;
  // 设置跨单元
  grid-column-start: span 2;

  .item-1 {
    grid-column: 1 / 3;
    grid-row: 1 / 2;
  }

  .header {
    grid-area: header;

  }

  .main {
    grid-area: main;
  }

  .aside {
    grid-area: aside;
  }

  .footer {
    grid-area: footer;
  }
}


.menu-box {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr repeat(auto-fill, 1fr);
  grid-row-gap: 20px;
  grid-column-gap: 20px;
  grid-template-areas: 'header header' 'main main' 'footer footer';
}

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


