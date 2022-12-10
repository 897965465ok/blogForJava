import {ref, computed, type Ref, reactive, Comment} from 'vue'
import {defineStore} from 'pinia'
import {getArticle} from '@/api/BlogApi'

export const useStore = defineStore('store', () => {
  const sideSwitch = ref(false);
  const pageInfo = ref();

  async function Articles(offset: number, limit: number) {
    pageInfo.value = await getArticle(offset, limit)
    return pageInfo.value
  }

  function setSideSwitch() {
    sideSwitch.value = !sideSwitch.value
  }

  return {pageInfo, sideSwitch, setSideSwitch, Articles}
})


