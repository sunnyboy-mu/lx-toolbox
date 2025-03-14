<template>
  <div class="bg-white rounded-lg shadow-md p-6 mb-4">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold flex items-center text-gray-700">
        <el-icon class="mr-2"><Upload /></el-icon>
        图片上传
      </h2>
      <Login />
    </div>

    <el-upload
      :show-file-list="false"
      class="upload-area"
      drag
      :auto-upload="false"
      :multiple="true"
      :on-change="handleFileChange"
      action="#"
    >
      <el-icon class="text-5xl text-gray-400 mb-3"><Picture /></el-icon>
      <p class="text-gray-600 mb-2">拖拽图片到此处，或点击上传</p>
      <p class="text-gray-400 text-sm">支持 JPG、PNG、GIF 等常见图片格式</p>
      <p class="text-gray-400 text-sm mt-2">也可以直接粘贴图片 (Ctrl+V)</p>
    </el-upload>
  </div>
</template>

<script setup>
  import { Picture, Upload } from '@element-plus/icons-vue';
  import { onMounted, onUnmounted } from 'vue';
  import Login from './Login.vue';

  const emit = defineEmits(['upload-success']);

  const handleFileChange = (uploadFile) => {
    processFiles([uploadFile.raw]);
  };

  const handlePaste = (event) => {
    const items = (event.clipboardData || event.originalEvent.clipboardData)
      .items;
    const files = [];

    for (const item of items) {
      if (item.type.startsWith('image/')) {
        files.push(item.getAsFile());
      }
    }

    if (files.length > 0) {
      files.forEach((file) => {
        handleFileChange({ raw: file });
      });
    }
  };

  const processFiles = (files) => {
    files.forEach((file) => {
      const reader = new FileReader();
      reader.onload = (e) => {
        emit('upload-success', {
          name: file.name,
          url: e.target.result,
          size: file.size,
          type: file.type,
          date: new Date().toISOString()
        });
      };
      reader.readAsDataURL(file);
    });
  };

  onMounted(() => {
    document.addEventListener('paste', handlePaste);
  });

  onUnmounted(() => {
    document.removeEventListener('paste', handlePaste);
  });
</script>
