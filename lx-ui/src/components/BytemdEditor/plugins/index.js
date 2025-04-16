import breaks from '@bytemd/plugin-breaks';
import gfm from '@bytemd/plugin-gfm';
import highlight from '@bytemd/plugin-highlight';
import alignPlugin from './plugin-align/index';
import codeCopyPlugin from './plugin-code-copy';
import catalogPlugin from './plugin-catalog';

// 参考逻辑 自行实现
// import mediumZoom from '@bytemd/plugin-medium-zoom';
import gfm_zh_Hans from '@bytemd/plugin-gfm/locales/zh_Hans.json';

export default [
  breaks(),
  alignPlugin(),
  gfm({ locale: gfm_zh_Hans }),
  highlight(),
  // codeCopyPlugin(),
  catalogPlugin()
  // mediumZoom()
];
