package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.ReloadUploadingSoundPackageStatus;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: ReloadUploadingSoundPackageBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ReloadUploadingSoundPackageBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "reloadUploadingSoundPackageStatus", "Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingSoundPackageStatus;", "getReloadUploadingSoundPackageStatus", "()Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingSoundPackageStatus;", "setReloadUploadingSoundPackageStatus", "(Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingSoundPackageStatus;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReloadUploadingSoundPackageBleResponse extends BaseBleResponse {
    private ReloadUploadingSoundPackageStatus reloadUploadingSoundPackageStatus;

    public ReloadUploadingSoundPackageBleResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand(BleCommands.COMMAND_RELOAD_UPLOADING_SOUND_PACKAGE);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final ReloadUploadingSoundPackageStatus getReloadUploadingSoundPackageStatus() {
        return this.reloadUploadingSoundPackageStatus;
    }

    public final void setReloadUploadingSoundPackageStatus(ReloadUploadingSoundPackageStatus reloadUploadingSoundPackageStatus) {
        this.reloadUploadingSoundPackageStatus = reloadUploadingSoundPackageStatus;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.reloadUploadingSoundPackageStatus = new ReloadUploadingSoundPackageStatus(BleHelper.takeShort(bytes[2], bytes[3]), BleHelper.takeShort(bytes[4], bytes[5]), BleHelper.takeShort(bytes[6], bytes[7]), BleHelper.takeShort(bytes[8], bytes[9]));
        }
    }
}
