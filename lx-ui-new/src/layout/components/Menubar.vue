<template>
  <div class="h-8 flex items-center text-sm gap-4 justify-center">
    <div
      class="cursor-pointer duration-300 hover:text-primary menu-item"
      @click="goPage('/')"
      :class="{ active: defaultActive === '/' }"
    >
      <i class="iconfont lx-home mr-1"></i>
      <span>Home</span>
    </div>
    <div
      class="cursor-pointer duration-300 hover:text-primary menu-item"
      @click="goPage('/notes')"
      :class="{ active: defaultActive === '/notes' }"
    >
      <i class="iconfont lx-book mr-1"></i>
      <span>Notes</span>
    </div>
    <div class="cursor-pointer duration-300 hover:text-primary menu-item">
      <i class="iconfont lx-guanyu mr-1"></i>
      <span>About</span>
    </div>
    <div class="relative group sub-menu">
      <div class="cursor-pointer duration-300 hover:text-primary menu-item">
        <i class="iconfont lx-guanyu mr-1"></i>
        <span>More</span>
      </div>
      <div
        class="sub-menu-content absolute bg-background rounded-md shadow-md min-w-24 left-1/2 transform -translate-x-1/2 top-[96%] h-0 duration-300 overflow-hidden group-hover:h-auto"
        style="interpolate-size: allow-keywords"
      >
        <div
          class="flex flex-col gap-1 border border-[var(--el-border-color-light)] py-1"
        >
          <div class="cursor-pointer duration-300 hover:text-primary menu-item">
            <i class="iconfont lx-guanyu mr-1"></i>
            <span>Navs</span>
          </div>
          <div class="cursor-pointer duration-300 hover:text-primary menu-item">
            <i class="iconfont lx-guanyu mr-1"></i>
            <span>Books</span>
          </div>
          <div class="cursor-pointer duration-300 hover:text-primary menu-item">
            <i class="iconfont lx-guanyu mr-1"></i>
            <span>Gallery</span>
          </div>
          <div class="cursor-pointer duration-300 hover:text-primary menu-item">
            <i class="iconfont lx-guanyu mr-1"></i>
            <span>Links</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { useRoute, useRouter } from 'vue-router';
  import { computed } from 'vue';
  const router = useRouter();
  const route = useRoute();

  const defaultActive = computed(() => {
    return route.meta.active || route.path;
  });

  function goPage(path) {
    router.push(path);
  }
</script>

<style lang="scss" scoped>
  .menu-item {
    min-height: 32px;
    line-height: 32px;
  }
  .sub-menu:has(.active) > .menu-item,
  .menu-item.active,
  .menu-item:has(.active) {
    color: var(--color-primary);
  }

  .sub-menu {
    .sub-menu-content {
      .menu-item {
        padding: 0 8px;
        &:hover,
        &.active {
          background-color: var(--el-color-primary-light-9);
        }
      }
    }
  }
</style>
