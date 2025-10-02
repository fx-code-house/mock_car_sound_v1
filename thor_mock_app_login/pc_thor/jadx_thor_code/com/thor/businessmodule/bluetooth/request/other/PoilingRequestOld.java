package com.thor.businessmodule.bluetooth.request.other;

import kotlin.Metadata;

/* compiled from: PoilingRequestOld.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/PoilingRequestOld;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequestOld;", "()V", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PoilingRequestOld extends BaseBleRequestOld {
    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequestOld, com.thor.businessmodule.bluetooth.request.other.BleRequest
    public byte[] getBody() {
        return new byte[0];
    }

    public PoilingRequestOld() {
        setCommand((short) 8);
    }
}
