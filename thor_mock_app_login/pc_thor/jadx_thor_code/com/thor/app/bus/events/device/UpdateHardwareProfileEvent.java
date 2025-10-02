package com.thor.app.bus.events.device;

import com.google.android.gms.common.Scopes;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpdateHardwareProfileEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/device/UpdateHardwareProfileEvent;", "", Scopes.PROFILE, "Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "(Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;)V", "getProfile", "()Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UpdateHardwareProfileEvent {
    private final HardwareProfile profile;

    public static /* synthetic */ UpdateHardwareProfileEvent copy$default(UpdateHardwareProfileEvent updateHardwareProfileEvent, HardwareProfile hardwareProfile, int i, Object obj) {
        if ((i & 1) != 0) {
            hardwareProfile = updateHardwareProfileEvent.profile;
        }
        return updateHardwareProfileEvent.copy(hardwareProfile);
    }

    /* renamed from: component1, reason: from getter */
    public final HardwareProfile getProfile() {
        return this.profile;
    }

    public final UpdateHardwareProfileEvent copy(HardwareProfile profile) {
        return new UpdateHardwareProfileEvent(profile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof UpdateHardwareProfileEvent) && Intrinsics.areEqual(this.profile, ((UpdateHardwareProfileEvent) other).profile);
    }

    public int hashCode() {
        HardwareProfile hardwareProfile = this.profile;
        if (hardwareProfile == null) {
            return 0;
        }
        return hardwareProfile.hashCode();
    }

    public String toString() {
        return "UpdateHardwareProfileEvent(profile=" + this.profile + ")";
    }

    public UpdateHardwareProfileEvent(HardwareProfile hardwareProfile) {
        this.profile = hardwareProfile;
    }

    public final HardwareProfile getProfile() {
        return this.profile;
    }
}
