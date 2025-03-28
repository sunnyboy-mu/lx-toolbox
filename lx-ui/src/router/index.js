import { createWebHistory } from 'vue-router';
import { createRouter } from 'vue-router';

const router = createRouter({
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('@/views/home/index.vue')
    },
    {
      path: '/image-bed',
      name: 'imageBed',
      component: () => import('@/views/frontend/image-bed/index.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue')
    }
  ],
  history: createWebHistory(),
  scrollBehavior: () => {
    return { top: 0 };
  }
});

export default router;
