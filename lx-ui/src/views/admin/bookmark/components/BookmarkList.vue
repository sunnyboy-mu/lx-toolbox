<template>
  <div class="pl-2 flex-1 flex flex-col overflow-hidden">
    <div class="table-tools mb-2">
      <el-button type="primary" :icon="Plus" @click="openEdit">
        新增
      </el-button>
    </div>

    <el-table class="flex-1" stripe :data="tableData" width="100%">
      <el-table-column type="index" label="序号" width="80" align="center">
        <template #default="{ $index }">
          {{ (page - 1) * size + $index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="网站标题" />
      <el-table-column prop="url" label="网址" />
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag
            size="small"
            disable-transitions
            v-if="row.status === 0"
            type="success"
            >显示</el-tag
          >
          <el-tag size="small" disable-transitions v-else type="danger"
            >隐藏</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button :icon="Position" link @click="previewUrl(row)">
            访问
          </el-button>
          <el-button :icon="Edit" type="primary" @click="openEdit(row)" link>
            编辑
          </el-button>
          <el-button
            :icon="Delete"
            type="danger"
            link
            @click="handleRemove(row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="table-agination pt-2 flex justify-center">
      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        size="small"
        background
        layout="prev, pager, next, sizes, total"
        :total="total"
        @change="loadTableData"
      />
    </div>

    <BookmarkEdit
      :data="current"
      v-model="visible"
      :group-id="props.groupId"
      @done="reload"
    />
  </div>
</template>
<script setup>
  import { watch, ref } from 'vue';

  import { Plus, Edit, Delete, Position } from '@element-plus/icons-vue';
  import { ElMessageBox, ElMessage } from 'element-plus/es';
  import { pageBmInfo, removeBmInfo } from '@/api/bookmark';
  import BookmarkEdit from './BookmarkEdit.vue';
  import { useRoute } from 'vue-router';

  const route = useRoute();

  const props = defineProps({
    groupId: String
  });

  const page = ref(1);
  const size = ref(10);
  const total = ref(0);
  const tableData = ref([]);

  const reload = () => {
    page.value = 1;
    size.value = 10;
    loadTableData();
  };

  const loadTableData = async () => {
    const category = route.params.category;
    const data = await pageBmInfo({
      current: page.value,
      size: size.value,
      groupId: props.groupId,
      categoryId: category
    });
    total.value = data.total;
    tableData.value = data.records;
  };

  const previewUrl = (row) => {
    window.open(row.url, '_blank');
  };

  const handleRemove = (row) => {
    ElMessageBox.confirm(`确认删除《${row.title}》的书签吗？`, '提示', {
      type: 'warning'
    })
      .then(() => {
        removeBmInfo(row.id).then((msg) => {
          ElMessage.success(msg);
          reload();
        });
      })
      .catch(() => {});
  };

  const current = ref(null);
  const visible = ref(false);
  const openEdit = (row) => {
    current.value = row;
    visible.value = true;
  };

  watch([() => props.groupId, () => route.params.category], reload, {
    immediate: true
  });
</script>
