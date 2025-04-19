<template>
  <el-dialog
    v-model="visible"
    width="540"
    :title="isUpdate ? '编辑书籍' : '添加书籍'"
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :lock-scroll="false"
  >
    <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
      <el-form-item label="书名" prop="name">
        <el-input v-model="form.name" placeholder="请输入书名" />
      </el-form-item>

      <el-form-item label="封面" prop="cover">
        <el-input v-model="form.cover" placeholder="请输入封面地址" />
      </el-form-item>

      <el-form-item label="作者" prop="author">
        <el-input v-model="form.author" placeholder="请输入作者" />
      </el-form-item>

      <el-form-item label="Douban" prop="link">
        <el-input v-model="form.link" placeholder="请输入豆瓣链接" />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :value="0" border>已读</el-radio>
          <el-radio :value="1" border>温习中...</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="描述" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          placeholder="请输入描述"
          clearable
          maxlength="255"
          show-word-limit
          :rows="4"
          resize="none"
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
  import { addBook, updateBook } from '@/api/book';

  const props = defineProps({
    data: Object
  });

  const emit = defineEmits(['done']);

  const visible = defineModel({ type: Boolean, default: false });

  const isUpdate = ref(false);

  const formRef = ref(null);

  const rules = reactive({
    name: [{ required: true, message: '请输入书名', trigger: 'blur' }],
    cover: [{ required: true, message: '请输入封面地址', trigger: 'blur' }],
    author: [{ required: true, message: '请输入作者', trigger: 'blur' }]
  });

  const [form, resetFields, assignFields, setFieldValue] = useFormData({
    id: void 0,
    name: '',
    cover: '',
    author: '',
    link: '',
    description: '',
    status: 0
  });

  const handleSubmit = () => {
    formRef.value.validate((valid) => {
      if (!valid) return;
      const api = isUpdate.value ? updateBook : addBook;
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
      if (props.data) {
        assignFields(props.data);
        isUpdate.value = true;
      } else {
        isUpdate.value = false;
      }
    } else {
      resetFields();
      formRef.value?.clearValidate();
    }
  });
</script>
