import {defineStore} from "pinia";
import * as BlogApi from "@/api/BlogApi";
// $reset() 重置
// $patch 批量修改
// $state 替换
export const tagStore = defineStore("tagStore", {
  state: () => {
    return {
      TagPages: {},
    };
  },
  getters: {},
  actions: {
    async queryManyTag(offset: number, limit: number) {
      this.TagPages = await BlogApi.queryManyTag(offset, limit);
      return this.TagPages;
    },
  },
});
