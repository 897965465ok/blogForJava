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
const user = reactive({
  avatar: '../assets/jerry.png'

})
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
  ...toRefs(user)
})
</script>

<template>
  <div class="header-avatar">
    <el-dropdown>
      <el-avatar :src="user.avatar" fit="fill" shape="square"/>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item> 退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<style lang='scss' scoped>
.header-avatar {
  margin: 0 16px;
}
</style>