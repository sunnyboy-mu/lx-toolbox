<template>
  <el-dialog
    v-model="visible"
    width="720"
    title="配置"
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <el-form
      label-position="top"
      :model="form"
      ref="formRef"
      label-width="80px"
      :rules="rules"
    >
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="所属分组" prop="groupId">
            <el-tree-select
              v-model="form.groupId"
              :data="typeTreeData"
              :props="{ label: 'title' }"
              node-key="id"
              accordion
              placeholder="请选择分组"
              auto-expand-parent
              :default-expanded-keys="defaultExpandedKeys"
            >
              <template #default="{ node, data }">
                <div class="flex items-center el-tree-node__label">
                  <i
                    v-if="data.icon"
                    class="lx-iconfont mr-1 shrink-0"
                    :class="data.icon"
                  ></i>
                  <span class="text-xs truncate flex-1 min-w-0">{{
                    data.title
                  }}</span>
                </div>
              </template>
            </el-tree-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序" prop="sort">
            <el-input-number
              v-model="form.sort"
              :min="0"
              :precision="0"
              step-strictly
              :controls="false"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="文章图标" prop="icon">
        <IconSelect v-model="form.icon" placeholder="请选择文章图标" />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :value="0" border>正常</el-radio>
          <el-radio :value="1" border>隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="高级（仅登录可见）" prop="special">
        <template #label>
          <div class="flex items-center">
            <el-tooltip
              effect="light"
              content="是：文章仅在登录后显示，否：文章所有人可见"
              placement="top"
            >
              <el-icon class="cursor-help"><QuestionFilled /></el-icon>
            </el-tooltip>

            <span class="ml-1">高级（仅登录后可见）</span>
          </div>
        </template>
        <div class="flex items-center justify-baseline flex-1">
          <el-radio-group v-model="form.special">
            <el-radio :value="0" border>是</el-radio>
            <el-radio :value="1" border>否</el-radio>
          </el-radio-group>
          <div class="ml-auto">
            <el-checkbox
              :false-value="1"
              :true-value="0"
              v-model="form.groupMainInfo"
              label="设为分组默认文章"
            />
          </div>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :loading="loading" @click="visible = false">取 消</el-button>
      <el-button :loading="loading" type="primary" @click="handleSubmit"
        >确 定</el-button
      >
    </template>
  </el-dialog>
</template>
<script setup>
  import { treeBlogType } from '@/api/blog/category';
  import { ref, watch, computed, reactive } from 'vue';
  import { ElMessage } from 'element-plus/es';
  import { addBlogInfo, updateBlogInfo } from '@/api/blog/info';
  import { useFormData } from '@/utils/use-form-data';
  import IconSelect from '@/components/IconSelect/index.vue';
  import { QuestionFilled } from '@element-plus/icons-vue';
  const visible = defineModel({ type: Boolean, default: false });

  const props = defineProps({
    data: Object
  });

  const emit = defineEmits(['done']);

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

  const defaultExpandedKeys = computed(() => {
    return form.groupId ? [form.groupId] : [];
  });

  const loading = ref(false);

  const formRef = ref(null);

  const isUpdata = ref(false);

  const rules = reactive({
    groupId: [{ required: true, message: '请选择分组', trigger: 'change' }],
    sort: [{ required: true, message: '请输入排序值', trigger: 'blur' }]
  });

  // 分类分组数
  const typeTreeData = ref([]);
  treeBlogType().then((data) => {
    typeTreeData.value = data;
  });

  const handleSubmit = () => {
    formRef.value.validate((valid) => {
      if (!valid) return;
      loading.value = true;
      const api = isUpdata.value ? updateBlogInfo : addBlogInfo;
      api({
        ...form,
        tags: JSON.stringify(form.tags),
        catalog: JSON.stringify(form.catalog)
      }).then((msg) => {
        loading.value = false;
        ElMessage.success(msg);
        emit('done');
      });
    });
  };

  watch(visible, (val) => {
    if (val) {
      isUpdata.value = !!props.data?.id;
      assignFields(props.data);
    } else {
      resetFields();
      formRef.value?.clearValidate();
    }
  });
</script>
