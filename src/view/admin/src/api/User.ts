import {api} from "@/api/index";


export const queryUserTableHeader = async () => {
    let response = await api.get("v1/queryUserTableHeader");
    return response.data.result;
};


export const deleteManyUser = async (userList: any) => {
    let { data } = await api.post("v1/deleteManyUser", userList);
    return data;
};

export const createUser = async (userForm: any) => {
    let response = await api.post("v1/register", userForm);
    return response.data;
};

export const userUpdate = async (userForm: any) => {
    let response = await api.post("v1/userUpdate", userForm);
    return response.data;
};

export const queryManyUser = async (offset: number, limit: number) => {
    let response = await api.get("v1/queryManyUser", {
        params: { offset: offset, limit: limit },
    });
    return response.data;
};
