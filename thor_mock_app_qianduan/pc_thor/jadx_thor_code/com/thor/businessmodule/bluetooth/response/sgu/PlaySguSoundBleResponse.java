package com.thor.businessmodule.bluetooth.response.sgu;

import com.thor.businessmodule.bluetooth.response.other.BaseBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: PlaySguSoundBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/sgu/PlaySguSoundBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "<set-?>", "", "soundFileId", "getSoundFileId", "()S", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PlaySguSoundBleResponse extends BaseBleResponse {
    private short soundFileId;

    public PlaySguSoundBleResponse(byte[] bArr) {
        super(bArr);
        setCommand((short) 34);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final short getSoundFileId() {
        return this.soundFileId;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        try {
            byte[] bytes = getBytes();
            if (bytes != null) {
                this.soundFileId = BleHelper.takeShort(bytes[2], bytes[3]);
            }
        } catch (Exception unused) {
            doHandleError();
        }
    }
}
