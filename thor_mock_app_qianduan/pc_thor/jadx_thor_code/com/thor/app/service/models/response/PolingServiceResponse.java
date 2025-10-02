package com.thor.app.service.models.response;

import androidx.core.app.NotificationCompat;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PolingServiceResponse.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0004\u0018\u0000 \t2\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lcom/thor/app/service/models/response/PolingServiceResponse;", "", "bytes", "", "([B)V", NotificationCompat.CATEGORY_STATUS, "", "getStatus", "()S", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PolingServiceResponse {
    public static final int POLLING_FAIL = 2;
    public static final int POLLING_SUCCESS = 0;
    public static final int POLLING_WAITE = 1;
    private final short status;

    public PolingServiceResponse(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.status = BleHelper.takeShort(bytes[2], bytes[3]);
    }

    public final short getStatus() {
        return this.status;
    }
}
