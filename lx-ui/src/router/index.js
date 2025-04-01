import { createWebHistory } from 'vue-router';
import { createRouter } from 'vue-router';
import routes from './routes';
import { useUserStore } from '@/store/module/user';

const router = createRouter({
  routes,
  history: createWebHistory(),
  scrollBehavior: () => {
    return { top: 0 };
  }
});

router.beforeEach(async (to, from) => {
  const userStore = useUserStore();
  // 加载用户信息
  if (userStore.isFirstLoad) {
    await userStore.loadUserInfo();
    userStore.setFirstLoad(false);
  }

  // 已登录且跳转登录页，则重定向到首页
  if (userStore.isLoggedIn && to.path === '/login') {
    return { path: '/' };
  }

  // 未登录且跳转后台，则重定向到登录页
  if (to.path.startsWith('/admin') && !userStore.isLoggedIn) {
    return { path: '/login' };
  }

  // 权限校验
  const auth = to.meta.auth;
  if (auth && !checkAuth(auth, userStore.roles)) {
    return { path: '/403' };
  }
});

function checkAuth(toAuth = [], roles = []) {
  return toAuth.some((item) => roles.includes(item));
}

export default router;
