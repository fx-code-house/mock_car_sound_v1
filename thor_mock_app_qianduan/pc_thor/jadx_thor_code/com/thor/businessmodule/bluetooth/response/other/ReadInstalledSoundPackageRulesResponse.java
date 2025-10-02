package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.InstalledPresetRules;
import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackage;
import com.thor.businessmodule.bluetooth.model.other.PresetRule;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: ReadInstalledSoundPackageRulesResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ReadInstalledSoundPackageRulesResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "installedPresetRules", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "getInstalledPresetRules", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "setInstalledPresetRules", "(Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadInstalledSoundPackageRulesResponse extends BaseBleResponse {
    private InstalledPresetRules installedPresetRules;

    public ReadInstalledSoundPackageRulesResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 52);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final InstalledPresetRules getInstalledPresetRules() {
        return this.installedPresetRules;
    }

    public final void setInstalledPresetRules(InstalledPresetRules installedPresetRules) {
        this.installedPresetRules = installedPresetRules;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            try {
                ArrayList arrayList = new ArrayList();
                short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
                short sTakeShort2 = BleHelper.takeShort(bytes[4], bytes[5]);
                int iTakeShort = BleHelper.takeShort(bytes[6], bytes[7]);
                int i = 1;
                if (1 <= iTakeShort) {
                    while (true) {
                        int i2 = i + 1;
                        int i3 = i2 * 4;
                        arrayList.add(new PresetRule(BleHelper.takeShort(bytes[i3], bytes[i3 + 1]), BleHelper.takeShort(bytes[i3 + 2], bytes[i3 + 3])));
                        if (i == iTakeShort) {
                            break;
                        } else {
                            i = i2;
                        }
                    }
                }
                this.installedPresetRules = new InstalledPresetRules(new InstalledSoundPackage(sTakeShort, (short) 0, sTakeShort2), arrayList);
            } catch (Exception e) {
                Timber.e(e);
            }
        }
    }
}
