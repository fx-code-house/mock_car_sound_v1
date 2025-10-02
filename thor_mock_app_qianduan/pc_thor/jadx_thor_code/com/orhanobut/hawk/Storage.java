package com.orhanobut.hawk;

/* loaded from: classes2.dex */
public interface Storage {
    boolean contains(String str);

    long count();

    boolean delete(String str);

    boolean deleteAll();

    <T> T get(String str);

    <T> boolean put(String str, T t);
}
