package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

/* compiled from: UploadReadBlockRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/UploadReadBlockRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "fileSize", "", "currentBlock", "maxBlockSize", "(III)V", "mTotalBlocks", "", "getMTotalBlocks", "()D", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadReadBlockRequest extends BaseBleRequest {
    private final int currentBlock;
    private final int fileSize;
    private final double mTotalBlocks;
    private final int maxBlockSize;

    public UploadReadBlockRequest(int i, int i2, int i3) {
        this.fileSize = i;
        this.currentBlock = i2;
        this.maxBlockSize = i3;
        this.mTotalBlocks = Math.ceil(i / i3);
        setCommand(Short.valueOf(BleCommands.COMMAND_UPLOAD_READ_BLOCK));
    }

    public final double getMTotalBlocks() {
        return this.mTotalBlocks;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        int i = this.fileSize;
        int i2 = this.maxBlockSize;
        int i3 = i % i2 == 0 ? i2 : i % i2;
        int i4 = this.currentBlock;
        if (i4 == ((int) this.mTotalBlocks)) {
            arrayList.addAll(ArraysKt.toList(BleHelper.convertIntToByteArray(Integer.valueOf(i3))));
        } else if (i4 == 0) {
            arrayList.addAll(ArraysKt.toList(BleHelper.convertIntToByteArray(0)));
        } else {
            arrayList.addAll(ArraysKt.toList(BleHelper.convertIntToByteArray(Integer.valueOf(i4 * i2))));
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
