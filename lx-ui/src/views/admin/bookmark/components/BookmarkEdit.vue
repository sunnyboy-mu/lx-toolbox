<template>
  <el-dialog
    v-model="visible"
    width="540"
    :title="isUpdate ? '编辑书签' : '添加书签'"
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
      <el-form-item label="分组" prop="groupId">
        <el-select v-model="form.groupId" placeholder="请选择分组">
          <el-option
            v-for="group in groupData"
            :key="group.id"
            :label="group.title"
            :value="group.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="网站标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入网站标题" clearable />
      </el-form-item>
      <el-form-item label="网址URL" prop="url">
        <el-input v-model="form.url" placeholder="请输入网址URL" clearable />
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input
          v-model="form.icon"
          placeholder="请输入ICON地址或“auto”"
          clearable
        >
          <template #append>
            <el-button :icon="View" @click="previewIcon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          placeholder="请输入网站描述"
          clearable
          maxlength="100"
          show-word-limit
          :rows="2"
          resize="none"
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

    <el-image-viewer
      v-if="showPreview && previewIconUrl"
      :url-list="[previewIconUrl]"
      show-progress
      :initial-index="0"
      @close="showPreview = false"
    />

    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleSubmit">确 定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
  import { addBmInfo, updateBmInfo } from '@/api/bookmark';
  import { useFormData } from '@/utils/use-form-data';
  import { inject, ref, watch, reactive } from 'vue';
  import { View } from '@element-plus/icons-vue';
  import { ElMessage } from 'element-plus/es';
  import { isExternalLink } from '@/utils/common';
  import { AUTO_ICON_URL } from '@/config/setting';
  const props = defineProps({
    data: Object,
    groupId: String
  });

  const emit = defineEmits(['done']);

  const visible = defineModel({ type: Boolean, default: false });

  const isUpdate = ref(false);

  const formRef = ref(null);

  const rules = reactive({
    groupId: [{ required: true, message: '请选择分组', trigger: 'change' }],
    title: [{ required: true, message: '请输入网址标题', trigger: 'blur' }],
    url: [{ required: true, message: '请输入网址URL', trigger: 'blur' }],
    icon: [
      { required: true, message: '请输入ICON地址或“auto”', trigger: 'blur' }
    ]
  });

  const [form, resetFields, assignFields, setFieldValue] = useFormData({
    id: void 0,
    title: '',
    url: '',
    icon: 'auto',
    description: '',
    groupId: '',
    status: 0
  });

  const groupData = inject('group-data');

  const handleSubmit = () => {
    formRef.value.validate((valid) => {
      if (!valid) return;
      const api = isUpdate.value ? updateBmInfo : addBmInfo;
      const msg = isUpdate.value ? '编辑成功' : '添加成功';
      api(form).then(() => {
        ElMessage.success(msg);
        visible.value = false;
        emit('done');
      });
    });
  };

  const previewIconUrl = ref('');
  const showPreview = ref(false);
  const previewIcon = () => {
    if (!form.url || !form.icon) {
      return ElMessage.warning('请先输入网址URL或ICON地址');
    }
    if (form.icon !== 'auto' && !isExternalLink(form.icon)) {
      return ElMessage.warning('请输入正确的ICON地址');
    }
    previewIconUrl.value =
      form.icon === 'auto' ? AUTO_ICON_URL + form.url : form.icon;
    showPreview.value = true;
  };

  watch(visible, (val) => {
    if (val) {
      if (props.data?.id) {
        assignFields(props.data);
        isUpdate.value = true;
      } else {
        setFieldValue('groupId', props.groupId);
        isUpdate.value = false;
      }
    } else {
      resetFields();
      formRef.value?.clearValidate();
    }
  });
</script>
