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
      },
      {
        path: 'blog/:id',
        name: 'blogDetail',
        component: () => import('@/views/frontend/blog/index.vue'),
        meta: {
          hidden: true
        }
      },
      {
        path: 'about',
        name: 'about',
        meta: {
          title: '关于我',
          hidden: true
        },
        children: [
          {
            path: 'book',
            name: 'book',
            component: () => import('@/views/frontend/about/book/index.vue'),
            meta: {
              title: '书单'
            }
          }
        ]
      }
    ]
  }
];
