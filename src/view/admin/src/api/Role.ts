import {api} from "@/api/index";

export const queryManyRole = async (offset: number, limit: number) => {
    let response = await api.get("v1/queryManyRole", {
        params: { offset: offset, limit: limit },
    });
    return response.data;
};

export const queryRoleTableHeader = async () => {
    let response = await api.get("v1/queryRoleTableHeader");
    return response.data.result;
};

export const createRole = async (roleAndMenu: any) => {
    let response = await api.post("v1/createRole", roleAndMenu);
    return response.data;
};
export const changeRole = async (roleAndMenu: any) => {
    let response = await api.post("v1/changeRole", roleAndMenu);
    return response.data;
};

export const deleteManyRole = async (Roles: any) => {
    let response = await api.post("v1/deleteManyRole", Roles);
    return response.data;
};

export const getAllRoles = async() =>{
    let response = await api.post("v1/getAllRoles");
    return response.data;
}

export const getPermsByRoleId = async( role:any) =>{
    let response = await api.post("v1/getPermsByRoleId",role);
    return response.data;
}