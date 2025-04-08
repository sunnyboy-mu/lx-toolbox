<template>
  <vue-particles
    id="particle-container"
    class="w-screen h-screen fixed left-0 top-0 z[9999] pointer-events-none"
    :options="options"
  ></vue-particles>
</template>
<script setup>
  const options = {
    particles: {
      number: {
        value: 120 // 颗粒数量
      },
      color: {
        value: '#fff' // 颗粒颜色
      },
      shape: {
        type: 'image', // 颗粒类型：图片
        options: {
          image: {
            // 雪花
            // src: 'https://upyun-oss.mu00.cn/2025/04/07//1744021064287.png',
            // 樱花
            src: 'https://upyun-oss.mu00.cn/2025/04/08//1744074652789.png',
            width: 10,
            height: 10,
            replaceColor: false
          }
        }
      },
      size: {
        value: { min: 2, max: 10 },
        animation: {
          startValue: 'random'
        }
      },
      move: {
        enable: true, // 启用移动
        speed: 3, // 移动速度
        direction: 'bottom', // 移动方向：向下
        straight: false // 是否是直线移动
      }
    },
    interactivity: {
      events: {
        resize: true // 监听画布尺寸变化
      }
    }
  };
</script>
<style lang="scss">
  #particle-container {
    canvas {
      display: block;
    }
  }
</style>
