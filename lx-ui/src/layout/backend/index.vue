<template>
  <div class="h-screen flex flex-col">
    <Header />
    <router-view></router-view>
  </div>
</template>

<script setup>
  import Header from '@/components/Header/index.vue';
</script>
