/**
 * token操作封装
 */
import { TOKEN_CACHE_NAME } from '@/config/setting';

/**
 * 获取缓存的token
 */
export function getToken() {
  return localStorage.getItem(TOKEN_CACHE_NAME);
}

/**
 * 缓存token
 * @param token token
 */
export function setToken(token) {
  removeToken();
  if (token) {
    localStorage.setItem(TOKEN_CACHE_NAME, token);
  }
}

/**
 * 移除token
 */
export function removeToken() {
  localStorage.removeItem(TOKEN_CACHE_NAME);
}
