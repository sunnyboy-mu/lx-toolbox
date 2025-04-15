<template>
  <div class="lx-page flex flex-col">
    <el-table
      class="flex-1"
      stripe
      :data="tableData"
      row-key="id"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="60" />
      <el-table-column type="index" width="80" label="序号">
        <template #default="{ $index }">
          {{ pageSize * (currentPage - 1) + $index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题">
        <template #default="{ row }">
          <div class="flex items-center">
            <i class="lx-iconfont mr-1" :class="row.icon"></i>
            <span>{{ row.title }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序值" width="120" />

      <el-table-column prop="tags" label="标签">
        <template #default="{ row }">
          <el-tag
            v-for="v in JSON.parse(row.tags)"
            :key="v"
            class="mr-1"
            size="small"
            type="info"
          >
            {{ v }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" />

      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button
            :icon="RefreshLeft"
            type="primary"
            link
            @click="recovery(row)"
          >
            恢复
          </el-button>
          <el-divider direction="vertical" />
          <el-button :icon="Delete" type="danger" link @click="remove(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="table-agination pt-2 flex justify-center">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        size="small"
        background
        layout="prev, pager, next, sizes, total"
        :total="total"
        @change="reload"
      />
    </div>
  </div>
</template>
<script setup>
  import {
    batchDelBlogRecycle,
    batchRecoveryBlogRecycle,
    pageBlogRecycle
  } from '@/api/blog/recycle';
  import { Delete, RefreshLeft } from '@element-plus/icons-vue';
  import { ref } from 'vue';
  import { ElMessage, ElMessageBox } from 'element-plus/es';

  const total = ref(0);
  const currentPage = ref(1);
  const pageSize = ref(10);

  const tableData = ref([]);

  const selection = ref([]);

  const reload = async (init = false) => {
    if (init) {
      currentPage.value = 1;
      pageSize.value = 10;
    }
    const data = await pageBlogRecycle({
      current: currentPage.value,
      size: pageSize.value
    });
    tableData.value = data.records;
    total.value = data.total;
  };

  const handleSelectionChange = (val) => {
    selection.value = val;
  };

  const recovery = async (row) => {
    const rows = row ? [row] : selection.value;
    if (rows.length === 0) {
      ElMessage.warning('请选择要恢复的文章');
      return;
    }
    ElMessageBox.confirm(
      `确定要恢复“${rows.map((v) => v.title)}”文章吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
      .then(async () => {
        await batchRecoveryBlogRecycle(rows.map((v) => v.id));
        ElMessage.success('恢复成功');
        reload(true);
      })
      .catch(() => {});
  };

  const remove = async (row) => {
    const rows = row ? [row] : selection.value;
    if (rows.length === 0) {
      ElMessage.warning('请选择要删除的文章');
      return;
    }
    ElMessageBox.confirm(
      `确定要删除“${rows.map((v) => v.title)}”文章吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
      .then(async () => {
        await batchDelBlogRecycle(rows.map((v) => v.id));
        ElMessage.success('恢复成功');
        reload(true);
      })
      .catch(() => {});
  };

  reload(true);
</script>
