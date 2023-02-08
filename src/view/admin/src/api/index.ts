import axios from 'axios'

const api = axios.create({
  timeout: 10000,
  headers:{
    "Content-type" :"application/json"
  },

})

// 环境的切换
switch (process.env.NODE_ENV) {
  case "development": {
    api.defaults.baseURL = "http://localhost:8888"
    break
  }
  case "debug": {
    api.defaults.baseURL = "http://146.56.206.160:8888"
    break
  }
  case "production": {
    api.defaults.baseURL = "http://146.56.206.160:8888"
    break
  }
  default: {
    api.defaults.baseURL = "http://localhost:8888"
  }
}

api.interceptors.response.use((config) => {
  let { status, data } = config
  switch (status) {
    case 200: {
      if (data.code == 200) {
        return config
      }
    }
    case 400: {

      return config
    }
    case 500: {
      return config
    }
    case 302: {
      return config
    }
    default: {
      return config
    }
  }
},
  (error) => {

    return new Promise.reject(error)
    
  })

api.interceptors.request.use((config:any) => {

  config.headers.authorization = window.localStorage.getItem("token");    //将token放到请求头发送给服务器


  return config;
}, (error) => {
  return Promise.reject(error);
});


export { api }
