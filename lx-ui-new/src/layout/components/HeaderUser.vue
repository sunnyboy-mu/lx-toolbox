<template>
  <div
    class="h-8 w-8 rounded-md duration-300 hover:bg-primary-900 cursor-pointer"
  >
    <el-dropdown v-if="loagined" @command="handleCommand">
      <div
        class="h-8 w-8 rounded-md duration-300 hover:bg-primary-900 cursor-pointer"
      >
        <div class="w-full h-full flex justify-center items-center">
          <el-avatar
            style="--el-avatar-bg-color: transparent"
            :size="24"
            :src="user.avatar"
          />
        </div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <!-- <el-dropdown-item command="admin" :icon="Position">
            后台
          </el-dropdown-item> -->
          <el-dropdown-item command="logout" :icon="SwitchButton">
            退出
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <el-tooltip v-else content="登录" placement="bottom" effect="light">
      <div
        class="w-full h-full flex justify-center items-center"
        @click="visible = true"
      >
        <el-icon> <User /> </el-icon>
      </div>
    </el-tooltip>
    <el-dialog
      v-model="visible"
      top="10vh"
      append-to-body
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :width="mobile ? '90%' : '960px'"
    >
      <LoginForm />
    </el-dialog>
  </div>
</template>
<script setup>
  import { useUserStore } from '@/store/module/user';
  import { User, SwitchButton, Position } from '@element-plus/icons-vue';
  import { ref } from 'vue';
  import LoginForm from '@/components/LoginForm/index.vue';
  import { useMobile } from '@/utils/use-mobile';
  import { storeToRefs } from 'pinia';
  import { ElMessageBox } from 'element-plus';
  const { mobile } = useMobile();

  const { loagined, user } = storeToRefs(useUserStore());

  function handleCommand(command) {
    switch (command) {
      case 'logout':
        ElMessageBox.confirm('确定要退出登录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            useUserStore().doUserLogout();
          })
          .catch(() => {});
        break;
      default:
        break;
    }
  }

  const visible = ref(false);
</script>
