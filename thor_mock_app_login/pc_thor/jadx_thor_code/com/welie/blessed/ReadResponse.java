package com.welie.blessed;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReadResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/welie/blessed/ReadResponse;", "", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/GattStatus;", "value", "", "(Lcom/welie/blessed/GattStatus;[B)V", "getStatus", "()Lcom/welie/blessed/GattStatus;", "getValue", "()[B", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadResponse {
    private final GattStatus status;
    private final byte[] value;

    public ReadResponse(GattStatus status, byte[] value) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(value, "value");
        this.status = status;
        this.value = value;
    }

    public final GattStatus getStatus() {
        return this.status;
    }

    public final byte[] getValue() {
        return this.value;
    }
}
