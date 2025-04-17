import request from '@/utils/request';

/**
 * 配置
 * @param {String} key
 * @returns
 */
export const configDetailByKey = (key) => {
  return request.get(`/common/config/getConfigByKey?key=${key}`);
};

/**
 * 配置组
 * @param {String} group
 * @returns
 */
export const configByGroup = (group) => {
  return request.get(`/common/config/getConfigByGroup?group=${group}`);
};

/**
 * 更新配置
 * @param {*} data
 * @returns
 */
export const updatConfig = (data) => {
  return request.put('/common/config', data);
};

/**
 * 更新配置组
 * @param {*} data
 * @returns
 */
export const updatConfigForGroup = (datas) => {
  return request.put('/common/config/batch', datas);
};
