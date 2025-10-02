package com.thor.businessmodule.bluetooth.response.sgu;

import com.thor.businessmodule.bluetooth.model.sgu.UploadingSguSoundStatus;
import com.thor.businessmodule.bluetooth.response.other.BaseBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: StartWriteSguSoundBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016R\"\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/sgu/StartWriteSguSoundBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "<set-?>", "Lcom/thor/businessmodule/bluetooth/model/sgu/UploadingSguSoundStatus;", "uploadingSguSoundStatus", "getUploadingSguSoundStatus", "()Lcom/thor/businessmodule/bluetooth/model/sgu/UploadingSguSoundStatus;", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class StartWriteSguSoundBleResponse extends BaseBleResponse {
    private UploadingSguSoundStatus uploadingSguSoundStatus;

    public StartWriteSguSoundBleResponse(byte[] bArr) {
        super(bArr);
        setCommand((short) 38);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final UploadingSguSoundStatus getUploadingSguSoundStatus() {
        return this.uploadingSguSoundStatus;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        try {
            byte[] bytes = getBytes();
            if (bytes != null) {
                this.uploadingSguSoundStatus = new UploadingSguSoundStatus(BleHelper.takeShort(bytes[2], bytes[3]), BleHelper.takeShort(bytes[4], bytes[5]), BleHelper.takeShort(bytes[6], bytes[7]));
            }
        } catch (Exception unused) {
            doHandleError();
        }
    }
}
