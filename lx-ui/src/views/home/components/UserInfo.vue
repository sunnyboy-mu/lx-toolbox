<template>
  <div class="h-screen bg-black/30">
    <!-- 背景 -->
    <img
      src="https://upyun-oss.mu00.cn/2025/03/28//1743153007697.png"
      class="h-full object-cover block w-full"
    />
    <!-- 蒙层 -->
    <div
      class="absolute top-0 left-0 w-full h-full bg-black/30 flex justify-center items-center"
    >
      <div
        class="animate__animated animate__fadeInUp flex flex-col justify-center items-center px-4"
      >
        <!-- 头像 -->
        <img
          src="https://upyun-oss.mu00.cn/2025/03/28//1743153285926.jpg"
          alt="沐沐物语"
          class="w-32 h-32 rounded-full border-6 border-white duration-300 hover:scale-130 mb-8"
        />

        <!-- 标题 -->
        <h2 class="text-4xl font-bold mb-8"> Moe-Cryo </h2>

        <!-- 描述 -->

        <p class="text-xl text-gray-300 font-zwzt text-center">
          风吹樱花落，人与樱花错；相逢本无意，花落人终散。
        </p>

        <!-- 一言 -->
        <div class="text-center mt-20 min-h-16">
          <div
            v-if="!!hitokotoData.hitokoto"
            class="animate__animated animate__fadeInUp"
          >
            <p class="text-xl">{{ hitokotoData.hitokoto }}</p>
            <p class="mt-2 text-gray-300">
              -「
              <strong>{{ hitokotoData.from }}</strong>
              」
            </p>
          </div>
        </div>

        <!-- 功能 -->
        <div class="mt-8">
          <router-link
            class="border-2 border-gray-300 px-4 py-2 rounded-full mr-4 font-bold duration-300 text-gray-300 hover:border-[var(--el-color-primary)] hover:bg-black/20 hover:text-[var(--el-color-primary)] btn-link"
            to="/fe/image-bed"
          >
            Get Start
          </router-link>
        </div>

        <!-- docker -->

        <div class="flex justify-center items-center gap-4 mt-16 flex-wrap">
          <a
            v-for="(v, i) in dockerData"
            :key="i"
            class="bg-white/20 border-2 border-white/20 w-12 h-12 cursor-pointer p-2 rounded-xl flex justify-center items-center duration-300 hover:scale-120 hover:border-[var(--el-color-primary)] hover-shadow-primary hover:bg-white/10 group"
            :href="v.link"
            target="_blank"
          >
            <i
              class="iconfont !text-2xl text-gray-300 duration-300 group-hover:text-[var(--el-color-primary)]"
              :class="v.icon"
            ></i>
          </a>
        </div>
      </div>

      <!-- 下滑提示箭头 -->
      <div
        class="absolute bottom-4 w-full flex justify-center items-center animate__animated animate__backInUp scroll-tip"
      >
        <i
          class="iconfont icon-arrow-down !text-2xl text-gray-200 cursor-pointer"
          @click="goNextScroll"
        ></i
      ></div>
    </div>
  </div>
</template>

<script setup>
  import { hitokoto } from '@/api/home';
  import { ref } from 'vue';
  const emit = defineEmits(['scroll']);

  const hitokotoData = ref({
    hitokoto: '',
    from: ''
  });

  const dockerData = ref([
    {
      link: 'https://blog.mu00.cn',
      icon: 'icon-blogger'
    },
    {
      link: 'https://chat.mu00.cn',
      icon: 'icon-openai-fill'
    },
    {
      link: 'https://web.mu00.cn:1314',
      icon: 'icon-a-1panel'
    },
    {
      link: 'https://gitee.com/SunnyBoy_mu',
      icon: 'icon-gitee'
    },
    {
      link: 'https://github.com/sunnyboy-mu',
      icon: 'icon-GitHub'
    }
  ]);

  hitokoto().then((res) => {
    hitokotoData.value = res;
  });

  const goNextScroll = () => {
    emit('scroll');
  };
</script>

<style scoped lang="scss">
  .btn-link {
    &:hover {
      box-shadow: 0 0 35px var(--el-color-primary);
      letter-spacing: 4px;
    }
  }

  .scroll-tip {
    animation: scroll-tip-animation 3s infinite;
  }

  @keyframes scroll-tip-animation {
    0% {
      transform: translateY(0);
      opacity: 1;
    }
    50% {
      transform: translateY(8px);
      opacity: 0.5;
    }
    100% {
      transform: translateY(0);
      opacity: 1;
    }
  }
</style>
