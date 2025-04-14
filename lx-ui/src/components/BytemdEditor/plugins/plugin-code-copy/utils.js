/**
 * 复制
 * @param {String} text
 */
export const copyToClipboard = async (text) => {
  if (navigator.clipboard) {
    try {
      await navigator.clipboard.writeText(text);
      console.log('当前代码已复制到剪贴板');
    } catch (err) {
      console.error('复制代码失败，请手动复制');
      console.error('复制失败!', err);
    }
  } else {
    const textarea = document.createElement('textarea');
    textarea.value = text;
    document.body.appendChild(textarea);
    textarea.select();
    try {
      document.execCommand('copy');
      document.body.removeChild(textarea);
    } catch (err) {
      document.body.removeChild(textarea);
      console.error('无法复制到剪贴板', err);
    }
  }
};
