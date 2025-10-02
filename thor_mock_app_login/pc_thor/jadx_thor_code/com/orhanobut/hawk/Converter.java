package com.orhanobut.hawk;

/* loaded from: classes2.dex */
interface Converter {
    <T> T fromString(String str, DataInfo dataInfo) throws Exception;

    <T> String toString(T t);
}
