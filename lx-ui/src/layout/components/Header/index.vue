<template>
  <div
    class="py-3 top-0 left-0 sticky w-full bg-white flex justify-between items-center px-8 z-50 shadow-md"
  >
    <div class="flex-1 flex items-center overflow-hidden">
      <Logo class="mr-4 shrink-0" />
      <div class="flex-1 overflow-hidden">
        <NavMenu :data="menuData" />
      </div>
    </div>
    <div class="ml-4 shrink-0">
      <UserInfo class="w-full" />
    </div>
  </div>
</template>
<script setup>
  import Logo from '../Logo/index.vue';
  import UserInfo from '../UserInfo/index.vue';
  import NavMenu from '../NavMenu/index.vue';

  const { menuData } = defineProps({
    menuData: Array
  });
</script>
