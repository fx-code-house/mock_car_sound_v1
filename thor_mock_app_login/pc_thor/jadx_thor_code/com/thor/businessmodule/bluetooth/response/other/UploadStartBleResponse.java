package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadStartBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\n¨\u0006\u0010"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/UploadStartBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "fileSize", "", "getFileSize", "()I", "setFileSize", "(I)V", "maxBlockSize", "getMaxBlockSize", "setMaxBlockSize", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadStartBleResponse extends BaseBleResponse {
    private int fileSize;
    private int maxBlockSize;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UploadStartBleResponse(byte[] bytes) {
        super(bytes);
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        setCommand((short) 128);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final int getFileSize() {
        return this.fileSize;
    }

    public final void setFileSize(int i) {
        this.fileSize = i;
    }

    public final int getMaxBlockSize() {
        return this.maxBlockSize;
    }

    public final void setMaxBlockSize(int i) {
        this.maxBlockSize = i;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes == null || bytes.length <= 4) {
            return;
        }
        this.fileSize = BleHelperKt.toInt(ArraysKt.copyOfRange(bytes, 2, 6));
        this.maxBlockSize = BleHelper.takeShort(ArraysKt.copyOfRange(bytes, 6, 8));
    }
}
