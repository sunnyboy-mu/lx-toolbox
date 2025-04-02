import BackendLayout from '@/layout/backend/index.vue';

export default [
  {
    path: '/admin',
    component: BackendLayout,
    children: [
      {
        path: 'workplace',
        name: 'workplace',
        component: () => import('@/views/admin/workplace/index.vue'),
        meta: {
          title: '工作台',
          icon: 'icon-workplace'
        }
      },
      {
        path: 'bookmark/:category',
        name: 'bmManage',
        component: () => import('@/views/admin/bookmark/index.vue'),
        meta: {
          title: '书签管理',
          icon: 'icon-daohang'
        }
      }
    ]
  }
];
