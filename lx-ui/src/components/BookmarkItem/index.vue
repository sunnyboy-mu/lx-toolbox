<template>
  <a
    :href="url ?? 'javascript:void(0)'"
    :target="url ? '_blank' : '_self'"
    class="min-h-26 bg-gray-100 flex flex-col p-2 rounded-md duration-300 border border-gray-100 hover:bg-white hover:border-primary hover:shadow-md relative"
  >
    <div class="flex items-center mb-2">
      <div class="p-2 bg-gray-200 rounded-md shrink-0 mr-2">
        <el-image class="w-6 h-6 !block rounded-md" lazy :src="iconUrl">
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
    <div class="absolute top-1 right-1 flex gap-1" v-if="tagList.length">
      <div
        class="text-[10px] bg-gray-200 px-1.5 rounded-full text-gray-400"
        v-for="tag in tagList"
        :key="tag"
      >
        {{ tag }}
      </div>
    </div>
  </a>
</template>
<script setup>
  import { computed } from 'vue';
  import { AUTO_ICON_URL } from '@/config/setting';
  import { randomHexColor } from '@/utils/common';

  const { title, url, icon, description, tags } = defineProps({
    title: String,
    url: String,
    icon: String,
    description: String,
    tags: String
  });

  const tagList = computed(() => {
    return (tags?.split(',') ?? []).filter(Boolean);
  });

  const iconUrl = computed(() => {
    return icon === 'auto' ? AUTO_ICON_URL + url : icon;
  });
</script>
