package com.thor.businessmodule.bluetooth.response.sgu;

import com.thor.businessmodule.bluetooth.model.other.WriteSguSoundResponse;
import com.thor.businessmodule.bluetooth.response.other.BaseBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: WriteSguSoundBleResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/sgu/WriteSguSoundBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "writeSguSoundsResponse", "Lcom/thor/businessmodule/bluetooth/model/other/WriteSguSoundResponse;", "getWriteSguSoundsResponse", "()Lcom/thor/businessmodule/bluetooth/model/other/WriteSguSoundResponse;", "setWriteSguSoundsResponse", "(Lcom/thor/businessmodule/bluetooth/model/other/WriteSguSoundResponse;)V", "doHandleResponse", "", "isUploadingCommand", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteSguSoundBleResponse extends BaseBleResponse {
    private WriteSguSoundResponse writeSguSoundsResponse;

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public boolean isUploadingCommand() {
        return true;
    }

    public WriteSguSoundBleResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 39);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final WriteSguSoundResponse getWriteSguSoundsResponse() {
        return this.writeSguSoundsResponse;
    }

    public final void setWriteSguSoundsResponse(WriteSguSoundResponse writeSguSoundResponse) {
        this.writeSguSoundsResponse = writeSguSoundResponse;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        try {
            byte[] bytes = getBytes();
            if (bytes != null) {
                this.writeSguSoundsResponse = new WriteSguSoundResponse(BleHelper.takeShort(bytes[2], bytes[3]), BleHelper.takeShort(bytes[4], bytes[5]), BleHelper.takeShort(bytes[6], bytes[7]));
            }
        } catch (Exception unused) {
            doHandleError();
        }
    }
}
