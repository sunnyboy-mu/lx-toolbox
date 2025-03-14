<template>
  <div class="flex items-center">
    <el-avatar
      :size="32"
      :src="
        userInfo.avatar ||
        'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      "
      @click="showLoginDialog"
      class="cursor-pointer"
    />
    <span class="ml-2 text-gray-600 text-sm cursor-pointer">请登录</span>

    <el-dialog v-model="dialogVisible" title="用户登录" width="400px">
      <el-tabs v-model="activeTab" stretch>
        <el-tab-pane label="账号密码" name="password">
          <el-form :model="loginForm" label-width="80px" class="pt-4">
            <el-form-item label="账号">
              <el-input v-model="loginForm.username" placeholder="请输入账号" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input
                v-model="loginForm.password"
                type="password"
                show-password
              />
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="授权码" name="token">
          <el-form :model="tokenForm" label-width="80px" class="pt-4">
            <el-form-item label="授权码">
              <el-input v-model="tokenForm.token" placeholder="请输入授权码" />
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <template #footer>
        <el-button type="primary" @click="handleLogin">立即登录</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </template>
    </el-dialog>

    <el-dropdown v-if="isLoggedIn" @command="handleCommand">
      <div
        class="user-info flex items-center gap-2 p-2 rounded transition-colors hover:bg-primary/10"
      >
        <el-avatar :size="32" :src="userInfo.avatar" />
        <span class="nickname max-w-[120px] truncate">{{
          userInfo.nickname || '未命名用户'
        }}</span>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup>
  import { ref, reactive, computed } from 'vue';
  import { useUserStore } from '@/store/module/user';

  const userStore = useUserStore();
  const dialogVisible = ref(false);
  const activeTab = ref('password');

  const loginForm = reactive({
    username: '',
    password: ''
  });

  const tokenForm = reactive({
    token: ''
  });

  const isLoggedIn = computed(() => userStore.isLoggedIn);
  const userInfo = computed(() => userStore.userInfo);

  const showLoginDialog = () => {
    if (!isLoggedIn.value) {
      dialogVisible.value = true;
    }
  };

  const handleLogin = async () => {
    try {
      if (activeTab.value === 'password') {
        await userStore.loginByPassword(loginForm);
      } else {
        await userStore.loginByToken(tokenForm);
      }
      dialogVisible.value = false;
      ElMessage.success('登录成功');
    } catch (error) {
      ElMessage.error(error.message);
    }
  };

  const handleCommand = (command) => {
    if (command === 'logout') {
      userStore.logout();
      ElMessage.success('已退出登录');
    }
  };
</script>
