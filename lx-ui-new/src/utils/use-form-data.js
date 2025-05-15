import { reactive } from 'vue';
import { cloneDeep, set as setValue } from 'lodash-es';

/**
 * 表单数据hook
 * @param initValue 初始值
 */
export function useFormData(init) {
  let initValue = init == null ? {} : cloneDeep(init);
  /** 表单数据 */
  const form = reactive(init == null ? {} : { ...init });

  /** 重置为初始值 */
  const resetFields = (field, excludes) => {
    const keys = Object.keys(form);
    if (typeof field === 'string' && field) {
      if (keys.includes(field)) {
        form[field] = cloneDeep(initValue[field]);
      }
      return;
    }
    keys.forEach((key) => {
      if (!excludes || !excludes.includes(key)) {
        form[key] = cloneDeep(initValue[key]);
      }
    });
  };

  /** 赋值不改变字段 */
  const assignFields = (data, excludes) => {
    if (excludes === true) {
      initValue = data == null ? {} : cloneDeep(data);
    }
    Object.keys(form).forEach((key) => {
      if (!excludes || excludes === true || !excludes.includes(key)) {
        form[key] = data?.[key];
      }
    });
  };

  /** 赋值某字段 */
  const setFieldValue = (field, value) => {
    //form[field] = value;
    setValue(form, field, value);
  };

  const result = [form, resetFields, assignFields, setFieldValue];
  // 支持对象解构以兼容旧版
  Object.assign(result, {
    form,
    resetFields,
    assignFields
  });
  return result;
}
