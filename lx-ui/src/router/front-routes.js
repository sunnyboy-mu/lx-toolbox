import FrontendLayout from '@/layout/frontend/index.vue';

export default [
  {
    path: '/fe',
    component: FrontendLayout,
    children: [
      {
        path: 'image-bed',
        name: 'image-bed',
        component: () => import('@/views/frontend/image-bed/index.vue')
      },
      {
        path: 'bookmark/:category',
        name: 'bookmark',
        component: () => import('@/views/frontend/bookmark/index.vue')
      }
    ]
  }
];
