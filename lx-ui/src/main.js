import { createApp } from 'vue';
import '@/styles/tailwind.css';
import App from '@/App.vue';
import store from './store';
import router from './router';
import '@/styles/element-plus-theme/index.scss';
import '@/styles/dark.scss';
import '@/styles/index.scss';
import 'bytemd/dist/index.css';
import 'animate.css';
import Particles from '@tsparticles/vue3';
import { loadSlim } from '@tsparticles/slim';
import installer from './as-needed';

const app = createApp(App);
app.use(store);
app.use(router);
app.use(installer);
app.use(Particles, {
  init: async (engine) => {
    await loadSlim(engine);
  }
});

app.mount('#app');
