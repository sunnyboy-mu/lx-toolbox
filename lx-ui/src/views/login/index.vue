<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="w-96 bg-white p-8 rounded-lg shadow-lg">
      <h2 class="text-2xl font-bold text-center mb-6 text-gray-800"
        >用户登录</h2
      >

      <el-tabs v-model="activeTab" stretch class="mb-6">
        <el-tab-pane label="账号密码" name="password">
          <el-form
            :model="loginForm"
            :rules="rules"
            class="space-y-4"
            @submit.prevent="handleLogin"
          >
            <el-form-item>
              <el-input
                v-model="loginForm.username"
                placeholder="请输入账号"
                class="w-full focus:border-primary focus:ring-1 focus:ring-primary"
              />
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="loginForm.password"
                type="password"
                show-password
                placeholder="请输入密码"
                class="w-full focus:border-primary focus:ring-1 focus:ring-primary"
              />
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="授权码" name="token">
          <el-form :model="tokenForm" class="space-y-4">
            <el-form-item>
              <el-input
                v-model="tokenForm.token"
                placeholder="请输入授权码"
                class="w-full focus:border-primary focus:ring-1 focus:ring-primary"
              />
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <el-button
        type="primary"
        @click="handleLogin"
        class="w-full bg-primary hover:bg-primary-dark transition-colors"
      >
        立即登录
      </el-button>
    </div>
  </div>
</template>

<script setup>
  import { ref, reactive } from 'vue';
  import router from '@/router';
  import { useUserStore } from '@/store/module/user';

  const userStore = useUserStore();
  const activeTab = ref('password');

  const loginForm = reactive({
    username: '',
    password: ''
  });

  const tokenForm = reactive({
    token: ''
  });

  const rules = {
    username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    token: [{ required: true, message: '请输入授权码', trigger: 'blur' }]
  };

  const handleLogin = async () => {
    try {
      if (activeTab.value === 'password') {
        await userStore.loginByPassword(loginForm);
      } else {
        await userStore.loginByToken(tokenForm);
      }
      ElMessage.success('登录成功');
      router.push('/');
    } catch (error) {
      ElMessage.error(error.message);
    }
  };
</script>

<style scoped>
  .bg-primary {
    background-color: var(--el-color-primary);
  }
  .bg-primary-dark:hover {
    background-color: var(--el-color-primary-dark-2);
  }
</style>
