<template>
  <el-dropdown @command="handleChangeThemeMode">
    <div
      class="h-8 w-8 rounded-md duration-300 hover:bg-primary-900 cursor-pointer"
    >
      <div
        class="w-full h-full flex justify-center items-center"
        ref="themeSwitchRef"
      >
        <el-icon>
          <i :class="`iconfont ${currentTheme?.icon}`"></i>
        </el-icon>
      </div>
    </div>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item
          :command="item.mode"
          v-for="item in themes"
          :key="item.mode"
        >
          <div :class="{ 'active-dropdown-item': themeMode === item.mode }">
            <el-icon>
              <i :class="`iconfont ${item.icon}`"></i>
            </el-icon>
            <span>{{ item.label }}</span>
          </div>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>
<script setup>
  import { doWithTransition } from '@/utils/common';
  import { ref, computed } from 'vue';
  import { useLocalStorage } from '@vueuse/core';
  import { useColorMode } from '@vueuse/core';
  const themeSwitchRef = ref(null);

  const mode = useColorMode();
  const themeMode = useLocalStorage('theme-mode', 'ligth');

  const themes = [
    {
      mode: 'light',
      icon: 'lx-taiyang',
      label: '亮色'
    },
    {
      mode: 'dark',
      icon: 'lx-moon_line',
      label: '暗色'
    },
    {
      mode: 'auto',
      icon: 'lx-gensuixitong',
      label: '自动'
    }
  ];

  const currentTheme = computed(() =>
    themes.find((item) => item.mode === themeMode.value)
  );

  /**
   * 切换主题
   * @param {'light' | 'dark' | 'auto'} theme
   */
  function handleChangeThemeMode(theme) {
    themeMode.value = theme;
    if (theme === 'auto') {
      mode.value = theme;
      return;
    }
    doWithTransition(() => {
      mode.value = theme;
    }, themeSwitchRef.value);
  }
</script>

<style lang="scss" scoped>
  .active-dropdown-item {
    color: var(--el-dropdown-menuItem-hover-color);
  }
</style>
