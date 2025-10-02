package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

/* compiled from: ApplyUpdateFirmwareBleRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\b\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/ApplyUpdateFirmwareBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequestOld;", "deviceId", "", "versionHW", "versionFW", "(SSS)V", "getDeviceId", "()S", "getVersionFW", "getVersionHW", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ApplyUpdateFirmwareBleRequest extends BaseBleRequestOld {
    private final short deviceId;
    private final short versionFW;
    private final short versionHW;

    public final short getDeviceId() {
        return this.deviceId;
    }

    public final short getVersionHW() {
        return this.versionHW;
    }

    public final short getVersionFW() {
        return this.versionFW;
    }

    public ApplyUpdateFirmwareBleRequest(short s, short s2, short s3) {
        this.deviceId = s;
        this.versionHW = s2;
        this.versionFW = s3;
        setCommand((short) 81);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequestOld, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.deviceId))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.versionHW))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.versionFW))));
        return CollectionsKt.toByteArray(arrayList);
    }
}
