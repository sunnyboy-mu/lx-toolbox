import { API_BASE_URL, TOKEN_CACHE_NAME } from '@/config/setting';
import axios from 'axios';
import { getToken } from './token-util';
import { ElMessage } from 'element-plus/es';
import router from '@/router';

const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000
});

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    const token = getToken();
    config.headers[TOKEN_CACHE_NAME] = token;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  ({ data: resp }) => {
    const { code, msg, data } = resp;
    if (code === 200) {
      return data;
    }
    ElMessage.error(msg);
    if (code === 401) {
      // 未登录
      return Promise.reject(msg);
    } else if (code === 403) {
      router.push({ name: '403' });
    } else if (code === 404) {
      router.push({ name: '404' });
    }
    return Promise.reject(msg);
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default request;
