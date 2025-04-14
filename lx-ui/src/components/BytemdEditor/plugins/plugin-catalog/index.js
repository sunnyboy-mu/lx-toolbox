import { visit } from 'unist-util-visit';
import { stringifyHeading } from './utils';

const HEAD_TAG_NAME = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];

/**
 * 目录
 */
export default function catalogPlugin() {
  return {
    rehype: (processor) =>
      processor.use(() => (tree) => {
        visit(tree, 'element', (node) => {
          if (!HEAD_TAG_NAME.includes(node.tagName)) return;
          const title = stringifyHeading(node);
          const id = node.properties.title
            ? `${node.properties.title} ${title}`
            : title;
          node.properties = {
            ...node.properties,
            'head-id': id,
            id: id
          };
        });
      })
  };
}
