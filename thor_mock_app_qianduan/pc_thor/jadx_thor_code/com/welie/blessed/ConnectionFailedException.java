package com.welie.blessed;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConnectionFailedException.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/welie/blessed/ConnectionFailedException;", "Ljava/lang/RuntimeException;", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/HciStatus;", "(Lcom/welie/blessed/HciStatus;)V", "getStatus", "()Lcom/welie/blessed/HciStatus;", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ConnectionFailedException extends RuntimeException {
    private final HciStatus status;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectionFailedException(HciStatus status) {
        super("connection failed: " + status + " (" + status.getValue() + ")");
        Intrinsics.checkNotNullParameter(status, "status");
        this.status = status;
    }

    public final HciStatus getStatus() {
        return this.status;
    }
}
