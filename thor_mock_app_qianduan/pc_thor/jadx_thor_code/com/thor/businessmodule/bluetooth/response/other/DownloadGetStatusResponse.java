package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/* compiled from: DownloadGetStatusResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/DownloadGetStatusResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "inputBytes", "", "([B)V", "responseModel", "Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "getResponseModel", "()Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DownloadGetStatusResponse extends BaseBleResponse {
    private final DownloadGetStatusModel responseModel;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DownloadGetStatusResponse(byte[] inputBytes) {
        super(inputBytes);
        Intrinsics.checkNotNullParameter(inputBytes, "inputBytes");
        this.responseModel = new DownloadGetStatusModel(null, null, null, 0, 15, null);
        setCommand(BleCommands.COMMAND_DOWNLOAD_GET_STATUS);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final DownloadGetStatusModel getResponseModel() {
        return this.responseModel;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        DownloadGetStatusModel.Status status;
        byte[] bytes = getBytes();
        if (bytes != null) {
            short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
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
            short sTakeShort2 = BleHelper.takeShort(bytes[4], bytes[5]);
            byte[] bArrSliceArray = ArraysKt.sliceArray(bytes, new IntRange(6, 9));
            int i2 = BleHelperKt.toInt(ArraysKt.sliceArray(bytes, new IntRange(10, 13)));
            this.responseModel.setStatus(status);
            this.responseModel.setErrorCode(Short.valueOf(sTakeShort2));
            this.responseModel.setId(BleHelper.parseIdFile(bArrSliceArray));
            this.responseModel.setCount(i2);
        }
    }
}
