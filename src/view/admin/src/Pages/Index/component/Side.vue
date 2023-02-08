<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect } from 'vue';
import { storeToRefs } from 'pinia';
import { useStore } from '@/stores'
import { useRoute, useRouter } from 'vue-router';
/**
 * 仓库
 */
const store = useStore();

const { sideSwitch, treeMap } = storeToRefs(store)

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
  store.getRouter();
})
onMounted(() => {


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
  <el-menu router :collapse="sideSwitch" active-text-color="#ffd04b" background-color="#304156" default-active="1"
    text-color="#fff" @close="handleClose" @open="handleOpen">
    <el-sub-menu v-for="(item, index) in treeMap" :key="index" :index="index">
      <template #title>
        <el-icon>
          <component :is="item.icon"></component>
        </el-icon>
        <span>{{ item.menuName }}</span>
      </template>
      <el-menu-item :route="{ path: child.path }" v-for="(child, childIndex) in  item.children" :key="childIndex"
        :index="(`${index}+${childIndex}`)">
        <template #title>
          <el-icon>
            <component :is="child.icon"></component>
          </el-icon>
          <span>{{ child.menuName }}</span>
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