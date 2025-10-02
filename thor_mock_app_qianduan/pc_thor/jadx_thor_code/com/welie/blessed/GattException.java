package com.welie.blessed;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GattException.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/welie/blessed/GattException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/GattStatus;", "(Lcom/welie/blessed/GattStatus;)V", "getStatus", "()Lcom/welie/blessed/GattStatus;", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class GattException extends RuntimeException {
    private final GattStatus status;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GattException(GattStatus status) {
        super("GATT error " + status + " (" + status.getValue() + ")");
        Intrinsics.checkNotNullParameter(status, "status");
        this.status = status;
    }

    public final GattStatus getStatus() {
        return this.status;
    }
}
