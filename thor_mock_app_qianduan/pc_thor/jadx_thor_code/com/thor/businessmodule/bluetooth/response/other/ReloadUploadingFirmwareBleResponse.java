package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.ReloadUploadingFirmwareStatus;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: ReloadUploadingFirmwareBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ReloadUploadingFirmwareBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponseOld;", "bytes", "", "([B)V", "reloadUploadingFirmwareStatus", "Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingFirmwareStatus;", "getReloadUploadingFirmwareStatus", "()Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingFirmwareStatus;", "setReloadUploadingFirmwareStatus", "(Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingFirmwareStatus;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReloadUploadingFirmwareBleResponse extends BaseBleResponseOld {
    private ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus;

    public ReloadUploadingFirmwareBleResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 79);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final ReloadUploadingFirmwareStatus getReloadUploadingFirmwareStatus() {
        return this.reloadUploadingFirmwareStatus;
    }

    public final void setReloadUploadingFirmwareStatus(ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus) {
        this.reloadUploadingFirmwareStatus = reloadUploadingFirmwareStatus;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponseOld
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.reloadUploadingFirmwareStatus = new ReloadUploadingFirmwareStatus(BleHelper.takeShort(bytes[2], bytes[3]), BleHelper.takeShort(bytes[4], bytes[5]), BleHelper.takeShort(bytes[6], bytes[7]), BleHelper.takeShort(bytes[8], bytes[9]), BleHelper.takeShort(bytes[10], bytes[11]));
        }
    }
}
