package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import timber.log.Timber;

/* compiled from: ActivatePresetBleResponse.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ActivatePresetBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "indexPreset", "", "getIndexPreset", "()Ljava/lang/Short;", "setIndexPreset", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "isError", "", "()Z", "setError", "(Z)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ActivatePresetBleResponse extends BaseBleResponse {
    private Short indexPreset;
    private boolean isError;

    public final Short getIndexPreset() {
        return this.indexPreset;
    }

    public final void setIndexPreset(Short sh) {
        this.indexPreset = sh;
    }

    /* renamed from: isError, reason: from getter */
    public final boolean getIsError() {
        return this.isError;
    }

    public final void setError(boolean z) {
        this.isError = z;
    }

    public ActivatePresetBleResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 69);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.isError = BleHelper.takeShort(ArraysKt.plus(new byte[1], ArraysKt.copyOfRange(bytes, 0, 1))) == 128;
            this.indexPreset = Short.valueOf(BleHelper.takeShort(bytes[2], bytes[3]));
        }
    }
}
