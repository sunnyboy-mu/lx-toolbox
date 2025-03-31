<template>
  <div class="backend-layout h-screen">
    <Header />
    <router-view></router-view>
  </div>
</template>
<script setup>
  import Header from './components/Header.vue';
</script>
