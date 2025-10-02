package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class Collections {
    private Collections() {
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... tArr) {
        HashSet hashSet = new HashSet(tArr.length);
        java.util.Collections.addAll(hashSet, tArr);
        return hashSet;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(Collection<T> collection, T... tArr) {
        HashSet hashSet = new HashSet(collection.size() + tArr.length);
        java.util.Collections.addAll(hashSet, tArr);
        return hashSet;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(Collection<T> collection, Collection<T>... collectionArr) {
        HashSet hashSet = new HashSet(collection);
        for (Collection<T> collection2 : collectionArr) {
            hashSet.addAll(collection2);
        }
        return hashSet;
    }

    public static <T> T first(Collection<T> collection) {
        return collection.iterator().next();
    }

    public static <T> T last(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> List<T> join(List<T> list, List<T> list2) {
        ArrayList arrayList = new ArrayList(list.size() + list2.size());
        arrayList.addAll(list);
        arrayList.addAll(list2);
        return arrayList;
    }

    public static <K, V> Map.Entry<K, V> first(Map<K, V> map) {
        return map.entrySet().iterator().next();
    }

    public static <K, V> V firstValue(Map<K, V> map) {
        return (V) first(map).getValue();
    }

    public static <K, V> K firstKey(Map<K, V> map) {
        return (K) first(map).getKey();
    }
}
