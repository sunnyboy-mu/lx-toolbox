<template>
  <div class="min-h-screen flex flex-col">
    <Header :menu-data="frontMenu" />
    <router-view></router-view>
  </div>
</template>

<script setup>
  import Header from '../components/Header/index.vue';
  import { useUserStore } from '@/store/module/user';
  import { storeToRefs } from 'pinia';

  const { frontMenu } = storeToRefs(useUserStore());
</script>
