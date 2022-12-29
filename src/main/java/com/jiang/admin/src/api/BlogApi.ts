import {api} from "./index";
import axios from "axios";
import qs from "qs";



export const queryManyRole = async (offset: number, limit: number) => {
  let response = await api.get("v1/queryManyRole", {params: {offset: offset, limit: limit}})
  return response.data
}



export const queryManyUser = async (offset: number, limit: number) => {
  let response = await api.get("v1/queryManyUser", {params: {offset: offset, limit: limit}})
  return response.data
}


export const createArticle = async (from:any) => {
  let response = await api.post("v1/createArticle",from)
  return response
}

export const getRouter = async () => {
  let response = await api.get("v1/getRouter")
  return response.data
}

export const queryManyMenu = async (offset: number, limit: number) => {
  let response = await api.get("v1/menus", {params: {offset: offset, limit: limit}})
  return response.data
}


export const getComments = async (articleId: any) => {
  let {data: comment} = await api.get("v1/comment", {
    params: {articleId}
  });
  // 如果有评论
  if (Array.isArray(comment.result)) {
    return comment.result.map((father: { Replys: any[]; UUID: any; }) => {
      if (Array.isArray(father.Replys)) {
        father.Replys.map((item: { leave: any; }) => {
          item.leave = father.UUID;
          return item;
        });
      }
      return father;
    });
  } else {
    return []
  }
};

export const getArticle = async (offset: any, limit: any) => {
  let {data} = await api.get("v1/articles", {params: {offset: offset, limit: limit}})
  return data
}


export const deleteArticle = async (id: any) => {
  let {data} = await api.delete("v1/articles", {params: {id}})
  return data
}

export const comment = async (articleId: any, content: any, replyArticle: any, userName: any) => {
  console.log(articleId, content, replyArticle, userName);
  return await api.post(
      "v1/comment",
      qs.stringify({
        articleId,
        content,
        replyArticle,
        userName
      })
  );
};

export const shouldJson = async (articleId: any, content: any) => {
  return await api.post(
      "v1/should",
      qs.stringify({
        articleId,
        content
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
        offset: 1
      }
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

        safesearch: true
      }
    });
  } catch (e) {
    return false;
  }
};
export const getOauthInfo = async (userInfo: { access_token: any; scope: any; origin: any; }) => {
  let url;
  const {access_token, scope, origin} = userInfo;
  origin == "gitee" ? (url = "https://gitee.com/api/v5/user") : (url = "");
  return await api.get(url, {
    params: {
      access_token
    }
  });
};

export const readArticler = async (uuid: any) => {
  return await api.get("v1/watchnumber", {
    params: {
      uuid,
    },
  });

}
export const like = async (uuid: any) => {
  return await api.get("/v1/like", {
    params: {
      uuid,
    },
  });
}

export const queryByTags = async (tags = "ES6", offset = 0, limit = 0) => {
  let response = await api.get("v1/query", {
    params: {
      tags,
      offset,
      limit,
    },
  })
  return response.data.result
}

