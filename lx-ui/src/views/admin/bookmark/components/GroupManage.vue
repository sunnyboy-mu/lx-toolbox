<template>
  <el-drawer
    v-model="visible"
    direction="ltr"
    append-to-body
    :close-on-press-escape="false"
    :size="360"
    body-class="!p-2"
    header-class="!mb-2"
    @close="emit('done')"
    destroy-on-close
  >
    <template #header>
      <div class="text-sm flex items-center">
        <span class="mr-2">分组管理</span>
        <el-tooltip effect="light" placement="right-start">
          <template #content>
            <div class="max-w-52 text-gray-500">
              <p>点击标题前的图标可以切换分组状态</p>
              <p>支持拖拽排序哦ヾ(≧▽≦*)o</p>
              <div class="flex flex-col">
                <div class="flex items-center gap-2">
                  <el-icon
                    class="cursor-pointer"
                    color="var(--el-color-primary)"
                  >
                    <Star />
                  </el-icon>
                  <span>:</span>
                  <span>正常</span>
                </div>
                <div class="flex items-center gap-2">
                  <el-icon
                    class="cursor-pointer"
                    color="var(--el-color-danger)"
                  >
                    <CircleClose />
                  </el-icon>
                  <span>:</span>
                  <span>隐藏</span>
                </div>
                <div class="flex items-center gap-2">
                  <el-icon
                    class="cursor-pointer"
                    color="var(--el-color-success)"
                  >
                    <StarFilled />
                  </el-icon>
                  <span>:</span>
                  <span>仅管理员可见</span>
                </div>
              </div>
            </div>
          </template>
          <el-icon class="cursor-help"><QuestionFilled /></el-icon>
        </el-tooltip>
      </div>
    </template>
    <el-scrollbar class="h-full">
      <Draggable
        ghost-class="ghost-group-item"
        :animation="200"
        class="grid grid-cols-1 gap-2"
        v-model="groupList"
        group="groupList"
        @end="handleSortEnd"
        item-key="id"
      >
        <template #item="{ element: item, index: i }">
          <div
            class="bg-gray-100 rounded-md p-2 h-10 flex items-center text-gray-600 group"
            :key="item.id"
          >
            <div class="truncate flex-1 text-xs">
              <el-input
                size="small"
                v-model="item.title"
                v-if="item.isEdit"
                maxlength="20"
                placeholder="请输入分组名称"
                @keydown.enter="handleSubmit(item, i)"
              />
              <div v-else class="flex items-center">
                <el-icon
                  class="cursor-pointer"
                  color="var(--el-color-primary)"
                  v-if="item.status === 0"
                  @click="handleStatusChange(item, 2)"
                >
                  <Star />
                </el-icon>
                <el-icon
                  class="cursor-pointer"
                  color="var(--el-color-danger)"
                  v-if="item.status === 1"
                  @click="handleStatusChange(item, 0)"
                >
                  <CircleClose />
                </el-icon>
                <el-icon
                  class="cursor-pointer"
                  color="var(--el-color-success)"
                  v-if="item.status === 2"
                  @click="handleStatusChange(item, 1)"
                >
                  <StarFilled />
                </el-icon>
                <span class="ml-1">{{ item.title }}</span>
              </div>
            </div>
            <div class="shrink-0 w-16 ml-2">
              <div class="hidden group-hover:block" v-if="item.status !== 1">
                <template v-if="!item.isEdit">
                  <el-button
                    type="primary"
                    :icon="Edit"
                    circle
                    size="small"
                    @click="item.isEdit = true"
                    :disabled="addBtnDisabled"
                  />
                  <el-popconfirm
                    title="是否确定删除该分组？"
                    placement="left"
                    confirm-button-text="是"
                    cancel-button-text="否"
                    width="120"
                    confirm-button-type="danger"
                    @confirm="handleDelete(item, i)"
                  >
                    <template #reference>
                      <el-button
                        type="danger"
                        :icon="Delete"
                        circle
                        size="small"
                      />
                    </template>
                  </el-popconfirm>
                </template>
                <template v-else>
                  <el-button
                    type="success"
                    :icon="Check"
                    circle
                    size="small"
                    @click="handleSubmit(item, i)"
                  />
                  <el-button
                    type="danger"
                    :icon="Close"
                    circle
                    size="small"
                    @click="handleClose(item, i)"
                  />
                </template>
              </div>
            </div>
          </div>
        </template>
        <template #footer>
          <div
            @click="handleAddItem"
            class="bg-gray-100 rounded-md p-2 flex items-center text-xs justify-center duration-300"
            :class="[
              addBtnDisabled
                ? 'cursor-not-allowed text-gray-400 '
                : 'text-gray-700 cursor-pointer hover:bg-primary/20 hover:text-primary '
            ]"
          >
            <el-icon class="mr-2">
              <CirclePlus />
            </el-icon>
            <span>添加一个</span>
          </div>
        </template>
      </Draggable>
    </el-scrollbar>
  </el-drawer>
</template>

<script setup>
  import { ref, watch, computed } from 'vue';
  import { cloneDeep } from 'lodash-es';
  import Draggable from 'vuedraggable';
  import {
    Delete,
    Edit,
    CirclePlus,
    Check,
    Close,
    Star,
    StarFilled,
    QuestionFilled,
    CircleClose
  } from '@element-plus/icons-vue';
  import {
    addBmGroup,
    removeBmGroup,
    updateBmGroup,
    updateBmGroupSort
  } from '@/api/bookmark/group';
  import { ElMessage } from 'element-plus/es';
  import { useRoute } from 'vue-router';

  const visible = defineModel({ type: Boolean, default: false });
  const emit = defineEmits(['done']);
  const props = defineProps({
    data: Array
  });

  const route = useRoute();

  const groupList = ref([]);

  const addBtnDisabled = computed(() => {
    return groupList.value.some((v) => v.isEdit);
  });

  const handleAddItem = () => {
    if (addBtnDisabled.value) return;
    groupList.value.push({
      title: '',
      status: 0,
      isEdit: true
    });
  };

  const handleDelete = (item, i) => {
    removeBmGroup(item.id).then((msg) => {
      ElMessage.success(msg);
      groupList.value.splice(i, 1);
    });
  };

  const handleSubmit = (item, i) => {
    if (!item.title) {
      ElMessage.warning('请输入分组名称');
      return;
    }
    item.isEdit = false;
    const api = item.id ? updateBmGroup : addBmGroup;
    const msg = item.id ? '更新成功!' : '添加成功!';

    const categoryId = route.params.category;
    api({ ...item, categoryId })
      .then((data) => {
        item.id = data.id;
        item.sourceTitle = item.title;
        ElMessage.success(msg);
      })
      .catch(() => {
        groupList.value.splice(i, 1);
      });
  };

  const handleClose = (item, i) => {
    if (item.id) {
      item.title = item.sourceTitle;
      item.isEdit = false;
      return;
    }
    groupList.value.splice(i, 1);
  };

  const handleStatusChange = (item, status) => {
    const msgMap = {
      0: '启用成功',
      1: '隐藏成功',
      2: '已设置仅管理员可见'
    };
    updateBmGroup({ id: item.id, status }).then(() => {
      item.status = status;
      ElMessage.success(msgMap[status]);
    });
  };

  const handleSortEnd = ({ newIndex, oldIndex }) => {
    const start = Math.min(newIndex, oldIndex);
    const end = Math.max(newIndex, oldIndex);
    const ids = groupList.value.slice(start, end + 1).map((v) => v.id);
    updateBmGroupSort(ids).then((msg) => {
      ElMessage.success(msg);
    });
  };

  watch(visible, (val) => {
    if (val) {
      groupList.value = cloneDeep(props.data).map((v) => ({
        ...v,
        sourceTitle: v.title,
        isEdit: false
      }));
    }
  });
</script>

<style>
  .ghost-group-item {
    background-color: var(--el-color-primary-light-9);
  }
</style>
