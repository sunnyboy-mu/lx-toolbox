import { isExternalLink } from '@/utils/common';
import { ref } from 'vue';

const iconfontData = ref([]);

export const useIconfont = () => {
  function parserIconfontSource(url) {
    if (!url || !isExternalLink(url)) return;
    fetch(url)
      .then((r) => r.text())
      .then((data) => {
        iconfontData.value = [
          ...new Set(
            Array.from(
              data.matchAll(/\.(lx-[^:]+):before/g),
              (match) => match[1]
            )
          )
        ];
      });
  }

  return {
    iconfontData,
    parserIconfontSource
  };
};
