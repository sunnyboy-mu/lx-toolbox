<template>
  <router-link to="/">
    <h1
      class="flex items-center text-xl text-red-600 font-ma-shan-zheng select-none"
    >
      <i class="iconfont lx-kongjian mr-1"></i>
      <SparklesText text="沐沐物语" :sparkles-count="5" />
    </h1>
  </router-link>
</template>
<script setup>
  import SparklesText from '@/components/SparklesText/index.vue';
</script>
