<template>
  <div class="bg-white p-4 rounded-md">
    <div class="flex items-center justify-between">
      <div class="text-sm">
        <i class="iconfont icon-shuqian"></i>
        <span class="ml-2">{{ title }}</span>
      </div>
      <div class="flex items-center gap-2">
        <el-image
          v-if="hihgLevel"
          title="高级分组"
          class="w-5 h-5 cursor-pointer"
          lazy
          src="https://upyun-oss.mu00.cn/2025/04/01//1743475356015.gif"
        >
        </el-image>
        <slot name="expand"> </slot>
      </div>
    </div>
    <div
      class="grid gap-4 mt-3 xl:grid-cols-5 lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 grid-cols-1"
    >
      <slot></slot>
    </div>
  </div>
</template>
<script setup>
  const { title, hihgLevel } = defineProps({
    title: {
      type: String,
      default: '默认·分组'
    },
    hihgLevel: {
      type: Boolean,
      default: false
    }
  });
</script>
