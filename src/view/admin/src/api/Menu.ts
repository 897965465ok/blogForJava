import {api} from "@/api/index";

export const getRouter = async () => {
    let response = await api.get("v1/getRouter");
    return response.data;
};

export const queryManyMenu = async (offset: number, limit: number) => {
    let response = await api.get("v1/menus", {
        params: { offset: offset, limit: limit },
    });
    return response.data;
};
export const queryMenuTableHeader = async () => {
    let response = await api.get("v1/queryMenuTableHeader");
    return response.data.result;
};

export const createMenu = async (Menu: Menu) => {
    let response = await api.post("v1/createMenu", Menu);
    return response.data;
};

export const deleteManyMenu = async (Menus: any) => {
    let response = await api.post("v1/deleteManyMenu", Menus);
    return response.data;
};
