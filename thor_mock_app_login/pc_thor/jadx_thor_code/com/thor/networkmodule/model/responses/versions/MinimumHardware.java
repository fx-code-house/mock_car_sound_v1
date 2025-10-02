package com.thor.networkmodule.model.responses.versions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MinimumHardware.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/MinimumHardware;", "", "firmware", "", "hardware", "device_type", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDevice_type", "()Ljava/lang/String;", "getFirmware", "getHardware", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class MinimumHardware {
    private final String device_type;
    private final String firmware;
    private final String hardware;

    public static /* synthetic */ MinimumHardware copy$default(MinimumHardware minimumHardware, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = minimumHardware.firmware;
        }
        if ((i & 2) != 0) {
            str2 = minimumHardware.hardware;
        }
        if ((i & 4) != 0) {
            str3 = minimumHardware.device_type;
        }
        return minimumHardware.copy(str, str2, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final String getFirmware() {
        return this.firmware;
    }

    /* renamed from: component2, reason: from getter */
    public final String getHardware() {
        return this.hardware;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDevice_type() {
        return this.device_type;
    }

    public final MinimumHardware copy(String firmware, String hardware, String device_type) {
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        Intrinsics.checkNotNullParameter(hardware, "hardware");
        Intrinsics.checkNotNullParameter(device_type, "device_type");
        return new MinimumHardware(firmware, hardware, device_type);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MinimumHardware)) {
            return false;
        }
        MinimumHardware minimumHardware = (MinimumHardware) other;
        return Intrinsics.areEqual(this.firmware, minimumHardware.firmware) && Intrinsics.areEqual(this.hardware, minimumHardware.hardware) && Intrinsics.areEqual(this.device_type, minimumHardware.device_type);
    }

    public int hashCode() {
        return (((this.firmware.hashCode() * 31) + this.hardware.hashCode()) * 31) + this.device_type.hashCode();
    }

    public String toString() {
        return "MinimumHardware(firmware=" + this.firmware + ", hardware=" + this.hardware + ", device_type=" + this.device_type + ")";
    }

    public MinimumHardware(String firmware, String hardware, String device_type) {
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        Intrinsics.checkNotNullParameter(hardware, "hardware");
        Intrinsics.checkNotNullParameter(device_type, "device_type");
        this.firmware = firmware;
        this.hardware = hardware;
        this.device_type = device_type;
    }

    public final String getFirmware() {
        return this.firmware;
    }

    public final String getHardware() {
        return this.hardware;
    }

    public final String getDevice_type() {
        return this.device_type;
    }
}
