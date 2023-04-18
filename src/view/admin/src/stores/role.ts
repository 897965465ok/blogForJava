import { ref, computed, type Ref, reactive, Comment } from "vue";
import { defineStore } from "pinia";
import * as BlogApi from "@/api/BlogApi";
// $reset() 重置
// $patch 批量修改
// $state 替换
export const useStore = defineStore("roleStore", {
  state: () => {
    return {
      RolePages: {},
    };
  },
  getters: {},
  actions: {
    async queryManyRole(offset: number, limit: number) {
      this.RolePages = await BlogApi.queryManyRole(offset, limit);
      return this.RolePages;
    },
    async getAllRoles() {
      let { result } = await BlogApi.getAllRoles();
      return result;
    },

  },
});
