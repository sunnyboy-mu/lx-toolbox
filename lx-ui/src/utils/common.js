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
