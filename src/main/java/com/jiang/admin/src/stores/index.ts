import { ref, computed, type Ref, reactive, Comment } from 'vue'
import { defineStore } from 'pinia'
import * as BlogApi from '@/api/BlogApi'

export const useStore = defineStore('store', () => {
  const sideSwitch = ref(false);
  const articlePages: any = ref({});
  const treeMap: treeMapTyep = ref({});
  const menuPages = ref();
  const UserPages = ref();
  const RolePages = ref();
  async function articles(offset: number, limit: number) {
    let data = await BlogApi.getArticle(offset, limit)
    articlePages.value = data.result;
    return articlePages.value
  }


  async function queryManyRole(offset: number, limit: number) {
    RolePages.value = await BlogApi.queryManyRole(offset, limit)
    return RolePages.value
  }



  async function queryManyMenu(offset: number, limit: number) {
    menuPages.value = await BlogApi.queryManyMenu(offset, limit)
    return menuPages.value
  }
  async function queryManyUser(offset: number, limit: number) {
    UserPages.value = await BlogApi.queryManyUser(offset, limit)
    return UserPages.value
  }



  async function getRouter() {
    let { result } = await BlogApi.getRouter()
    treeMap.value = result
  }



  function setSideSwitch() {
    sideSwitch.value = !sideSwitch.value
  }

  return {
    menuPages,
    treeMap,
    articlePages,
    sideSwitch,
    setSideSwitch,
    articles,
    getRouter,
    queryManyMenu,
    queryManyUser,
    queryManyRole
  }
})


