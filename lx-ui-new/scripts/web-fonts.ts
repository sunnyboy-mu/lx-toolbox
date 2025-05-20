/**
 * @module web-fonts
 * @description Web 字体下载
 * 从 https://fonts.google.com/ 下载 Web 字体
 *
 * 1. 本地化字体文件
 * 2. 根据使用的字符，下载裁剪后的最小字体文件
 */
import path from 'node:path';
import fs from 'node:fs';
import { Buffer } from 'node:buffer';

/**
 * Checks if the input is a string
 * @category Is
 */
export function isString(v: unknown): v is string {
  return typeof v === 'string';
}

/**
 * Strict typed `Object.entries`
 *
 * @category Object
 * @example
 * ```ts
 * objectEntries({ a: 1, b: 2 }) // => [['a', 1], ['b', 2]]
 * ```
 */
export function objectEntries<T extends object>(
  obj: T
): Array<[keyof T, T[keyof T]]> {
  return Object.entries(obj) as Array<[keyof T, T[keyof T]]>;
}

/**
 * null or whatever
 * @category Types
 */
export type Nullable<T> = T | null | undefined;

/**
 * array or not yet
 * @category Types
 */
export type Arrayable<T> = T | T[];

/**
 * Checks if the input is an array
 * @category Is
 */
export function isArray<T>(v: unknown): v is T[] {
  return Array.isArray(v);
}

/**
 * Convert `Arrayable<T>` to `Array<T>`
 *
 * 将 `Arrayable<T>` 转换为 `Array<T>`
 *
 * @category Array
 * @example
 * ```ts
 * toArray(null) // => []
 * toArray(undefined) // => []
 * toArray([]) // => []
 * toArray(1) // => [1]
 * ```
 */
export function toArray<T>(v: Nullable<Arrayable<T>>): Array<T> {
  if (v === null || v === undefined) return [];
  if (isArray(v)) return v;
  return [v];
}

/**
 * Replace all backslashes with forward slashes
 *
 * 将所有反斜杠替换为正斜杠
 *
 * @category String
 * @example
 * ```ts
 * slash('foo\\bar') // => foo/bar
 * ```
 */
export function slash(s: string): string {
  return s.replace(/\\/g, '/');
}

/**
 * Unique array
 *
 * 数组去重
 *
 * @category Array
 * @example
 * ```ts
 * uniq([1, 1, 2, 2, 3, 3]) // => [1, 2, 3]
 * ```
 */
export function uniq<T>(v: T[]): T[] {
  return Array.from(new Set(v));
}

export interface Params {
  family: string;
  text?: string | string[];
  display?: 'block' | 'swap' | 'optional' | 'auto' | 'fallback';
  ital?: '0' | '1' | '01';
  wght?: `${number}` | `${number}..${number}`;
}

const EXT = {
  'font/woff2': 'woff2',
  'font/woff': 'woff',
  'font/ttf': 'ttf',
  'font/otf': 'otf',
  'font/eot': 'eot',
  'font/collection': 'ttc'
};

const stylesDir = path.join(import.meta.dirname, '../src/styles/');
const fontDir = path.join(stylesDir, '../fonts/');
const cssFilename = 'web-fonts.css';

const RE_FONT_URL = /url\((.*?)\)/g;

async function downloadWebFontCss(url: string, output: string): Promise<void>;
async function downloadWebFontCss(
  params: Params,
  output?: string
): Promise<void>;
async function downloadWebFontCss(
  params: Params | string,
  output?: string
): Promise<void> {
  const url = isString(params) ? params : getUrl(params);

  let filename =
    output ||
    (isString(params) ? cssFilename : params.family.replace(/\s+/g, '-'));

  filename = filename.endsWith('.css') ? filename : `${filename}.css`;

  console.log(
    `Downloading ${isString(params) ? url : `${params.family} text: ${toArray(params.text).join(', ')}`}`
  );

  const outputPath = path.join(stylesDir, filename);
  fs.mkdirSync(path.dirname(outputPath), { recursive: true });
  fs.mkdirSync(fontDir, { recursive: true });

  let css = await fetch(url).then((res) => res.text());

  let matched: RegExpExecArray | null;
  const fontsUrl = new Set<string>();

  while ((matched = RE_FONT_URL.exec(css))) {
    fontsUrl.add(matched[1]);
  }

  const basename = path.basename(outputPath, '.css');
  let uuid = 1;
  let cache: Record<string, string> = {};
  for (const url of fontsUrl) {
    const res = await fetch(url);
    const buffer = Buffer.from(await res.arrayBuffer());
    const ext = EXT[res.headers.get('content-type') ?? ''];
    cache[url] = `${basename}-${uuid++ * 100}.${ext}`;
    await fs.promises.writeFile(path.join(fontDir, cache[url]), buffer);
  }

  const fontPath = path.relative(path.dirname(outputPath), fontDir);

  objectEntries(cache).forEach(([key, value]) => {
    css = css.replaceAll(key, slash(path.join(fontPath, value)));
  });

  await fs.promises.writeFile(outputPath, css);
}

function getUrl(params: Params) {
  let family = params.family.replace(/\s/g, '+');

  const spec: string[] = [];

  params.ital && spec.push('ital');
  params.wght && spec.push('wght');

  if (spec.length > 0) {
    family += `:${spec.join(',')}@`;
    const ital = params.ital?.split('');
    const wght = params.wght;
    if (ital) {
      ital.forEach((v, i) => {
        family +=
          `${v}` + (wght ? `,${wght}` : '') + (i < ital.length - 1 ? ';' : '');
      });
    } else if (wght) {
      family += `${wght}`;
    }
  }
  let url = `https://fonts.googleapis.com/css2?family=${family}&display=${params.display ?? 'swap'}`;
  const text = toArray(params.text);
  if (text.length) {
    const format = uniq(text.join('').split('')).join('').replace(/\s+/g, '');
    url += `&text=${encodeURIComponent(format)}`;
  }

  return url;
}

downloadWebFontCss({
  family: 'Ma Shan Zheng',
  text: ['沐沐物语', '风吹樱花落，人与樱花错；相逢本无意，花落人终散。']
});

downloadWebFontCss({
  family: 'Pacifico',
  text: 'Moe Cyro'
});

downloadWebFontCss({
  family: 'Londrina Sketch',
  text: ['1234567890:']
});
