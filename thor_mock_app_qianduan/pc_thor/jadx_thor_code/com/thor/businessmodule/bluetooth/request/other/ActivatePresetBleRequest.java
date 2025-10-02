package com.thor.businessmodule.bluetooth.request.other;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: ActivatePresetBleRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/ActivatePresetBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", FirebaseAnalytics.Param.INDEX, "", "(S)V", "getIndex", "()S", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ActivatePresetBleRequest extends BaseBleRequest {
    private final short index;

    public ActivatePresetBleRequest(short s) {
        this.index = s;
        setCommand((short) 69);
    }

    public final short getIndex() {
        return this.index;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        return BleHelper.convertShortToByteArray(Short.valueOf(this.index));
    }
}
