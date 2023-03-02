<script lang='ts' setup>
import {
  onBeforeMount,
  onMounted,
  reactive,
  ref,
  toRaw,
  toRef,
  toRefs,
  unref,
  watch,
  watchEffect,
  type Ref,
  nextTick
} from 'vue';
import {storeToRefs} from 'pinia';
import {useStore} from '@/stores';
import {useRoute, useRouter} from 'vue-router';
import TableVue from './RoleTable.vue';
import * as BlogApi from '@/api/BlogApi';

/**
 * 仓库
 */

const store = useStore();
// const { treeMap } = storeToRefs(store);

const treeMap = ref([]);
const treeTwo = ref();
const props = {
  value: 'menuId', //   表头 id
  label: 'menuName', // 表头 内容
  children: 'children', // 表头 子节点
}


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
onMounted(async () => {
  treeMap.value = await store.getRouter()
})

// 使用toRefs解构
// let { } = { ...toRefs(data) }
const RoleList = ref([]);
const dialogTableVisible = ref(true)

function checkButton(selectorList: any) {
  RoleList.value = selectorList
}

function deleteSelectorRoleList() {
  // 删除选择中的文章

}

function changeRole() {
  // 修改选中的文章
  dialogTableVisible.value = true
  Object.keys(form).forEach((item) => {
    form[item] = RoleList.value[0][item]

  })

}


async function creatRole() {
  // 创建文章
  dialogTableVisible.value = true
  // let result  =   await createRole(form);
}

// default-expanded-keys 
// default-checked-keys

const form: any = reactive({
  createBy: "",
  de1Flag: "O",
  deptCheckStrictly: "1",
  menuCheckStrictly: "1",
  remark: "超级管理员",
  roleId: "",
  roleKey: "",
  roleName: "",
  roleSort: "400",
  status: "1",
  updateBy: "",
  updateTime: "",
})

const keys: any[] = [];
const strictly = ref(true)

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
  } else {
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

// 发送信息
const onSubmit = async () => {
  let roleAndResource = {
    role: form,
    resource: treeTwo.value.getCheckedNodes()
  }

  let result = await BlogApi.createRole(roleAndResource);

}


</script>
<template>
  <div class="button-wrapper">

    <el-button @click="creatRole">新增</el-button>
    <el-button :disabled="(RoleList.length < 1)" @click="deleteSelectorRoleList">删除</el-button>
    <el-button :disabled="(RoleList.length != 1)" @click="changeRole">修改</el-button>
  </div>
  <TableVue @check="checkButton"></TableVue>
  <el-dialog width="30vw" v-model="dialogTableVisible" destroy-on-close title="添加角色">
    <el-form :model="form">
      <div class="flex flex-col">
        <div class="flex flex-col font-black ">
          <!--          createBy: "",-->
          <!--          de1Flag: "O",-->
          <!--          deptCheckStrictly : "1",-->
          <!--          menuCheckStrictly: "1",-->
          <!--          remark: "超级管理员",-->
          <!--          roleId: "",-->
          <!--          roleKey : "",-->
          <!--          roleName:"",-->
          <!--          rolesort: "",-->
          <!--          status: "",-->
          <!--          updateBy: "",-->
          <!--          updateTime: "",-->
          <!--          -->
          <div>
            <el-form-item label="角色名称">
              <el-input v-model="form.roleName"/>
            </el-form-item>
            <el-form-item label=" 权限字符">
              <el-input v-model="form.roleKey"/>
            </el-form-item>
            <el-form-item label="角色顺序">
              <el-input-number v-model="form.roleSort" :min="1"/>
            </el-form-item>
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio label="1"> 正常</el-radio>
                <el-radio label="0"> 禁止</el-radio>
              </el-radio-group>
            </el-form-item>
          </div>
          <div>

            <el-form-item label="菜单权限">
              <el-checkbox label="展开/折叠" @change="allOpen"/>
              <el-checkbox label="全选/全不选" @change="allSelector"/>
              <el-checkbox label="父子联动" @change="linkage"/>
            </el-form-item>

            <el-tree-v2 v-if="treeMap"
                        ref="treeTwo"
                        :data="treeMap"
                        :props="props"
                        show-checkbox :height="100"
                        :check-strictly="strictly">
            </el-tree-v2>
          </div>
        </div>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
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

.Role-title {
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


