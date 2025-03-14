import { createWebHistory } from 'vue-router';
import { createRouter } from 'vue-router';

const router = createRouter({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/home/index.vue')
    }
  ],
  history: createWebHistory(),
  scrollBehavior: () => {
    return { top: 0 };
  }
});

export default router;
