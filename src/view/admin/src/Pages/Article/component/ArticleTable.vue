<script lang='ts' setup>
import { onBeforeMount, onMounted, reactive, ref, watchEffect } from 'vue'
import { useStore } from '@/stores/article'
import { useRoute, useRouter } from 'vue-router';
import * as blogApi from '@/api/BlogApi'
const articleList = ref();
const store = useStore();
const { articles } = store;
const columns = ref();
const pageInfo = ref();

onBeforeMount(async () => {
  pageInfo.value = await articles(1, 7)
  articleList.value = pageInfo.value.list;
  columns.value = await blogApi.queryArticleTableHeader();
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

const eimits = defineEmits(['check'])

function handleSelectionChange(selection: any) {
  eimits('check', selection)

}


async function jump(current: number) {
  pageInfo.value = await articles(current * 1, 7)
  articleList.value = pageInfo.value.list;
}

</script>

<template>
  <el-table :data="articleList" border @selection-change="handleSelectionChange">
    <el-table-column class="column" type="selection"></el-table-column>
    <el-table-column
      v-for=" ( item , index ) in columns" :key="index" :label="item" :prop="item" :show-overflow-tooltip="true"
      align="center" fixed="right">

      <template v-slot:header="{ column, $index }">
        <div class="column">
          {{ column.property }}
        </div>
      </template>
      <template v-slot:default="{ row, column }">
        <div class="column">
         
         {{ row[column.rawColumnKey] }}
       
       </div>
      </template>

    </el-table-column>
  </el-table>
  <div class="demo-pagination-block">
    <el-pagination v-if="pageInfo" @current-change="jump" v-model:page-size="pageInfo.pageSize" :total="pageInfo.total"
      layout="total, prev, pager, next, jumper" />
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