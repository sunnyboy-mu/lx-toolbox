<template>
  <div class="flex-1 p-4 bg-gray-100">
    <Group />
    <div class="flex flex-col gap-4">
      <BookmarkGroup
        v-for="i in 10"
        :key="i"
        title="常用网站"
        :id="i"
        :hihg-level="i % 2 === 0"
      >
        <BookmarkItem
          title="微信小程序文档"
          url="https://developers.weixin.qq.com/miniprogram/dev/framework/"
          icon="https://res.wx.qq.com/a/wx_fed/assets/res/OTE0YTAw.png"
          description="微信小程序官方开发者文档"
          v-for="i in 10"
          :key="i"
        />
      </BookmarkGroup>
    </div>
  </div>
</template>

<script setup>
  import BookmarkGroup from '@/components/BookmarkGroup/index.vue';
  import BookmarkItem from '@/components/BookmarkItem/index.vue';

  import { useRoute } from 'vue-router';
  import { watch, ref } from 'vue';
  import Group from './components/Group.vue';

  const route = useRoute();

  const loadBookmark = ({ category }) => {
    console.log('load bookmark', category);
  };

  watch(
    () => route.params.category,
    () => {
      location.hash = '';
      loadBookmark(route.params);
    },
    {
      immediate: true
    }
  );
</script>
