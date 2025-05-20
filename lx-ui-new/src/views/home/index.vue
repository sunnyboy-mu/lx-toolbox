<template>
  <div class="home w-full" ref="homeRef">
    <Banner />
    <Weather />
    <SpecialEffects />
  </div>
</template>

<script setup>
  import { ref } from 'vue';
  import Banner from './components/Banner.vue';
  import Weather from './components/Weather.vue';
  import SpecialEffects from './components/SpecialEffects.vue';

  const homeRef = ref(null);

  const handleScroll = () => {
    homeRef.value.scrollTop += window.innerHeight;
  };
</script>

<style scoped lang="scss">
  .home {
    // height: 100vh;
    // overflow-y: scroll;
    // scroll-snap-type: y mandatory;
    // scroll-behavior: smooth;
    // color: #fff;
    // background-color: #0a0d1e;
    // font-family: 'ZWZT';
    // &::-webkit-scrollbar {
    //   width: 0;
    // }
    // .home-item {
    //   scroll-snap-align: start;
    //   height: 100vh;
    //   position: relative;
    // }
    .btn-link {
      &:hover {
        box-shadow: 0 0 35px var(--el-color-primary);
        letter-spacing: 4px;
      }
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
