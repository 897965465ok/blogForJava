import {api} from "@/api/index";
import qs from "qs";

export const createArticle = async (from: any) => {
    let response = await api.post("v1/createArticle", from);
    return response;
};
export const getArticle = async (offset: any, limit: any) => {
    let { data } = await api.get("v1/articles", {
        params: { offset: offset, limit: limit },
    });
    return data;
};

export const deleteArticle = async (id: any) => {
    let { data } = await api.delete("v1/articles", { params: { id } });
    return data;
};

export const readArticler = async (uuid: any) => {
    return await api.get("v1/watchnumber", {
        params: {
            uuid,
        },
    });
};
export const queryArticleTableHeader = async () => {
    let response = await api.get("v1/queryArticleTableHeader");
    return response.data.result;
};

export const comment = async (
    articleId: any,
    content: any,
    replyArticle: any,
    userName: any
) => {
    console.log(articleId, content, replyArticle, userName);
    return await api.post(
        "v1/comment",
        qs.stringify({
            articleId,
            content,
            replyArticle,
            userName,
        })
    );
};

export const getComments = async (articleId: any) => {
    let { data: comment } = await api.get("v1/comment", {
        params: { articleId },
    });
    // 如果有评论
    if (Array.isArray(comment.result)) {
        return comment.result.map((father: { Replys: any[]; UUID: any }) => {
            if (Array.isArray(father.Replys)) {
                father.Replys.map((item: { leave: any }) => {
                    item.leave = father.UUID;
                    return item;
                });
            }
            return father;
        });
    } else {
        return [];
    }
};
export const like = async (uuid: any) => {
    return await api.get("/v1/like", {
        params: {
            uuid,
        },
    });
};
