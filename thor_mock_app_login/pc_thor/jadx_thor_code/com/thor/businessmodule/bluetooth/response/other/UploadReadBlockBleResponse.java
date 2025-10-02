package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleCommands;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadReadBlockBleResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0004¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/UploadReadBlockBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "bytesData", "getBytesData", "()[B", "setBytesData", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadReadBlockBleResponse extends BaseBleResponse {
    private byte[] bytesData;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UploadReadBlockBleResponse(byte[] bytes) {
        super(bytes);
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        setCommand(BleCommands.COMMAND_UPLOAD_READ_BLOCK);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final byte[] getBytesData() {
        return this.bytesData;
    }

    public final void setBytesData(byte[] bArr) {
        this.bytesData = bArr;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.bytesData = ArraysKt.copyOfRange(bytes, 4, bytes.length);
        }
    }
}
