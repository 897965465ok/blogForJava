import {api} from "./index";
import axios from "axios";
import qs from "qs";
export * from "./Article";
export * from "./Menu";
export * from "./Role";
export * from "./User";

export const shouldJson = async (articleId: any, content: any) => {
    return await api.post(
        "v1/should",
        qs.stringify({
            articleId,
            content,
        })
    );
};

export const generateJSON = async () => {
    try {
        let data = (await api.get("v1/returnjson")).data;
        return JSON.parse(data.json);
    } catch (e) {
        return false;
    }
};

export const wallhaven = async (params: any) => {
    try {
        let {data} = await api.get("v1/wallhaven", {
            params: {
                limit: 216,
                offset: 1,
            },
        });
        return data.result;
    } catch (e) {
        return false;
    }
};
export const GetUrl = async () => {
    try {
        return await axios.get("https://pixabay.com/api", {
            params: {
                key: "21226858-57a14a3bedc89005c85e668cc",
                per_page: 200,
                //    q:"",
                category: "nature",

                safesearch: true,
            },
        });
    } catch (e) {
        return false;
    }
};

export const getOauthInfo = async (userInfo: {
    access_token: any;
    scope: any;
    origin: any;
}) => {
    let url;
    const {access_token, scope, origin} = userInfo;
    origin == "gitee" ? (url = "https://gitee.com/api/v5/user") : (url = "");
    return await api.get(url, {
        params: {
            access_token,
        },
    });
};


export const queryByTags = async (tags = "ES6", offset = 0, limit = 0) => {
    let response = await api.get("v1/query", {
        params: {
            tags,
            offset,
            limit,
        },
    });
    return response.data.result;
};




