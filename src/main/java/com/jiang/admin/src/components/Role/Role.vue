<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores';
import { useRoute, useRouter } from 'vue-router';
import { deleteArticle } from "@/api/BlogApi";
import TableVue from './RoleTable.vue';
import { createArticle } from '@/api/BlogApi';
import type { Tree } from "element-plus/es/components/tree-v2/src/types";



/**
 * 仓库
 */
const store = useStore();
const { treeMap } = storeToRefs(store);

const treeTwo = ref(null);


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

onBeforeMount(() => {
  //console.log('2.组件挂载页面之前执行----onBeforeMount')
})
onMounted( () => {


 })
watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 


const ArticleList = ref([]);
const dialogTableVisible = ref(true)



function checkButton(selectorList: any) {
  ArticleList.value = selectorList
}



function deleteSelectorArticleList() {
  // 删除选择中的文章

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

// default-expanded-keys 
// default-checked-keys
const form = reactive({
  "userName": "",
  "nickName": "",
  "email": "",
  "phonenumber": "",
  "sex": "0",
  "password": 1,
  "status": "0",
  "roles": [],
  "strictly": false
})

const onSubmit = () => {
  console.log(form)
}



const props = {
  value: 'menuId', //   表头 id
  label: 'menuName', // 表头 内容
  children: 'children', // 表头 子节点
}


</script>
<template>
  <div class="button-wrapper">
    <el-button @click="creatArticle">新增</el-button>
    <el-button :disabled="(ArticleList.length < 1)" @click="deleteSelectorArticleList">删除</el-button>
    <el-button :disabled="(ArticleList.length != 1)" @click="changeArticle">修改</el-button>
  </div>
  <TableVue @check="checkButton"></TableVue>
  <el-dialog width="30vw" v-model="dialogTableVisible" destroy-on-close title="添加角色">
    <el-form :model="form">
      <div class="flex flex-col">
        <div class="flex flex-col font-black ">
          <div>
            <el-form-item label="角色名称">
              <el-input v-model="form.userName" />
            </el-form-item>
            <el-form-item label=" 权限字符">
              <el-input v-model="form.nickName" />
            </el-form-item>
            <el-form-item label="角色顺序">
              <el-input-number v-model="form.password" :min="1" />
            </el-form-item>
            <el-form-item label="状态">
              <el-radio-group v-model="form.resource">
                <el-radio label="正常" />
                <el-radio label="停用" />
              </el-radio-group>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="菜单权限">
              <el-checkbox-group v-model="form.strictly">
                <el-checkbox label="展开/折叠" name="type" />
                <el-checkbox label="全选/全不选" name="type" />
                <el-checkbox label="父子联动" name="type" />
              </el-checkbox-group>
            </el-form-item>
            <el-tree-v2 
            ref="treeTwo"
            :data="treeMap" 
            :props="props" 
            show-checkbox 
            :height="100"
            :check-strictly="form.strictly"
            >
            </el-tree-v2>
          </div>
        </div>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">修改</el-button>
          <el-button @click="dialogTableVisible = false">取消</el-button>
        </el-form-item>
      </div>
    </el-form>
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

:deep(.el-form-item__content) {
  @apply justify-end
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


