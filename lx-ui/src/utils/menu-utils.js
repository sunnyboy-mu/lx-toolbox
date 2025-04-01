import adminRoutes from '@/router/admin-routes';

/**
 * 特殊菜单
 */
const SPECIAL_MENUS = [];

/**
 * 生成前台菜单
 * @param {Object} systemInfo
 * @returns
 */
export function generateFrontEndMenu(systemInfo) {
  const menu = [];

  menu.push({
    title: '首页',
    path: '/',
    icon: 'icon-home'
  });

  menu.push({
    title: '图床',
    path: '/fe/image-bed',
    icon: 'icon-image'
  });

  if (systemInfo?.bookmark?.length) {
    menu.push(generateBookmarkMenu(systemInfo.bookmark));
  }

  return menu;
}

/**
 * 生成后台菜单
 * @param {Object} systemInfo
 * @returns
 */
export function generateAdminMenu(systemInfo) {
  const menu = [];

  adminRoutes.forEach((route) => {
    route.children?.forEach((child) => {
      menu.push({
        title: child.meta.title,
        path: `${route.path}/${child.path}`,
        icon: child.meta.icon
      });
    });
  });

  return menu;
}

/**
 * 生成书签菜单
 * @param {Array} data
 * @returns
 */
function generateBookmarkMenu(data = []) {
  return {
    title: '书签',
    path: '/fe/bookmark',
    icon: 'icon-daohang',
    children: data.map((v, i) => ({
      title: v.name,
      path: `/fe/bookmark/${v.id}`,
      icon: v.icon ?? ['icon-kaifa', 'icon-zonghekongzhitai'][i % 2]
    }))
  };
}
