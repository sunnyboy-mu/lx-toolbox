<template>
  <div class="layout min-h-screen flex flex-col">
    <Header />
    <div class="flex-1 bg-background flex">
      <router-view></router-view>
    </div>
  </div>
</template>
<script setup>
  import Header from './components/Header.vue';
</script>
