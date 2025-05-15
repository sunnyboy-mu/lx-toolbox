<template>
  <div
    class="h-[var(--header-height)] top-0 left-0 sticky w-full bg-background px-4 shadow-sm dark:shadow-[#858585]"
  >
    <div class="container mx-auto flex items-center h-full gap-4">
      <div class="flex items-center flex-1 gap-4">
        <Logo class="shrink-0" />
        <Menubar class="flex-1" />
      </div>
      <Tools class="shrink-0" />
    </div>
  </div>
</template>
<script setup>
  import Logo from './Logo.vue';
  import Menubar from './Menubar.vue';
  import Tools from './Tools.vue';
</script>
