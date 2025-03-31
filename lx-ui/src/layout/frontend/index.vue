<template>
  <div class="h-screen flex flex-col pt-14">
    <Header />
    <router-view></router-view>
  </div>
</template>

<script setup>
  import Header from './components/Header.vue';
</script>
