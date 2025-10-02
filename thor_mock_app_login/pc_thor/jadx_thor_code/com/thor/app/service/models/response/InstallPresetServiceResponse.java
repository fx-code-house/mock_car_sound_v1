package com.thor.app.service.models.response;

import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstallPresetServiceResponse.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/service/models/response/InstallPresetServiceResponse;", "", "data", "", "([B)V", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "getInstalledPresets", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class InstallPresetServiceResponse {
    private final InstalledPresets installedPresets;

    public InstallPresetServiceResponse(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        InstalledPresets installedPresets = new InstalledPresets((short) 0, (short) 0, null, 7, null);
        this.installedPresets = installedPresets;
        installedPresets.setCurrentPresetId(BleHelper.takeShort(data[2], data[3]));
        short sTakeShort = BleHelper.takeShort(data[4], data[5]);
        installedPresets.setAmount(sTakeShort);
        if (sTakeShort == 0) {
            return;
        }
        int i = 1;
        if (1 > sTakeShort) {
            return;
        }
        int i2 = 6;
        while (true) {
            short sTakeShort2 = BleHelper.takeShort(data[i2], data[i2 + 1]);
            short sTakeShort3 = BleHelper.takeShort(data[i2 + 2], data[i2 + 3]);
            short sTakeShort4 = BleHelper.takeShort(data[i2 + 4], data[i2 + 5]);
            i2 += 6;
            this.installedPresets.getPresets().add(new InstalledPreset(sTakeShort2, sTakeShort3, sTakeShort4));
            if (i == sTakeShort) {
                return;
            } else {
                i++;
            }
        }
    }

    public final InstalledPresets getInstalledPresets() {
        return this.installedPresets;
    }
}
