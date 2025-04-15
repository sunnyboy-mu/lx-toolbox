import request from '@/utils/request';

/**
 * 分页回收站文章
 */
export const pageBlogRecycle = async (params) => {
  return await request.get('/blog/recycle/page', {
    params
  });
};

/**
 * 批量删除
 */
export const batchDelBlogRecycle = async (ids) => {
  return await request.put('/blog/recycle/batch-del', ids);
};

/**
 * 批量恢复
 */
export const batchRecoveryBlogRecycle = async (ids) => {
  return await request.put('/blog/recycle/batch-recovery', ids);
};
