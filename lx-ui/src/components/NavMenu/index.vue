<template>
  <el-menu
    :default-active="useRoute().path"
    class="lx-admin-menus"
    mode="horizontal"
    @select="handleSelect"
  >
    <el-menu-item index="/">首页</el-menu-item>
    <el-menu-item index="/fe/image-bed">图床</el-menu-item>
  </el-menu>
</template>

<script setup>
  import { isExternalLink } from '@/utils/common';
  import { useRoute, useRouter } from 'vue-router';

  const router = useRouter();

  const handleSelect = (url) => {
    if (isExternalLink(url)) {
      window.open(url, '_blank');
    } else {
      router.push(url);
    }
  };
</script>
