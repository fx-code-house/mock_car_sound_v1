package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: InstalledPresetsResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/InstalledPresetsResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "getInstalledPresets", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class InstalledPresetsResponse extends BaseBleResponse {
    private final InstalledPresets installedPresets;

    public InstalledPresetsResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 49);
        this.installedPresets = new InstalledPresets((short) 0, (short) 0, null, 7, null);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            try {
                doHandleResponse();
            } catch (Exception e) {
                Timber.e(e);
                setStatus(false);
            }
        }
    }

    public final InstalledPresets getInstalledPresets() {
        return this.installedPresets;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.installedPresets.setCurrentPresetId(BleHelper.takeShort(bytes[2], bytes[3]));
            short sTakeShort = BleHelper.takeShort(bytes[4], bytes[5]);
            this.installedPresets.setAmount(sTakeShort);
            Timber.i("Amount: %s", Short.valueOf(sTakeShort));
            if (sTakeShort == 0) {
                return;
            }
            if (1 <= sTakeShort) {
                int i = 1;
                int i2 = 6;
                while (true) {
                    short sTakeShort2 = BleHelper.takeShort(bytes[i2], bytes[i2 + 1]);
                    short sTakeShort3 = BleHelper.takeShort(bytes[i2 + 2], bytes[i2 + 3]);
                    short sTakeShort4 = BleHelper.takeShort(bytes[i2 + 4], bytes[i2 + 5]);
                    i2 += 6;
                    this.installedPresets.getPresets().add(new InstalledPreset(sTakeShort2, sTakeShort3, sTakeShort4));
                    if (i == sTakeShort) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            Timber.i("Installed presets: %s", this.installedPresets);
        }
    }
}
