import { defineStore } from 'pinia';
import { ref } from 'vue';
import { login, loginByAuthCode, logout, getUserInfo } from '@/api/login';
import { setToken, removeToken } from '@/utils/token-util';
import { ElMessage } from 'element-plus/es';

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false);
  const userInfo = ref(null);

  const loginDialogVisible = ref(false);

  /**
   * 用户登录
   * @param {{username?:string, password?: string, authCode?: string}} loginForm
   * @param {'auth' | 'account'} type
   */
  const doUserlogin = async (loginForm, type) => {
    const api = type === 'auth' ? loginByAuthCode : login;
    const data = await api(loginForm);
    ElMessage.success('登录成功');
    setToken(data.token);
    isLoggedIn.value = true;
    userInfo.value = data;
    loginDialogVisible.value = false;
  };

  const doUserLogout = async () => {
    await logout();
    ElMessage.success('退出成功');
    removeToken();
    isLoggedIn.value = false;
    userInfo.value = null;
  };

  const loadUserInfo = async () => {
    const data = await getUserInfo();
    if (data) {
      isLoggedIn.value = true;
      userInfo.value = data;
    }
  };

  return {
    isLoggedIn,
    userInfo,
    loginDialogVisible,
    doUserlogin,
    doUserLogout,
    loadUserInfo
  };
});
