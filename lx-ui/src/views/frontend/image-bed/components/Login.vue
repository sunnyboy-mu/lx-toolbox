<template>
  <div>
    <div class="flex items-center">
      <el-avatar
        :size="32"
        :src="
          userInfo?.avatar ||
          'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        "
        :style="{ '--el-avatar-bg-color': '#ffffff' }"
        @click="handleClick"
        class="cursor-pointer"
      />
      <span class="ml-2 text-gray-600 text-sm cursor-pointer">{{
        userInfo?.nickname || '未登录'
      }}</span>
    </div>

    <el-dialog v-model="loginDialogVisible">
      <LoginForm />
    </el-dialog>
  </div>
</template>

<script setup>
  import { useUserStore } from '@/store/module/user';
  import { storeToRefs } from 'pinia';
  import LoginForm from '@/components/LoginForm/index.vue';

  const { userInfo, isLoggedIn, loginDialogVisible } =
    storeToRefs(useUserStore());

  const handleClick = () => {
    if (isLoggedIn.value) return;
    loginDialogVisible.value = true;
  };
</script>
