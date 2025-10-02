package com.orhanobut.hawk;

/* loaded from: classes2.dex */
public interface Serializer {
    DataInfo deserialize(String str);

    <T> String serialize(String str, T t);
}
