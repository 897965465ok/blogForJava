<template>
  <div id="screenFul" @click="handleFullScreen">
    <el-icon>
      <FullScreen/>
    </el-icon>
  </div>
</template>

<script lang="ts" setup>
import screenfull from 'screenfull'
import {ref} from 'vue'

const icon = ref(screenfull.isFullscreen)

const handleFullScreen = () => {
  if (screenfull.isEnabled) {
    screenfull.toggle()
  }
}

const changeIcon = () => {
  icon.value = screenfull.isFullscreen
}

// onMounted(() => {
//   screenfull.on('', changeIcon)
// })

// onBeforeMount(() => {
//   screenfull.off('change')
// })
</script>

<style lang="scss" scoped>
.el-icon {
  font-size: 25px;
  cursor: pointer;
}

</style>
