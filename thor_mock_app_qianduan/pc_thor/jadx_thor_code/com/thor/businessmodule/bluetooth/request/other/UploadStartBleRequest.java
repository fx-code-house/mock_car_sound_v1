package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

/* compiled from: UploadStartBleRequest.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/UploadStartBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "fileID", "", "maxBlockSize", "", "(IS)V", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadStartBleRequest extends BaseBleRequest {
    private final int fileID;
    private final short maxBlockSize;

    public UploadStartBleRequest(int i, short s) {
        this.fileID = i;
        this.maxBlockSize = s;
        setCommand((short) 128);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ArraysKt.toList(BleHelper.convertIntToByteArray(Integer.valueOf(this.fileID))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.maxBlockSize))));
        return CollectionsKt.toByteArray(arrayList);
    }
}
