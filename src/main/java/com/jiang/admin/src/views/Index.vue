<script lang="ts" setup>
import {useStore} from '@/stores'
import {storeToRefs} from 'pinia';

const {sideSwitch} = storeToRefs(useStore())
</script>
<template>
  <el-container :class="[sideSwitch ? 'switch-side' : '', 'app-wrapper']">
    <el-aside :class="['sidebar-container', 'mini-side']">
      <Side></Side>
    </el-aside>
    <el-container class="right-container mini-container">
      <el-header>
        <Header></Header>
      </el-header>
      <el-main>
        <Main></Main>
      </el-main>
    </el-container>
  </el-container>
</template>
<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  height: 100%;
}

.sidebar-container {
  width: 210px;
  height: 100%;
  transition: width 1s;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 1001;
  overflow: hidden;
  background-color: #304156;
}

.right-container {
  position: relative;
  height: 100%;
  margin-left: 210px;
  transition: margin 1s;
}


.switch-side {
  .mini-side {
    width: 54px;
  }

  .mini-container {
    transition: margin 1s;
    margin-left: 54px;
  }
}
</style>
