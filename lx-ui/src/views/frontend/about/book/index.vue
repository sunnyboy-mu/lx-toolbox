<template>
  <div class="container mx-auto bg-gray-100 p-4">
    <div class="rounded-md p-2" v-for="group in books" :key="group.year">
      <h2
        class="text-3xl font-bold my-2 font-zwzt flex items-center justify-center select-none"
      >
        <i class="iconfont icon-shudan mr-2"></i>
        <span>{{ group.year }}年度</span>
      </h2>
      <div class="grid grid-cols-3 gap-4">
        <div
          class="bg-white shadow-md rounded-md p-4 duration-300 h-80 flex flex-col overflow-hidden hover:shadow-lg hover:-translate-y-2"
          v-for="book in group.books"
          :key="book.id"
        >
          <div class="flex"></div>
          <el-text tag="div" line-clamp="3">{{ book.description }}</el-text>
          <!-- {{ book }} -->
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
  import { ref } from 'vue';
  import { listBookGroupByYear } from '@/api/book';

  const books = ref([]);

  const loading = ref(true);

  listBookGroupByYear().then((data) => {
    loading.value = true;
    books.value = data;
    loading.value = false;
  });
</script>
