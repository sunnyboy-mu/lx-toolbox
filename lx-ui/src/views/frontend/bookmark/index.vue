<template>
  <div class="flex-1 p-4 bg-gray-100">
    <Group v-if="groupList.length" :data="groupList" />
    <div class="flex flex-col gap-4">
      <BookmarkGroup
        v-for="group in bookmarkList"
        :key="group.groupId"
        :title="group.groupTitle"
        :id="group.groupId"
        :hihg-level="group.groupStatus === 2"
      >
        <BookmarkItem
          :title="bmInfo.title"
          :url="bmInfo.url"
          :icon="bmInfo.icon"
          :description="bmInfo.description"
          :tags="bmInfo.tags"
          v-for="bmInfo in group.bmInfoList"
          :key="bmInfo.id"
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
  import { listBookmark } from '@/api/bookmark';

  const route = useRoute();

  const bookmarkList = ref([]);

  const groupList = ref([]);

  const loadBookmark = async ({ category }) => {
    const data = await listBookmark(category);
    groupList.value = data;
    bookmarkList.value = data.filter((v) => !!v.bmInfoList?.length);
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
