<template>
  <div
    class="banner h-screen flex flex-col items-center justify-center relative bg-cover bg-black/40"
    :style="{ 'background-image': `url(https://api.星.fun/AcgnPic?id=9995)` }"
  >
    <!-- 标题 -->
    <h2
      class="text-4xl text-gray-100 font-bold mb-8 font-pacifico animate__animated animate__fadeInUp"
    >
      Moe-Cryo
    </h2>

    <!-- 描述 -->

    <!-- <p
      class="text-xl text-gray-100 font-zwzt text-center font-ma-shan-zheng animate__animated animate__fadeInUp"
    >
      风吹樱花落，人与樱花错；相逢本无意，花落人终散。
    </p> -->

    <!-- 一言 -->
    <div class="text-center mt-20 min-h-16">
      <div
        v-if="!!hitokotoData.hitokoto"
        class="animate__animated animate__fadeInUp"
      >
        <p class="text-2xl text-gray-100">{{ hitokotoData.hitokoto }}</p>
        <p class="mt-2 text-gray-200">
          -「
          <strong>{{ hitokotoData.from }}</strong>
          」
        </p>
      </div>
    </div>

    <!-- svg 水波 -->
    <section class="absolute w-full left-0 -bottom-[5px]">
      <svg
        class="waves-svg h-12 w-full"
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 24 150 28"
        preserveAspectRatio="none"
        shape-rendering="auto"
      >
        <defs>
          <path
            id="gentle-wave"
            d="M -160 44 c 30 0 58 -18 88 -18 s 58 18 88 18 s 58 -18 88 -18 s 58 18 88 18 v 44 h -352 Z"
          ></path>
        </defs>
        <g class="parallax">
          <use xlink:href="#gentle-wave" x="48" y="0"></use>
          <use xlink:href="#gentle-wave" x="48" y="3"></use>
          <use xlink:href="#gentle-wave" x="48" y="5"></use>
          <use xlink:href="#gentle-wave" x="48" y="7"></use>
        </g>
      </svg>
    </section>
  </div>
</template>
<script setup>
  import { ref } from 'vue';
  import { hitokoto } from '@/api/home';
  const hitokotoData = ref({
    hitokoto: '',
    from: ''
  });

  hitokoto().then((res) => {
    hitokotoData.value = res;
  });
</script>
<style lang="scss" scoped>
  .banner {
    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAKUlEQVQImU3IMREAIAgAwJfNkQCEsH8cijjpMf6vnXlQaIiJFx+omEBfmqIEZLe2jzcAAAAASUVORK5CYII=);
    }

    .waves-svg {
      .parallax {
        & > use {
          animation: move-forever 25s cubic-bezier(0.55, 0.5, 0.45, 0.5)
            infinite;
          &:nth-child(1) {
            animation-delay: -2s;
            animation-duration: 7s;
            fill: rgba(255, 255, 255, 0.6);
            opacity: 0.9;
          }
          &:nth-child(2) {
            animation-delay: -3s;
            animation-duration: 10s;
            fill: rgba(255, 255, 255, 0.85);
          }
          &:nth-child(3) {
            animation-delay: -4s;
            animation-duration: 13s;
            fill: rgba(255, 255, 255, 0.85);
          }
          &:nth-child(4) {
            animation-delay: -5s;
            animation-duration: 20s;
            fill: rgba(255, 255, 255, 0.6);
          }
        }
      }
    }
  }

  @keyframes move-forever {
    0% {
      transform: translate3d(-90px, 0, 0);
    }

    to {
      transform: translate3d(85px, 0, 0);
    }
  }
</style>
