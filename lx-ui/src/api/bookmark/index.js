import request from '@/utils/request';

/**
 * 分页书签
 */
export const pageBmInfo = async (params) => {
  return await request.get('/bm/info/page', { params });
};

/**
 * 添加书签
 */
export const addBmInfo = async (data) => {
  return await request.post(`/bm/info`, data);
};

/**
 * 更新书签
 */
export const updateBmInfo = async (data) => {
  return await request.put(`/bm/info`, data);
};

/**
 * 删除书签
 */
export const removeBmInfo = async (id) => {
  return await request.delete(`/bm/info/${id}`);
};

/**
 * 书签列表
 * @param {String} categoryId 分类ID
 */
export const listBookmark = async (categoryId) => {
  return await request.get(`/bm/info/list/${categoryId}.interface`);
};
