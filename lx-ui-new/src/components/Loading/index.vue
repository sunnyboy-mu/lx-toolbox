<!-- 加载组件 -->
<template>
  <div
    v-loading="isCircle && loading"
    :element-loading-text="isCircle ? text : void 0"
    :element-loading-background="isCircle ? background : void 0"
    :element-loading-spinner="isCircle ? spinner : void 0"
    :element-loading-svg-view-box="isCircle ? svgViewBox : void 0"
    :class="['ele-loading', { 'ele-loading-show': loading }]"
  >
    <slot></slot>
    <div
      v-if="!isCircle"
      v-show="loading"
      :style="spinnerStyle"
      :class="[
        'ele-loading-spinner',
        { 'ele-loading-blur': blur },
        { 'ele-loading-small': size === 'small' },
        { 'ele-loading-large': size === 'large' }
      ]"
    >
      <slot name="spinner">
        <div class="ele-loading-dot">
          <i></i>
          <i></i>
          <i></i>
          <i></i>
        </div>
      </slot>
      <div v-if="text" class="ele-loading-text" :style="textStyle">
        {{ text }}
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { computed } from 'vue';
  import { vLoading } from 'element-plus';

  defineOptions({ name: 'Loading' });

  const props = defineProps({
    /** 是否显示动画 */
    loading: Boolean,
    /** 显示在加载图标下方的加载文案 */
    text: String,
    /** 背景遮罩的颜色 */
    background: String,
    /** 自定义加载图标 */
    spinner: String,
    /** 自定义加载图标svg的view-box */
    svgViewBox: String,
    /** 加载器类型 'circle' | 'dot' */
    type: {
      type: String,
      default: 'dot'
    },
    /** 背景模糊效果 */
    blur: Boolean,
    /** 加载器尺寸 'small' | 'default' | 'large' */
    size: String,
    /** 自定义加载器样式 */
    spinnerStyle: Object || String,
    /** 自定义文本样式 */
    textStyle: Object || String
  });

  /** 是否是圆形加载器 */
  const isCircle = computed(() => {
    return props.type === 'circle';
  });
</script>

<!-- <style scoped lang="scss">
  .ele-loading {
    box-sizing: border-box;
  }

  .ele-loading-show {
    position: relative;
  }

  .ele-loading-spinner {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: transparent;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  /* 模糊背景 */
  .ele-loading-blur {
    backdrop-filter: blur(2px);
  }

  /* 文本 */
  .ele-loading-text {
    color: var(--el-color-primary);
    font-size: 16px;
    margin-top: 10px;
    text-align: center;
  }

  /* 圆点风格 */
  .ele-loading-dot {
    width: eleVar('loading', 'size');
    height: eleVar('loading', 'size');
    display: grid;
    grid-gap: eleVar('loading', 'space');
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: repeat(2, 1fr);
    transform: rotate(45deg);
    animation: eleloadingRotate 1.2s infinite linear;

    & > i {
      background: elVar('color-primary');
      border-radius: 50%;

      &:nth-child(2) {
        background: elVar('color-primary', 'light-3');
      }

      &:nth-child(3) {
        background: elVar('color-primary', 'light-7');
      }

      &:nth-child(4) {
        background: elVar('color-primary', 'light-8');
      }
    }
  }

  @keyframes eleloadingRotate {
    to {
      transform: rotate(405deg);
    }
  }

  /* 小型尺寸 */
  .ele-loading-small .ele-loading-dot {
    width: eleVar('loading', 'sm-size');
    height: eleVar('loading', 'sm-size');
    grid-gap: eleVar('loading', 'sm-space');
  }

  /* 大型尺寸 */
  .ele-loading-large .ele-loading-dot {
    width: eleVar('loading', 'lg-size');
    height: eleVar('loading', 'lg-size');
    grid-gap: eleVar('loading', 'lg-space');
  }
</style> -->
