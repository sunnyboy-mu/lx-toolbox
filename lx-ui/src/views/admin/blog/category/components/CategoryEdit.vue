<template>
  <el-dialog
    v-model="visible"
    width="540"
    :title="isUpdate ? '编辑分类' : '添加分类'"
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
      <el-form-item label="分类标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入分类标题" clearable />
      </el-form-item>
      <el-form-item label="分类图标" prop="icon">
        <IconSelect v-model="form.icon" placeholder="请输入分类图标" />
        <!-- <el-input v-model="form.icon" placeholder="请输入分类图标" clearable>
          <template #append>
            <div class="w-4">
              <i class="lx-iconfont" :class="form.icon"></i
            ></div>
          </template>
        </el-input> -->
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
  import { ref, watch, reactive } from 'vue';
  import { ElMessage } from 'element-plus/es';
  import {
    addBlogCategory,
    updateBlogCategory,
    nextBlogCategorySortValue
  } from '@/api/blog/category';
  import IconSelect from '@/components/IconSelect/index.vue';
  const props = defineProps({
    data: Object
  });

  const emit = defineEmits(['done']);

  const visible = defineModel({ type: Boolean, default: false });

  const isUpdate = ref(false);

  const formRef = ref(null);

  const rules = reactive({
    title: [{ required: true, message: '请输入网址标题', trigger: 'blur' }],
    sort: [{ required: true, message: '请输入排序值', trigger: 'blur' }]
  });

  const [form, resetFields, assignFields, setFieldValue] = useFormData({
    id: void 0,
    title: '',
    sort: 0,
    icon: '',
    status: 0
  });

  const getSortValue = () => {
    nextBlogCategorySortValue().then((data) => {
      setFieldValue('sort', data);
    });
  };

  const handleSubmit = () => {
    formRef.value.validate((valid) => {
      if (!valid) return;
      const api = isUpdate.value ? updateBlogCategory : addBlogCategory;
      const msg = isUpdate.value ? '编辑成功' : '添加成功';
      api(form).then(() => {
        ElMessage.success(msg);
        visible.value = false;
        emit('done');
      });
    });
  };

  watch(visible, (val) => {
    if (val) {
      if (props.data?.id) {
        assignFields(props.data);
        isUpdate.value = true;
      } else {
        getSortValue();
        isUpdate.value = false;
      }
    } else {
      resetFields();
      formRef.value?.clearValidate();
    }
  });
</script>
