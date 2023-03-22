import { ref, computed, type Ref, reactive, Comment } from 'vue'
import { defineStore } from 'pinia'
import * as BlogApi from '@/api/BlogApi'
// $reset() 重置
// $patch 批量修改
// $state 替换
export const useStore = defineStore('store', {
  state: () => {
    return {
      sideSwitch: false,
      articlePages: {},
      treeMap: {},
      menuPages: {},
      UserPages: {},
      RolePages: {}
    }
  },
  getters: {

  },
  actions: {

    async deleteManyMenu(Menus: any) {
  
      return await BlogApi.deleteManyMenu(Menus)
    },

    async articles(offset: number, limit: number) {
      let { result } = await BlogApi.getArticle(offset, limit)
      this.articlePages = result;
      return this.articlePages
    },
    async queryManyRole(offset: number, limit: number) {
      this.RolePages = await BlogApi.queryManyRole(offset, limit)
      return this.RolePages
    },

    async queryManyMenu(offset: number, limit: number) {
      this.menuPages = await BlogApi.queryManyMenu(offset, limit)
      return this.menuPages
    },

    async queryManyUser(offset: number, limit: number) {
      this.UserPages = await BlogApi.queryManyUser(offset, limit)
      return this.UserPages
    },

    async getRouter() {
      let { result } = await BlogApi.getRouter()
      this.treeMap = result
      return this.treeMap
    },

    setSideSwitch() {
      this.sideSwitch = !this.sideSwitch
    },

    generate(result: any): any {
      if (Array.isArray(result)) {
        return result.map((item: any) => {
          return {
            label: item.menuName,
            value: item.menuId,
            children: this.generate(item.children)
          }
        })
      } else {
        return []
      }
    },

    async generateTree() {
      let { result } = await BlogApi.getRouter()
      return this.generate(result)
    }
    ,
    async getAllRoles() {
      let { result } = await BlogApi.getAllRoles()
      return result;

    }
    

  }

})