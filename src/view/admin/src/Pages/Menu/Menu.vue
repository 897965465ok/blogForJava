<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect, provide, type Ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores/menu';
import { useRoute, useRouter } from 'vue-router';
import TableVue from './components/MenuTable.vue'
import CreateMenu from "@/Pages/Menu/components/CreateMenu.vue";
import ChangeMenuVue from "@/Pages/Menu/components/ChangeMenu.vue";
import { ElMessage } from 'element-plus';


const store = useStore()

const menuList = ref([])
const visible = ref(false)
const showChangeVue = ref(false)
const isDelete = ref(false)
const isLoading = ref(false)
const ChangeMenu = ref();
provide("visible", visible)
provide("showChangeVue", showChangeVue)

const { menuPages } = storeToRefs(store);


/**
 * 路由对象
 */
const route = useRoute();
/**
 * 路由实例
 */
const router = useRouter();

onBeforeMount(() => {
})
onMounted(() => {
})
watchEffect(() => {
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
async function changeMenu() {
  let [first] = toRefs(menuList.value)
  let firstNode = (first as any)

  let list = menuPages.value.result.list

  Object.keys(firstNode.value).forEach((key: any) => {
    ChangeMenu.value[key] = firstNode.value[key]
  })

  let fatherName = list.find((item: any) => item.menuId == firstNode.value.parentId)
  ChangeMenu.value["fatherName"] = fatherName.menuName

  showChangeVue.value = !showChangeVue.value
}

</script>
<template>
  <el-row class="button-wrapper">
    <el-button @click="visible = !visible">新增</el-button>
    <el-button :disabled="(menuList.length < 1)" @click="isDelete = !isDelete">删除</el-button>
    <el-button :disabled="(menuList.length != 1)" @click="changeMenu">修改</el-button>
  </el-row>

  <TableVue @check="checkButton"></TableVue>
  <CreateMenu :change="visible"></CreateMenu>
  <ChangeMenuVue ref="ChangeMenu"></ChangeMenuVue>
  <el-dialog v-model="isDelete" title="删除菜单" width="30%" align-center>
    <span>是否删除菜单？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" :loading="isLoading" @click="deleteSelectByMenuList">确定</el-button>
        <el-button @click="isDelete = false">取消</el-button>
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


