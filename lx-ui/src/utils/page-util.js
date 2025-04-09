import { BLOG_ICON } from '@/enum/common-config';
import { isExternalLink } from './common';
import { ElMessage } from 'element-plus/es';
import { useIconfont } from '@/composables/use-iconfont';

export async function injectionBlogIconfontCss(data) {
  if (
    !data ||
    data.configKey !== BLOG_ICON ||
    !data.configValue ||
    !isExternalLink(data.configValue)
  ) {
    ElMessage.error('博客图标配置错误！');
    return;
  }

  useIconfont().parserIconfontSource(data.configValue);

  // 删除旧link
  document.querySelector(`#${BLOG_ICON}`)?.remove();
  // 添加新link
  const iconCssLink = document.createElement('link');
  iconCssLink.rel = 'stylesheet';
  iconCssLink.href = data.configValue;
  iconCssLink.id = BLOG_ICON;
  document.head.appendChild(iconCssLink);
}
