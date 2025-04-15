import request from '@/utils/request';

/**
 * 分页文章
 */
export const pageBlogInfo = async (params) => {
  return await request.get('/blog/info/page', {
    params
  });
};

/**
 * 排序值
 */
export const nextBlogInfoSortValue = async () => {
  return await request.get('/blog/info/getSortValue');
};

/**
 * 添加文章
 */
export const addBlogInfo = (data) => {
  return request.post('/blog/info', data);
};

/**
 * 更新文章
 */
export const updateBlogInfo = (data) => {
  return request.put('/blog/info', data);
};

/**
 * 文章详细
 */
export const detailBlogInfo = async (id) => {
  return await request.get(`/blog/info/detail/${id}`);
};

/**
 * 删除文章
 */
export const deleteBlogInfo = (id) => {
  return request.delete(`/blog/info/${id}`);
};

/**
 * 上传图片
 */
export const uploadBlogInfoImage = async (file) => {
  return await request.postForm('/blog/info/upload-img', { file });
};
