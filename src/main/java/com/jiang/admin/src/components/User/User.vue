<script lang='ts' setup>
import { nextTick, onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores';
import { useRoute, useRouter } from 'vue-router';
import * as blogApi from "@/api/BlogApi";
import TableVue from './UserTable.vue'
import { ElMessage } from 'element-plus';

/**
 * 仓库
 */
const store = useStore();
const { treeMap } = storeToRefs(store);
const openDeleteBox = ref(false);
const isCreateUser = ref(true);
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
const ruleFormRef = ref();

watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 
defineExpose({
  ...toRefs(data)
})

const userList = ref([]);
const dialogTableVisible = ref(false)
function checkButton(selectorList: any) {
  userList.value = selectorList
}



async function sureDeleteUserList() {
  let ids = userList.value.map((item: any) => item.userId);
  let { code, message, result } = await blogApi.deleteManyUser(ids);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: `删除成功，删除${result}条记录`,
      type: 'success',
    })
  } else {
    ElMessage.success({
      message: '删除失败',
      type: 'error',
    })
  }
  openDeleteBox.value = false;
}

// 创建用户函数
async function createUser() {
  let { code, message } = await blogApi.createUser(form);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: '创建用户成功',
      type: 'success',
    })
  } else {
    ElMessage.success({
      message: '创建用户失败',
      type: 'error',
    })
  }
}


// 修改选中的用户
async function changeUser() {
  // Object.keys(form).forEach((item) => {
  //   form[item] = userList.value[0][item]
  // })
  let { code, message } = await blogApi.createUser(form);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: '修改用户成功',
      type: 'success',
    })
  } else {
    ElMessage.success({
      message: '修改用户失败',
      type: 'error',
    })
  }
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

const rules = reactive({
  nickName: [
    { required: true, message: "请输入用户昵称", trigger: "blur" },
  ],
  userName: [
    { required: true, message: "请输入账号", trigger: "blur" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    {
      pattern: /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
      message: "请正确输入邮箱",
    },
  ],
  phonenumber: [
    { required: true, message: "请输入手机号码", trigger: "blur" },
  ],
  sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
  status: [{ required: true, message: "请选择账号状态", trigger: "blur" }],
})



//   <el-button :plain="true" @click="open1">message</el-button>
//   <el-button :plain="true" @click="open2">success</el-button>
//   <el-button :plain="true" @click="open3">warning</el-button>
//   <el-button :plain="true" @click="open4">error</el-button>

// 创建或者修改用户
const onSubmit = () => {
  ruleFormRef.value.validate(async (valid: any) => {
    if (valid) {
      if (isCreateUser.value) {
        await createUser()
      } else {
        await changeUser()
      }
    } else {
      return false;
    }
  });
}

function openBox(number: Number) {
  dialogTableVisible.value = true
  isCreateUser.value = (number == 1) ? true : false;
}


</script>
<template>
  <div class="button-wrapper">
    <el-button @click="openBox(1)">新增</el-button>
    <el-button :disabled="(userList.length != 1)" @click="openBox(2)">修改</el-button>
    <el-button :disabled="(userList.length < 1)" @click="openDeleteBox = true">删除</el-button>
  
  </div>
  <TableVue @check="checkButton"></TableVue>
  <el-dialog v-model="dialogTableVisible" destroy-on-close title="添加用户">
    <el-form ref="ruleFormRef" :model="form" :rules="rules">
      <div class="flex flex-col">
        <div class=" flex  flex-row    justify-around  font-black">
          <div>
            <el-form-item prop="userName" label="用户账号">
              <el-input v-model="form.userName" />
            </el-form-item>
            <el-form-item prop="nickName" label=" 用户昵称">
              <el-input v-model="form.nickName" />
            </el-form-item>
            <el-form-item prop="password" label="用户密码">
              <el-input v-model="form.password" />
            </el-form-item>
            <el-form-item prop="phonenumber" label="手机号码">
              <el-input v-model="form.phonenumber" />
            </el-form-item>
          </div>
          <div>
            <el-form-item prop="email" label="用户邮箱">
              <el-input v-model="form.email" />
            </el-form-item>
            <el-form-item prop="sex" label="用户性别">
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
            <el-form-item prop="status" label="用户状态">
              <el-switch active-value="1" inactive-value="0" v-model="form.status" />
            </el-form-item>

          </div>
        </div>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="dialogTableVisible = false">取消</el-button>
        </el-form-item>
      </div>
    </el-form>

  </el-dialog>

  <!-- 删除遮罩层 -->
  <el-dialog v-model="openDeleteBox" title="确认信息" width="30%" align-center>
    <span>是否删除这些用户?</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="sureDeleteUserList">
          确定
        </el-button>
        <el-button @click="openDeleteBox = false">取消</el-button>
      
      </span>
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


