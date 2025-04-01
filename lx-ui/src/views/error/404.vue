<template>
  <div class="bg-gray-50 min-h-screen flex items-center justify-center p-4">
    <div class="max-w-3xl w-full bg-white rounded-lg overflow-hidden">
      <div class="flex flex-col md:flex-row">
        <!-- 左侧图标区域 -->
        <div
          class="bg-primary-light w-full md:w-1/3 flex items-center justify-center p-8"
        >
          <div class="relative w-40 h-40">
            <div
              class="absolute inset-0 bg-primary rounded-full opacity-20"
            ></div>
            <div class="absolute inset-4 flex items-center justify-center">
              <svg
                class="w-full h-full text-warning"
                viewBox="0 0 24 24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M12 2L2 7L12 12L22 7L12 2Z"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M2 17L12 22L22 17"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M2 12L12 17L22 12"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M12 12V17"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
          </div>
        </div>

        <!-- 右侧内容区域 -->
        <div class="w-full md:w-2/3 p-8">
          <h1 class="text-4xl font-bold text-gray-800 mb-2">404</h1>
          <h2 class="text-2xl font-semibold text-gray-700 mb-4">页面不存在</h2>

          <div class="border-t border-gray-200 my-6"></div>

          <p class="text-gray-600 mb-6">
            抱歉，您访问的页面不存在或已被移除。可能的原因包括：
          </p>

          <ul class="list-disc pl-5 text-gray-600 mb-8 space-y-2">
            <li>输入的URL地址不正确</li>
            <li>页面已被删除或移动</li>
            <li>暂时性的服务不可用</li>
          </ul>

          <div class="flex flex-col sm:flex-row gap-4">
            <router-link
              to="/"
              class="flex items-center justify-center bg-primary hover:bg-primary-dark text-white py-2 px-6 rounded-md text-center transition duration-300 ease-in-out transform hover:scale-105"
            >
              <svg class="w-5 h-5 mr-2" viewBox="0 0 20 20" fill="currentColor">
                <path
                  d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z"
                />
              </svg>
              返回首页
            </router-link>
            <button
              @click="router.back()"
              class="flex items-center justify-center border border-primary text-primary hover:bg-primary-light py-2 px-6 rounded-md text-center transition duration-300 ease-in-out cursor-pointer"
            >
              <svg class="w-5 h-5 mr-2" viewBox="0 0 20 20" fill="currentColor">
                <path
                  fill-rule="evenodd"
                  d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
                  clip-rule="evenodd"
                />
              </svg>
              返回上一页
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { useRouter } from 'vue-router';

  const router = useRouter();
</script>
