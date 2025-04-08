<template>
  <el-menu
    :default-active="useRoute().path"
    class="lx-admin-menus"
    mode="horizontal"
    @select="handleSelect"
  >
    <NavMenuItem v-for="menu in data" :menu="menu" :key="menu.path" />
  </el-menu>
</template>

<script setup>
  import { isExternalLink } from '@/utils/common';
  import { useRoute, useRouter } from 'vue-router';
  import NavMenuItem from './NavMenuItem.vue';

  const { data } = defineProps({
    data: Array
  });

  const router = useRouter();

  const handleSelect = (url) => {
    if (isExternalLink(url)) {
      window.open(url, '_blank');
    } else {
      router.push(url);
    }
  };
</script>
