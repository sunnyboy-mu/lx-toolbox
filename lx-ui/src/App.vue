<script setup>
  import { useUserStore } from './store/module/user';
  useUserStore().loadUserInfo();
</script>

<template>
  <router-view></router-view>
</template>

<style scoped></style>
