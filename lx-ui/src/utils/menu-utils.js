import adminRoutes from '@/router/admin-routes';
import frontRoutes from '@/router/front-routes';

/**
 * 特殊菜单
 */
const SPECIAL_MENUS = ['bookmark'];

/**
 * 生成前台菜单
 * @param {Object} systemInfo
 * @returns
 */
export function generateFrontEndMenu(systemInfo) {
  const menu = generateMenu(frontRoutes, systemInfo);
  menu.unshift({
    title: '首页',
    path: '/',
    icon: 'icon-home'
  });
  return menu;
}

/**
 * 生成后台菜单
 * @param {Object} systemInfo
 * @returns
 */
export function generateAdminMenu(systemInfo) {
  return generateMenu(adminRoutes, systemInfo);
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

function generateMenu(routes, systemInfo, currentRoute) {
  const menu = [];
  routes.forEach((route) => {
    route.children?.forEach((child) => {
      const item = {
        title: child.meta.title,
        path: `${currentRoute ? currentRoute.path : route.path}/${child.path}`,
        icon: child.meta.icon
      };
      if (child.children) {
        item.children = generateMenu([child], systemInfo, item).flat();
      }
      if (SPECIAL_MENUS.some((v) => child.path.startsWith(v))) {
        handleSpecialMenu(item, systemInfo);
      }
      menu.push(item);
    });
  });
  return menu;
}

/**
 * 处理特殊菜单
 */
function handleSpecialMenu(route, systemInfo) {
  route.path = route.path.substring(route.path.lastIndexOf('/'), -1);

  const key = SPECIAL_MENUS.find((v) => route.path.includes(v));
  if (!systemInfo || !systemInfo[key] || !systemInfo[key]?.length) {
    return;
  }

  route.children = systemInfo[key].map((v) => ({
    title: v.name,
    path: `${route.path}/${v.id}`,
    icon: v.icon
  }));
}
