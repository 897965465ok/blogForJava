<script lang='ts' setup>
import {isReactive, isRef, onBeforeMount, onMounted, reactive, toRaw, toRef, toRefs, watchEffect, ref} from 'vue';
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
  avatar: "jerry"
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


const props = defineProps({
  change: Boolean
})

const cancel = ref(true);

const form = reactive({

  show: "dir"

})

const value = ref()

const data = [
  {
    value: '1',
    label: 'Level one 1',
    children: [
      {
        value: '1-1',
        label: 'Level two 1-1',
        children: [
          {
            value: '1-1-1',
            label: 'Level three 1-1-1',
          },
        ],
      },
    ],
  },

]
</script>

<template>
  <el-dialog
      v-model="props.change"
      title="添加菜单"
      width="40%"
  >
    <el-row>
      <el-form class=" grid flex-col w-full ">
        <el-row>
          <el-form-item label="上级菜单">
            <el-tree-select
                v-model="value"
                :data="data"
                check-strictly
                :render-after-expand="false"
                show-checkbox
                check-on-click-node
            />
          </el-form-item>
        </el-row>
        <el-tabs v-model="form.show"  @tab-click="">
          <el-tab-pane label="目录" name="dir">
            <el-row class="grid gap-x-10">
              <el-form-item label="菜单图标">
                <el-input v-model="form.icon"/>
              </el-form-item>
              <el-form-item label="菜单名称">
                <el-input v-model="form.name"/>
              </el-form-item>
            </el-row>

            <el-row class="grid gap-x-10 ">
              <el-form-item label="是否外链">
                <el-radio-group v-model="form.foreign">
                  <el-radio label="是"/>
                  <el-radio label="否"/>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="路由地址">
                <el-input v-model="form.path"/>
              </el-form-item>
            </el-row>
            <el-row class="grid gap-x-10 ">
              <el-form-item label="菜单状态">
                <el-radio-group v-model="form.state">
                  <el-radio label="正常"/>
                  <el-radio label="停用"/>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="菜单顺序">
                <el-input-number v-model="num" :min="1" :max="10" @change="handleChange"/>
              </el-form-item>
            </el-row>

          </el-tab-pane>
          <el-tab-pane label="菜单" name="menu">
            <el-row class="grid gap-x-10">
              <el-form-item label="菜单图标">
                <el-input v-model="form.icon"/>
              </el-form-item>
              <el-form-item label="菜单名称">
                <el-input v-model="form.name"/>
              </el-form-item>
            </el-row>
            <el-row class="grid gap-x-10 ">
              <el-form-item label="是否外链">
                <el-radio-group v-model="form.foreign">
                  <el-radio label="是"/>
                  <el-radio label="否"/>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="路由地址">
                <el-input v-model="form.path"/>
              </el-form-item>
            </el-row>
            <el-row class="grid gap-x-10 ">
              <el-form-item label="菜单状态">
                <el-radio-group v-model="form.state">
                  <el-radio label="正常"/>
                  <el-radio label="停用"/>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="菜单顺序">
                <el-input-number v-model="num" :min="1" :max="10" @change="handleChange"/>
              </el-form-item>
            </el-row>

            <el-row class="grid gap-x-10 ">
              <el-form-item label="路由组件">
                <el-input v-model="form.name"/>
              </el-form-item>
              <el-form-item label="权限字符">
                <el-input v-model="form.name"/>
              </el-form-item>
            </el-row>

            <el-row class="grid gap-x-10 ">

              <el-form-item label="路由参数">
                <el-input v-model="form.name"/>
              </el-form-item>
              <el-form-item label="是否缓存">
                <el-radio-group v-model="form.foreign">
                  <el-radio label="是"/>
                  <el-radio label="否"/>
                </el-radio-group>
              </el-form-item>
            </el-row>

            <el-row class="grid gap-x-10 ">

              <el-form-item label="显示状态">
                <el-radio-group v-model="form.foreign">
                  <el-radio label="显示"/>
                  <el-radio label="隐藏"/>
                </el-radio-group>
              </el-form-item>
            </el-row>


          </el-tab-pane>
          <el-tab-pane label="按钮" name="third">
            <el-row class="grid gap-x-10">
              <el-form-item label="菜单名称">
                <el-input v-model="form.name"/>
              </el-form-item>
              <el-form-item label="菜单顺序">
                <el-input-number v-model="num" :min="1" :max="10" @change="handleChange"/>
              </el-form-item>
            </el-row>
            <el-form-item class="w-1/2" label="权限字符">
              <el-input v-model="form.name"/>
            </el-form-item>

          </el-tab-pane>
        </el-tabs>

      </el-form>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary">确定</el-button>
        <el-button @click="cancel=!cancel">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style lang='scss' scoped>
:deep(.el-tabs__nav-wrap::after) {
  background: none;
}

</style>