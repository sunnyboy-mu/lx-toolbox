export const stringifyHeading = (node) => {
  return node.children
    ?.filter((v) => v.type == 'text')
    .map((v) => v.value)
    .join('');
};
