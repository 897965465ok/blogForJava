<script lang="ts" setup xmlns="">
import {
  isReactive,
  isRef,
  onBeforeMount,
  onMounted,
  reactive,
  toRaw,
  toRef,
  toRefs,
  watchEffect,
  ref,
  inject,
  nextTick,
  type Ref,
} from "vue";
import { useStore } from "@/stores/menu";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import * as BlogApi from "@/api/BlogApi";
import { ElMessage } from "element-plus";

import * as ElementPlusIconsVue from '@element-plus/icons-vue'



const visible: any = inject("visible");
const store = useStore();
const route = useRoute();
const router = useRouter();
const data = ref();
const isLoading = ref(false);

const user = reactive({
  avatar: "jerry",
});
const ruleFormRef = ref();
const popoverRef = ref()

const onClickOutside = () => {
  popoverRef.value.hidden();
}
onBeforeMount(() => {
});
onMounted(async () => {
  data.value = await store.generateTree();
});
watchEffect(() => {
});
// 使用toRefs解构
// let { } = { ...toRefs(data) }
defineExpose({
  ...toRefs(user),
});

function selectIcon($event: any) {

  if ($event.target.nodeName == "BUTTON") {

    form.icon = $event.target.getAttribute("icanName")

  }
}
const SelectType = {
  M: {
    status: [{ required: true, message: "选择状态", trigger: "blur" }],
    orderNum: [{ required: true, message: "请输入菜单顺序", trigger: "blur" }],
    menuName: [{ required: true, message: "请输入菜单名", trigger: "blur" }],
  },
  C: {
    query: [{ required: true, message: "请输入组件名字", trigger: "blur" }],
    component: [{ required: true, message: "请输入组件名字", trigger: "blur" }],
    icon: [{ required: true, message: "选择图标", trigger: "blur" }],
    perms: [{ required: true, message: "权限字符", trigger: "blur" }],
    status: [{ required: true, message: "选择状态", trigger: "blur" }],
    visible: [{ required: true, message: "是否显示", trigger: "blur" }],
    menuType: [
      { required: true, message: "菜单类型", trigger: "blur" },

      {
        pattern: /[CMF]/g,
        message: "请选择菜单",
      },
    ],
    isCache: [{ required: true, message: "是否缓存", trigger: "blur" }],
    isFrame: [{ required: true, message: "是否外链", trigger: "blur" }],
    path: [{ required: true, message: "请输入路由路径", trigger: "blur" }],
    orderNum: [{ required: true, message: "请输入菜单顺序", trigger: "blur" }],

    parentId: [{ required: true, message: "选择上级菜单", trigger: "blur" }],

    menuName: [{ required: true, message: "请输入菜单名", trigger: "blur" }],
  },
  F: {
    status: [{ required: true, message: "选择状态", trigger: "blur" }],
    orderNum: [{ required: true, message: "请输入菜单顺序", trigger: "blur" }],
    menuName: [{ required: true, message: "请输入菜单名", trigger: "blur" }],
    perms: [{ required: true, message: "权限字符", trigger: "blur" }],
  },
};

// 校验类型
const rules = ref<any>(SelectType["M"]);

const form = reactive<Menu>({
  menuId: "",
  menuName: "",
  parentId: "0",
  orderNum: "1",
  path: "",
  component: "",
  query: "",
  isFrame: "0",
  isCache: "0",
  menuType: "M",
  visible: "1",
  status: "1",
  perms: "",
  icon: "Search",
  createBy: "",
  createTime: "",
  updateBy: "",
  updateTime: "",
  remark: "",
  fatherName: "",
});

// 选择节点
function nodeClick(node: any) {
  if (form.menuType != "M") {
    form.parentId = node.value;
  } else {
    form.parentId = "0";
  }
}

// 切换标签时操作
function changeTable(paneName: string) {
  form.menuType = paneName;
  switch (paneName) {
    case "M": {
      form.parentId = "0";
      console.log(rules);
      rules.value = SelectType[paneName];
      break;
    }
    case "C": {
      rules.value = SelectType[paneName];
      break;
    }
    case "F": {
      rules.value = SelectType[paneName];
      break;
    }
    default: {
      return;
    }
  }
}

// 校验和创建菜单
function submit() {
  ruleFormRef.value.validate(async (valid: any) => {
    if (valid) {
      isLoading.value = true
      let { code, message } = await BlogApi.createMenu(form);
      if (code == 200 && message == "SUCCESS") {
        ElMessage.success({
          message: '添加成功',
          type: 'success',
        })
        visible.value = !visible.value
      } else {
        ElMessage.error({
          message: '添加失败',
          type: 'error',
        })
      }
      isLoading.value = false
    } else {
      ElMessage.warning({
        message: '请填写所有内容',
        type: 'error',
      })
      return false;
    }
  });

}
</script>
<template>
  <el-dialog v-model="visible" title="添加菜单" width="40%">
    <el-row>
      <el-form class="grid flex-col w-full" :model="form" ref="ruleFormRef" :rules="rules">
        <el-tabs v-model="form.menuType" @tab-change="changeTable">
          <!-- 上级菜单 -->
          <el-row v-if="form.menuType != 'M'">
            <el-form-item label="上级菜单" prop="parentId">
              <el-tree-select  placeholder="选择上级菜单" @node-click="nodeClick" :data="data" v-model="form.fatherName" :check-strictly="true"
                :render-after-expand="false" :accordion="true" :check-on-click-node="false" />
            </el-form-item>
          </el-row>

          <!-- 创建目录UI -->
          <el-tab-pane label="目录" name="M">

            <el-row class="grid gap-x-10  ">
              <el-form-item label="菜单名称" prop="menuName">
                <el-input v-model="form.menuName" />
              </el-form-item>
            </el-row>

            <el-row class="grid gap-x-10">
              <el-form-item label="菜单顺序" prop="orderNum">
                <el-input-number v-model.number="form.orderNum" :min="1" />
              </el-form-item>
              <el-form-item label="菜单状态" prop="status">
                <el-radio-group v-model="form.status">
                  <el-radio label="1">正常</el-radio>
                  <el-radio label="0">停用</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-row>

            <el-row @click="selectIcon">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="form.icon" placeholder="查找图标" :prefix-icon="form.icon">
                </el-input>
                <el-scrollbar>
                  <el-button :icanName="item" v-for="item in Object.keys(ElementPlusIconsVue)" :key="item" :icon="item" />
                </el-scrollbar>
              </el-form-item>
            </el-row>

          </el-tab-pane>

          <!-- 创建按钮UI -->
          <el-tab-pane label="按钮" name="F">
            <el-row class="grid gap-x-10">
              <el-form-item label="菜单名称" prop="menuName">
                <el-input v-model="form.menuName" />
              </el-form-item>
              <el-form-item label="菜单顺序" prop="orderNum">
                <el-input-number v-model.number="form.orderNum" :min="1" />
              </el-form-item>
            </el-row>
            <el-row>
            <el-form-item label="权限字符" prop="perms">
              <el-input v-model="form.perms" />
            </el-form-item>
          </el-row>
            <el-row @click="selectIcon">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="form.icon" placeholder="查找图标" :prefix-icon="form.icon">
                </el-input>
                <el-scrollbar>
                  <el-button :icanName="item" v-for="item in Object.keys(ElementPlusIconsVue)" :key="item" :icon="item" />
                </el-scrollbar>
              </el-form-item>
            </el-row>
          </el-tab-pane>

          <!-- 创建菜单UI -->
          <el-tab-pane label="菜单" name="C">
            <el-row class="grid gap-x-10">
              <!-- <el-form-item label="菜单图标">
                                                                                          <el-input v-model="form.icon" />
                                                                                        </el-form-item> -->
              <el-form-item label="菜单名称" prop="menuName">
                <el-input v-model="form.menuName" />
              </el-form-item>

              <el-form-item label="路由地址" prop="path">
                <el-input v-model="form.path" />
              </el-form-item>
            </el-row>
            <el-row class="grid gap-x-10">
              <el-form-item label="路由组件" prop="component">
                <el-input v-model="form.component" />
              </el-form-item>
              <el-form-item label="权限字符" prop="path">
                <el-input v-model="form.perms" />
              </el-form-item>
            </el-row>
            <el-row class="grid gap-x-10">
              <el-form-item label="菜单状态" prop="status">
                <el-radio-group v-model="form.status">
                  <el-radio label="1">正常</el-radio>
                  <el-radio label="0">停用</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="菜单顺序" prop="orderNum">
                <el-input-number v-model.number="form.orderNum" :min="1" />
              </el-form-item>
            </el-row>
            <el-row class="grid gap-x-10">
              <el-form-item label="路由参数">
                <el-input v-model="form.query" />
              </el-form-item>
              <el-form-item label="是否缓存" prop="isCache">
                <el-radio-group v-model="form.isCache">
                  <el-radio label="1">是</el-radio>
                  <el-radio label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-row>

            <el-row class="grid gap-x-10">
              <el-form-item label="显示状态" prop="visible">
                <el-radio-group v-model="form.visible">
                  <el-radio label="1">显示</el-radio>
                  <el-radio label="0">隐藏</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-row>
            <el-row @click="selectIcon">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="form.icon" placeholder="查找图标" :prefix-icon="form.icon">
                </el-input>
                <el-scrollbar>
                  <el-button :icanName="item" v-for="item in Object.keys(ElementPlusIconsVue)" :key="item" :icon="item" />
                </el-scrollbar>
              </el-form-item>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-row>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="submit" :loading="isLoading">确定</el-button>
        <el-button @click="visible = !visible">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style lang="scss" scoped>
:deep(.el-tabs__nav-wrap::after) {
  background: none;
}

:deep(.el-scrollbar) {

  @apply h-96 relative top-2 left-0 bottom-0;
}

:deep(.el-scrollbar .el-button) {
  @apply w-12 h-10 mx-1 my-1;

  .el-icon,
  svg {
    width: 100%;
    height: 100%;
    pointer-events: none;
  }
}
</style>
