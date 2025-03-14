<template>
  <div class="flex flex-col h-screen p-4 bg-gray-50">
    <!-- Upload Area -->
    <div class="bg-white rounded-lg shadow-md p-6 mb-4">
      <h2 class="text-xl font-semibold mb-4 flex items-center text-gray-700">
        <el-icon class="mr-2"><Upload /></el-icon>
        图片上传
      </h2>

      <div
        class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center cursor-pointer hover:border-blue-400 transition-colors"
        @dragover.prevent
        @drop.prevent="handleDrop"
        @click="triggerFileInput"
        @paste.prevent="handlePaste"
        :class="{ 'border-blue-400 bg-blue-50': isDragging }"
        @dragenter.prevent="isDragging = true"
        @dragleave.prevent="isDragging = false"
      >
        <input
          type="file"
          ref="fileInput"
          class="hidden"
          accept="image/*"
          @change="handleFileChange"
          multiple
        />

        <el-icon class="text-5xl text-gray-400 mb-3"><Picture /></el-icon>
        <p class="text-gray-600 mb-2">拖拽图片到此处，或点击上传</p>
        <p class="text-gray-400 text-sm">支持 JPG、PNG、GIF 等常见图片格式</p>
        <p class="text-gray-400 text-sm mt-2">也可以直接粘贴图片 (Ctrl+V)</p>
      </div>
    </div>

    <!-- History Records -->
    <div
      class="flex-1 bg-white rounded-lg shadow-md p-6 overflow-hidden flex flex-col"
    >
      <div class="flex justify-between items-center mb-4">
        <h2 class="text-xl font-semibold flex items-center text-gray-700">
          <el-icon class="mr-2"><List /></el-icon>
          历史记录
        </h2>
        <el-button
          type="danger"
          size="small"
          :icon="Delete"
          @click="clearHistory"
          :disabled="imageHistory.length === 0"
        >
          清空记录
        </el-button>
      </div>

      <div class="overflow-y-auto flex-1">
        <el-empty
          v-if="imageHistory.length === 0"
          description="暂无上传记录"
        ></el-empty>

        <div
          v-else
          class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4"
        >
          <el-card
            v-for="(item, index) in imageHistory"
            :key="index"
            class="mb-4 hover:shadow-lg transition-shadow"
            shadow="hover"
          >
            <div class="flex flex-col h-full">
              <div
                class="relative pb-[56.25%] overflow-hidden mb-2 bg-gray-100 rounded"
              >
                <img
                  :src="item.url"
                  class="absolute top-0 left-0 w-full h-full object-contain"
                  :alt="item.name || '图片'"
                />
              </div>

              <div class="flex-1">
                <p
                  class="text-sm text-gray-600 truncate mb-1"
                  :title="item.name"
                >
                  {{ item.name || '未命名图片' }}
                </p>
                <p class="text-xs text-gray-400 mb-2">
                  {{ formatDate(item.date) }}
                </p>

                <div class="flex justify-between mt-2">
                  <el-button
                    type="primary"
                    size="small"
                    :icon="CopyDocument"
                    @click="copyImageUrl(item.url)"
                    text
                  >
                    复制链接
                  </el-button>

                  <el-button
                    type="danger"
                    size="small"
                    :icon="Delete"
                    @click="removeImage(index)"
                    text
                  >
                    删除
                  </el-button>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue';
  import { useLocalStorage } from '@vueuse/core';
  import { ElMessage } from 'element-plus/es';
  import {
    Picture,
    Upload,
    List,
    Delete,
    CopyDocument
  } from '@element-plus/icons-vue';

  // State
  const isDragging = ref(false);
  const fileInput = ref(null);
  const imageHistory = useLocalStorage('image-history', []);

  // Methods
  const triggerFileInput = () => {
    fileInput.value.click();
  };

  const handleFileChange = (event) => {
    const files = event.target.files;
    if (files.length > 0) {
      processFiles(Array.from(files));
    }
    // Reset input to allow selecting the same file again
    event.target.value = '';
  };

  const handleDrop = (event) => {
    isDragging.value = false;
    const files = Array.from(event.dataTransfer.files).filter((file) =>
      file.type.startsWith('image/')
    );

    if (files.length > 0) {
      processFiles(files);
    } else {
      ElMessage.warning('请上传图片文件');
    }
  };

  const handlePaste = (event) => {
    const items = (event.clipboardData || event.originalEvent.clipboardData)
      .items;
    const files = [];

    for (const item of items) {
      if (item.type.startsWith('image/')) {
        const file = item.getAsFile();
        files.push(file);
      }
    }

    if (files.length > 0) {
      processFiles(files);
    }
  };

  const processFiles = (files) => {
    for (const file of files) {
      const reader = new FileReader();

      reader.onload = (e) => {
        addImageToHistory({
          name: file.name,
          url: e.target.result,
          size: file.size,
          type: file.type,
          date: new Date().toISOString()
        });

        ElMessage.success(`图片 ${file.name} 上传成功`);
      };

      reader.readAsDataURL(file);
    }
  };

  const addImageToHistory = (imageData) => {
    // Add to the beginning of the array (newest first)
    imageHistory.value.unshift(imageData);

    // Limit to 50 entries
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

  const copyImageUrl = (url) => {
    navigator.clipboard
      .writeText(url)
      .then(() => {
        ElMessage.success('图片链接已复制到剪贴板');
      })
      .catch(() => {
        ElMessage.error('复制失败，请手动复制');
      });
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  };
</script>
