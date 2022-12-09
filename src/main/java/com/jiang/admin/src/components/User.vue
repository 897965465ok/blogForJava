<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, toRefs, watchEffect} from 'vue';
import {useStore} from '@/stores'
import {useRoute, useRouter} from 'vue-router';

/**
 * 仓库
 */
const store = useStore();
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
</script>

<template>
  <div></div>
</template>

<style lang='scss' scoped>
</style>