<template>
  <div class="flex flex-col h-screen p-4 bg-gray-50">
    <ImageUpload @upload-success="addImageToHistory" class="mb-4" />

    <ImageList
      class="flex-1"
      :data="imageHistory"
      @remove="removeImage"
      @clear="clearHistory"
    />
  </div>
</template>

<script setup>
  import { useLocalStorage } from '@vueuse/core';
  import { ElMessage } from 'element-plus/es';
  import ImageUpload from './components/ImageUpload.vue';
  import ImageList from './components/ImageList.vue';

  const imageHistory = useLocalStorage('image-history', []);

  const addImageToHistory = (imageData) => {
    imageHistory.value.unshift(imageData);
    ElMessage.success('图片上传成功');
    if (imageHistory.value.length > 50) {
      imageHistory.value = imageHistory.value.slice(0, 50);
    }
  };

  const removeImage = (index) => {
    imageHistory.value.splice(index, 1);
    ElMessage.success('已删除图片');
  };

  const clearHistory = () => {
    imageHistory.value = [];
    ElMessage.success('已清空历史记录');
  };
</script>
