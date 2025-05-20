import 'element-plus/global';
import 'ele-admin-plus/typings/global';

declare module 'vue' {
  export interface GlobalComponents {
    Loading: (typeof import('@/components/Loading/index.vue'))['default'];
  }
}

export {};
