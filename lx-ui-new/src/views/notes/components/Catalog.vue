<template>
  <el-scrollbar height="100%" ref="scrollbarRef">
    <div class="pr-3">
      <el-tree
        class="catalog-tree"
        :data="catalogData"
        empty-text="暂无数据"
        node-key="id"
        :props="{ label: 'title' }"
        ref="catalogTreeRef"
        highlight-current
        :default-expanded-keys="defaultExpandedKeys"
        @node-click="handleNodeClick"
        :expand-on-click-node="!isEdit"
        draggable
        @node-drop="handleNodeDrop"
        :allow-drop="handleAllowDrop"
      >
        <template #default="{ data, node }">
          <div
            class="flex flex-1 items-center min-w-0 group"
            :data-id="data.id"
          >
            <div class="flex-1 truncate notes-title duration-300">
              <span v-if="!data.edit">{{ node.label }}</span>
              <el-input
                v-else
                :value="node.label"
                class="w-full"
                size="small"
                placeholder="请输入标题"
                ref="titleInputRef"
                v-model="editNode.title"
                @blur="handleSubmitTitle"
                @keyup.enter="handleSubmitTitle"
              />
            </div>
            <div class="shrink-0 gap-1 items-center flex w-14 justify-end h-6">
              <div
                class="w-6 h-6 rounded-md duration-300 hover:bg-gray-200 hidden justify-center items-center group-hover:flex"
                @click.stop="(e) => handleMoreClick(e.currentTarget, data)"
                v-click-outside="handleClickOutside"
              >
                <el-icon>
                  <More class="transform rotate-90" />
                </el-icon>
              </div>
              <div
                class="w-6 h-6 rounded-md duration-300 hover:bg-gray-200 hidden justify-center items-center group-hover:flex"
                @click.stop="(e) => handleAddClick(e.currentTarget, data)"
                v-click-outside="handleClickOutside"
              >
                <el-icon>
                  <Plus />
                </el-icon>
              </div>
            </div>
          </div>
        </template>
      </el-tree>
    </div>

    <el-popover
      ref="popoverRef"
      trigger="null"
      v-model:visible="popoverVisible"
      :virtual-ref="virtualRef"
      virtual-triggering
      popper-class="!p-2"
      :show-arrow="false"
      :hide-after="0"
    >
      <div class="w-full flex flex-col gap-1">
        <div
          class="w-full flex gap-1 h-8 items-center text-gray-600 px-2 rounded-md cursor-pointer duration-300 hover:bg-gray-100"
          v-for="item in propverItems"
          @click="handlePopverItemClick(item.commad)"
        >
          <i class="iconfont" :class="item.icon"></i>
          <span>{{ item.label }}</span>
        </div>
      </div>
    </el-popover>
  </el-scrollbar>
</template>

<script setup>
  import { ClickOutside as vClickOutside, ElMessageBox } from 'element-plus/es';
  import { Plus, More } from '@element-plus/icons-vue';
  import { ref, onMounted, nextTick, unref, watch } from 'vue';
  import { useCatalogStore } from '@/store/module/catalog';
  import { storeToRefs } from 'pinia';
  import { useRouter } from 'vue-router';
  import { CATALOG_TYPE } from '@/enum/catalog';
  import { ElMessage } from 'element-plus/es';
  import {
    deleteCatalogOrNoteApi,
    dropSortCatalogOrNoteApi
  } from '@/api/notes';
  import { DROP_POSITION } from '@/enum/drop';
  const router = useRouter();
  const catalogStore = useCatalogStore();
  const { catalogData } = storeToRefs(catalogStore);

  const { data, activeDoc } = defineProps({
    activeDoc: String
  });

  const isEdit = ref(false);

  const defaultExpandedKeys = ref([]);

  const scrollbarRef = ref(null);
  const virtualRef = ref(null);
  const popoverRef = ref(null);
  const catalogTreeRef = ref(null);

  const currentNode = ref(null);
  const popoverVisible = ref(false);
  const propverItems = ref([]);

  function handleMoreClick(targetEl, data) {
    handleClickOutside();
    propverItems.value = [
      { icon: 'lx-zhongmingming', label: '重命名', commad: 'Rename' },
      { icon: 'lx-shanchu', label: '删除', commad: 'Delete' }
    ];
    nextTick(() => {
      openPopover(targetEl, data);
    });
  }

  function handleAddClick(targetEl, data) {
    handleClickOutside();
    if (data.type === CATALOG_TYPE.GROUP) {
      propverItems.value = [
        { icon: 'lx-wendang', label: '新建文档', commad: 'AddDoc' },
        { icon: 'lx-xinjianfenzu', label: '新建分组', commad: 'AddGroup' }
      ];
      nextTick(() => {
        openPopover(targetEl, data);
      });
    } else {
      ElMessage.warning('只能在分组中新建！');
    }
  }

  function openPopover(targetEl, data) {
    currentNode.value = data;
    virtualRef.value = targetEl?.parentElement ?? targetEl;
    nextTick(() => {
      popoverVisible.value = true;
    });
  }

  function handleClickOutside() {
    popoverVisible.value = false;
    unref(popoverRef).hide?.();
    unref(popoverRef).popperRef?.delayHide?.();
    virtualRef.value = null;
  }

  function handlePopverItemClick(commad) {
    switch (commad) {
      case 'AddDoc': {
        handleAdd(CATALOG_TYPE.DOC, currentNode.value?.id);
        break;
      }
      case 'AddGroup': {
        handleAdd(CATALOG_TYPE.GROUP, currentNode.value?.id);
        break;
      }
      case 'Rename': {
        handleRename(currentNode.value);
        break;
      }
      case 'Delete': {
        handleDelete(currentNode.value);
        break;
      }
    }
    currentNode.value = null;
  }

  /**
   * 添加
   */
  function handleAdd(type, parentId) {
    catalogStore.addCatalog({ type, parentId });
    parentId && (defaultExpandedKeys.value = [parentId]);
  }

  const titleInputRef = ref(null);
  const editNode = ref({
    id: void 0,
    title: ''
  });

  /**
   * 重命名
   */
  async function handleRename({ id, title }) {
    isEdit.value = true;
    editNode.value = {
      id,
      title
    };
    catalogStore.toggleCatalogEdit(id);
    await nextTick();
    titleInputRef.value?.focus();
  }

  /**
   * 提交修改标题
   */
  function handleSubmitTitle() {
    catalogStore.renameCatalog(editNode.value);
    isEdit.value = false;
    catalogStore.toggleCatalogEdit(editNode.value.id);
    editNode.value = {
      id: void 0,
      title: ''
    };
  }

  /**
   * 删除
   * @param row
   */
  function handleDelete(row) {
    ElMessageBox.confirm(`确认删除“${row.title}”及相关数据吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        deleteCatalogOrNoteApi(row.id).then(async (msg) => {
          ElMessage.success(msg);
          await catalogStore.getCatalogData();
          if (!catalogStore.hasCatalog(activeDoc)) {
            router.push({ name: 'notes' });
          }
        });
      })
      .catch(() => {});
  }

  /**
   * 目录节点点击
   * @param data
   */
  function handleNodeClick(data) {
    if (!data.children && data.type === CATALOG_TYPE.DOC) {
      nextTick(() => {
        router.push({ name: 'notes', params: { id: data.id } });
      });
      return;
    }
    setNodeHighlight(isEdit.value);
  }

  /**
   * 设置节点高亮
   */
  function setNodeHighlight(isExpanded = true) {
    catalogTreeRef.value?.setCurrentKey(
      activeDoc && catalogStore.isDoc(activeDoc) ? activeDoc : null,
      isExpanded
    );
  }

  /**
   * 初始化滚动
   */
  function initScrollbar() {
    if (!activeDoc || !scrollbarRef.value) return;
    const wrapEl = scrollbarRef.value.wrapRef;
    nextTick(() => {
      const rect = wrapEl
        .querySelector(`[data-key='${activeDoc}']`)
        ?.getBoundingClientRect();
      scrollbarRef.value.setScrollTop(rect?.top ?? 0);
    });
  }

  /**
   * 拖拽时判定目标节点能否成为拖动目标位置
   */
  function handleAllowDrop(_, { data }, type) {
    return !(DROP_POSITION.INNER === type && data.type === CATALOG_TYPE.DOC);
  }

  /**
   * 节点拖拽
   * @param {*} node 拖拽节点
   * @param {*} target 目标节点
   * @param {'before' | 'after' | 'inner'} position
   */
  function handleNodeDrop(node, target, position) {
    const cur = node.data;
    const tag = target.data;

    dropSortCatalogOrNoteApi({
      nodeId: cur.id,
      targetNodeId: tag.id,
      dropType: position
    });
    if (cur.type === CATALOG_TYPE.DOC && cur.id === activeDoc) {
      setNodeHighlight();
    }
  }

  // 初始化
  onMounted(() => {
    setNodeHighlight();
    initScrollbar();
  });

  watch(() => activeDoc, setNodeHighlight);
</script>

<style lang="scss" scoped>
  .el-tree.el-tree--highlight-current
    .el-tree-node.is-current
    > .el-tree-node__content {
    .notes-title {
      font-weight: 700;
    }
  }
</style>
