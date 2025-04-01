<template>
  <div class="rounded-md bg-white p-4 mb-4">
    <div class="bg-gray-50 flex items-center flex-wrap gap-x-10 gap-y-4 p-4">
      <a
        href="#8"
        v-for="i in 20"
        :key="i"
        class="text-gray-500 duration-300 hover:text-primary"
      >
        常用·网站
      </a>
    </div>
  </div>
</template>
<script setup></script>
