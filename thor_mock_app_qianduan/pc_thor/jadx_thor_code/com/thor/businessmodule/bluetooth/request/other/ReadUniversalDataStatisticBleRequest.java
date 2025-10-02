package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* compiled from: ReadUniversalDataStatisticBleRequest.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/ReadUniversalDataStatisticBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "dataGroup", "", "(S)V", "getBody", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadUniversalDataStatisticBleRequest extends BaseBleRequest {
    public static final short dataGroupDevice = 2;
    public static final short dataGroupDiagnostic = 1;
    public static final short dataGroupStatistics = 3;
    private final short dataGroup;

    public ReadUniversalDataStatisticBleRequest(short s) {
        this.dataGroup = s;
        setCommand((short) 56);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        for (byte b : BleHelper.convertShortToByteArray(Short.valueOf(this.dataGroup))) {
            arrayList.add(Byte.valueOf(b));
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
