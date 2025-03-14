import { createWebHistory } from 'vue-router';
import { createRouter } from 'vue-router';

const router = createRouter({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/home/index.vue')
    },
    {
      path: '/image-bed',
      name: 'ImageBed',
      component: () => import('@/views/frontend/image-bed/index.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/login/index.vue')
    }
  ],
  history: createWebHistory(),
  scrollBehavior: () => {
    return { top: 0 };
  }
});

export default router;
