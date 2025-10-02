package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: SelectInstalledPresetBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0016R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/SelectInstalledPresetBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "installedPresetId", "", "getInstalledPresetId", "()Ljava/lang/Short;", "setInstalledPresetId", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SelectInstalledPresetBleResponse extends BaseBleResponse {
    private Short installedPresetId;

    public SelectInstalledPresetBleResponse(byte[] bArr) {
        super(bArr);
        setCommand((short) 69);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final Short getInstalledPresetId() {
        return this.installedPresetId;
    }

    public final void setInstalledPresetId(Short sh) {
        this.installedPresetId = sh;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.installedPresetId = Short.valueOf(BleHelper.takeShort(bytes[2], bytes[3]));
        }
    }
}
