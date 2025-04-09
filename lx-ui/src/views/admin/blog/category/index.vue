<template>
  <div class="lx-page flex flex-col">
    <div class="table-tools mb-2">
      <el-button type="primary" :icon="Plus" @click="openEdit()">
        添加分类
      </el-button>
    </div>
    <el-table class="flex-1" stripe :data="tableData" width="100%">
      <el-table-column type="index" label="序号" width="80" align="center">
      </el-table-column>
      <el-table-column prop="title" label="分组标题" />
      <el-table-column prop="icon" label="分组图标">
        <template #default="{ row }">
          <el-icon v-if="row.icon">
            <i class="lx-iconfont" :class="row.icon"></i>
          </el-icon>
        </template>
      </el-table-column>
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
      <el-table-column label="操作" width="220">
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

    <CategoryEdit :data="current" v-model="visible" @done="reload" />
  </div>
</template>

<script setup>
  import { deleteBlogCategory, listBlogCategory } from '@/api/blog/category';
  import { Plus, Edit, Delete } from '@element-plus/icons-vue';
  import { ref } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus/es';
  import CategoryEdit from './components/CategoryEdit.vue';

  const tableData = ref([]);

  const current = ref(null);

  const reload = () => {
    listBlogCategory().then((data) => {
      tableData.value = data;
    });
  };

  const visible = ref(false);

  const openEdit = (row) => {
    current.value = row;
    visible.value = true;
  };

  const remove = (row) => {
    ElMessageBox.confirm(`确认删除“${row.title}”吗？`, '提示', {
      type: 'warning'
    })
      .then(() => {
        deleteBlogCategory(row.id).then((msg) => {
          ElMessage.success(msg);
          reload();
        });
      })
      .catch(() => {});
  };

  reload();
</script>
