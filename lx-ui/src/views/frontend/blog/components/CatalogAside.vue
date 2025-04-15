<template>
  <div class="p-2">
    <el-scrollbar>
      <h3 class="font-bold mb-2">目录</h3>
      <a
        :style="{ paddingLeft: `${(item.level - 1) * 10}px` }"
        class="text-sm text-gray-400 duration-300 hover:text-primary block py-1 truncate"
        :class="{ 'text-primary': highlight === item.title }"
        v-for="(item, index) in catalog"
        :key="index"
        :href="`#${item.title}`"
        @click="highlight = item.title"
      >
        {{ item.title }}
      </a>
    </el-scrollbar>
  </div>
</template>
<script setup>
  import { ref, watch } from 'vue';
  const { catalog, active } = defineProps({
    catalog: Array,
    active: String
  });

  const highlight = ref('');

  watch(
    () => active,
    (val) => {
      highlight.value = val;
    }
  );
</script>
