<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores';
import { useRoute, useRouter } from 'vue-router';
import { deleteArticle } from "@/api/BlogApi";
import TableVue from './UserTable.vue'
import { createArticle } from '@/api/BlogApi';

/**
 * 仓库
 */
const store = useStore();
const { treeMap } = storeToRefs(store);
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
onMounted(async () => { })
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


const form = reactive({
  "userName": "",
  "nickName": "",
  "email": "",
  "phonenumber": "",
  "sex": "0",
  "password": "",
  "status": "0",
  "roles": []
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
  <el-dialog v-model="dialogTableVisible" destroy-on-close title="添加用户">
    <el-form :model="form">
      <div class="flex flex-col">
        <div class=" flex  flex-row    justify-around  font-black">
          <div>
            <el-form-item label="用户账号">
              <el-input v-model="form.userName" />
            </el-form-item>
            <el-form-item label=" 用户昵称">
              <el-input v-model="form.nickName" />
            </el-form-item>
            <el-form-item label="用户密码">
              <el-input v-model="form.password" />
            </el-form-item>
            <el-form-item label="手机号码">
              <el-input v-model="form.phonenumber" />
            </el-form-item>
          </div>
          <div>
            <el-form-item label="用户邮箱">
              <el-input v-model="form.email" />
            </el-form-item>
            <el-form-item label="用户性别">

              <el-select v-model="form.sex" placeholder="选择性别">
                <el-option label="女孩子" value="0" />
                <el-option label="男孩子" value="1" />
              </el-select>

            </el-form-item>
            <el-form-item label="用户角色">
              <el-select multiple v-model="form.roles" placeholder="用户角色">
                <el-option label="CEO" value="董事长" />
              </el-select>
            </el-form-item>
            <el-form-item label="用户状态">
              <el-switch v-model="form.status" />
            </el-form-item>
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


