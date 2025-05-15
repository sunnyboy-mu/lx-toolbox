<template>
  <div class="bg-gray-50 min-h-screen flex items-center justify-center p-4">
    <div
      class="max-w-3xl w-full bg-white rounded-lg element-shadow overflow-hidden"
    >
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
                class="animate-pulse-slow w-full h-full text-danger"
                viewBox="0 0 24 24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M12 8V12"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M12 16H12.01"
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
          <h1 class="text-4xl font-bold text-gray-800 mb-2">403</h1>
          <h2 class="text-2xl font-semibold text-gray-700 mb-4">无权限访问</h2>

          <div class="border-t border-gray-200 my-6"></div>

          <p class="text-gray-600 mb-6">
            抱歉，您没有权限访问此页面。这可能是由于以下原因：
          </p>

          <ul class="list-disc pl-5 text-gray-600 mb-8 space-y-2">
            <li>您的账户权限不足</li>
            <li>页面需要特殊权限才能访问</li>
            <li>系统管理员限制了您的访问</li>
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
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
