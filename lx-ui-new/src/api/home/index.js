/**
 * 每日一言
 */
export const hitokoto = async () => {
  const local = sessionStorage.getItem('hitokoto');
  if (local) {
    return JSON.parse(local);
  }
  const res = await fetch('https://v1.hitokoto.cn/');
  const data = await res.json();
  sessionStorage.setItem('hitokoto', JSON.stringify(data));
  return data;
};
