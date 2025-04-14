import request from '@/utils/request';

/**
 * 分类列表
 */
export const listBlogCategory = async () => {
  return await request.get('/blog/category');
};

export const nextBlogCategorySortValue = async () => {
  return await request.get('/blog/category/next-sort');
};

/**
 * 添加分类
 */
export const addBlogCategory = async (data) => {
  return await request.post(`/blog/category`, data);
};

/**
 * 更新分类
 */
export const updateBlogCategory = async (data) => {
  return await request.put(`/blog/category`, data);
};

/**
 * 删除分类
 */
export const deleteBlogCategory = async (id) => {
  return await request.delete(`/blog/category/${id}`);
};

/**
 * 博客类型树
 */
export const treeBlogType = async () => {
  return await request.get(`/blog/category/typeTree`);
};
