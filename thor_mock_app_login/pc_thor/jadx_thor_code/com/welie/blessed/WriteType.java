package com.welie.blessed;

import kotlin.Metadata;

/* compiled from: WriteType.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lcom/welie/blessed/WriteType;", "", "writeType", "", "property", "(Ljava/lang/String;III)V", "getProperty", "()I", "getWriteType", "WITH_RESPONSE", "WITHOUT_RESPONSE", "SIGNED", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum WriteType {
    WITH_RESPONSE(2, 8),
    WITHOUT_RESPONSE(1, 4),
    SIGNED(4, 64);

    private final int property;
    private final int writeType;

    WriteType(int i, int i2) {
        this.writeType = i;
        this.property = i2;
    }

    public final int getProperty() {
        return this.property;
    }

    public final int getWriteType() {
        return this.writeType;
    }
}
