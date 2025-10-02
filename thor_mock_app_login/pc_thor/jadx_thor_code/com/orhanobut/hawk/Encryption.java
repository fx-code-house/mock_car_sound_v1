package com.orhanobut.hawk;

/* loaded from: classes2.dex */
interface Encryption {
    String decrypt(String str, String str2) throws Exception;

    String encrypt(String str, String str2) throws Exception;

    boolean init();
}
