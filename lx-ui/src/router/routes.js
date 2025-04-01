import adminRoutes from './admin-routes';
import frontRoutes from './front-routes';

const routes = [
  {
    path: '/',
    name: 'index',
    component: () => import('@/views/home/index.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  }
];

routes.push(...frontRoutes);
routes.push(...adminRoutes);

const errorRoutes = [
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/error/403.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/error/404.vue')
  },
  {
    path: '/:pathMatch(.*)',
    redirect: '/404'
  }
];

routes.push(...errorRoutes);

export default routes;
