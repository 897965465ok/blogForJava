<script lang='ts' setup>
import {onBeforeMount, onBeforeUnmount, onMounted, reactive, ref, watchEffect} from 'vue'
import {useStore} from '@/stores/article'
import {useRoute, useRouter} from 'vue-router';
import * as blogApi from '@/api/BlogApi'

const articleList = ref();
const store = useStore();
const {articles} = store;
const columns = ref();
const pageInfo = ref();


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

// 加载数据的函数
async function loadData() {
  pageInfo.value = await articles(1, 7)
  articleList.value = pageInfo.value.list;
  columns.value = await blogApi.queryArticleTableHeader()
}


onBeforeMount(async () => {
  await loadData()
})

// 监听文章刷新事件
onMounted(() => {
  window.addEventListener('article-refresh', loadData)
})

onBeforeUnmount(() => {
  window.removeEventListener('article-refresh', loadData)
})

watchEffect(() => {
})
// 使用toRefs解构
// let { } = { ...toRefs(data) } 

const eimits = defineEmits(['check'])

function handleSelectionChange(selection: any) {
  eimits('check', selection)

}

// 格式化日期
function formatDate(val: any): string {
  if (!val) return '-'
  const d = new Date(val)
  if (isNaN(d.getTime())) return val
  const pad = (n: number) => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}`
}

// 格式化单元格（自动识别日期字段和布尔/开关字段）
function formatCell(row: any, column: any): string {
  const val = row[column.property]
  const prop = column.property
  // 日期字段
  if (prop.endsWith('At') || prop.endsWith('time') || prop === 'createdAt' || prop === 'updatedAt' || prop === 'deletedAt') {
    return formatDate(val)
  }
  // 空值
  if (val === null || val === undefined) return '-'
  return String(val)
}


async function jump(current: number) {
  pageInfo.value = await articles(current * 1, 7)
  articleList.value = pageInfo.value.list;
}

</script>

<template>
  <el-table :data="articleList" border @selection-change="handleSelectionChange">
    <el-table-column class="column" type="selection"></el-table-column>
    <el-table-column v-for="col in columns" :key="col.prop" :label="col.label" :prop="col.prop"
                     :show-overflow-tooltip="true" align="center">

      <template v-slot:header="{ column }">
        <div class="column">
          {{ column.label }}
        </div>
      </template>
      <template v-slot:default="{ row, column }">
        <div class="column">
          {{ formatCell(row, column) }}
        </div>
      </template>

    </el-table-column>
  </el-table>

  <div class="demo-pagination-block">
    <el-pagination v-if="pageInfo" @current-change="jump" v-model:page-size="pageInfo.pageSize"
                   :total="Number(pageInfo.total)"
                   layout="total, prev, pager, next, jumper">
    </el-pagination>
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