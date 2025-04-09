<template>
  <el-select
    class="lx-icon-select"
    popper-class="lx-icon-select-popper"
    v-model="model"
    :placeholder="placeholder"
    :clearable="clearable"
    :filterable="filterable"
    fit-input-width
    @change="emit('change', $event)"
  >
    <template #label>
      <i class="lx-iconfont" :class="model"></i>
      <span class="ml-2">{{ model }}</span>
    </template>
    <el-option
      v-for="icon in iconfontData"
      :value="icon"
      :label="icon"
      :key="icon"
    >
      <i class="lx-iconfont" :class="icon"></i>
      <span class="ml-2">{{ icon }}</span>
    </el-option>
  </el-select>
</template>
<script setup>
  import { useIconfont } from '@/composables/use-iconfont';
  const model = defineModel({ type: String });
  const emit = defineEmits(['change']);
  const { placeholder, clearable, filterable } = defineProps({
    placeholder: {
      type: String,
      default: '请选择图标'
    },
    clearable: {
      type: Boolean,
      default: true
    },
    filterable: {
      type: Boolean,
      default: false
    }
  });
  const { iconfontData } = useIconfont();
</script>

<style lang="scss">
  .lx-icon-select {
  }
  .lx-icon-select-popper {
    .el-select-dropdown__list {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
    }
  }
</style>
