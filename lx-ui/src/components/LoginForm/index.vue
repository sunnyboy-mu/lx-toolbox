<template>
  <div class="flex bg-white h-96 overflow-hidden">
    <!-- 左侧风景照 -->
    <div class="w-80 hidden justify-center items-center md:flex">
      <img
        src="https://upyun-oss.mu00.cn/2025/03/15//1742003309134.png"
        alt="登录背景"
        class="w-full h-full object-cover"
      />
    </div>

    <!-- 右侧登录区域 -->
    <div class="w-96 flex-1 p-8 lg:p-12">
      <div class="mx-auto">
        <!-- Logo和标题 -->
        <div class="text-center mb-4">
          <h2 class="text-3xl font-bold text-gray-900">{{ PROJECT_NAME }}</h2>
          <p class="mt-2 text-sm text-gray-600">欢迎登录</p>
        </div>

        <!-- 登录方式切换器 -->
        <el-segmented
          v-model="loginType"
          :options="loginOptions"
          class="w-full mb-6"
        >
          <template #default="{ item }">
            <el-icon>
              <component :is="item.icon" />
            </el-icon>
          </template>
        </el-segmented>

        <!-- 登录表单 -->
        <el-form
          ref="loginFormRef"
          :model="form"
          :rules="rules"
          :validate-on-rule-change="false"
          class="space-y-6"
        >
          <template v-if="loginType === 'auth'">
            <el-form-item prop="authCode">
              <el-input
                v-model="form.authCode"
                placeholder="请输入授权码"
                :prefix-icon="Key"
                show-password
              />
            </el-form-item>
          </template>
          <template v-else>
            <el-form-item prop="username">
              <el-input
                v-model="form.username"
                placeholder="请输入用户名"
                :prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="form.password"
                type="password"
                placeholder="请输入密码"
                :prefix-icon="Lock"
                show-password
              />
            </el-form-item>
          </template>

          <el-button
            type="primary"
            class="w-full"
            @click="handleLogin"
            :loading="loading"
          >
            登 录
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref } from 'vue';
  import { User, Lock, Key } from '@element-plus/icons-vue';
  import { useFormData } from '@/utils/use-form-data';
  import { PROJECT_NAME } from '@/config/setting';
  import { useUserStore } from '@/store/module/user';
  import { useRouter } from 'vue-router';
  import { useRoute } from 'vue-router';
  const userStore = useUserStore();
  const router = useRouter();
  const route = useRoute();

  // 登录方式选项
  const loginOptions = [
    { value: 'auth', icon: Key },
    { value: 'account', icon: User }
  ];

  // 登录方式
  const loginType = ref('auth');

  // 表单
  const [form, resetFields] = useFormData({
    username: '',
    password: '',
    authCode: ''
  });

  // 校验
  const rules = {
    authCode: [
      { required: true, message: '请输入授权码', trigger: 'blur' },
      {
        min: 6,
        max: 20,
        message: '授权码长度在 6 到 20 个字符',
        trigger: 'blur'
      }
    ],
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      {
        min: 3,
        max: 20,
        message: '用户名长度在 3 到 20 个字符',
        trigger: 'blur'
      }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 5, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
    ]
  };

  const loginFormRef = ref(null);

  const loading = ref(false);

  const handleLogin = () => {
    loginFormRef.value.validate(async (valid) => {
      if (!valid) return;
      // 校验成功
      loading.value = true;
      userStore
        .doUserlogin(form, loginType.value)
        .then(() => {
          if (route.path === '/login') {
            router.push('/');
          }
        })
        .finally(() => {
          loading.value = false;
        });
    });
  };
</script>

<style scoped lang="scss"></style>
