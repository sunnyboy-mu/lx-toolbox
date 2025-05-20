<template>
  <div class="flex flex-1 pl-72">
    <div
      class="w-72 shrink-0 bg-white dark:bg-background p-2 fixed left-0 top-[var(--header-height)] bottom-0 flex flex-col"
    >
      <!-- 功能 -->
      <div class="shrink-0 h-8 text-sm flex cursor-pointer items-center mb-2">
        <router-link
          to="/notes"
          class="flex-1 duration-300 hover:bg-gray-100 h-8 flex items-center rounded-md mr-2 pl-2"
          :class="{ active: !active }"
        >
          <i class="iconfont lx-home"></i>
          <span>首页</span>
        </router-link>

        <el-popover
          placement="bottom-start"
          :show-arrow="false"
          popper-class="!p-2"
          v-model:visible="popoverVisible"
        >
          <template #reference>
            <div
              class="border border-gray-200 flex items-center justify-center w-7 h-7 rounded-md duration-300 hover:bg-gray-100"
            >
              <el-icon>
                <Plus />
              </el-icon>
            </div>
          </template>
          <div class="w-full flex flex-col gap-1">
            <div
              class="w-full flex gap-1 h-8 items-center text-gray-600 px-2 rounded-md cursor-pointer duration-300 hover:bg-gray-100"
              @click="handleAddDoc"
            >
              <i class="iconfont lx-wendang"></i>
              <span>新建文档</span>
            </div>
            <div
              class="w-full flex gap-1 h-8 items-center text-gray-600 px-2 rounded-md cursor-pointer duration-300 hover:bg-gray-100"
              @click="handleAddGroup"
            >
              <i class="iconfont lx-xinjianfenzu"></i>
              <span>新建分组</span>
            </div>
          </div>
        </el-popover>
      </div>

      <!-- 目录 -->
      <Loading class="flex-1 min-h-0" :loading="loading">
        <Catalog v-if="!loading" :active-doc="active" />
      </Loading>
    </div>
    <div class="flex-1 bg-gray-50">1111-{{ active }}</div>
  </div>
</template>

<script setup>
  import { Plus } from '@element-plus/icons-vue';
  import { useRoute } from 'vue-router';
  import { computed, ref } from 'vue';
  import Loading from '@/components/Loading/index.vue';
  import Catalog from './components/Catalog.vue';
  import { useCatalogStore } from '@/store/module/catalog';
  import { CATALOG_TYPE } from '@/enum/catalog';
  const catalogStore = useCatalogStore();

  const route = useRoute();

  const active = computed(() => route.params.id);

  const loading = ref(true);

  const popoverVisible = ref(false);

  async function loadCatalog() {
    await catalogStore.getCatalogData();
    loading.value = false;
  }

  function handleAddDoc() {
    catalogStore.addCatalog({
      type: CATALOG_TYPE.DOC
    });
    popoverVisible.value = false;
  }

  function handleAddGroup() {
    catalogStore.addCatalog({
      type: CATALOG_TYPE.GROUP
    });
    popoverVisible.value = false;
  }

  loadCatalog();
</script>

<style lang="scss" scoped>
  .active {
    background-color: oklch(96.7% 0.003 264.542);
  }
</style>
