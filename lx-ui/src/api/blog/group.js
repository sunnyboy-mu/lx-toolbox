import request from '@/utils/request';

/**
 * 分组列表
 */
export const listBlogGroup = async (categoryId) => {
  return await request.get(`/blog/group?categoryId=${categoryId}`);
};

export const nextBlogGroupSortValue = async () => {
  return await request.get('/blog/group/nextSortValue');
};

/**
 * 添加分组
 */
export const addBlogGroup = async (data) => {
  return await request.post(`/blog/group`, data);
};

/**
 * 更新分组
 */
export const updateBlogGroup = async (data) => {
  return await request.put(`/blog/group`, data);
};

/**
 * 删除分组
 */
export const deleteBlogGroup = async (id) => {
  return await request.delete(`/blog/group/${id}`);
};

/**
 * 分组下文章
 */
export const listBlogInfoByGroupId = async (groupId) => {
  return await request.get('/blog/group/info?groupId=' + groupId);
};
