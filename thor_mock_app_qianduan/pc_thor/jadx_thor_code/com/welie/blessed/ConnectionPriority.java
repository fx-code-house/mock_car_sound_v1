package com.welie.blessed;

import kotlin.Metadata;

/* compiled from: ConnectionPriority.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/welie/blessed/ConnectionPriority;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "BALANCED", "HIGH", "LOW_POWER", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum ConnectionPriority {
    BALANCED(0),
    HIGH(1),
    LOW_POWER(2);

    private final int value;

    ConnectionPriority(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
