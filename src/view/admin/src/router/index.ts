import {createRouter, createWebHistory} from 'vue-router';
import Index from '@/Pages/Index/Index.vue';
import Login from '@/Pages/Login/Login.vue';
import User from '@/Pages/User/User.vue';
import Article from '@/Pages/Article/Article.vue';
import Menu from '@/Pages/Menu/Menu.vue';
import Role from '@/Pages/Role/Role.vue';
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/',
      name: 'Index',
      component: Index,
      redirect: '/menu',
      children: [
        {
          path: '/article',
          name: 'article',
          component: Article
        },
        {
          path: '/menu',
          name: 'menu',
          component: Menu
        },
        {
          path: '/user',
          name: 'user',
          component: User
        },
        {
          path: '/role',
          name: 'role',
          component: Role
        },
      ]
    }

  ]
})
router.beforeEach((to, from, next) => {

  next()
})


export default router
