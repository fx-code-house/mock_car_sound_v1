package com.thor.businessmodule.bus.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ResponseErrorCodeEvent.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bus/events/ResponseErrorCodeEvent;", "", "byteArray", "", "([B)V", "getByteArray", "()[B", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ResponseErrorCodeEvent {
    private final byte[] byteArray;

    public ResponseErrorCodeEvent(byte[] byteArray) {
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        this.byteArray = byteArray;
    }

    public final byte[] getByteArray() {
        return this.byteArray;
    }
}
