<template>
  <div class="lx-page flex flex-col">
    <el-tabs v-model="activeTabName">
      <el-tab-pane label="博 客" name="BlogConfig" />
    </el-tabs>
    <div class="flex-1"><component :is="activeTabName" /> </div>
  </div>
</template>

<script setup>
  import { ref } from 'vue';
  import BlogConfig from './components/BlogConfig.vue';
  defineOptions({
    components: { BlogConfig }
  });

  const activeTabName = ref('BlogConfig');
</script>
