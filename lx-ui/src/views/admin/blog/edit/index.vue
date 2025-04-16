<template>
  <el-form
    :model="form"
    label-position="top"
    :rules="rules"
    ref="formRef"
    class="lx-page flex"
    v-loading="loading"
    element-loading-text="加载中..."
  >
    <div class="flex-1 flex flex-col">
      <div class="flex items-start justify-between gap-4">
        <div class="flex-1">
          <el-form-item prop="title">
            <el-input v-model="form.title" placeholder="请输入文章标题" />
          </el-form-item>
        </div>
        <div class="flex items-center justify-end shrink-0">
          <el-button class="ml-auto" :icon="ArrowLeft" @click="router.back()">
            返回
          </el-button>
          <el-tooltip
            effect="light"
            content="立即发布文章"
            placement="bottom-end"
          >
            <el-button type="primary" :icon="Position" @click="submit"
              >保存</el-button
            >
          </el-tooltip>
        </div>
      </div>
      <div class="flex-1 overflow-hidden">
        <ByteMdEditor
          v-model="form.sourceText"
          placeholder="请输入文章内容"
          ref="byteMdEditorRef"
        />
      </div>
    </div>

    <InfoConfig v-model="visible" :data="form" @done="handleDone" />
  </el-form>
</template>

<script setup>
  import { useFormData } from '@/utils/use-form-data';
  import { ref, reactive } from 'vue';
  import { Position, ArrowLeft } from '@element-plus/icons-vue';
  import ByteMdEditor from '@/components/ByteMdEditor/index.vue';
  import { useRoute } from 'vue-router';
  import { detailBlogInfo, nextBlogInfoSortValue } from '@/api/blog/info';
  import { useRouter } from 'vue-router';
  import InfoConfig from './components/InfoConfig.vue';

  const router = useRouter();

  const route = useRoute();

  const loading = ref(true);

  const [form, resetFields, assignFields, setFieldValue] = useFormData({
    id: void 0,
    title: '', // 文章标题
    description: '', // 文章描述
    icon: '', // 文章图标
    banner: '', // 文章封面
    sourceText: '', // 源文
    renderText: '', // 渲染文
    catalog: [], // 目录
    tags: [], // 标签
    sort: 0, // 排序
    special: 1, // 是否高级 0是 1否
    top: 0, // 是否置顶 0是 1否
    groupId: '', // 所属分组
    categoryId: '', // 所属分类
    status: 0, // 状态 0是 1否
    groupMainInfo: 1 // 是否为分组默认文章
  });

  const formRef = ref(null);

  const byteMdEditorRef = ref(null);

  const rules = reactive({
    title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
  });

  const visible = ref(false);

  const submit = () => {
    formRef.value.validate((valid) => {
      if (!valid) return;

      setFieldValue('renderText', byteMdEditorRef.value?.getHtml());
      setFieldValue('catalog', byteMdEditorRef.value?.getCatalog());
      visible.value = true;
    });
  };

  const handleDone = () => {
    loading.value = false;
    router.back();
  };

  const init = async () => {
    const { blogId } = route.params;
    const { groupId } = route.query;
    if (blogId) {
      const data = await detailBlogInfo(blogId);
      assignFields(data);
      setFieldValue('tags', JSON.parse(data.tags));
      setFieldValue('catalog', JSON.parse(data.catalog));
    } else {
      setFieldValue('sort', await nextBlogInfoSortValue());
      setFieldValue('groupId', groupId);
    }
    loading.value = false;
  };

  init();
</script>
