import FrontendLayout from '@/layout/frontend/index.vue';

export default [
  {
    path: '/fe',
    component: FrontendLayout,
    children: [
      {
        path: 'image-bed',
        name: 'image-bed',
        component: () => import('@/views/frontend/image-bed/index.vue'),
        meta: {
          title: '图床',
          icon: 'icon-image'
        }
      },
      {
        path: 'bookmark/:category',
        name: 'bookmark',
        component: () => import('@/views/frontend/bookmark/index.vue'),
        meta: {
          title: '书签',
          icon: 'icon-daohang'
        }
      }
    ]
  }
];
