import { createApp } from 'vue';
import '@/styles/tailwind.css';
import App from '@/App.vue';
import store from './store';
import router from './router';
import '@/styles/element-plus-theme/index.scss';
import '@/styles/dark.scss';
import '@/styles/index.scss';
import 'animate.css';
import Particles from '@tsparticles/vue3';
import { loadSlim } from '@tsparticles/slim';
import installer from './as-needed';
import permission from './utils/permission';
import { useUserStore } from './store/module/user';
const app = createApp(App);
app.use(store);
app.use(router);
app.use(installer);
app.use(permission);

app.use(Particles, {
  init: async (engine) => {
    await loadSlim(engine);
  }
});

async function bootstrap() {
  await useUserStore().loadUserInfo();
  app.mount('#app');
}

bootstrap();
