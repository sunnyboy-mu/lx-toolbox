import { watch } from 'vue';
import { useMediaQuery } from '@vueuse/core';

/**
 * 获取是否是移动端小屏幕hook
 * @param onChange 值改变回调
 */
export function useMobile(onChange) {
  const mobile = useMediaQuery('(max-width: 768px)');

  watch(mobile, (isMobile) => {
    onChange && onChange(isMobile);
  });

  return { mobile };
}

/**
 * 获取是否是移动端触摸设备hook
 * @param onChange 值改变回调
 */
export function useMobileDevice(onChange) {
  const mobileDevice = useMediaQuery('(pointer: coarse)');

  watch(mobileDevice, (isMobile) => {
    onChange && onChange(isMobile);
  });

  return { mobileDevice };
}
