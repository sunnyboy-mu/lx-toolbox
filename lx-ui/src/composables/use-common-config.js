import { configByGroup, updatConfigForGroup } from '@/api/config';
import { ref, onMounted } from 'vue';

/**
 * 公共配置 hooks
 * @param {String} group
 * @returns
 */
export const useCommonConfig = (group) => {
  const loading = ref(false);

  const configs = ref([]);

  const updateConfigs = (cb) => {
    loading.value = true;
    updatConfigForGroup(configs.value).then(() => {
      loading.value = false;
      cb && cb();
    });
  };

  onMounted(async () => {
    if (!group) {
      throw new Error('group is required');
    }
    loading.value = true;
    configs.value = await configByGroup(group);
    loading.value = false;
  });

  return { configs, loading, updateConfigs };
};
