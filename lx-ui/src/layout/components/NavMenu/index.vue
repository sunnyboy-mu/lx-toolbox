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
  const route = useRoute();
  const router = useRouter();

  const defaultActive = computed(() => {
    const { meta, path } = route;
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
