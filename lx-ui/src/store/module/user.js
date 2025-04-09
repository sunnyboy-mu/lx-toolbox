import { defineStore } from 'pinia';
import { nextTick, ref } from 'vue';
import { BLOG_ICON } from '@/enum/common-config';
import {
  login,
  loginByAuthCode,
  logout,
  getUserInfo,
  systemInfo
} from '@/api/login';
import { setToken, removeToken } from '@/utils/token-util';
import { ElMessage } from 'element-plus/es';
import { generateAdminMenu, generateFrontEndMenu } from '@/utils/menu-utils';
import { injectionBlogIconfontCss } from '@/utils/page-util';

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false);
  const userInfo = ref(null);

  const roles = ref([]);

  const isFirstLoad = ref(true);

  const loginDialogVisible = ref(false);

  // 前台菜单
  const frontMenu = ref([]);
  // 后台菜单
  const backMenu = ref([]);

  /**
   * 用户登录
   * @param {{username?:string, password?: string, authCode?: string}} loginForm
   * @param {'auth' | 'account'} type
   */
  const doUserlogin = async (loginForm, type) => {
    const api = type === 'auth' ? loginByAuthCode : login;
    const data = await api(loginForm);
    ElMessage.success('登录成功');
    await loadSystemInfo();
    setToken(data.token);
    isLoggedIn.value = true;
    userInfo.value = data;
    roles.value = data.role?.split(',');
    loginDialogVisible.value = false;
  };

  const doUserLogout = async () => {
    await logout();
    ElMessage.success('退出成功');
    removeToken();
    isLoggedIn.value = false;
    userInfo.value = null;
    frontMenu.value = [];
    backMenu.value = [];
    roles.value = [];
    loadSystemInfo();
  };

  const loadUserInfo = async () => {
    const data = await getUserInfo();
    await loadSystemInfo();
    if (!data) return;
    isLoggedIn.value = true;
    userInfo.value = data;
    roles.value = data.role?.split(',');
  };

  const loadSystemInfo = async () => {
    const data = await systemInfo();
    nextTick(() => {
      // 注入iconfont
      injectionBlogIconfontCss(data[BLOG_ICON]);
    });

    // 前台菜单
    frontMenu.value = generateFrontEndMenu(data);

    // 后台菜单
    backMenu.value = generateAdminMenu(data);
  };

  /**
   *
   * @param {Boolean} val
   */
  const setFirstLoad = (val) => {
    isFirstLoad.value = val;
  };

  return {
    isLoggedIn,
    userInfo,
    loginDialogVisible,
    frontMenu,
    backMenu,
    isFirstLoad,
    roles,
    doUserlogin,
    doUserLogout,
    loadUserInfo,
    setFirstLoad
  };
});
