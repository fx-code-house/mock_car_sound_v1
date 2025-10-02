package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WriteLockDeviceBleResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/WriteLockDeviceBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteLockDeviceBleResponse extends BaseBleResponse {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteLockDeviceBleResponse(byte[] bytes) {
        super(bytes);
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        setCommand((short) 12);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            BleHelper.takeShort(bytes[2], bytes[3]);
        }
    }
}
