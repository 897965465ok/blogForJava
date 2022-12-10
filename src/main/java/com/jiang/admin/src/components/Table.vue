<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, ref, toRefs, watchEffect} from 'vue'
import {useStore} from '@/stores'
import {useRoute, useRouter} from 'vue-router';
import {ElTable, type TableColumnCtx} from 'element-plus'

const articleList = ref();
const store = useStore();
const {Articles} = store;
let columns = ref();
onBeforeMount(async () => {
  let pageInfo = await Articles(1, 7)
  articleList.value = pageInfo.result.list
  columns.value = Object.keys(articleList.value[0]).map((item: string) => item);
})

function handleSelectionChange() {

}

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
  <el-table :data="articleList" border @selection-change="handleSelectionChange">
    <el-table-column v-for="item in columns" :key="item.id" :label="item" :prop="item" :show-overflow-tooltip="true"
                     align="center" fixed="right">
      <template v-slot:header="{ column, $index }">
        <div class="column">
          {{ column.property }}
        </div>
      </template>
      <template v-slot:default="{ row, column }">
        <div class="column">
          {{ row[column.property] }}
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<style lang="scss" scoped>
.column {
  height: 25px;
  text-align: center;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  display: block;
}
</style>