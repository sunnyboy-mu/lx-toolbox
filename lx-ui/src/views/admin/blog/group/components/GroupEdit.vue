<template>
  <el-dialog
    v-model="visible"
    width="540"
    :title="isUpdate ? '编辑分组' : '添加分组'"
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
      <el-form-item label="所属分类" prop="categoryId">
        <el-select v-model="form.categoryId" placeholder="请选择所属分类">
          <el-option
            v-for="item in blogCategoryList"
            :key="item.id"
            :label="item.title"
            :value="item.id"
          >
            <i class="lx-iconfont" :class="item.icon"></i>
            <span class="ml-2">{{ item.title }}</span>
          </el-option>
          <template #label>
            <i class="lx-iconfont" :class="selectedCategory?.icon"></i>
            <span class="ml-2">{{ selectedCategory?.title }}</span>
          </template>
        </el-select>
      </el-form-item>
      <el-form-item label="分组名称" prop="title">
        <el-input v-model="form.title" placeholder="请输入分组名称" clearable />
      </el-form-item>
      <el-form-item label="分类图标" prop="icon">
        <IconSelect v-model="form.icon" placeholder="请输入分类图标" />
      </el-form-item>
      <el-form-item label="跳转文章" prop="url" v-if="form.id">
        <el-select v-model="form.url" placeholder="请选择文章" clearable>
          <el-option
            v-for="info in infoList"
            :key="info.id"
            :value="info.id"
            :label="info.title"
          >
            <div class="flex items-center">
              <i
                class="lx-iconfont mr-1"
                v-if="info.icon"
                :class="info.icon"
              ></i>
              <span class="text-xs">{{ info.title }}</span>
            </div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序值" prop="sort">
        <el-input-number
          v-model="form.sort"
          :min="0"
          :precision="0"
          step-strictly
          :controls="false"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-switch
          v-model="form.status"
          inline-prompt
          style="
            --el-switch-on-color: #13ce66;
            --el-switch-off-color: #ff4949;
            margin-top: 2px;
          "
          active-text="显示"
          inactive-text="隐藏"
          :active-value="0"
          :inactive-value="1"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleSubmit">确 定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
  import { useFormData } from '@/utils/use-form-data';
  import { ref, watch, reactive, inject, computed } from 'vue';
  import { ElMessage } from 'element-plus/es';
  import IconSelect from '@/components/IconSelect/index.vue';
  import {
    addBlogGroup,
    listBlogInfoByGroupId,
    nextBlogGroupSortValue,
    updateBlogGroup
  } from '@/api/blog/group';
  const props = defineProps({
    data: Object,
    categoryId: String
  });

  const emit = defineEmits(['done']);

  const blogCategoryList = inject('category-data');

  const selectedCategory = computed(() => {
    return blogCategoryList.value.find((item) => item.id === form.categoryId);
  });

  const visible = defineModel({ type: Boolean, default: false });

  const isUpdate = ref(false);

  const formRef = ref(null);

  const rules = reactive({
    title: [{ required: true, message: '请输入网址标题', trigger: 'blur' }],
    // url: [{ required: true, message: '请输入跳转文章', trigger: 'blur' }],
    sort: [{ required: true, message: '请输入排序值', trigger: 'blur' }]
  });

  const [form, resetFields, assignFields, setFieldValue] = useFormData({
    id: void 0,
    title: '',
    icon: '',
    url: '',
    sort: 0,
    categoryId: '',
    status: 0
  });

  const getSortValue = () => {
    nextBlogGroupSortValue().then((data) => {
      setFieldValue('sort', data);
    });
  };

  const handleSubmit = () => {
    formRef.value.validate((valid) => {
      if (!valid) return;
      const api = isUpdate.value ? updateBlogGroup : addBlogGroup;
      const msg = isUpdate.value ? '编辑成功' : '添加成功';
      api(form).then(() => {
        ElMessage.success(msg);
        visible.value = false;
        emit('done');
      });
    });
  };

  const infoList = ref([]);
  const getInfoList = async () => {
    infoList.value = await listBlogInfoByGroupId(form.id);
    console.log(infoList.value);
  };

  watch(visible, (val) => {
    if (val) {
      if (props.data?.id) {
        assignFields(props.data);
        isUpdate.value = true;
        getInfoList();
      } else {
        getSortValue();
        setFieldValue('categoryId', props.categoryId);
        isUpdate.value = false;
      }
    } else {
      resetFields();
      formRef.value?.clearValidate();
    }
  });
</script>
