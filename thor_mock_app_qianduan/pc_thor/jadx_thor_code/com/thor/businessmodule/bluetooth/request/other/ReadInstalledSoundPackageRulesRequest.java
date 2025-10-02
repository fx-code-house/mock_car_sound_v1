package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackage;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReadInstalledSoundPackageRulesRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/ReadInstalledSoundPackageRulesRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "installedSoundPackage", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "(Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;)V", "getInstalledSoundPackage", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadInstalledSoundPackageRulesRequest extends BaseBleRequest {
    private final InstalledSoundPackage installedSoundPackage;

    public ReadInstalledSoundPackageRulesRequest(InstalledSoundPackage installedSoundPackage) {
        Intrinsics.checkNotNullParameter(installedSoundPackage, "installedSoundPackage");
        this.installedSoundPackage = installedSoundPackage;
        setCommand((short) 52);
    }

    public final InstalledSoundPackage getInstalledSoundPackage() {
        return this.installedSoundPackage;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf(this.installedSoundPackage.getPackageId()));
        byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf(this.installedSoundPackage.getMode()));
        for (byte b : bArrConvertShortToByteArray) {
            arrayList.add(Byte.valueOf(b));
        }
        for (byte b2 : bArrConvertShortToByteArray2) {
            arrayList.add(Byte.valueOf(b2));
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
