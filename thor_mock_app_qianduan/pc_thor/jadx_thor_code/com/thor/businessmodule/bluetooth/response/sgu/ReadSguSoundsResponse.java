package com.thor.businessmodule.bluetooth.response.sgu;

import com.thor.businessmodule.bluetooth.response.other.BaseBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: ReadSguSoundsResponse.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\n\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/sgu/ReadSguSoundsResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "soundIds", "", "", "getSoundIds", "()Ljava/util/List;", "<set-?>", "soundsAmount", "getSoundsAmount", "()S", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadSguSoundsResponse extends BaseBleResponse {
    private final List<Short> soundIds;
    private short soundsAmount;

    public ReadSguSoundsResponse(byte[] bArr) {
        super(bArr);
        this.soundIds = new ArrayList();
        Timber.i("init", new Object[0]);
        setCommand((short) 36);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final List<Short> getSoundIds() {
        return this.soundIds;
    }

    public final short getSoundsAmount() {
        return this.soundsAmount;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
            this.soundsAmount = sTakeShort;
            Timber.i("Amount: %s", Short.valueOf(sTakeShort));
            if (sTakeShort == 0) {
                return;
            }
            if (1 <= sTakeShort) {
                int i = 4;
                int i2 = 1;
                while (true) {
                    this.soundIds.add(Short.valueOf(BleHelper.takeShort(bytes[i], bytes[i + 1])));
                    i += 2;
                    if (i2 == sTakeShort) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            Timber.i("Packages: %s", this.soundIds);
        }
    }
}
