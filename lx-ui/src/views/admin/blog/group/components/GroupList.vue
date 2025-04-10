<template>
  <div class="h-full pl-2 border-l border-gray-200 flex flex-col">
    <div class="table-tools mb-2">
      <el-button type="primary" :icon="Plus" @click="openEdit()">
        新增分组
      </el-button>
    </div>
    <el-table class="flex-1" stripe :data="tableData">
      <el-table-column type="index" width="80" label="序号" />
      <el-table-column prop="title" label="分组名称" width="180">
        <template #default="{ row }">
          <div class="flex items-center">
            <i class="lx-iconfont mr-1" :class="row.icon"></i>
            <span>{{ row.title }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="url" label="跳转文章" />
      <el-table-column prop="sort" label="排序值" />
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
      <el-table-column prop="createdAt" label="创建时间" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button :icon="Edit" type="primary" link @click="openEdit(row)">
            编辑
          </el-button>
          <el-divider direction="vertical" />
          <el-button :icon="Delete" type="danger" link @click="remove(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <GroupEdit
      v-model="visible"
      :categoryId="props.categoryId"
      :data="current"
      @done="reload"
    />
  </div>
</template>

<script setup>
  import { Plus, Edit, Delete } from '@element-plus/icons-vue';
  import { ElMessageBox, ElMessage } from 'element-plus/es';
  import { ref, watchEffect } from 'vue';
  import GroupEdit from './GroupEdit.vue';
  import { deleteBlogGroup, listBlogGroup } from '@/api/blog/group';
  const props = defineProps({
    categoryId: {
      type: String,
      default: ''
    }
  });
  const tableData = ref([]);

  const visible = ref(false);
  const current = ref(null);

  const openEdit = (row) => {
    current.value = row;
    visible.value = true;
  };

  const reload = async (categoryId = props.categoryId) => {
    const data = await listBlogGroup(categoryId);
    tableData.value = data;
  };

  const remove = (row) => {
    ElMessageBox.confirm(`确认删除“${row.title}”吗？`, '提示', {
      type: 'warning'
    })
      .then(() => {
        deleteBlogGroup(row.id).then((msg) => {
          ElMessage.success(msg);
          reload();
        });
      })
      .catch(() => {});
  };

  watchEffect(() => {
    reload(props.categoryId);
  });
</script>
