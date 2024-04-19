import {api} from "@/api/index";
import type {Ref} from "vue";


export const queryManyTag = async (offset: number, limit: number) => {
    let response = await api.get("v1/tags");

    return response.data;
};

export const queryTagsTableHeader = async () => {
    let response = await api.get("v1/queryTagsTableHeader");
    return response.data.result;
};


export const createTag = async (tagName: Ref<String>) => {
    let response = await api.post("v1/tags", null, {
        params: {
            article_tag: tagName.value
        }
    });
    return response.data;
};

export const deleteTag = async (tagList: Array<number>) => {


    let {data} = await api.post("v1/deleteTags", null, {
        params: {id: tagList.join(',')}

    });

    return data;
};

export const updateTags = async (id: String, content: String) => {
    let response = await api.post("v1/updateTags", null, {
        params: {
            id,
            content
        }
    });

    return response.data;
};
