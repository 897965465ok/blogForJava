import {defineStore} from "pinia";
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
    getters: {
        get_user_page: state => state.UserPages
    },
    actions: {

        Set_User_Pages(content: any) {
            this.UserPages = content;
        },
        async queryManyUser(offset: number, limit: number) {
            let page = await BlogApi.queryManyUser(offset, limit);
            this.Set_User_Pages(page);
            return this.get_user_page;
        },
    },
});
