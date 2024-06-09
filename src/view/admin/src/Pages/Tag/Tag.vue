<script lang="ts" setup>
import {onMounted, ref,} from "vue";
import {tagStore} from "@/stores/tag";
import {useRoute, useRouter} from "vue-router";
import * as blogApi from "@/api/BlogApi";
import TableVue from "./TagTable.vue";
import {ElMessage} from "element-plus";
import {useAuth} from "@/utils/useAuth";

/**
 * 仓库
 */
const store = tagStore();
const openDeleteBox = ref(false);
const options = ref([]);
const tagName = ref();
const sign = ref<Number>(1);
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


onMounted(async () => {
  let {result}: any = await store.queryManyTag(1, 9999);
  options.value = result.list;
});

const ruleFormRef = ref();
const userList = ref([]);
const dialogTableVisible = ref(false);

function checkButton(selectorList: any) {
  userList.value = selectorList;
}

async function sureDeleteTagList() {
  let ids = userList.value.map((item: any) => item.id);
  let {code, message, result} = await blogApi.deleteTag(ids);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: `删除成功，删除${result}条记录`,
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "删除失败",
      type: "error",
    });
  }
  openDeleteBox.value = false;
}

// 创建标签函数
async function createTag() {
  let {code, message} = await blogApi.createTag(tagName);

  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: "创建标签成功",
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "创建标签失败",
      type: "error",
    });
  }

}


async function invoke() {
  if (sign.value == 1) {
    await createTag()
  } else if (sign.value == 2) {
    await changeTags()
  }
  dialogTableVisible.value = false;
}

// 修改选中的标签
async function changeTags() {
  // @ts-ignore
  let {code, message} = await blogApi.updateTags(userList.value[0].id, tagName.value);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: "修改标签成功",
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "修改标签失败",
      type: "error",
    });
  }
}


//   <el-button :plain="true" @click="open1">message</el-button>
//   <el-button :plain="true" @click="open2">success</el-button>
//   <el-button :plain="true" @click="open3">warning</el-button>
//   <el-button :plain="true" @click="open4">error</el-button>


function openBox(number: Number) {
  dialogTableVisible.value = true;
  sign.value = number;

}
</script>
<template>
  <div class="button-wrapper">

    <el-button v-if='useAuth("tags:plus")' @click="openBox(1)">新增</el-button>

    <el-button v-if='useAuth(  "tags:edit")' :disabled="userList.length != 1" @click="openBox(2)"
    >修改
    </el-button>

    <el-button v-if='useAuth("tags:delete")' :disabled="userList.length < 1" @click="openDeleteBox = true"
    >删除
    </el-button

    >
  </div>
  <TableVue @check="checkButton"></TableVue>

  <el-dialog v-model="dialogTableVisible" destroy-on-close title="添加标签" width="30%">

    <el-form>

      <el-form-item label="分类名称">
        <el-input v-model="tagName" placeholder="请输入分类名" clearable/>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="invoke">提交</el-button>
        <el-button @click="dialogTableVisible = false">取消</el-button>
      </el-form-item>

    </el-form>

  </el-dialog>

  <!-- 删除遮罩层 -->
  <el-dialog v-model="openDeleteBox" title="确认信息" width="30%" align-center>
    <span>是否删除这些标签?</span>
    <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="sureDeleteTagList"> 确定 </el-button>
          <el-button @click="openDeleteBox = false">取消</el-button>
        </span>
    </template>
  </el-dialog>

</template>

<style lang="scss" scoped>
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
  @apply justify-end;
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
