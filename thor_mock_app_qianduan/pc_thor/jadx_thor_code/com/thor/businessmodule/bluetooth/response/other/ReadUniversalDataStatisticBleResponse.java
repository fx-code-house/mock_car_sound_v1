package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.UniversalDataParameter;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReadUniversalDataStatisticBleResponse.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0016R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ReadUniversalDataStatisticBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "universalDataParameters", "", "Lcom/thor/businessmodule/bluetooth/model/other/UniversalDataParameter;", "getUniversalDataParameters", "()Ljava/util/List;", "setUniversalDataParameters", "(Ljava/util/List;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadUniversalDataStatisticBleResponse extends BaseBleResponse {
    private List<UniversalDataParameter> universalDataParameters;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadUniversalDataStatisticBleResponse(byte[] bytes) {
        super(bytes);
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        setCommand((short) 56);
        this.universalDataParameters = new ArrayList();
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final List<UniversalDataParameter> getUniversalDataParameters() {
        return this.universalDataParameters;
    }

    public final void setUniversalDataParameters(List<UniversalDataParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.universalDataParameters = list;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        this.universalDataParameters.clear();
        byte[] bytes = getBytes();
        if (bytes == null) {
            return;
        }
        short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
        short sTakeShort2 = BleHelper.takeShort(bytes[4], bytes[5]);
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        if (1 > sTakeShort2) {
            return;
        }
        int i = 1;
        int i2 = 6;
        while (true) {
            this.universalDataParameters.add(new UniversalDataParameter(sTakeShort, BleHelper.takeShort(bytes[i2], bytes[i2 + 1]), BleHelper.takeShort(bytes[i2 + 2], bytes[i2 + 3]), jCurrentTimeMillis));
            i2 += 4;
            if (i == sTakeShort2) {
                return;
            } else {
                i++;
            }
        }
    }
}
