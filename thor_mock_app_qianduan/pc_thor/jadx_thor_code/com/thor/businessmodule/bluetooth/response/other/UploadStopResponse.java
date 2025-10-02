package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleCommands;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadStopResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/UploadStopResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "inputBytes", "", "([B)V", "getInputBytes", "()[B", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadStopResponse extends BaseBleResponse {
    private final byte[] inputBytes;

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UploadStopResponse(byte[] inputBytes) {
        super(inputBytes);
        Intrinsics.checkNotNullParameter(inputBytes, "inputBytes");
        this.inputBytes = inputBytes;
        setCommand(BleCommands.COMMAND_UPLOAD_STOP);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final byte[] getInputBytes() {
        return this.inputBytes;
    }
}
