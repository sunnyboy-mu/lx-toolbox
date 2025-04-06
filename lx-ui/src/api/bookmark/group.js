import request from '@/utils/request';

/**
 * 分组列表
 */
export const listBmGroup = async (categoryId) => {
  return await request.get(`/bm/group?categoryId=${categoryId}`);
};

/**
 * 添加分组
 */
export const addBmGroup = async (data) => {
  return await request.post(`/bm/group`, data);
};

/**
 * 更新分组
 */
export const updateBmGroup = async (data) => {
  return await request.put(`/bm/group`, data);
};

/**
 * 删除分组
 */
export const removeBmGroup = async (id) => {
  return await request.delete(`/bm/group/${id}`);
};

/**
 * 调整分组排序
 * @param groupIds {Array} 分组id数组
 */
export const updateBmGroupSort = async (groupIds) => {
  return await request.put(`/bm/group/sort`, groupIds);
};
