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
      }
    ]
  }
];
