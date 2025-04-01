<template>
  <div
    class="py-3 top-0 left-0 sticky w-full bg-white flex justify-between items-center px-8 z-50"
  >
    <div class="flex-1 flex items-center overflow-hidden">
      <Logo class="mr-4 shrink-0" />
      <div class="flex-1 overflow-hidden">
        <NavMenu />
      </div>
    </div>
    <div class="ml-4 shrink-0">
      <UserInfo class="w-full" />
    </div>
  </div>
</template>
<script setup>
  import Logo from '@/components/Logo/index.vue';
  import UserInfo from '@/components/UserInfo/index.vue';
  import NavMenu from '@/components/NavMenu/index.vue';
</script>
