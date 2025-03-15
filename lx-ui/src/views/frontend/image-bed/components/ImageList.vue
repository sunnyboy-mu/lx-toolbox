<template>
  <div class="bg-white rounded-lg shadow-md p-6 overflow-hidden flex flex-col">
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
        :disabled="data.length === 0"
      >
        清空记录
      </el-button>
    </div>

    <div class="overflow-y-auto flex-1">
      <el-table :data="data" stripe class="w-full" empty-text="暂无上传记录">
        <el-table-column label="缩略图" width="120">
          <template #default="{ row }">
            <div class="h-20 max-w-2xs bg-gray-100 rounded overflow-hidden">
              <el-image
                :src="row.url"
                class="w-full h-full object-contain"
                :alt="row.name || '图片'"
                :preview-src-list="[row.url]"
                preview-teleported
                hide-on-click-modal
                fit="cover"
              />
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="name" label="文件名" min-width="180">
          <template #default="{ row }">
            <span class="truncate">{{ row.name || '未命名图片' }}</span>
          </template>
        </el-table-column>

        <el-table-column label="URL" min-width="240">
          <template #default="{ row }">
            <el-text class="w-full" truncated>
              {{ row.url }}
            </el-text>
          </template>
        </el-table-column>

        <el-table-column prop="size" label="大小" width="100">
          <template #default="{ row }">
            {{ formatFileSize(row.size) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="160">
          <template #default="{ $index, row }">
            <div class="flex gap-2">
              <el-button
                type="primary"
                size="small"
                :icon="CopyDocument"
                @click="copyImageUrl(data[$index].url)"
                link
              >
                复制
              </el-button>
              <el-button
                type="danger"
                size="small"
                :icon="Delete"
                @click="removeImage($index, row)"
                link
              >
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
  import { formatFileSize } from '@/utils/file-utils';
  import { List, Delete, CopyDocument } from '@element-plus/icons-vue';

  const { data } = defineProps({
    data: {
      type: Array,
      default: () => []
    }
  });

  const emit = defineEmits(['remove', 'clear']);

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

  const clearHistory = () => {
    emit('clear');
  };

  const removeImage = (i, row) => {
    emit('remove', i, row);
  };
</script>
