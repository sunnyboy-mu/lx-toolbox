<template>
  <div class="rounded-md bg-white p-4 mb-4">
    <div class="bg-gray-50 flex items-center flex-wrap gap-x-10 gap-y-4 p-4">
      <a
        :href="`#${group.groupId}`"
        v-for="group in data"
        :key="group.groupId"
        class="text-gray-500 duration-300 hover:text-primary"
      >
        {{ group.groupTitle }}
      </a>
    </div>
  </div>
</template>
<script setup>
  const { data } = defineProps({
    data: Array
  });
</script>
