<template>
  <div class="home">
    <div class="home-item bg-black/30">
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
          <div class="text-center mt-16">
            <p class="text-xl">{{ hitokotoData.hitokoto }}</p>
            <p class="mt-2 text-gray-300">
              -「
              <strong>{{ hitokotoData.from }}</strong>
              」
            </p>
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
      </div>
    </div>
    <div class="home-item">
      <Weather />
    </div>
    <!-- <div class="home-item bg-amber-300"></div>
    <div class="home-item bg-blue-300"></div>
    <div class="home-item bg-amber-300"></div> -->
  </div>
</template>

<script setup>
  import { hitokoto } from '@/api/home';
  import { ref } from 'vue';
  import Weather from './Weather.vue';

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
</script>

<style scoped lang="scss">
  .home {
    height: 100vh;
    overflow-y: scroll;
    scroll-snap-type: y mandatory;
    scroll-behavior: smooth;
    color: #fff;
    &::-webkit-scrollbar {
      width: 0;
    }
    .home-item {
      scroll-snap-align: start;
      height: 100vh;
      position: relative;
    }
    .btn-link {
      &:hover {
        box-shadow: 0 0 35px var(--el-color-primary);
        letter-spacing: 4px;
      }
    }
  }
</style>
