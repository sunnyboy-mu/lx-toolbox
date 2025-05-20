import { defineStore } from 'pinia';
import { ref } from 'vue';
import {
  notesCatalogApi,
  addCatalogOrNoteApi,
  renameCatalogOrNoteApi
} from '@/api/notes';
import { CATALOG_TYPE, DEFAULT_CATALOG_TITLE } from '@/enum/catalog';
import { findTree } from '@moe-cryo/utils';
import { eachTree } from '@moe-cryo/utils';

export const useCatalogStore = defineStore('catalog', () => {
  /**
   * 目录数据
   */
  const catalogData = ref([]);

  /**
   * 扁平化目录数据
   */
  const flatCatalogData = ref([]);

  /**
   * 目录类型
   */
  const catalogTypeMap = ref({});

  /**
   * 添加目录
   * @param {Object} data
   * @param {'DOC' | 'GROUP'} data.type
   * @param {String} data.parentId
   * @param {String?} data.title
   * @param {String?} data.icon
   */
  async function addCatalog(data) {
    data.title = data.title || DEFAULT_CATALOG_TITLE[data.type];
    const res = await addCatalogOrNoteApi(data);
    if (data.parentId) {
      const row = findTree(
        catalogData.value,
        (item) => item.id === data.parentId
      );
      if (!row.children) {
        row.children = [];
      }
      row.children.unshift(res);
    } else {
      catalogData.value.unshift(res);
    }
    return res;
  }

  /**
   * 切换编辑
   */
  function toggleCatalogEdit(id) {
    eachTree(catalogData.value, (row) => {
      if (id === row.id) {
        row.edit = !row.edit;
        return;
      }
    });
  }

  /**
   * 重命名
   */
  async function renameCatalog({ id, title }) {
    eachTree(catalogData.value, async (row) => {
      if (row.id === id) {
        row.title = title;
      }
    });
    const row = flatCatalogData.value.find((v) => v.id === id);
    await renameCatalogOrNoteApi({
      ...row,
      title
    });
  }

  /**
   * 加载目录数据
   */
  async function getCatalogData() {
    const data = await notesCatalogApi();
    catalogData.value = data;
    const arr = [];
    const map = {};
    eachTree(data, (v) => {
      const item = { ...v };
      delete item.children;
      arr.push(item);
      map[v.id] = v.type;
    });
    flatCatalogData.value = arr;
    catalogTypeMap.value = map;
  }

  /**
   * 是否有目录
   * @param {String} id
   */
  function hasCatalog(id) {
    return flatCatalogData.value.some((v) => v.id === id);
  }

  /**
   * 是否是文档
   * @param {String} id
   * @returns
   */
  function isDoc(id) {
    return catalogTypeMap.value[id] === CATALOG_TYPE.DOC;
  }

  return {
    catalogData,
    getCatalogData,
    addCatalog,
    renameCatalog,
    hasCatalog,
    isDoc,
    toggleCatalogEdit
  };
});
