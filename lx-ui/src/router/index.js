import { createWebHistory } from 'vue-router';
import { createRouter } from 'vue-router';
import FrontendLayout from '@/layout/frontend/index.vue';
import BackendLayout from '@/layout/backend/index.vue';

const router = createRouter({
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('@/views/home/index.vue')
    },
    {
      path: '/fe',
      component: FrontendLayout,
      children: [
        {
          path: 'image-bed',
          name: 'image-bed',
          component: () => import('@/views/frontend/image-bed/index.vue')
        }
      ]
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
