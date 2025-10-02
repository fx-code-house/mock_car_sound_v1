package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WriteInstalledPresetsBleRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteInstalledPresetsBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "(Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;)V", "getInstalledPresets", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteInstalledPresetsBleRequest extends BaseBleRequest {
    private final InstalledPresets installedPresets;

    public WriteInstalledPresetsBleRequest(InstalledPresets installedPresets) {
        Intrinsics.checkNotNullParameter(installedPresets, "installedPresets");
        this.installedPresets = installedPresets;
        setCommand((short) 48);
    }

    public final InstalledPresets getInstalledPresets() {
        return this.installedPresets;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        for (byte b : BleHelper.convertShortToByteArray(Short.valueOf(((InstalledPreset) CollectionsKt.first(this.installedPresets.getPresets())).getPackageId()))) {
            arrayList.add(Byte.valueOf(b));
        }
        for (byte b2 : BleHelper.convertShortToByteArray(Short.valueOf(this.installedPresets.getAmount()))) {
            arrayList.add(Byte.valueOf(b2));
        }
        for (InstalledPreset installedPreset : this.installedPresets.getPresets()) {
            for (byte b3 : BleHelper.convertShortToByteArray(Short.valueOf(installedPreset.getPackageId()))) {
                arrayList.add(Byte.valueOf(b3));
            }
            for (byte b4 : BleHelper.convertShortToByteArray(Short.valueOf(installedPreset.getMode()))) {
                arrayList.add(Byte.valueOf(b4));
            }
            for (byte b5 : BleHelper.convertShortToByteArray(Short.valueOf(installedPreset.getActivity()))) {
                arrayList.add(Byte.valueOf(b5));
            }
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
