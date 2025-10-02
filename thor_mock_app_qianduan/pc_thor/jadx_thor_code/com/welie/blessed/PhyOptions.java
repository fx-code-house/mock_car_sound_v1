package com.welie.blessed;

import kotlin.Metadata;

/* compiled from: PhyOptions.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/welie/blessed/PhyOptions;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "NO_PREFERRED", "S2", "S8", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum PhyOptions {
    NO_PREFERRED(0),
    S2(1),
    S8(2);

    private final int value;

    PhyOptions(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
