package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: WriteLockDeviceBleRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteLockDeviceBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "lock", "", "(Z)V", "lockDevice", "", "unlockDevice", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteLockDeviceBleRequest extends BaseBleRequest {
    private final boolean lock;
    private final short lockDevice = 1;
    private final short unlockDevice = 20344;

    public WriteLockDeviceBleRequest(boolean z) {
        this.lock = z;
        setCommand((short) 12);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        return this.lock ? BleHelper.convertShortToByteArray(Short.valueOf(this.lockDevice)) : BleHelper.convertShortToByteArray(Short.valueOf(this.unlockDevice));
    }
}
