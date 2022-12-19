<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, toRefs, watchEffect} from 'vue';
import {storeToRefs} from 'pinia';
import {useStore} from '@/stores'
import {useRoute, useRouter} from 'vue-router';

/**
 * 仓库
 */
const store = useStore();

const {sideSwitch} = storeToRefs(useStore())

const menuList = reactive([
  {
    "id": 125,
    "authName": "用户管理",
    "path": "users",
    "children": [
      {
        "id": 110,
        "authName": "用户列表",
        "path": "users",
        "children": [],
        "order": null
      }
    ],
    "order": 1
  },
  {
    "id": 103,
    "authName": "权限管理",
    "path": "rights",
    "children": [
      {
        "id": 111,
        "authName": "角色列表",
        "path": "roles",
        "children": [],
        "order": null
      },
      {
        "id": 112,
        "authName": "权限列表",
        "path": "rights",
        "children": [],
        "order": null
      }
    ],
    "order": 2
  },
  {
    "id": 101,
    "authName": "商品管理",
    "path": "goods",
    "children": [
      {
        "id": 104,
        "authName": "商品列表",
        "path": "goods",
        "children": [],
        "order": 1
      },
      {
        "id": 115,
        "authName": "分类参数",
        "path": "params",
        "children": [],
        "order": 2
      },
      {
        "id": 121,
        "authName": "商品分类",
        "path": "categories",
        "children": [],
        "order": 3
      }
    ],
    "order": 3
  },
  {
    "id": 102,
    "authName": "订单管理",
    "path": "orders",
    "children": [
      {
        "id": 107,
        "authName": "订单列表",
        "path": "orders",
        "children": [],
        "order": null
      }
    ],
    "order": 4
  },
  {
    "id": 145,
    "authName": "数据统计",
    "path": "reports",
    "children": [
      {
        "id": 146,
        "authName": "数据报表",
        "path": "reports",
        "children": [],
        "order": null
      }
    ],
    "order": 5
  }
])


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
onMounted(() => {
  //console.log('3.-组件挂载到页面之后执行-------onMounted')
})
watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 
defineExpose({
  ...toRefs(data)
})


const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

</script>
<template>
  <el-menu :collapse="sideSwitch" active-text-color="#ffd04b" background-color="#304156" default-active="1"
           text-color="#fff" @close="handleClose" @open="handleOpen">
    <el-sub-menu v-for="(item, index) in menuList" :key="index" :index="index">
      <template #title>
        <el-icon>
          <Document/>
        </el-icon>
        <span>{{ item.authName }}</span>
      </template>
      <el-menu-item v-for="(child, childIndex) in  item.children" :key="childIndex" :index="(`${index}+${childIndex}`)">
        <template #title>
          <el-icon>
            <location/>
          </el-icon>
          <span>{{ child.authName }}</span>
        </template>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<style lang='scss' scoped>
.el-menu {
  border: 0;
}
</style>