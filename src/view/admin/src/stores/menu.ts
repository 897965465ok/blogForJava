import {defineStore} from "pinia";
import * as BlogApi from "@/api/BlogApi";
// $reset() 重置
// $patch 批量修改
// $state 替换
export const useStore = defineStore("menuStore", {
    state: () => {
        return {
            menuPages: {},
        };
    },
    getters: {
        getMenuPage: state => state.menuPages
    },
    actions: {

        setMenuPage(menuPages: any) {
            this.menuPages = menuPages;
        },

        async deleteManyMenu(Menus: any) {
            return await BlogApi.deleteManyMenu(Menus);
        },
        async queryManyMenu(offset: number, limit: number) {
            let menuPages = await BlogApi.queryManyMenu(offset, limit);
            this.setMenuPage(menuPages)
            return this.getMenuPage;
        },

        generate(result: any): any {
            if (Array.isArray(result)) {
                return result.map((item: any) => {
                    return {
                        label: item.menuName,
                        value: item.menuId,
                        children: this.generate(item.children),
                    };
                });
            } else {
                return [];
            }
        },

        async generateTree() {
            let {result} = await BlogApi.getRouter();
            return this.generate(result);
        },
    },
});
