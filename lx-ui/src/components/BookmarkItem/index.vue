<template>
  <a
    :href="url ?? 'javascript:void(0)'"
    :target="url ? '_blank' : '_self'"
    class="min-h-26 bg-gray-100 flex flex-col p-2 rounded-md duration-300 border border-gray-100 hover:bg-white hover:border-primary hover:shadow-md"
  >
    <div class="flex items-center mb-2">
      <div class="p-2 bg-gray-200 rounded-md shrink-0 mr-2">
        <el-image
          v-if="icon"
          class="w-6 h-6 !block rounded-md"
          lazy
          :src="icon"
        >
          <template #placeholder>
            <img src="/loading.webp" class="w-6 h-6 !block rounded-md" />
          </template>
          <template #error>
            <div
              class="w-6 h-6 text-center text-white font-bold rounded-md"
              :style="`background-color: ${randomHexColor()}`"
            >
              {{ title?.slice(0, 1) ?? '沐' }}
            </div>
          </template>
        </el-image>
        <div
          v-else
          class="w-6 h-6 bg-primary text-center text-white font-bold rounded-md"
        >
          {{ title?.slice(0, 1) ?? '沐' }}
        </div>
      </div>
      <div class="text-base font-bold truncate flex-1 text-gray-800">
        {{ title }}
      </div>
    </div>
    <el-text
      v-if="description"
      class="!text-gray-500 !self-start"
      :line-clamp="2"
      size="small"
    >
      {{ description }}
    </el-text>
  </a>
</template>
<script setup>
  import { randomHexColor } from '@/utils/common';

  const { title, url, icon, description } = defineProps({
    title: String,
    url: String,
    icon: String,
    description: String
  });
</script>
