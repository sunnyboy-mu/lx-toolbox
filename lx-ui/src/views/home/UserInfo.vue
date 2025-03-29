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
        class="animate__animated animate__fadeInUp flex flex-col justify-center items-center"
      >
        <!-- 头像 -->
        <img
          src="https://upyun-oss.mu00.cn/2025/03/28//1743153285926.jpg"
          alt="沐沐物语"
          class="w-32 h-32 rounded-full border-6 border-white duration-300 hover:scale-110 hover:rotate-360 mb-8"
        />

        <!-- 标题 -->
        <h2 class="text-4xl font-bold mb-4"> 沐沐物语 </h2>

        <!-- 描述 -->

        <p class="text-xl text-gray-300 font-zwzt">
          予万物以理解，涵万事而有容
        </p>

        <!-- 一言 -->
        <div class="text-center mt-16 min-h-16 px-2">
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
            class="border-2 border-white px-4 py-2 rounded-full mr-4 font-bold duration-300 hover:border-[var(--el-color-primary)] hover:bg-black/20 hover:text-[var(--el-color-primary)] btn-link"
            to="/image-bed"
          >
            Mu Bed Image
          </router-link>
        </div>

        <!-- docker -->

        <div class="flex justify-center items-center gap-4 mt-16 flex-wrap">
          <a
            v-for="(v, i) in dockerData"
            :key="i"
            class="bg-white/20 border-2 border-white/20 w-12 h-12 cursor-pointer p-2 rounded-xl duration-300 hover:scale-120 hover:border-[var(--el-color-primary)] hover-shadow-primary"
            :href="v.link"
            target="_blank"
          >
            <img :src="v.icon" class="w-full h-full object-cover block" />
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
      link: 'https://chat.mu00.cn',
      icon: 'https://upyun-oss.mu00.cn/2025/03/28//1743150784848.png'
    },
    {
      link: 'https://web.mu00.cn:1314',
      icon: 'https://upyun-oss.mu00.cn/2025/03/29//1743214885456.png'
    },
    {
      link: 'https://gitee.com/SunnyBoy_mu',
      icon: 'https://upyun-oss.mu00.cn/2025/03/29//1743214888267.png'
    },
    {
      link: 'https://github.com/sunnyboy-mu',
      icon: 'https://upyun-oss.mu00.cn/2025/03/29//1743214890448.png'
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
