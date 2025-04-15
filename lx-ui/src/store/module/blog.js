import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useBlogStore = defineStore('blog', () => {
  const activeMenuUrl = ref('');

  return { activeMenuUrl };
});
