<template>
  <el-menu
    :default-active="useRoute().path"
    class="lx-admin-menus"
    mode="horizontal"
    @select="handleSelect"
  >
    <template v-for="menu in data" :key="menu.path">
      <el-sub-menu :index="menu.path" v-if="menu.children">
        <template #title>
          <div class="flex items-center gap-1">
            <i class="iconfont" :class="menu.icon"></i>
            <span>{{ menu.title }}</span>
          </div>
        </template>
        <el-menu-item
          :index="child.path"
          v-for="child in menu.children"
          :key="child.path"
        >
          <div class="flex items-center gap-1">
            <i class="iconfont" :class="child.icon"></i>
            <span>{{ child.title }}</span>
          </div>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item :index="menu.path" v-else>
        <div class="flex items-center gap-1">
          <i class="iconfont" :class="menu.icon"></i>
          <span>{{ menu.title }}</span>
        </div>
      </el-menu-item>
    </template>
  </el-menu>
</template>

<script setup>
  import { isExternalLink } from '@/utils/common';
  import { useRoute, useRouter } from 'vue-router';

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
