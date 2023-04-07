import { ref, computed, type Ref, reactive, Comment } from "vue";
import { defineStore } from "pinia";
import * as BlogApi from "@/api/BlogApi";
// $reset() 重置
// $patch 批量修改
// $state 替换
export const useStore = defineStore("articleStore", {
  state: () => {
    return {
      articlePages: {},
      treeMap:{},
    };
  },
  getters: {},
  actions: {
    async getRouter() {
      let { result } = await BlogApi.getRouter();
      this.treeMap = result;
      return this.treeMap;
    },
    async articles(offset: number, limit: number) {
      let { result } = await BlogApi.getArticle(offset, limit);
      this.articlePages = result;
      return this.articlePages;
    }
  },
});
