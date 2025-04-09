<template>
  <div class="bg-white rounded-md text-center p-4">
    <div class="text-2xl font-bold text-cyan-200 h-32 leading-32"
      >我是工作台</div
    >
    <div>
      <IconSelect v-model="icon" />
    </div>
  </div>
</template>
<script setup>
  import IconSelect from '@/components/IconSelect/index.vue';
  import { ref } from 'vue';
  const icon = ref('');
</script>
