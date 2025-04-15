import { visit } from 'unist-util-visit';
import { clipboardCheckIcon, foldBtn, newSvgIcon, successTip } from './icons';
import './styles/index.css';
import { copyToClipboard } from './utils';

export default function codeCopyPlugin() {
  return {
    rehype: (processor) =>
      processor.use(() => (tree) => {
        visit(tree, 'element', (node) => {
          if (node.tagName === 'pre') {
            node.properties.style = 'padding-top: 3rem;';
            const codeNode = node.children.find(
              (child) => child.tagName === 'code'
            );
            const language =
              codeNode?.properties?.className
                ?.find((cls) => cls.startsWith('language-'))
                ?.replace('language-', '') || 'text';

            if (codeNode) {
              node.children.unshift({
                type: 'element',
                tagName: 'div',
                properties: {
                  className: ['code-block-extension-header']
                },
                children: [
                  {
                    type: 'element',
                    tagName: 'div',
                    properties: {
                      className: ['code-block-extension-headerLeft']
                    },
                    children: [
                      {
                        type: 'element',
                        tagName: 'div',
                        properties: {
                          className: ['code-block-extension-foldBtn']
                        },
                        children: [
                          {
                            type: 'text',
                            value: '▼'
                          }
                        ]
                      },
                      {
                        type: 'element',
                        tagName: 'span',
                        properties: {
                          className: ['code-block-extension-lang']
                        },
                        children: [{ type: 'text', value: language }]
                      }
                    ]
                  },
                  {
                    type: 'element',
                    tagName: 'div',
                    properties: {
                      className: ['code-block-extension-headerRight'],
                      style: 'cursor: pointer;'
                    },

                    children: [
                      {
                        type: 'element',
                        tagName: 'div',
                        properties: {
                          className: ['code-block-extension-copyCodeBtn'],
                          style: 'filter: invert(0.5); opacity: 0.6;'
                        },
                        children: [{ type: 'text', value: '复制代码' }]
                      }
                    ]
                  }
                ]
              });

              node.properties = {
                ...node.properties
              };
            }
          }
        });
      }),

    viewerEffect({ markdownBody }) {
      const copyButtons = markdownBody.querySelectorAll(
        '.code-block-extension-copyCodeBtn'
      );
      const foldButtons = markdownBody.querySelectorAll(
        '.code-block-extension-foldBtn'
      );

      copyButtons.forEach((button) => {
        button.addEventListener('click', () => {
          const pre = button.closest('pre');
          const code = pre?.querySelector('code')?.textContent || '';
          copyToClipboard(code);

          const tmp = button.innerHTML;
          button.innerHTML = clipboardCheckIcon + successTip;
          setTimeout(() => {
            button.innerHTML = tmp;
          }, 1500);
        });
      });

      // 处理折叠按钮的点击事件，实现旋转
      foldButtons.forEach((foldButton) => {
        foldButton.addEventListener('click', () => {
          foldButton.classList.toggle('code-block-extension-fold'); // 切换折叠类名
          // 找到最近的 pre 标签
          const pre = foldButton.closest('pre');
          if (pre) {
            if (pre.style.paddingTop === '1em') {
              pre.style.paddingTop = '3em'; // 恢复原来的 padding
            } else {
              pre.style.paddingTop = '1em'; // 设置 padding 为 0
            }
          }

          // 在 pre 标签下找到 code 标签
          const code = pre?.querySelector('code');
          // 切换 code 标签的类名
          if (code) {
            code.classList.toggle('code-block-extension-fold');
          }

          // 在 pre 标签下找到 code-block-extension-header
          const headerElement = pre?.querySelector(
            '.code-block-extension-header'
          );

          // 切换 code-block-extension-header 的类名
          if (headerElement) {
            headerElement.classList.toggle('code-block-extension-fold');
          }
        });
      });
    }
  };
}
