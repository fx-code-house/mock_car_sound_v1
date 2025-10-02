package com.thor.app.service.models.response;

import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ServiceHardwareResponse.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/thor/app/service/models/response/ServiceHardwareResponse;", "", "data", "", "([B)V", "hardwareProfile", "Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "getHardwareProfile", "()Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "setHardwareProfile", "(Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;)V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ServiceHardwareResponse {
    private HardwareProfile hardwareProfile;

    public ServiceHardwareResponse(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.hardwareProfile = new HardwareProfile(BleHelper.takeShort(data[2], data[3]), BleHelper.takeShort(data[4], data[5]), BleHelper.takeShort(data[6], data[7]), null, 8, null);
    }

    public final HardwareProfile getHardwareProfile() {
        return this.hardwareProfile;
    }

    public final void setHardwareProfile(HardwareProfile hardwareProfile) {
        Intrinsics.checkNotNullParameter(hardwareProfile, "<set-?>");
        this.hardwareProfile = hardwareProfile;
    }
}
