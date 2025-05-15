import request from '@/utils/request';

/**
 * 登录
 * @param {*} data
 * @returns
 */
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  });
}

/**
 * 用户信息
 */
export const getUserInfo = async () => {
  return await request.get('/userInfo.interface');
};

/**
 * 退出登录
 */
export const logout = async (data) => {
  return await request.get('/logout');
};
