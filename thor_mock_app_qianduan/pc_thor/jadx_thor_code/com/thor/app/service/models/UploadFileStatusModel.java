package com.thor.app.service.models;

import com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/* compiled from: UploadFileStatusModel.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/service/models/UploadFileStatusModel;", "", "data", "", "([B)V", "uploadStatusModel", "Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "getUploadStatusModel", "()Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadFileStatusModel {
    private final DownloadGetStatusModel uploadStatusModel;

    public UploadFileStatusModel(byte[] data) {
        DownloadGetStatusModel.Status status;
        Intrinsics.checkNotNullParameter(data, "data");
        short sTakeShort = BleHelper.takeShort(data[2], data[3]);
        DownloadGetStatusModel.Status[] statusArrValues = DownloadGetStatusModel.Status.values();
        int length = statusArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                status = null;
                break;
            }
            status = statusArrValues[i];
            if (status.getIdStatus() == sTakeShort) {
                break;
            } else {
                i++;
            }
        }
        this.uploadStatusModel = new DownloadGetStatusModel(status, Short.valueOf(BleHelper.takeShort(data[4], data[5])), BleHelper.parseIdFile(ArraysKt.sliceArray(data, new IntRange(6, 9))), BleHelperKt.toInt(ArraysKt.sliceArray(data, new IntRange(10, 13))));
    }

    public final DownloadGetStatusModel getUploadStatusModel() {
        return this.uploadStatusModel;
    }
}
