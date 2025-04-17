<template>
  <div class="w-full h-full">
    <ConfigRender :group="BLOG_GROUP" @done="handleDone" />
  </div>
</template>
<script setup>
  import { BLOG_GROUP, BLOG_ICON } from '@/enum/common-config';
  import ConfigRender from './ConfigRender.vue';
  import { useIconfont } from '@/composables/use-iconfont';
  const { parserIconfontSource } = useIconfont();

  const handleDone = (configs) => {
    const url = configs.find(
      (item) => item.configKey === BLOG_ICON
    )?.configValue;
    parserIconfontSource(url);
  };
</script>
