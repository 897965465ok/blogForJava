<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, ref, toRaw} from 'vue';
import {storeToRefs} from 'pinia';
import {useStore} from '@/stores/article';
import {useRoute, useRouter} from 'vue-router';
import TableVue from './component/ArticleTable.vue';
import {tagStore as useTagStore} from "@/stores/tag";
import * as BlogApi from "@/api/BlogApi"
import {ElMessage} from "element-plus";
import type {UploadUserFile} from "element-plus/lib/components";


/**
 * 仓库
 */
const store = useStore();
const {treeMap} = storeToRefs(store);
const tagStore = useTagStore();
const {queryManyTag} = tagStore
const treeTwo = ref();
const ArticleList = ref([]);
const dialogTableVisible = ref(false)
const select = ref<Array<string>>([]);
const sign = ref<Number>(0);
const tag = ref();
const sideArticle = ref();
const hot = ref();

const fileRaw = ref();
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

//console.log('2.组件挂载页面之前执行----onBeforeMount')
onBeforeMount(async () => {


})
onMounted(async () => {
  let {result}: any = await queryManyTag(1, 7);
  select.value.push(...result);

})

// 使用toRefs解构
// let { } = { ...toRefs(data) }


function openBox(number: Number) {

  dialogTableVisible.value = true;
  sign.value = number;

}

const openDeleteBox = ref<boolean>(false)


function checkButton(selectorList: any) {
  ArticleList.value = selectorList
}


async function deleteSelectorArticleList() {
  let id = ArticleList.value.map((item: any) => item.id)
  // 删除选择中的文章
  let {code, message} = await BlogApi.deleteArticle(id)

  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: `删除成功`,
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "删除失败",
      type: "error",
    });
  }


  openDeleteBox.value = !openDeleteBox.value

}

function changeArticle() {
  // 修改选中的文章
  dialogTableVisible.value = true

}


async function creatArticle() {
  // 创建文章
  dialogTableVisible.value = true
  const form: FormData = new FormData();
  form.append("tag", tag.value);
  form.append("sideArticle", sideArticle.value);
  form.append("hot", hot.value);
  form.append("file", fileRaw.value);
  let {code,message}: any = await BlogApi.createArticle(form);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: "创建文章成功",
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "创建文章失败",
      type: "error",
    });
  }
  dialogTableVisible.value = false;
}

// default-expanded-keys
// default-checked-keys


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
  let RawArr: any = toRaw(reactive(treeMap.value))
  GeneratorKeys(RawArr)
  if (isCheck) {
    keys.forEach(id => {
      let node = treeTwo.value.getNode(id)
      treeTwo.value.expandNode(node)
    })
  } else {
    keys.forEach(id => {
      let node = treeTwo.value.getNode(id)
      treeTwo.value.collapseNode(node)
    })
  }

}
// 全选/反选
const allSelector = (isCheck: boolean) => {
  let RawArr: any = toRaw(reactive(treeMap.value))
  GeneratorKeys(RawArr)
  if (isCheck) {
    keys.forEach(id => {
      treeTwo.value.setChecked(id, true)
    })
  } else {
    keys.forEach(id => {
      treeTwo.value.setChecked(id, false)
    })
  }

}
// 父子联动
const linkage = (isCheck: boolean) => {

  strictly.value = !strictly.value

}

const changeFile = (file: UploadUserFile) => {
  fileRaw.value = file.raw
}

const FileRemove = () => {

  fileRaw.value = null;
}

async function invoke() {
  switch (sign.value) {
    case 1: {
      await creatArticle();
      break
    }
    case 2 : {
      await changeArticle();
      break
    }
  }

}


const props = {
  value: 'menuId', //   表头 id
  label: 'menuName', // 表头 内容
  children: 'children', // 表头 子节点
}


</script>
<template>
  <el-row>
    <el-button @click="openBox(1)">新增</el-button>
    <el-button :disabled="(ArticleList.length != 1)" @click="openBox(3)">修改</el-button>
    <el-button :disabled="(ArticleList.length < 1)" @click="openDeleteBox = !openDeleteBox">删除</el-button>
  </el-row>
  <TableVue @check="checkButton"></TableVue>
  <el-dialog v-model="dialogTableVisible" destroy-on-close title="文章上传" width="35%">
    <template #default>
      <el-form :model="form">
        <div class="form-header">
          <el-row class="header-upload">
            <el-upload
                @change="changeFile"
                @remove="FileRemove"
                ref="uploadRef"
                :limit="1"
                style="width: 100%;"
                drag
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                multiple
                :auto-upload="false"
            >
              <el-icon class="el-icon--upload">
                <upload-filled/>
              </el-icon>
              <div class="el-upload__text">
                <em>拖入文件 点击上传</em>
              </div>
            </el-upload>

          </el-row>
        </div>

        <el-row class="form-body">
          <el-form-item label="热门">
            <el-switch v-model="hot"/>
          </el-form-item>
          <el-form-item label="侧边栏文章">
            <el-switch v-model="sideArticle"/>
          </el-form-item>
          <el-form-item label="分类">
            <el-select
                v-model="tag"
                placeholder="ES6"
                style="width: 70%"
            >
              <el-option
                  v-for="(item,index) in select"
                  :key="item.id"
                  :label="item.articleTag"
                  :value="item.articleTag"
              />
            </el-select>
          </el-form-item>
        </el-row>

        <el-row class="form-footer">
          <el-form-item>
            <el-button :disabled=" fileRaw == null ? true:false" type="primary" @click="invoke">确定</el-button>
            <el-button @click="dialogTableVisible = !dialogTableVisible">取消</el-button>
          </el-form-item>
        </el-row>

      </el-form>
    </template>


  </el-dialog>
  <!-- 删除遮罩层 -->
  <el-dialog v-model="openDeleteBox" title="确认信息" width="30%" align-center>
    <span>是否删除这些标签?</span>
    <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="deleteSelectorArticleList"> 确定 </el-button>
          <el-button @click="openDeleteBox = false">取消</el-button>
        </span>
    </template>
  </el-dialog>


</template>


<style lang='scss' scoped>
.form-header {
  .header-upload {
    .el-upload-list {
      margin: 0;
    }
  }

}

:deep(.el-upload-list) {
  margin: 0;
}

.form-body {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}
</style>