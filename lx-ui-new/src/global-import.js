/** 全局安装(开发环境) */
import ElementPlus from 'element-plus/es';
import 'element-plus/theme-chalk/src/index.scss';

const installer = {
  install(app) {
    app.use(ElementPlus);
  }
};

export default installer;
