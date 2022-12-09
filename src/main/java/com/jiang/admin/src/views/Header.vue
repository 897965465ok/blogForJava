<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, toRefs, watchEffect} from 'vue';
import {mapActions} from 'pinia';
import {useStore} from '@/stores'
import {useRoute, useRouter} from 'vue-router';

/**
 * 仓库
 */
const store = useStore();

const {setSideSwitch} = mapActions(useStore, ['setSideSwitch'])

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
const user = reactive({})
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
  <header class="header">
    <div class="header-left">
      <div class="switch" @click="setSideSwitch">
        <el-icon>
          <Fold/>
        </el-icon>
      </div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for=" (item, index) in  route.matched" :key="index" :to="{ path: item.path }">{{
            item.name
          }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="header-right">
      <ScreenFullvue/>
      <Lanuages/>
      <Avatar/>
    </div>

  </header>
</template>

<style lang='scss' scoped>
.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  box-shadow: 5px 5px gray 5px;

  .header-left {
    display: flex;
    align-items: center;

    .switch {
      margin-right: 12px;
      cursor: pointer;
      width: 25px;
      height: 25px;
      font-size: 25px;
    }
  }

  .header-right {
    display: flex;
    align-items: center;
  }
}
</style>