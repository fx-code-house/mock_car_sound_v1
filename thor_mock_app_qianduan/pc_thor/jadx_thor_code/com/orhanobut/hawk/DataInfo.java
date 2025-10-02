package com.orhanobut.hawk;

/* loaded from: classes2.dex */
final class DataInfo {
    static final char TYPE_LIST = '1';
    static final char TYPE_MAP = '2';
    static final char TYPE_OBJECT = '0';
    static final char TYPE_SET = '3';
    final String cipherText;
    final char dataType;
    final Class keyClazz;
    final Class valueClazz;

    DataInfo(char c, String str, Class cls, Class cls2) {
        this.cipherText = str;
        this.keyClazz = cls;
        this.valueClazz = cls2;
        this.dataType = c;
    }
}
