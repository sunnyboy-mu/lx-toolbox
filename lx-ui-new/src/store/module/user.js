import { getUserInfo, login, logout } from '@/api/login';
import { removeToken, setToken } from '@/utils/token-util';
import { ElMessage } from 'element-plus/es';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore('user', () => {
  const loagined = ref(false);

  const user = ref(null);

  async function doLogin(form) {
    try {
      const data = await login(form);
      user.value = data;
      setToken(data.token);
      loagined.value = true;
      return data;
    } catch (err) {
      return await Promise.reject(err);
    }
  }

  async function doUserLogout() {
    await logout();
    ElMessage.success('退出成功');
    removeToken();
    loagined.value = false;
    user.value = null;
    window.location.reload();
  }

  const loadUserInfo = async () => {
    const data = await getUserInfo();
    if (!data) return;
    loagined.value = true;
    user.value = data;
  };

  return {
    loagined,
    user,
    doLogin,
    doUserLogout,
    loadUserInfo
  };
});
