<template>
  <el-menu
    :default-active="defaultActive"
    class="lx-admin-menus"
    mode="horizontal"
    @select="handleSelect"
  >
    <NavMenuItem v-for="menu in data" :menu="menu" :key="menu.path" />
  </el-menu>
</template>

<script setup>
  import { computed } from 'vue';
  import { isExternalLink } from '@/utils/common';
  import { useRoute, useRouter } from 'vue-router';
  import NavMenuItem from './NavMenuItem.vue';
  import { useBlogStore } from '@/store/module/blog';
  const route = useRoute();
  const router = useRouter();
  const blogStore = useBlogStore();

  const defaultActive = computed(() => {
    const { meta, path } = route;
    if (path.startsWith('/fe/blog')) {
      return blogStore.activeMenuUrl ?? path;
    }
    return meta.active ?? path;
  });

  const { data } = defineProps({
    data: Array
  });

  const handleSelect = (url) => {
    if (isExternalLink(url)) {
      window.open(url, '_blank');
    } else {
      router.push(url);
    }
  };
</script>
