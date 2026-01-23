export const isDir = file => file.type === 'DIR';
export const joinPath = (path, name) => path ? `${path}\\${name}` : name;
export const filterFiles = files =>
  files.filter(f => !f.name.startsWith('.') && f.name !== 'node_modules');
