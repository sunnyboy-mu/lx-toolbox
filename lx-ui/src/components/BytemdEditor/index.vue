<template>
  <div ref="editorRef" class="lx-bytemd-wrap"></div>
</template>

<script setup>
  import { ref, onMounted, watch } from 'vue';
  import { Editor, getProcessor } from 'bytemd';
  import zh_Hans from 'bytemd/locales/zh_Hans.json';
  import 'bytemd/dist/index.min.css';
  import plugins from './plugins';
  import '@/styles/byte-md-theme/index.css';
  import { uploadBlogInfoImage } from '@/api/blog/info';

  defineOptions({ name: 'ByteMdEditor' });

  const HEAD_TAG_NAME = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];

  const props = defineProps({
    /** 编辑器配置 */
    config: Object,
    /** 高度 */
    height: {
      type: String,
      default: '100%'
    },
    /** 全屏时的层级 */
    fullIndex: {
      type: Number,
      default: 999
    },
    placeholder: {
      type: String,
      default: '请输入内容'
    }
  });

  const emit = defineEmits(['change']);

  const model = defineModel({ type: String, default: '' });

  const editorRef = ref(null);

  let editor = null;

  const handleImageUpload = async (files) => {
    const res = await Promise.all(
      files.map((file) => {
        return uploadBlogInfoImage(file);
      })
    );
    return res.map((file) => {
      return {
        title: file.name,
        url: file.url
      };
    });
  };

  const getHtml = () => {
    return document.querySelector('.bytemd-preview .markdown-body').innerHTML;
  };

  const getCatalog = () => {
    const parser = new DOMParser().parseFromString(getHtml(), 'text/html');
    return Array.from(parser.querySelectorAll('[head-id]'))
      .filter((node) => HEAD_TAG_NAME.includes(node.tagName.toLowerCase()))
      .map((item) => ({
        title: item.getAttribute('head-id'),
        level: Number(item.tagName.toLowerCase()?.replace('h', ''))
      }));
  };

  onMounted(() => {
    window.getCatalog = getCatalog;
    window.getHtml = getHtml;
    const ins = new Editor({
      target: editorRef.value,
      props: {
        value: model.value,
        locale: zh_Hans,
        plugins,
        uploadImages: handleImageUpload,
        placeholder: props.placeholder,
        editorConfig: {
          autofocus: false
        }
      }
    });

    ins.$on('change', (e) => {
      model.value = e.detail.value;
      emit('change', e.detail.value);
    });
    editor = ins;
  });

  watch(
    () => props.config,
    (config) => {
      const option = Object.assign({}, config);
      Object.keys(option).forEach((key) => {
        if (typeof option[key] === 'undefined') {
          delete option[key];
        }
      });
      editor?.$set?.(option);
    },
    { deep: true }
  );

  watch(model, (value) => {
    editor?.$set?.({ value });
  });

  defineExpose({
    getCatalog,
    getHtml
  });
</script>
<style lang="scss" scoped>
  .lx-bytemd-wrap {
    width: 100%;
    line-height: initial;
    height: v-bind(height);
  }

  /* 修改编辑器高度 */
  .lx-bytemd-wrap :deep(.bytemd) {
    height: 100%;
    /* 修改全屏的zIndex */
    &.bytemd-fullscreen {
      z-index: v-bind(fullIndex);
    }

    /* 去掉默认的最大宽度限制 */
    .CodeMirror .CodeMirror-lines {
      max-width: 100%;
    }

    pre.CodeMirror-line,
    pre.CodeMirror-line-like {
      padding: 0 24px;
    }

    .markdown-body {
      max-width: 100%;
      padding: 16px 24px;
    }

    /* 去掉github图标 */
    .bytemd-toolbar-right > .bytemd-toolbar-icon:last-child {
      display: none;
    }
  }
</style>
