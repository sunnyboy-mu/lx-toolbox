import request from '@/utils/request';

/**
 * 公共图库上传
 */
export const imageUpload = async (formData) => {
  return await request.postForm('/image/upload.interface', formData, {
    timeout: -1
  });
};

/**
 * 删除图片
 */
export const imageDelete = async (filePath) => {
  return await request.postForm('/image/delete', { filePath });
};
