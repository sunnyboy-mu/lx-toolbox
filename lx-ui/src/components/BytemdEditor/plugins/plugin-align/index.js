import { ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT } from './icons';
import zh_Hans from './localels/zh_Hans.json';

/**
 * 对齐方式插件
 * @param {object} options
 * @param {object?} options.locale
 */
export default function alignPlugin(options) {
  const locale = { ...zh_Hans, ...options?.locale };

  return {
    actions: [
      {
        title: locale.alignType,
        icon: ALIGN_CENTER,
        handler: {
          type: 'dropdown',
          actions: [
            {
              title: locale.alignTypeLeft,
              icon: ALIGN_LEFT,
              handler: {
                type: 'action',
                click: (ctx) => {
                  ctx.wrapText('<p align="left">', '</p>');
                  ctx.editor.focus();
                }
              }
            },
            {
              title: locale.alignTypeCenter,
              icon: ALIGN_CENTER,
              handler: {
                type: 'action',
                click: (ctx) => {
                  ctx.wrapText('<p align="center">', '</p>');
                  ctx.editor.focus();
                }
              }
            },
            {
              title: locale.alignTypeRight,
              icon: ALIGN_RIGHT,
              handler: {
                type: 'action',
                click: (ctx) => {
                  ctx.wrapText('<p align="right">', '</p>');
                  ctx.editor.focus();
                }
              }
            }
          ]
        }
      }
    ]
  };
}
