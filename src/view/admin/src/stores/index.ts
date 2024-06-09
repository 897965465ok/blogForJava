import {defineStore} from "pinia";
import * as BlogApi from "@/api/BlogApi";
// $reset() 重置
// $patch 批量修改
// $state 替换
export const useStore = defineStore("index", {
    state: () => {
        return {
            sideSwitch: false,
            treeMap: {},
            userInfo: {}
        };
    },
    getters: {
        getUserInfo: state => state.userInfo
    },
    actions: {
        async getRouter() {
            let {result} = await BlogApi.getRouter();
            this.treeMap = result;
            return this.treeMap;
        },
        setSideSwitch() {
            this.sideSwitch = !this.sideSwitch;
        },
        async setUserInfo(content: any) {
            this.userInfo = content;
        },
        auth(pemers: String) {
            // @ts-ignore
            return this.getUserInfo.permissions.includes(pemers);
        },

    },
    persist: true,
});
