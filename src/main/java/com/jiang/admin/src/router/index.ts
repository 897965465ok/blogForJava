import {createRouter, createWebHistory} from 'vue-router';
import Index from '@/views/Index.vue';
import Login from '@/views/Login.vue';
import User from '@/components/User/User.vue';
import Article from '@/components/Article/Article.vue';
import Menu from '@/components/Menu/Menu.vue';
import Role from '@/components/Role/Role.vue';
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
