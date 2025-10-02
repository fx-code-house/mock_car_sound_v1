package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackage;
import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackages;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: InstalledSoundPackagesResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/InstalledSoundPackagesResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "packages", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackages;", "getPackages", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackages;", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class InstalledSoundPackagesResponse extends BaseBleResponse {
    private final InstalledSoundPackages packages;

    public final InstalledSoundPackages getPackages() {
        return this.packages;
    }

    public InstalledSoundPackagesResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 71);
        this.packages = new InstalledSoundPackages((short) 0, null, 3, null);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
            this.packages.setAmount(sTakeShort);
            Timber.i("Amount: %s", Short.valueOf(sTakeShort));
            if (sTakeShort == 0) {
                return;
            }
            if (1 <= sTakeShort) {
                int i = 4;
                int i2 = 1;
                while (true) {
                    short sTakeShort2 = BleHelper.takeShort(bytes[i], bytes[i + 1]);
                    short sTakeShort3 = BleHelper.takeShort(bytes[i + 2], bytes[i + 3]);
                    short sTakeShort4 = BleHelper.takeShort(bytes[i + 4], bytes[i + 5]);
                    i += 6;
                    this.packages.getSoundPackages().add(new InstalledSoundPackage(sTakeShort2, sTakeShort3, sTakeShort4));
                    if (i2 == sTakeShort) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            Timber.i("Packages: %s", this.packages);
        }
    }
}
