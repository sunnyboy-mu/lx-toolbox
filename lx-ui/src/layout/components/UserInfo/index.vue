<template>
  <div>
    <el-button v-if="!isLoggedIn" @click="loginDialogVisible = true">
      登录
    </el-button>

    <el-dropdown v-else @command="handleCommand">
      <div class="cursor-pointer flex items-center gap-1">
        <el-avatar
          :size="28"
          :src="
            userInfo?.avatar ||
            'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
          "
          :style="{ '--el-avatar-bg-color': '#ffffff' }"
        />
        <span class="flex items-center gap-1">
          <span class="font-[ZWZT]">
            {{ userInfo?.nickname || userInfo?.username }}
          </span>
          <el-icon>
            <arrow-down />
          </el-icon>
        </span>
      </div>

      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item
            :icon="Position"
            command="goToAdmin"
            v-if="!route.path.startsWith('/admin')"
          >
            前往后台
          </el-dropdown-item>
          <el-dropdown-item :icon="User" command="userInfo">
            个人信息
          </el-dropdown-item>
          <el-dropdown-item divided :icon="SwitchButton" command="logout">
            退出系统
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <!-- 登录弹窗 -->
    <el-dialog v-model="loginDialogVisible">
      <LoginForm />
    </el-dialog>
  </div>
</template>

<script setup>
  import {
    SwitchButton,
    Position,
    ArrowDown,
    User
  } from '@element-plus/icons-vue';
  import { useUserStore } from '@/store/module/user';
  import LoginForm from '@/components/LoginForm/index.vue';
  import { storeToRefs } from 'pinia';
  import { ElMessage, ElMessageBox } from 'element-plus/es';
  import { useRouter, useRoute } from 'vue-router';

  const route = useRoute();
  const router = useRouter();

  const userStore = useUserStore();

  const { isLoggedIn, userInfo, loginDialogVisible } = storeToRefs(userStore);

  const handleCommand = (command) => {
    switch (command) {
      case 'goToAdmin':
        router.push({ name: 'workplace' });
        break;
      case 'logout':
        handleLogout();
        break;
      default:
        ElMessage.warning('暂未开放');
        break;
    }
  };

  const handleLogout = () => {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        userStore.doUserLogout();
        router.push('/');
      })
      .catch(() => {});
  };
</script>
