package com.thor.app.serviceg;

import com.thor.businessmodule.bluetooth.request.other.BaseBleRequest;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BleParseRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lcom/thor/app/serviceg/BleParseRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "commandRequest", "", "data", "", "(S[B)V", "getData", "()[B", "getBody", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BleParseRequest extends BaseBleRequest {
    private final byte[] data;

    public final byte[] getData() {
        return this.data;
    }

    public BleParseRequest(short s, byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        setCommand(Short.valueOf(s));
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody, reason: from getter */
    public byte[] getData() {
        return this.data;
    }
}
