<template>
  <div class="lx-page flex">
    <div
      class="w-40 -ml-2 flex flex-col"
      v-loading="loading"
      element-loading-text="加载中"
    >
      <el-menu
        @select="handleGroupChange"
        :default-active="activeGroup"
        class="flex-1 bookmark-aside"
      >
        <el-menu-item index="">
          <span class="text-sm">全部</span>
        </el-menu-item>
        <el-menu-item
          :index="group.id"
          v-for="group in bmGroupData"
          :key="group.id"
        >
          <span class="text-sm">{{ group.title }}</span>
        </el-menu-item>
      </el-menu>
      <div
        class="flex shrink-0 justify-center border-r border-[var(--el-border-color-light)] px-2 pt-2"
      >
        <el-button class="w-full" :icon="Setting" @click="openGroupManage">
          分组管理
        </el-button>
      </div>
    </div>
    <div class="flex-1 flex flex-col">
      <BookmarkList :group-id="activeGroup" />
    </div>
    <GroupManage
      v-model="groupManageVisible"
      @done="getBmGroupList"
      :data="bmGroupData"
    />
  </div>
</template>

<script setup>
  import { useRoute } from 'vue-router';
  import { watch, ref, provide } from 'vue';
  import { listBmGroup } from '@/api/bookmark/group';
  import { Setting } from '@element-plus/icons-vue';
  import GroupManage from './components/GroupManage.vue';
  import BookmarkList from './components/BookmarkList.vue';

  const route = useRoute();

  const loading = ref(true);

  const bmGroupData = ref([]);
  const activeGroup = ref('');

  const groupManageVisible = ref(false);

  const getBmGroupList = async () => {
    const category = route.params.category;
    if (!category) return;
    loading.value = true;
    bmGroupData.value = await listBmGroup(category);
    if (
      activeGroup.value &&
      !bmGroupData.value.find((item) => item.id === activeGroup.value)
    ) {
      activeGroup.value = '';
    }
    loading.value = false;
  };

  const handleGroupChange = (id) => {
    activeGroup.value = id;
  };

  const openGroupManage = () => {
    groupManageVisible.value = true;
  };

  provide('group-data', bmGroupData);

  watch(
    () => route.params,
    () => {
      activeGroup.value = '';
      getBmGroupList();
    },
    {
      immediate: true
    }
  );
</script>

<style lang="scss">
  .bookmark-aside {
    --el-menu-item-height: 40px;
    --el-menu-base-level-padding: 16px;
    --el-menu-border-color: var(--el-border-color-light);
    .el-menu-item {
      margin: 3px 6px 6px;
      border-radius: 6px;
      &.is-active {
        background-color: var(--el-color-primary-light-9);
      }
    }
  }
</style>
