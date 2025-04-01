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
