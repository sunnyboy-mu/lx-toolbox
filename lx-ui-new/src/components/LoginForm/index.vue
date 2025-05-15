<template>
  <div class="flex flex-col md:flex-row bg-white">
    <!-- 左侧图片区域 -->
    <div class="hidden md:w-1/2 md:flex items-center justify-center p-8">
      <div class="max-w-md text-center">
        <img
          src="https://upyun-oss.mu00.cn/2025/03/15//1742003309134.png"
          alt="登录页面插图"
          class="rounded-xl mb-8 w-full object-cover"
        />
      </div>
    </div>

    <!-- 右侧登录表单区域 -->
    <div class="w-full md:w-1/2 flex justify-center md:p-12">
      <div class="w-full max-w-md">
        <div class="text-center mb-8">
          <h1 class="text-3xl font-bold text-gray-800">欢迎回来</h1>
          <p class="text-gray-600 mt-6">予万物以理解，涵万事而有容</p>
        </div>

        <el-form
          :model="form"
          :rules="rules"
          ref="loginForm"
          label-position="top"
          @submit.prevent="handleSubmit"
          class="space-y-6"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              :prefix-icon="User"
              size="large"
              class="w-full"
            />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              show-password
              :prefix-icon="Lock"
              size="large"
              class="w-full"
            />
          </el-form-item>

          <el-button
            type="primary"
            native-type="submit"
            class="w-full mt-6"
            size="large"
            :loading="loading"
          >
            登录
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script setup>
  defineOptions({
    name: 'LoginForm'
  });
  import { ref } from 'vue';
  import { User, Lock } from '@element-plus/icons-vue';
  import { ElMessage } from 'element-plus';
  import { useUserStore } from '@/store/module/user';
  const { doLogin } = useUserStore();

  // 表单数据
  const form = ref({
    username: '',
    password: ''
  });

  // 表单验证规则
  const rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
    ]
  };

  const loading = ref(false);
  const loginForm = ref(null);

  // 提交表单
  const handleSubmit = async () => {
    loginForm.value.validate((valid) => {
      if (!valid) return;
      loading.value = true;
      doLogin(form.value)
        .then(() => {
          ElMessage.success('登录成功');
          window.location.reload();
        })
        .finally(() => {
          loading.value = false;
        });
    });
  };
</script>
