<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRaw, toRef, toRefs, unref, watch, watchEffect, type Ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores/article';
import { useRoute, useRouter } from 'vue-router';
import TableVue from './component/ArticleTable.vue';

/**
 * 仓库
 */
const store = useStore();
const { treeMap } = storeToRefs(store);

const treeTwo = ref();


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
onMounted(() => {

  // console.log(treeTwo)

})

// 使用toRefs解构
// let { } = { ...toRefs(data) }



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

// default-expanded-keys
// default-checked-keys
const form: any = reactive({
  "userName": "",
  "nickName": "",
  "email": "",
  "phonenumber": "",
  "sex": "0",
  "password": 1,
  "status": "0",
  "roles": [],
})

const keys: any[] = [];

const strictly = ref(false)
function GeneratorKeys(treeData: Array<Object>) {
  treeData.forEach((item: any) => {
    keys.push(item.menuId)
    if (Array.isArray(item.children)) {
      GeneratorKeys(item.children)
    }
  })

}

//点击展开
const allOpen = (isCheck: boolean) => {
  let RawArr = toRaw(reactive(treeMap.value))
  GeneratorKeys(RawArr)
  if (isCheck) {
    keys.forEach(id => {
      let node = treeTwo.value.getNode(id)
      treeTwo.value.expandNode(node)
    })
  }else{
    keys.forEach(id => {
      let node = treeTwo.value.getNode(id)
      treeTwo.value.collapseNode(node)
    })
  }

}
// 全选/反选
const allSelector = (isCheck: boolean) => {
  let RawArr = toRaw(reactive(treeMap.value))
  GeneratorKeys(RawArr)
  if (isCheck) {
    keys.forEach(id => {
      treeTwo.value.setChecked(id,true)
    })
  }else{
    keys.forEach(id => {
      treeTwo.value.setChecked(id,false)
    })
  }

}
// 父子联动
const linkage = (isCheck: boolean) => {

  strictly.value = !strictly.value

}




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
  <div>
    <el-button @click="dialogTableVisible = !dialogTableVisible">新增</el-button>
    <el-button :disabled="(ArticleList.length < 1)" @click="deleteSelectorArticleList">删除</el-button>
    <el-button :disabled="(ArticleList.length != 1)" @click="changeArticle">修改</el-button>
  </div>
  
  <TableVue @check="checkButton"></TableVue>

  <el-dialog v-model="dialogTableVisible" destroy-on-close title="修改文章">
    <template #default>
      <el-form :model="form" label-width="100px">
        <div class="form-header">
          <div class="header-upload">
            <el-upload action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" drag multiple>
              <el-icon>
                <upload-filled/>
              </el-icon>
              <div class="el-upload__text">
                <em>图片拖放</em><br>
                <em>点击上传</em>
              </div>
            </el-upload>
          </div>
          <div class="header-textarea">
            <el-input v-model="form.paragraph" resize="none" type="textarea"/>
          </div>
        </div>

        <div class="form-body">
          <div class="form-switch">
            <el-form-item label="热门">
              <el-switch v-model="form.hot"/>
            </el-form-item>
            <el-form-item label="侧边栏文章">
              <el-switch v-model="form.sideArticle"/>
            </el-form-item>
            <el-form-item label="文章名">
              <el-input v-model="form.name"/>
            </el-form-item>
            <el-form-item label="分类命">
              <el-input v-model="form.tag"/>
            </el-form-item>
            <el-form-item label="原始路径">
              <el-input v-model="form.articlePath"/>
            </el-form-item>
          </div>
        </div>

        <!-- <el-form-item label="文章内容">
          content:""
        </el-form-item> -->

        <el-form-item>
          <el-button type="primary" @submit="onSubmit">修改</el-button>
          <el-button @click="dialogTableVisible = !dialogTableVisible">取消</el-button>
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
// .form-switch {}
</style>