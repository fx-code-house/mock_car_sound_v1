package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceId.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u0006HÆ\u0003J\u0013\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceId;", "", "data", "", "([B)V", "deviceId", "", "(Ljava/lang/String;)V", "getDeviceId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DeviceId {
    private final String deviceId;

    public static /* synthetic */ DeviceId copy$default(DeviceId deviceId, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = deviceId.deviceId;
        }
        return deviceId.copy(str);
    }

    /* renamed from: component1, reason: from getter */
    public final String getDeviceId() {
        return this.deviceId;
    }

    public final DeviceId copy(String deviceId) {
        Intrinsics.checkNotNullParameter(deviceId, "deviceId");
        return new DeviceId(deviceId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DeviceId) && Intrinsics.areEqual(this.deviceId, ((DeviceId) other).deviceId);
    }

    public int hashCode() {
        return this.deviceId.hashCode();
    }

    public String toString() {
        return "DeviceId(deviceId=" + this.deviceId + ")";
    }

    public DeviceId(String deviceId) {
        Intrinsics.checkNotNullParameter(deviceId, "deviceId");
        this.deviceId = deviceId;
    }

    public final String getDeviceId() {
        return this.deviceId;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeviceId(byte[] data) {
        this(BleHelperKt.toHex(data));
        Intrinsics.checkNotNullParameter(data, "data");
    }
}
