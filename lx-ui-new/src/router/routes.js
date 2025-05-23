import Home from '@/views/home/index.vue';
import Layout from '@/layout/index.vue';

const routes = [
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        name: 'home',
        component: Home,
        meta: {
          title: '首页'
        }
      },
      {
        path: '/notes/:id?',
        name: 'notes',
        component: () => import('@/views/notes/index.vue'),
        meta: {
          title: '笔记',
          active: '/notes'
        }
      },
      {
        path: '/navs',
        name: 'navs',
        component: () => import('@/views/navs/index.vue'),
        meta: {
          title: '书签'
        }
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/index.vue')
  }
];

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
