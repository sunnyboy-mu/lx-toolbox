<template>
  <div class="flex-1 p-4 article-info" v-loading="loading">
    <GroupSidebar v-if="groups.length" class="sidebar" :data="groups" />
    <div class="article-content p-2" v-if="articleInfo">
      <h1 class="text-3xl font-bold pt-2 pb-6">{{ articleInfo.title }}</h1>
      <div
        class="pb-2 mb-4 border-b border-gray-200 flex items-center justify-between text-xs text-gray-500"
      >
        <div class="flex items-center gap-2">
          <span
            class="bg-gray-100 py-0.5 px-1 rounded-md cursor-pointer duration-300 hover:bg-primary/40 hover:text-white"
            v-for="tag in JSON.parse(articleInfo.tags)"
            :key="tag"
            >{{ tag }}</span
          >
        </div>
        <div class="flex items-center">
          <el-icon class="mr-2"><Calendar /></el-icon>
          <span>{{ articleInfo.createdAt }}</span>
        </div>
      </div>
      <div v-html="articleInfo.renderText" class="markdown-body"></div>
    </div>
    <CatalogAside
      v-if="catalog.length"
      class="catalog-aside"
      :catalog="catalog"
      :active="activeTitle"
    />
  </div>
</template>
<script setup>
  import '@/components/ByteMdEditor/bytemd-theme-styles/index.css';
  import '@/components/ByteMdEditor/bytemd-theme-styles/github-markdown.css';
  import { detailArticle } from '@/api/blog/info';
  import { useBlogStore } from '@/store/module/blog';
  import { ref, watch, onMounted, onBeforeUnmount, nextTick } from 'vue';
  import { useRoute } from 'vue-router';
  import GroupSidebar from './components/GroupSidebar.vue';
  import CatalogAside from './components/CatalogAside.vue';
  import { Calendar } from '@element-plus/icons-vue';
  import { debounce } from '@/utils/common';

  const route = useRoute();

  const loading = ref(false);

  const blogStore = useBlogStore();

  const groups = ref([]);

  const catalog = ref([]);

  const articleInfo = ref(null);

  const activeTitle = ref('');

  let titles = [];

  const loadArticleInfo = async () => {
    loading.value = true;
    const id = route.params.id;
    const data = await detailArticle(id);
    const { mainUrl } = data;
    mainUrl && (blogStore.activeMenuUrl = `/fe/blog/${mainUrl}`);
    groups.value = data.groups;
    catalog.value = JSON.parse(data.catalog);
    articleInfo.value = {
      ...data,
      catalog: null,
      groups: null
    };
    loading.value = false;
  };

  watch(() => route.params.id, loadArticleInfo);

  const scrollHandle = debounce(() => {
    const range = [70, 300];
    const rects = titles.map((title) => title.getBoundingClientRect());
    for (let i = 0; i < titles.length; i++) {
      const title = titles[i];
      const rect = rects[i];
      if (rect.top >= range[0] && rect.top <= range[1]) {
        activeTitle.value = title.id;
        break;
      }
      if (i < titles.length - 1) {
        const nextRect = rects[i + 1];
        if (rect.top < range[0] && nextRect.top >= range[1]) {
          activeTitle.value = title.id;
          break;
        }
      }
    }
  }, 500);

  onMounted(async () => {
    await loadArticleInfo();
    nextTick(() => {
      titles = Array.from(
        document
          .querySelector('.markdown-body')
          .querySelectorAll('h1,h2,h3,h4,h5,h6')
      );
      window.addEventListener('scroll', scrollHandle);
      const element = document.getElementById(
        decodeURI(location.hash.slice(1))
      );
      if (element) element.scrollIntoView();
    });
  });

  onBeforeUnmount(() => {
    window.removeEventListener('scroll', scrollHandle);
  });

  // watch(activeTitle, () => {
  //   history.replaceState(null, null, `#${activeTitle.value}`);
  // });
</script>

<style lang="scss" scoped>
  .article-info {
    .sidebar {
      width: 320px;
      position: fixed;
      top: 56px;
      left: 0;
      bottom: 0;
    }
  }

  .article-content {
    margin: 0 340px 0 340px;
  }

  .catalog-aside {
    width: 320px;
    position: fixed;
    top: 56px;
    right: 0;
    bottom: 0;
  }
</style>
