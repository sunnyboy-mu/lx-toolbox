<template>
  <el-sub-menu :index="menu.path" v-if="menu.children" :key="menu.path">
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

<script setup>
  const { menu } = defineProps({
    menu: {
      type: Object,
      required: true
    }
  });
</script>
