<template>
  <el-form
    label-width="100px"
    v-loading="loading"
    element-loading-text="加载中···"
  >
    <template v-for="item in configs" :key="item.configKey">
      <el-alert :title="item.configDesc" type="info" :closable="false" />
      <el-form-item :label="item.configName" class="mt-4">
        <el-input
          clearable
          v-model="item.configValue"
          v-if="item.configType === 'input'"
        />
      </el-form-item>
    </template>

    <el-form-item label-width="0">
      <el-button type="primary" class="mx-auto" @click="save">保 存</el-button>
    </el-form-item>
  </el-form>
</template>
<script setup>
  import { useCommonConfig } from '@/composables/use-common-config';
  import { ElMessage } from 'element-plus/es';

  const props = defineProps({
    group: {
      type: String,
      required: true
    }
  });

  const emit = defineEmits(['done']);

  const { configs, updateConfigs, loading } = useCommonConfig(props.group);

  const save = () => {
    updateConfigs(() => {
      ElMessage.success('保存成功');
      emit('done', configs.value);
    });
  };
</script>
