<template>
  <el-form
    :model="form"
    label-position="top"
    :rules="rules"
    ref="formRef"
    class="lx-page flex"
  >
    <div class="flex-1 pr-2 flex flex-col">
      <el-form-item prop="title">
        <el-input v-model="form.title" placeholder="请输入文章标题" />
      </el-form-item>
      <div class="flex-1 overflow-hidden">
        <BytemdEditor />
      </div>
    </div>
    <div class="shrink-0 w-96 pl-2 border-l border-gray-200">
      <div class="pb-2 flex justify-end">
        <el-tooltip
          effect="light"
          content="保持文章至草稿箱"
          placement="bottom-end"
        >
          <el-button :icon="EditPen">保存</el-button>
        </el-tooltip>
        <el-tooltip
          effect="light"
          content="立即发布文章"
          placement="bottom-end"
        >
          <el-button type="primary" :icon="Position">发布</el-button>
        </el-tooltip>
      </div>
      <el-form-item label="所属分组" prop="groupId">
        <el-tree-select
          v-model="form.groupId"
          :data="typeTreeData"
          :props="{ label: 'title' }"
          node-key="id"
          accordion
          placeholder="请选择分组"
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
      <el-form-item label="文章图标" prop="icon">
        <IconSelect v-model="form.icon" placeholder="请选择文章图标" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number
          v-model="form.sort"
          :min="0"
          :precision="0"
          step-strictly
          :controls="false"
        />
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
        <el-radio-group v-model="form.special">
          <el-radio :value="0" border>是</el-radio>
          <el-radio :value="1" border>否</el-radio>
        </el-radio-group>
      </el-form-item>
    </div>
  </el-form>
</template>

<script setup>
  import { treeBlogType } from '@/api/blog/category';
  import { useFormData } from '@/utils/use-form-data';
  import { ref, reactive, onMounted } from 'vue';
  import IconSelect from '@/components/IconSelect/index.vue';
  import { EditPen, Position, QuestionFilled } from '@element-plus/icons-vue';
  import BytemdEditor from '@/components/BytemdEditor/index.vue';

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
    status: 0 // 状态 0是 1否
  });

  const formRef = ref(null);

  const rules = reactive({
    title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
    groupId: [{ required: true, message: '请选择分组', trigger: 'change' }],
    sort: [{ required: true, message: '请输入排序值', trigger: 'blur' }]
  });

  // 分类分组数
  const typeTreeData = ref([]);
  treeBlogType().then((data) => {
    typeTreeData.value = data;
  });

  const handleChange = (value) => {
    setFieldValue('sourceText', value);
  };
</script>
