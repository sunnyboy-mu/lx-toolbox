<template>
  <div class="bg-gray-100">
    <el-scrollbar>
      <div class="py-2 px-8">
        <div
          class="border-t border-gray-200 pb-3 pt-3"
          v-for="group in data"
          :key="group.id"
        >
          <div class="overflow-hidden">
            <div
              @click="toggleGroup(group.id)"
              class="flex items-center justify-between h-8 cursor-pointer text-sm"
            >
              <h2 class="flex items-center truncate font-bold">
                <i
                  class="lx-iconfont mr-2"
                  v-if="group.icon"
                  :class="group.icon"
                >
                </i>
                <span>{{ group.title }}</span>
              </h2>
              <el-icon>
                <ArrowDownBold v-if="expandGroupId.includes(group.id)" />
                <ArrowRightBold v-else />
              </el-icon>
            </div>
            <div
              class="pl-4 text-xs text-gray-500 group-info-content"
              :class="{ collapse: !expandGroupId.includes(group.id) }"
            >
              <router-link
                v-for="item in group.blogInfos"
                :key="item.id"
                class="flex h-8 items-center truncate duration-300 hover:text-primary"
                :class="{
                  'text-primary': route.params.id === item.id
                }"
                :to="`/fe/blog/${item.id}`"
              >
                <i class="lx-iconfont mr-2" v-if="item.icon" :class="item.icon">
                </i>
                <span>{{ item.title }}</span>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </div>
</template>
<script setup>
  import { ArrowRightBold, ArrowDownBold } from '@element-plus/icons-vue';
  import { useRoute } from 'vue-router';
  import { ref, watch } from 'vue';

  const { data } = defineProps({
    data: Array
  });

  const route = useRoute();

  const expandGroupId = ref([]);

  const toggleGroup = (id) => {
    if (expandGroupId.value.includes(id)) {
      expandGroupId.value = expandGroupId.value.filter((v) => v !== id);
    } else {
      expandGroupId.value.push(id);
    }
  };
  watch(
    () => data,
    () => {
      expandGroupId.value = data.map((v) => v.id);
    },
    {
      immediate: true
    }
  );
</script>

<style lang="scss" scoped>
  .group-info-content {
    interpolate-size: allow-keywords;
    transition: all 0.3s ease-in-out;
    height: auto;
  }
  .collapse {
    height: 0;
  }
</style>
