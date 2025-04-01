import request from '@/utils/request';

/**
 * 用户登录
 * @param {object} data
 * @param {string} data.username
 * @param {string} data.password
 */
export const login = async (data) => {
  return await request.post('/login', data);
};

/**
 * 授权码登录
 * @param {object} data
 * @param {string} data.authCode
 */
export const loginByAuthCode = async (data) => {
  return await request.post('/oauth/login', data);
};

/**
 * 退出登录
 */
export const logout = async (data) => {
  return await request.get('/logout');
};

/**
 * 用户信息
 */
export const getUserInfo = async () => {
  return await request.get('/userInfo.interface');
};

/**
 * 系统信息
 */
export const systemInfo = async () => {
  return await request.get('/common/system-info.interface');
};
