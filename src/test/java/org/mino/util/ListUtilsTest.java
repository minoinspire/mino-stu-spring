package org.mino.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUtilsTest {

    @Test
    @DisplayName("intersection: 基本交集与顺序保持")
    void testIntersection() {
        List<Integer> a = Arrays.asList(1, 2, 3, 4);
        List<Integer> b = Arrays.asList(3, 4, 5, 6);
        List<Integer> res = ListUtils.intersection(a, b);
        assertEquals(Arrays.asList(3, 4), res);
    }

    @Test
    @DisplayName("union: 基本并集与去重顺序")
    void testUnion() {
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(3, 4, 5);
        List<Integer> res = ListUtils.union(a, b);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), res);
    }

    @Test
    @DisplayName("difference: 基本差集")
    void testDifference() {
        List<Integer> a = Arrays.asList(1, 2, 3, 4);
        List<Integer> b = Arrays.asList(2, 4, 6);
        List<Integer> res = ListUtils.difference(a, b);
        assertEquals(Arrays.asList(1, 3), res);
    }

    @Test
    @DisplayName("unionNonNull: 过滤空后并集")
    void testUnionNonNull() {
        List<String> a = Arrays.asList("a", null, "b");
        List<String> b = Arrays.asList(null, "b", "c");
        List<String> res = ListUtils.unionNonNull(a, b);
        assertEquals(Arrays.asList("a", "b", "c"), res);
    }

    @Test
    @DisplayName("空集合与null容错")
    void testNullSafety() {
        assertEquals(Collections.emptyList(), ListUtils.intersection(null, null));
        assertEquals(Collections.emptyList(), ListUtils.difference(null, List.of(1)));
        assertEquals(Collections.singletonList(1), ListUtils.union(List.of(1), null));
    }
}
