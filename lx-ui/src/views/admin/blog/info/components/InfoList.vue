<template>
  <div class="flex flex-col overflow-hidden px-2">
    <div class="table-tools flex items-center justify-between mb-2">
      <div>
        <el-button :icon="Plus" type="primary" @click="sendArticle">
          发文章
        </el-button>
      </div>
      <el-segmented
        v-model="segmentedValue"
        :options="segmentedOptions"
        @change="reload(true)"
      />
    </div>
    <el-table class="flex-1" stripe :data="tableData" row-key="id">
      <el-table-column type="index" width="80" label="序号">
        <template #default="{ $index }">
          {{ pageSize * (page - 1) + $index + 1 }}
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

      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag
            size="small"
            disable-transitions
            v-if="row.status === 1"
            type="danger"
          >
            隐藏
          </el-tag>

          <el-tag
            size="small"
            disable-transitions
            v-else-if="row.special === 0"
            type="success"
          >
            高级
          </el-tag>
          <el-tag size="small" disable-transitions v-else>正常</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button :icon="Edit" type="primary" link @click="editArticle(row)">
            编辑
          </el-button>
          <el-divider direction="vertical" v-if="row.status === 1" />
          <el-button
            v-if="row.status === 1"
            :icon="Delete"
            type="danger"
            link
            @click="remove(row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="table-agination pt-2 flex justify-center">
      <el-pagination
        v-model:current-page="page"
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
  import { deleteBlogInfo, pageBlogInfo } from '@/api/blog/info';
  import { Plus, Edit, Delete } from '@element-plus/icons-vue';
  import { useRouter } from 'vue-router';
  import { ref, watch } from 'vue';
  import { ElMessage, ElMessageBox } from 'element-plus/es';

  const router = useRouter();

  const props = defineProps({
    groupId: String
  });

  const segmentedValue = ref('');

  const segmentedOptions = [
    { label: '全 部', value: '' },
    { label: '正 常', value: 'normal' },
    { label: '特 殊', value: 'special' },
    { label: '隐 藏', value: 'hidden' }
  ];

  const total = ref(0);
  const page = ref(1);
  const pageSize = ref(10);

  const tableData = ref([]);

  const reload = async (init = false) => {
    if (init) {
      page.value = 1;
      pageSize.value = 10;
    }

    const queryParams = {
      groupId: props.groupId,
      current: page.value,
      size: pageSize.value
    };

    switch (segmentedValue.value) {
      case 'normal':
        queryParams.status = 0;
        break;
      case 'hidden':
        queryParams.status = 1;
        break;
      case 'special':
        queryParams.special = 0;
        break;
      default:
        break;
    }

    const data = await pageBlogInfo(queryParams);
    total.value = data.total;
    tableData.value = data.records;
  };

  const sendArticle = () => {
    router.push({
      name: 'blogAddOrEdit',
      query: {
        groupId: props.groupId
      }
    });
  };

  const editArticle = (row) => {
    router.push({
      name: 'blogAddOrEdit',
      params: {
        blogId: row.id
      }
    });
  };

  const remove = (row) => {
    ElMessageBox.confirm(`确认删除文章《${row.title}》吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(async () => {
        await deleteBlogInfo(row.id);
        ElMessage.success('删除成功');
        reload();
      })
      .catch(() => {});
  };

  reload(true);

  watch(
    () => props.groupId,
    () => {
      reload(true);
    }
  );
</script>
