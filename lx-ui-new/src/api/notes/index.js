import request from '@/utils/request';

/**
 * 笔记目录
 */
export function notesCatalogApi() {
  return request.get('/blog/catalog/tree.interface');
}

/**
 * 添加目录/笔记
 */
export function addCatalogOrNoteApi(data) {
  return request.post('/blog/catalog/add', data);
}

/**
 * 重命名目录/笔记
 */
export function renameCatalogOrNoteApi(data) {
  return request.put('/blog/catalog/update', data);
}

/**
 * 删除目录/笔记
 */
export function deleteCatalogOrNoteApi(id) {
  return request.delete(`/blog/catalog/delete/${id}`);
}

/**
 * 拖拽排序目录/笔记
 */
export function dropSortCatalogOrNoteApi(data) {
  return request.put('/blog/catalog/dropSort', data);
}
