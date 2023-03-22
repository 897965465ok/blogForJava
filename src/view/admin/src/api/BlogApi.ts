import { api } from "./index";
import axios from "axios";
import qs from "qs";

export const deleteManyUser = async (userList: any) => {
  let { data } = await api.post("v1/deleteManyUser", userList);
  return data;
};

export const queryManyRole = async (offset: number, limit: number) => {
  let response = await api.get("v1/queryManyRole", {
    params: { offset: offset, limit: limit },
  });
  return response.data;
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

export const createArticle = async (from: any) => {
  let response = await api.post("v1/createArticle", from);
  return response;
};

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
    let { data } = await api.get("v1/wallhaven", {
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
  const { access_token, scope, origin } = userInfo;
  origin == "gitee" ? (url = "https://gitee.com/api/v5/user") : (url = "");
  return await api.get(url, {
    params: {
      access_token,
    },
  });
};

export const readArticler = async (uuid: any) => {
  return await api.get("v1/watchnumber", {
    params: {
      uuid,
    },
  });
};
export const like = async (uuid: any) => {
  return await api.get("/v1/like", {
    params: {
      uuid,
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

export const queryMenuTableHeader = async () => {
  let response = await api.get("v1/queryMenuTableHeader");
  return response.data.result;
};

export const queryArticleTableHeader = async () => {
  let response = await api.get("v1/queryArticleTableHeader");
  return response.data.result;
};

export const queryUserTableHeader = async () => {
  let response = await api.get("v1/queryUserTableHeader");
  return response.data.result;
};

export const queryRoleTableHeader = async () => {
  let response = await api.get("v1/queryRoleTableHeader");
  return response.data.result;
};

export const createRole = async (roleAndMenu: any) => {
  let response = await api.post("v1/createRole", roleAndMenu);
  return response.data;
};

export const deleteManyRole = async (Roles: any) => {
  let response = await api.post("v1/deleteManyRole", Roles);
  return response.data;
};

export const createMenu = async (Menu: Menu) => {
  let response = await api.post("v1/createMenu", Menu);
  return response.data;
};

export const deleteManyMenu = async (Menus: any) => {
  let response = await api.post("v1/deleteManyMenu", Menus);
  return response.data;
};

export const getAllRoles = async() =>{
  let response = await api.post("v1/getAllRoles");
  return response.data;
}