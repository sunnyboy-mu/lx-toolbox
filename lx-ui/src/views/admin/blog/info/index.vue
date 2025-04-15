<template>
  <div class="lx-page flex">
    <div
      class="w-64 shrink-0 p-2 border-r border-gray-200 min-h-0"
      v-loading="loading"
      element-loading-text="数据加载中···"
    >
      <el-scrollbar>
        <el-tree
          ref="treeRef"
          :data="blogTypeData"
          :props="treeProps"
          node-key="id"
          highlight-current
          :expand-on-click-node="false"
          check-on-click-node
          @node-click="handleNodeClick"
        >
          <template #default="{ node, data }">
            <div class="flex items-center el-tree-node__label">
              <i
                v-if="data.icon"
                class="lx-iconfont mr-1 shrink-0"
                :class="data.icon"
              ></i>
              <span class="text-xs truncate flex-1 min-w-0">{{
                data.title
              }}</span>
            </div>
          </template>
        </el-tree>
      </el-scrollbar>
    </div>
    <div class="flex-1">
      <InfoList v-if="activeGroupId" class="h-full" :group-id="activeGroupId" />
    </div>
  </div>
</template>

<script setup>
  import { treeBlogType } from '@/api/blog/category';
  import { ref, nextTick } from 'vue';
  import InfoList from './components/InfoList.vue';

  const loading = ref(true);

  const blogTypeData = ref([]);

  const treeRef = ref(null);

  const activeGroupId = ref(null);

  const treeProps = {
    label: 'title'
  };

  treeBlogType().then((data) => {
    blogTypeData.value = data;
    loading.value = false;
    const firstCategory = data[0];
    if (!firstCategory) return;
    const firstGroup = firstCategory.children?.[0];
    handleSelectedType(firstGroup);
  });

  const handleSelectedType = (item) => {
    activeGroupId.value = item.id;
    nextTick(() => {
      treeRef.value.setCurrentKey(item.id);
    });
  };

  const handleNodeClick = (_, { level, data }) => {
    if (level === 1) {
      handleSelectedType({ id: activeGroupId.value });
      return;
    }
    handleSelectedType(data);
  };
</script>
