package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.CanInfo;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReadCanInfoBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ReadCanInfoBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "canInfo", "Lcom/thor/businessmodule/bluetooth/model/other/CanInfo;", "getCanInfo", "()Lcom/thor/businessmodule/bluetooth/model/other/CanInfo;", "setCanInfo", "(Lcom/thor/businessmodule/bluetooth/model/other/CanInfo;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadCanInfoBleResponse extends BaseBleResponse {
    private CanInfo canInfo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadCanInfoBleResponse(byte[] bytes) {
        super(bytes);
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        setCommand((short) 74);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final CanInfo getCanInfo() {
        return this.canInfo;
    }

    public final void setCanInfo(CanInfo canInfo) {
        this.canInfo = canInfo;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.canInfo = new CanInfo(BleHelper.takeShort(bytes[2], bytes[3]), BleHelper.takeShort(bytes[4], bytes[5]));
        }
    }
}
