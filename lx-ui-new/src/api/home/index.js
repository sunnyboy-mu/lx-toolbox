/**
 * 每日一言
 */
export const hitokoto = async () => {
  const res = await fetch('https://v1.hitokoto.cn/');
  const data = await res.json();
  return data;
};
