import { createRouter, createWebHistory,createWebHashHistory } from "vue-router";
import Index from "@/Pages/Index/Index.vue";
import Login from "@/Pages/Login/Login.vue";
import User from "@/Pages/User/User.vue";
import Article from "@/Pages/Article/Article.vue";
import Menu from "@/Pages/Menu/Menu.vue";
import Role from "@/Pages/Role/Role.vue";
const router = createRouter({
  //添加  createWebHashHistory  和vite.config 添加 base:'./'  解决首页白屏问题
  //
  history: createWebHashHistory (import.meta.env.BASE_URL),
  routes: [
    {
      path: "/login",
      name: "login",
      component: Login,
    },
    {
      path: "/",
      name: "Index",
      component: Index,
      redirect: "/login",
      children: [
        {
          path: "/article",
          name: "Article",
          component: Article,
        },
        {
          path: "/menu",
          name: "Menu",
          component: Menu,
        },
        {
          path: "/user",
          name: "User",
          component: User,
        },
        {
          path: "/role",
          name: "Role",
          component: Role,
        },
      ],
    },
  ],
});


const ascynRouters = [

]


/*
router.addRoute("Index",{
  path: "/v3",
  name: "role",
  component: Role,
},)

*/


// next(false): 中断当前的导航。如果浏览器的 URL 改变了 (可能是用户手动或者浏览器后退按钮)，那么 URL 地址会重置到 from 路由对应的地址。

// next('/') 或者 next({ path: '/' }): 跳转到一个不同的地址。当前的导航被中断，然后进行一个新的导航。你可以向 next 传递任意位置对象，且允许设置诸如 replace: true、name: 'home' 之类的选项以及任何用在 router-link 的 to prop 或 router.push 中的选项。

// next(error): (2.4.0+) 如果传入 next 的参数是一个 Error 实例，则导航会被终止且该错误会被传递给 router.onError() 注册过的回调。


// 判断是否登录
router.beforeEach((to, from, next) => {

  console.log(to,from)

  next();
});

export default router;
