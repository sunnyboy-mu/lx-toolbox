import request from '@/utils/request';

/**
 * 年度书单
 */
export const listBookGroupByYear = async () => {
  return await request.get('/book/info/year.interface');
};

/**
 * 书单列表
 */
export const listBook = async (year) => {
  return await request.get(`/book/info/list?year=${year}`);
};

/**
 * 新增书单
 */
export const addBook = async (data) => {
  return await request.post(`/book/info`, data);
};

/**
 * 修改书单
 */
export const updateBook = async (data) => {
  return await request.put(`/book/info`, data);
};

/**
 * 删除书单
 */
export const deteleBook = async (id) => {
  return await request.delete(`/book/info/${id}`);
};
