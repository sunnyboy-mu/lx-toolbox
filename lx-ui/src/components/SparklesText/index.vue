<template>
  <div :class="[className, 'sparkles-text']">
    <span class="svg-list">
      <template v-for="sparkle in sparkles" :key="sparkle.id">
        <svg
          class="svg-item pointer-events-none absolute z-20 duration-300"
          :style="{
            left: sparkle.x,
            top: sparkle.y,
            opacity: sparkle.opacity,
            transform: `scale(${sparkle.scale}) rotate(${sparkle.rotation}deg)`
          }"
          width="21"
          height="21"
          viewBox="0 0 21 21"
        >
          <path
            d="M9.82531 0.843845C10.0553 0.215178 10.9446 0.215178 11.1746 0.843845L11.8618 2.72026C12.4006 4.19229 12.3916 6.39157 13.5 7.5C14.6084 8.60843 16.8077 8.59935 18.2797 9.13822L20.1561 9.82534C20.7858 10.0553 20.7858 10.9447 20.1561 11.1747L18.2797 11.8618C16.8077 12.4007 14.6084 12.3916 13.5 13.5C12.3916 14.6084 12.4006 16.8077 11.8618 18.2798L11.1746 20.1562C10.9446 20.7858 10.0553 20.7858 9.82531 20.1562L9.13819 18.2798C8.59932 16.8077 8.60843 14.6084 7.5 13.5C6.39157 12.3916 4.19225 12.4007 2.72023 11.8618L0.843814 11.1747C0.215148 10.9447 0.215148 10.0553 0.843814 9.82534L2.72023 9.13822C4.19225 8.59935 6.39157 8.60843 7.5 7.5C8.60843 6.39157 8.59932 4.19229 9.13819 2.72026L9.82531 0.843845Z"
            :fill="sparkle.color"
          />
        </svg>
      </template>
      <strong>{{ text }}</strong>
    </span>
  </div>
</template>

<script setup>
  import { ref, onMounted, onUnmounted } from 'vue';

  // Props
  const props = defineProps({
    text: String,
    colors: {
      type: Object,
      default: () => ({ first: '#9E7AFF', second: '#FE8BBB' })
    },
    className: String,
    sparklesCount: {
      type: Number,
      default: 10
    }
  });

  // State
  const sparkles = ref([]);

  // Functions
  const generateStar = () => {
    const starX = `${Math.random() * 100}%`;
    const starY = `${Math.random() * 100}%`;
    const color =
      Math.random() > 0.5 ? props.colors.first : props.colors.second;
    const delay = Math.random() * 2;
    const lifespan = Math.random() * 10 + 5;
    const id = `${starX}-${starY}-${Date.now()}`;
    const rotation = Math.random() * 360; // 随机旋转角度
    return {
      id,
      x: starX,
      y: starY,
      color,
      delay,
      lifespan,
      opacity: 1,
      scale: Math.random() * 1 + 0.3,
      rotation
    };
  };

  const initializeStars = () => {
    const newSparkles = Array.from(
      { length: props.sparklesCount },
      generateStar
    );
    sparkles.value = newSparkles;
  };

  const updateStars = () => {
    sparkles.value = sparkles.value.map((star) => {
      if (star.lifespan <= 0) {
        return generateStar();
      } else {
        return {
          ...star,
          lifespan: star.lifespan - 0.8,
          opacity: star.opacity - 0.1,
          rotation: star.rotation + 20 // 每次更新增加旋转角度
        };
      }
    });
  };

  // Lifecycle hooks
  onMounted(() => {
    initializeStars();
    const interval = setInterval(updateStars, 100);
    onUnmounted(() => clearInterval(interval));
  });
</script>

<style scoped>
  .sparkles-text {
  }

  .sparkles-text .svg-list {
    position: relative;
    display: inline-block;
  }

  .sparkles-text .svg-list .svg-item {
    pointer-events: none;
    position: absolute;
    z-index: 20;
    animation-duration: 300ms;
  }
</style>
