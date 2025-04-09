package cn.mu00.tools.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortExchangeUtil {

    /**
     * 执行排序交换
     *
     * @param ids           需要排序的ID列表
     * @param list          需要排序的数据
     * @param idGetter      获取实体ID的函数
     * @param sortGetter    获取实体排序值的函数
     * @param entityCreator 创建新实体的函数
     * @param idSetter      设置实体ID的函数
     * @param sortSetter    设置实体排序值的函数
     * @return 排序后的实体列表
     */
    public static <T, ID, SORT extends Comparable<SORT>> List<T> exchangeSort(
            List<ID> ids,
            List<T> list,
            Function<T, ID> idGetter,
            Function<T, SORT> sortGetter,
            Supplier<T> entityCreator,
            BiFunction<T, ID, T> idSetter,
            BiFunction<T, SORT, T> sortSetter
    ) {
        // 获取数据并构建映射
        Map<ID, T> entityMap = list.stream()
                .collect(Collectors.toMap(idGetter, Function.identity()));

        // 按IDs顺序收集排序值
        List<SORT> sortValues = new ArrayList<>(ids.size());
        for (ID id : ids) {
            T entity = entityMap.get(id);
            if (entity == null) {
                throw new IllegalArgumentException("无效的ID: " + id);
            }
            sortValues.add(sortGetter.apply(entity));
        }

        // 排序
        Collections.sort(sortValues);

        // 构建更新后的实体列表
        return IntStream.range(0, ids.size())
                .mapToObj(i -> {
                    T entity = entityCreator.get();
                    idSetter.apply(entity, ids.get(i));
                    sortSetter.apply(entity, sortValues.get(i));
                    return entity;
                })
                .collect(Collectors.toList());
    }
    /**
     * 执行排序交换（简化版本，适用于标准的getId/setId/getSort/setSort方法）
     *
     * @param ids           需要排序的ID列表
     * @param list          需要排序的数据
     * @param entityCreator 创建新实体的函数
     * @return 排序后的实体列表
     */
    public static <T, ID, SORT extends Comparable<SORT>> List<T> exchangeSort(
            List<ID> ids,
            List<T> list,
            Supplier<T> entityCreator
    ) {
        // 使用反射获取和设置ID和排序值
        try {
            // 获取数据并构建映射
            Map<ID, T> entityMap = list.stream()
                    .collect(Collectors.toMap(
                            entity -> getPropertyValue(entity, "getId"),
                            Function.identity()));

            // 按IDs顺序收集排序值
            List<SORT> sortValues = new ArrayList<>(ids.size());
            for (ID id : ids) {
                T entity = entityMap.get(id);
                if (entity == null) {
                    throw new IllegalArgumentException("无效的ID: " + id);
                }
                sortValues.add(getPropertyValue(entity, "getSort"));
            }

            // 排序
            Collections.sort(sortValues);

            // 构建更新后的实体列表
            return IntStream.range(0, ids.size())
                    .mapToObj(i -> {
                        T entity = entityCreator.get();
                        setPropertyValue(entity, "setId", ids.get(i));
                        setPropertyValue(entity, "setSort", sortValues.get(i));
                        return entity;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("实体类必须包含getId、setId、getSort和setSort方法", e);
        }
    }

    /**
     * 通过反射获取属性值
     */
    @SuppressWarnings("unchecked")
    private static <T, R> R getPropertyValue(T entity, String methodName) {
        try {
            return (R) entity.getClass().getMethod(methodName).invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("无法调用方法: " + methodName, e);
        }
    }

    /**
     * 通过反射设置属性值
     */
    private static <T, V> void setPropertyValue(T entity, String methodName, V value) {
        try {
            entity.getClass().getMethod(methodName, value.getClass()).invoke(entity, value);
        } catch (Exception e) {
            throw new RuntimeException("无法调用方法: " + methodName, e);
        }
    }
}