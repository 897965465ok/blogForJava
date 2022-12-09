<template>
  <el-button @click="resetDateFilter">reset date filter</el-button>
  <el-button @click="clearFilter">reset all filters</el-button>
  <el-table ref="tableRef" :data="tableData" row-key="date" style="width: 100%">
    <el-table-column :filter-method="filterHandler" :filters="[
      { text: '2016-05-01', value: '2016-05-01' },
      { text: '2016-05-02', value: '2016-05-02' },
      { text: '2016-05-03', value: '2016-05-03' },
      { text: '2016-05-04', value: '2016-05-04' },
    ]" column-key="date" label="Date" prop="date" sortable width="180"/>
    <el-table-column label="Name" prop="name" width="180"/>
    <el-table-column :formatter="formatter" label="Address" prop="address"/>

    <el-table-column :filter-method="filterTag" :filters="[
      { text: 'Home', value: 'Home' },
      { text: 'Office', value: 'Office' },
    ]" filter-placement="bottom-end" label="Tag" prop="tag" width="100">
      <template #default="scope">
        <el-tag :type="scope.row.tag === 'Home' ? '' : 'success'" disable-transitions>{{ scope.row.tag }}</el-tag>
      </template>
    </el-table-column>
  </el-table>

  <div class="demo-pagination-block">
    <div>
      {{ $t("menus.users") }}
    </div>
    <el-pagination
        v-model:current-page="currentPage4"
        v-model:page-size="pageSize4"
        :background="background"
        :disabled="disabled"
        :page-sizes="[100, 200, 300, 400]"
        :small="small"
        :total="400"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import {ElTable, type TableColumnCtx} from 'element-plus'

interface User {
  date: string
  name: string
  address: string
  tag: string
}

const tableRef = ref<InstanceType<typeof ElTable>>()

const resetDateFilter = () => {
  tableRef.value!.clearFilter(['date'])
}
// TODO: improvement typing when refactor table
const clearFilter = () => {
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-expect-error
  tableRef.value!.clearFilter()
}
const formatter = (row: User, column: TableColumnCtx<User>) => {
  return row.address
}
const filterTag = (value: string, row: User) => {
  return row.tag === value
}
const filterHandler = (
    value: string,
    row: User,
    column: TableColumnCtx<User>
) => {
  const property = column['property']
  return row[property] === value
}

const tableData: User[] = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
    tag: 'Home',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
    tag: 'Office',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
    tag: 'Home',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
    tag: 'Office',
  },
]
const currentPage1 = ref(5)
const currentPage2 = ref(5)
const currentPage3 = ref(5)
const currentPage4 = ref(4)
const pageSize2 = ref(100)
const pageSize3 = ref(100)
const pageSize4 = ref(100)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
}
</script>
