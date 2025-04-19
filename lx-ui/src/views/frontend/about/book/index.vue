<template>
  <div class="container mx-auto bg-gray-100 p-4 flex flex-col gap-4">
    <div
      class="bg-white rounded-md p-4 text-center text-2xl font-bold font-zwzt relative"
    >
      “翻开书页，打开世界——每一本书都是通向未知自我的秘密通道。”
      <el-button
        v-role="'admin'"
        :icon="Plus"
        class="absolute right-4 top-1/2 -translate-y-1/2"
        @click="openEdit()"
      >
        添加
      </el-button>
    </div>
    <div class="rounded-md" v-for="group in books" :key="group.year">
      <h2
        class="text-3xl font-bold mb-4 font-zwzt flex items-center justify-center select-none bg-white rounded-md py-4"
      >
        <i class="iconfont icon-shudan mr-2"></i>
        <span>Year {{ group.year }}</span>
      </h2>
      <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-4">
        <div
          class="bg-white shadow-md rounded-md p-4 duration-300 h-72 flex flex-col overflow-hidden hover:shadow-lg hover:-translate-y-2 group"
          v-for="book in group.books"
          :key="book.id"
        >
          <h3
            class="truncate text-center text-primary/70 duration-300 hover:text-primary mb-4 shrink-0"
          >
            <a
              :href="book.link ?? 'javascript:;'"
              :title="book.name"
              :target="book.link ? '_blank' : ''"
            >
              《{{ book.name }}》
            </a>
          </h3>
          <div class="flex flex-1 overflow-hidden">
            <div class="w-36 shrink-0 p-2 mr-4 perspective-midrange">
              <div class="book-cover">
                <el-image
                  :src="book.cover"
                  class="book-cover-img"
                  lazy
                ></el-image>
              </div>
            </div>
            <div class="flex-1 overflow-hidden flex flex-col">
              <el-text tag="div" class="!mb-4 !self-start"
                >作者: {{ book.author }}</el-text
              >
              <el-text tag="div" class="!mb-4 !self-start"
                >阅读进度:
                <el-tag
                  disable-transitions
                  type="success"
                  size="small"
                  v-if="book.status === 0"
                >
                  已读
                </el-tag>
                <el-tag disable-transitions size="small" v-else
                  >温习中...</el-tag
                >
              </el-text>
              <el-text
                tag="div"
                line-clamp="4"
                class="leading-6 indent-[2em]"
                >{{ book.description }}</el-text
              >
              <div
                v-role="'admin'"
                class="mt-auto justify-end items-center hidden group-hover:flex"
              >
                <el-button
                  :icon="Edit"
                  @click="openEdit(book)"
                  circle
                ></el-button>
                <el-popconfirm
                  width="220"
                  title="你确定要删除此书籍吗?"
                  @confirm="remove(book)"
                >
                  <template #reference>
                    <el-button :icon="Delete" circle></el-button>
                  </template>
                  <template #actions="{ confirm, cancel }">
                    <el-button size="small" @click="cancel">No!</el-button>
                    <el-button type="danger" size="small" @click="confirm">
                      Yes?
                    </el-button>
                  </template>
                </el-popconfirm>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <BookEdit v-model="visible" :data="current" @done="reload" />
  </div>
</template>
<script setup>
  import { ref } from 'vue';
  import { deteleBook, listBookGroupByYear } from '@/api/book';
  import { Delete, Edit, Plus } from '@element-plus/icons-vue';
  import { ElMessage } from 'element-plus/es';
  import BookEdit from './components/BookEdit.vue';

  const books = ref([]);

  const loading = ref(true);

  const reload = () => {
    listBookGroupByYear().then((data) => {
      loading.value = true;
      books.value = data;
      loading.value = false;
    });
  };

  const visible = ref(false);
  const current = ref(null);

  const remove = (row) => {
    deteleBook(row.id).then(() => {
      loading.value = true;
      ElMessage.success('删除成功');
      reload();
    });
  };

  const openEdit = (row) => {
    current.value = row ?? null;
    visible.value = true;
  };

  reload();
</script>
<style lang="scss" scoped>
  .book-cover {
    position: relative;
    transform-style: preserve-3d;
    transform: rotateY(-30deg);
    transition: 1s;
    &::before {
      content: ' ';
      position: absolute;
      left: 0;
      top: 2px;
      width: 23px;
      height: 166px;
      transform: translateX(118.5px) rotateY(90deg);
      background: linear-gradient(
        90deg,
        #fff,
        #f9f9f9 5%,
        #fff 10%,
        #f9f9f9 15%,
        #fff 20%,
        #f9f9f9 25%,
        #fff 30%,
        #f9f9f9 35%,
        #fff 40%,
        #f9f9f9 45%,
        #fff 50%,
        #f9f9f9 55%,
        #fff 60%,
        #f9f9f9 65%,
        #fff 70%,
        #f9f9f9 75%,
        #fff 80%,
        #f9f9f9 85%,
        #fff 90%,
        #f9f9f9 95%,
        #fff
      );
    }
    &::after {
      content: ' ';
      position: absolute;
      top: 0;
      left: 0;
      width: 132px;
      height: 170px;
      transform: translateZ(-12.5px);
      background-color: #555;
      border-radius: 0 2px 2px 0;
    }
    .book-cover-img {
      position: absolute;
      top: 0;
      left: 0;
      width: 132px;
      height: 170px;
      transform: translateZ(12.5px);
      border-radius: 0 2px 2px 0;
      box-shadow: 5px 5px 10px #666;
    }
  }
</style>
