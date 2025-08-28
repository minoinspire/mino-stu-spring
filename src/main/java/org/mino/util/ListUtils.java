package org.mino.util;

import java.util.*;

/**
 * 列表工具类：交集、并集、差集
 */
public final class ListUtils {

    private ListUtils() {}

    /**
     * 取交集（唯一，保持按第一个列表出现顺序）
     */
    public static <T> List<T> intersection(Collection<T> first, Collection<T> second) {
        if (first == null || second == null) {
            return new ArrayList<>();
        }
        Set<T> secondSet = new LinkedHashSet<>(second);
        Set<T> result = new LinkedHashSet<>();
        for (T item : first) {
            if (secondSet.contains(item)) {
                result.add(item);
            }
        }
        return new ArrayList<>(result);
    }

    /**
     * 取并集（唯一，保持先first后second的出现顺序）
     */
    public static <T> List<T> union(Collection<T> first, Collection<T> second) {
        Set<T> result = new LinkedHashSet<>();
        if (first != null) {
            result.addAll(first);
        }
        if (second != null) {
            result.addAll(second);
        }
        return new ArrayList<>(result);
    }

    /**
     * 取差集（first中存在且second中不存在，唯一，保持first顺序）
     */
    public static <T> List<T> difference(Collection<T> first, Collection<T> second) {
        if (first == null) {
            return new ArrayList<>();
        }
        Set<T> exclude = new LinkedHashSet<>();
        if (second != null) {
            exclude.addAll(second);
        }
        Set<T> result = new LinkedHashSet<>();
        for (T item : first) {
            if (!exclude.contains(item)) {
                result.add(item);
            }
        }
        return new ArrayList<>(result);
    }

    /**
     * 便捷方法：过滤空值后取并集
     */
    public static <T> List<T> unionNonNull(Collection<T> first, Collection<T> second) {
        List<T> a = new ArrayList<>();
        if (first != null) {
            for (T t : first) if (Objects.nonNull(t)) a.add(t);
        }
        List<T> b = new ArrayList<>();
        if (second != null) {
            for (T t : second) if (Objects.nonNull(t)) b.add(t);
        }
        return union(a, b);
    }
}
