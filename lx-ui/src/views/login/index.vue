<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 p-4">
    <div class="w-full max-w-[1200px] bg-white rounded-xl shadow-lg flex overflow-hidden">
      <!-- 左侧风景照 -->
      <div class="w-[600px] flex justify-center items-center">
        <div class="aspect-[16/9] relative h-100">
          <img
            src="@/assets/images/login-bg.png"
            alt="登录背景"
            class="absolute inset-0 w-full h-full object-cover"
          />
        </div>
      </div>

      <!-- 右侧登录区域 -->
      <div class="flex-1 p-8 lg:p-12">
        <div class="max-w-[360px] mx-auto">
          <!-- Logo和标题 -->
          <div class="text-center mb-8">
            <h2 class="text-3xl font-bold text-gray-900">沐沐物语</h2>
            <p class="mt-2 text-sm text-gray-600">欢迎登录</p>
          </div>

          <!-- 登录方式切换器 -->
          <el-segmented
            size="large"
            v-model="loginType"
            :options="loginOptions"
            class="w-full mb-6"
          >
            <template #default="{ item }">
              <div class="flex items-center justify-center gap-2">
                <el-icon>
                  <component :is="item.icon" />
                </el-icon>
                <span>{{ item.label }}</span>
              </div>
            </template>
          </el-segmented>

          <!-- 授权码登录表单 -->
          <el-form
            v-if="loginType === 'auth'"
            ref="authFormRef"
            :model="authForm"
            :rules="authRules"
            class="space-y-6"
            size="large"
          >
            <el-form-item prop="authCode">
              <el-input
                v-model="authForm.authCode"
                placeholder="请输入授权码"
                :prefix-icon="Key"
              />
            </el-form-item>
            <el-button type="primary" class="w-full" @click="handleAuthLogin">
              登录
            </el-button>
          </el-form>

          <!-- 账号密码登录表单 -->
          <el-form
            v-else
            ref="accountFormRef"
            :model="accountForm"
            :rules="accountRules"
            class="space-y-6"
            size="large"
          >
            <el-form-item prop="username">
              <el-input
                v-model="accountForm.username"
                placeholder="请输入用户名"
                :prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="accountForm.password"
                type="password"
                placeholder="请输入密码"
                :prefix-icon="Lock"
                show-password
              />
            </el-form-item>
            <el-button type="primary" class="w-full" @click="handleAccountLogin">
              登录
            </el-button>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { User, Lock, Key } from '@element-plus/icons-vue'

// 登录方式选项
const loginOptions = [
  {
    value: 'auth',
    label: '授权码登录',
    icon: Key
  },
  {
    value: 'account',
    label: '账号密码登录',
    icon: User
  }
]

// 登录方式
const loginType = ref('auth')

// 授权码表单
const authFormRef = ref()
const authForm = reactive({
  authCode: ''
})
const authRules = {
  authCode: [
    { required: true, message: '请输入授权码', trigger: 'blur' },
    { min: 6, max: 20, message: '授权码长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

// 账号密码表单
const accountFormRef = ref()
const accountForm = reactive({
  username: '',
  password: ''
})
const accountRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

// 授权码登录处理
const handleAuthLogin = async () => {
  if (!authFormRef.value) return
  await authFormRef.value.validate((valid) => {
    if (valid) {
      console.log('授权码登录', authForm)
      // TODO: 实现登录逻辑
    }
  })
}

// 账号密码登录处理
const handleAccountLogin = async () => {
  if (!accountFormRef.value) return
  await accountFormRef.value.validate((valid) => {
    if (valid) {
      console.log('账号密码登录', accountForm)
      // TODO: 实现登录逻辑
    }
  })
}
</script>

<style scoped lang="scss">

</style>
