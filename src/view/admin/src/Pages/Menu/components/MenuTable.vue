<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, ref, watchEffect, onBeforeUnmount} from 'vue'
import {useStore} from '@/stores/menu'
import {useRoute, useRouter} from 'vue-router';
import * as blogApi from '@/api/BlogApi';

const menuList = ref();
const store = useStore();
const MenuTableHeader = ref();

async function loadData() {
  menuList.value = await generateTree()
  MenuTableHeader.value = await blogApi.queryMenuTableHeader();
}

onBeforeMount(async () => {
  await loadData()
})

onMounted(() => {
  window.addEventListener('menu-refresh', loadData)
})

onBeforeUnmount(() => {
  window.removeEventListener('menu-refresh', loadData)
})


/**
 * 仓库
 */

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

onMounted(() => {
  //console.log('3.-组件挂载到页面之后执行-------onMounted')
})
watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 

const eimits = defineEmits(['check'])


const generate = (result: any): any => {
  if (Array.isArray(result)) {
    return result.map((item: any) => {
      return {
        ...item,
        children: generate(item.children),
      };
    });
  } else {
    return [];
  }
}

const generateTree = async () => {
  let {result} = await blogApi.getRouter();
  return generate(result);
}

function handleSelectionChange(selection: any) {
  eimits('check', selection)

}


</script>
<template>
  <div v-if="menuList">
    <el-table row-key="menuId" :data="menuList" border @selection-change="handleSelectionChange">
      <el-table-column class="column" type="selection"></el-table-column>
      <el-table-column prop="menuId" label="菜单编号"></el-table-column>
      <el-table-column prop="menuName" label="菜单名称"></el-table-column>
      <el-table-column prop="orderNum" label="显示顺序"></el-table-column>
      <el-table-column prop="perms" label="权限标识"></el-table-column>
      <el-table-column prop="status" label="菜单状态"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
    </el-table>
  </div>
</template>

<style lang="scss" scoped>
.el-table {
  min-width: 1500px;
}

.column {
  height: 25px;
  text-align: center;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  display: block;
}
</style>