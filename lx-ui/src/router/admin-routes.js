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
      },
      {
        path: 'blog',
        name: 'blogManage',
        meta: {
          title: '博客管理',
          icon: 'icon-blogger'
        },
        children: [
          {
            path: 'category',
            name: 'blogCategory',
            component: () => import('@/views/admin/blog/category/index.vue'),
            meta: {
              title: '分类管理',
              icon: 'icon-fenlei'
            }
          },
          {
            path: 'group',
            name: 'blogGroup',
            component: () => import('@/views/admin/blog/group/index.vue'),
            meta: {
              title: '分组管理',
              icon: 'icon-fenzu'
            }
          },
          {
            path: 'info',
            name: 'blogInfo',
            component: () => import('@/views/admin/blog/info/index.vue'),
            meta: {
              title: '文章管理',
              icon: 'icon-16'
            }
          },
          {
            path: 'recycle',
            name: 'blogRecycle',
            component: () => import('@/views/admin/blog/recycle/index.vue'),
            meta: {
              title: '回收站',
              icon: 'icon-huishouzhan'
            }
          },
          {
            path: 'edit/:blogId?',
            name: 'blogAddOrEdit',
            component: () => import('@/views/admin/blog/edit/index.vue'),
            meta: {
              title: '编辑文章',
              icon: 'icon-16',
              hidden: true,
              active: '/admin/blog/info'
            }
          }
        ]
      }
    ]
  }
];
