<template>
  <div class="min-h-screen flex flex-col">
    <Header :menu-data="frontMenu" />
    <router-view></router-view>
    <el-backtop :bottom="100">
      <el-icon> <Coordinate /> </el-icon>
    </el-backtop>
  </div>
</template>

<script setup>
  import Header from '../components/Header/index.vue';
  import { useUserStore } from '@/store/module/user';
  import { Coordinate } from '@element-plus/icons-vue';
  import { storeToRefs } from 'pinia';

  const { frontMenu } = storeToRefs(useUserStore());
</script>
