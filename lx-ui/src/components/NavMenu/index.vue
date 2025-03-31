<template>
  <el-menu
    :default-active="useRoute().path"
    class="lx-admin-menus"
    mode="horizontal"
    @select="handleSelect"
  >
    <el-menu-item index="/">
      <div class="flex items-center gap-1">
        <i class="iconfont icon-home"></i>
        <span>首页</span>
      </div>
    </el-menu-item>
    <el-menu-item index="/fe/image-bed">
      <div class="flex items-center gap-1">
        <i class="iconfont icon-image"></i>
        <span>图床</span>
      </div>
    </el-menu-item>
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
