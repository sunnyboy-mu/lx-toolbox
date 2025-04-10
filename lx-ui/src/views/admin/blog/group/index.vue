<template>
  <div class="lx-page flex">
    <div
      class="category-list w-56 mr-2"
      v-loading="loading"
      element-loading-text="加载中···"
    >
      <el-scrollbar>
        <div class="grid grid-cols-1 gap-2">
          <div
            v-for="item in categoryList"
            :key="item.id"
            class="flex gap-2 h-10 items-center cursor-pointer duration-300 rounded-md px-2 hover:bg-[var(--el-color-primary-light-9)] hover:text-primary"
            :class="{ active: activeCategory === item.id }"
            @click="handleClick(item)"
          >
            <i class="lx-iconfont shrink-0" :class="item.icon"></i>
            <span class="truncate text-sm">{{ item.title }}</span>
          </div>
        </div>
      </el-scrollbar>
    </div>
    <div class="h-full flex-1 min-w-0" v-if="activeCategory">
      <GroupList :category-id="activeCategory" />
    </div>
  </div>
</template>
<script setup>
  import { listBlogCategory } from '@/api/blog/category';

  import { ref, provide } from 'vue';
  import GroupList from './components/GroupList.vue';

  const loading = ref(true);

  const categoryList = ref([]);

  const activeCategory = ref('');

  const handleClick = (item) => {
    activeCategory.value = item?.id;
  };

  listBlogCategory().then((data) => {
    categoryList.value = data;
    handleClick(categoryList.value?.[0]);
    loading.value = false;
  });

  provide('category-data', categoryList);
</script>

<style lang="scss" scoped>
  .category-list {
    .active {
      background-color: var(--el-color-primary-light-9);
      color: var(--el-color-primary);
    }
  }
</style>
