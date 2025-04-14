<template>
  <div ref="editorRef" class="w-full h-full"></div>
</template>

<script setup>
  import zh_Hans from 'bytemd/locales/zh_Hans.json';
  import { ref, onMounted, onBeforeUnmount } from 'vue';
  import { Editor } from 'bytemd';

  const editorRef = ref(null);

  const editorInstance = ref(null);

  onMounted(() => {
    editorInstance.value = new Editor({
      target: editorRef.value, // DOM to render
      props: {
        value: '',
        locale: zh_Hans
      }
    });

    editorInstance.value.$on('change', (e) => {
      editorInstance.value.$set({ value: e.detail.value });
    });

    console.log(editorInstance.value);
  });

  onBeforeUnmount(() => {
    editorInstance.value?.$destroy();
    editorInstance.value = null;
  });
</script>
<style lang="scss" scoped>
  :deep(.bytemd) {
    height: 100%;
  }
</style>
