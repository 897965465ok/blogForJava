<script lang='ts' setup>
import { isRef, nextTick, onBeforeMount, onMounted, reactive, ref, watchEffect } from 'vue'
import type { Ref } from 'vue'
import { useStore } from '@/stores'
import { useRoute, useRouter } from 'vue-router';
import * as blogApi from "@/api/BlogApi";
const userList = ref();
const store = useStore();
const pageInfo = ref();
const userTableHeader = ref(null);
onBeforeMount(async () => {
  pageInfo.value = await store.queryManyUser(1, 7)
  console.log(pageInfo.value)
  //TODO 做这个
  userList.value = pageInfo.value.result
  // console.log(userList.value)
  userTableHeader.value = await blogApi.queryUserTableHeader()
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

function handleSelectionChange(selection: any) {
  eimits('check', selection)

}


async function jump(current: number) {
  pageInfo.value = await store.queryManyUser(current * 1, 7)
  userList.value = pageInfo.value.list;
}


</script>
<template>
  <div v-if="pageInfo">
    <el-table :data="userList" border @selection-change="handleSelectionChange">
      <el-table-column class="column" type="selection"></el-table-column>
      <el-table-column v-for="item, index in userTableHeader" :key="index" :label="item" :prop="item"
        :show-overflow-tooltip="true" align="center" fixed="right">

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
      <el-pagination v-if="pageInfo" @current-change="jump" :page-size="pageInfo.result.pageSize"
        :total="pageInfo.result.total" layout="total, prev, pager, next, jumper" />
    </div>
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