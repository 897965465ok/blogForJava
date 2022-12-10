import {createRouter, createWebHistory} from 'vue-router';
import Index from '@/views/Index.vue';
import Login from '@/views/Login.vue';
import User from '@/components/User.vue';
import Article from '@/components/Article.vue';

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
      redirect: '/article',
      children: [
        {
          path: '/user',
          name: 'user',
          component: User
        },
        {
          path: '/article',
          name: 'article',
          component: Article
        },
      ]
    }

  ]
})
router.beforeEach((to, from, next) => {

  next()
})


export default router
