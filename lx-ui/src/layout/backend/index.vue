<template>
  <div class="h-screen flex flex-col overflow-hidden">
    <Header :menu-data="backMenu" />
    <div class="flex-1 bg-gray-100 p-4 flex flex-col overflow-hidden">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
  import Header from '../components/Header/index.vue';
  import { useUserStore } from '@/store/module/user';
  import { storeToRefs } from 'pinia';

  const { backMenu } = storeToRefs(useUserStore());
</script>
