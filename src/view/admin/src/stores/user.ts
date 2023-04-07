import { ref, computed, type Ref, reactive, Comment } from "vue";
import { defineStore } from "pinia";
import * as BlogApi from "@/api/BlogApi";
// $reset() 重置
// $patch 批量修改
// $state 替换
export const useStore = defineStore("userStore", {
  state: () => {
    return {
      UserPages: {},
    };
  },
  getters: {},
  actions: {
    async queryManyUser(offset: number, limit: number) {
      this.UserPages = await BlogApi.queryManyUser(offset, limit);
      return this.UserPages;
    },
  },
});
