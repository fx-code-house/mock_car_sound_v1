package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

/* compiled from: ApplyUploadSoundPackageBleRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/ApplyUploadSoundPackageBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "packageId", "", "versionId", "(SS)V", "getPackageId", "()S", "getVersionId", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ApplyUploadSoundPackageBleRequest extends BaseBleRequest {
    private final short packageId;
    private final short versionId;

    public ApplyUploadSoundPackageBleRequest(short s, short s2) {
        this.packageId = s;
        this.versionId = s2;
        setCommand((short) 100);
    }

    public final short getPackageId() {
        return this.packageId;
    }

    public final short getVersionId() {
        return this.versionId;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.packageId))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.versionId))));
        return CollectionsKt.toByteArray(arrayList);
    }
}
