import request from '@/utils/request';

/**
 * 获取配置
 */
export const configByKey = async (key) => {
  return await request.get(`/common/config?key=${key}`);
};
