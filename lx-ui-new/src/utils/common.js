/**
 * 是否是外部链接
 * @param {String} url
 * @returns
 */
export function isExternalLink(url) {
  return !!(
    url &&
    (url.startsWith('http://') ||
      url.startsWith('https://') ||
      url.startsWith('//'))
  );
}

/**
 * 随机颜色
 * @returns
 */
export function randomHexColor() {
  return `#${Math.floor(Math.random() * 0xffffff).toString(16)}`;
}

/**
 * 防抖
 * @param {*} fn
 * @param {*} delay
 * @returns
 */
export function debounce(fn, delay) {
  // 定义一个定时器变量
  let timer = null;
  // 返回一个新的函数
  return function () {
    // 如果定时器存在，则清除定时器
    if (timer) clearTimeout(timer);
    // 设置一个新的定时器，延迟执行传入的函数
    timer = setTimeout(() => {
      // 使用apply方法将传入的参数传递给传入的函数
      fn.apply(this, arguments);
    }, delay);
  };
}

/**
 * 切换主题过渡动画
 * @param callback 执行的方法
 * @param el 过渡动画触发元素
 * @param isOut 是否是退出方向
 * @param isBody 是否在 body 上执行动画
 */
export function doWithTransition(callback, el, isOut, isBody) {
  if (!el || typeof document.startViewTransition !== 'function') {
    callback().then(() => {});
    return;
  }
  document.documentElement.classList.add('disabled-transition');
  el.classList.add('view-transition-trigger');
  el.style.setProperty('view-transition-name', 'view-transition-trigger');
  if (isBody) {
    document.body.style.setProperty('view-transition-name', 'body');
  }
  const rect = el.getBoundingClientRect();
  const x = rect.left + rect.width / 2;
  const y = rect.top + rect.height / 2;
  const endRadius = Math.hypot(
    Math.max(x, innerWidth - x),
    Math.max(y, innerHeight - y)
  );
  document.startViewTransition(callback).ready.then(() => {
    const clipPath = [
      `circle(0px at ${x}px ${y}px)`,
      `circle(${endRadius}px at ${x}px ${y}px)`
    ];
    const anim = document.documentElement.animate(
      { clipPath: isOut ? [...clipPath].reverse() : clipPath },
      {
        duration: 400,
        easing: 'ease-in',
        pseudoElement: isOut
          ? `::view-transition-old(${isBody ? 'body' : 'root'})`
          : `::view-transition-new(${isBody ? 'body' : 'root'})`
      }
    );
    anim.onfinish = () => {
      document.body.style.removeProperty('view-transition-name');
      el.style.removeProperty('view-transition-name');
      el.classList.remove('view-transition-trigger');
      document.documentElement.classList.remove('disabled-transition');
    };
  });
}
