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
