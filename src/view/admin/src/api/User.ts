import {api} from "@/api/index";
import qs from "qs";


// @ts-ignore
import Cookies from "js-cookie";


export const queryUserTableHeader = async () => {
    let response = await api.get("v1/queryUserTableHeader");
    return response.data.result;
};


export const deleteManyUser = async (userList: any) => {
    let {data} = await api.post("v1/deleteManyUser", userList);
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
        params: {offset: offset, limit: limit},
    });
    return response.data;
};

export const userLogin = async (account: string, password: string, email: string) => {
    return await api.post(
        '/v1/login',
        qs.stringify({account, password, email}),
        {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
    );
};

export const userLogout = async () => {
     let tokenInfo =   await api.post(
        '/v1/logout',
        {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
    );

     return tokenInfo.data
};
