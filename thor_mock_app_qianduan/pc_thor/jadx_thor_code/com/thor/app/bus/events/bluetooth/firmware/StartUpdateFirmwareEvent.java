package com.thor.app.bus.events.bluetooth.firmware;

import kotlin.Metadata;

/* compiled from: StartUpdateFirmwareEvent.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J1\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\b¨\u0006\u0014"}, d2 = {"Lcom/thor/app/bus/events/bluetooth/firmware/StartUpdateFirmwareEvent;", "", "isStart", "", "isEmergency", "isNeedReloadSoundPackages", "isNeedUploadCan", "(ZZZZ)V", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class StartUpdateFirmwareEvent {
    private final boolean isEmergency;
    private final boolean isNeedReloadSoundPackages;
    private final boolean isNeedUploadCan;
    private final boolean isStart;

    public static /* synthetic */ StartUpdateFirmwareEvent copy$default(StartUpdateFirmwareEvent startUpdateFirmwareEvent, boolean z, boolean z2, boolean z3, boolean z4, int i, Object obj) {
        if ((i & 1) != 0) {
            z = startUpdateFirmwareEvent.isStart;
        }
        if ((i & 2) != 0) {
            z2 = startUpdateFirmwareEvent.isEmergency;
        }
        if ((i & 4) != 0) {
            z3 = startUpdateFirmwareEvent.isNeedReloadSoundPackages;
        }
        if ((i & 8) != 0) {
            z4 = startUpdateFirmwareEvent.isNeedUploadCan;
        }
        return startUpdateFirmwareEvent.copy(z, z2, z3, z4);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsStart() {
        return this.isStart;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getIsEmergency() {
        return this.isEmergency;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getIsNeedReloadSoundPackages() {
        return this.isNeedReloadSoundPackages;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getIsNeedUploadCan() {
        return this.isNeedUploadCan;
    }

    public final StartUpdateFirmwareEvent copy(boolean isStart, boolean isEmergency, boolean isNeedReloadSoundPackages, boolean isNeedUploadCan) {
        return new StartUpdateFirmwareEvent(isStart, isEmergency, isNeedReloadSoundPackages, isNeedUploadCan);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StartUpdateFirmwareEvent)) {
            return false;
        }
        StartUpdateFirmwareEvent startUpdateFirmwareEvent = (StartUpdateFirmwareEvent) other;
        return this.isStart == startUpdateFirmwareEvent.isStart && this.isEmergency == startUpdateFirmwareEvent.isEmergency && this.isNeedReloadSoundPackages == startUpdateFirmwareEvent.isNeedReloadSoundPackages && this.isNeedUploadCan == startUpdateFirmwareEvent.isNeedUploadCan;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v2, types: [boolean] */
    public int hashCode() {
        boolean z = this.isStart;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.isEmergency;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        ?? r22 = this.isNeedReloadSoundPackages;
        int i4 = r22;
        if (r22 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        boolean z2 = this.isNeedUploadCan;
        return i5 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public String toString() {
        return "StartUpdateFirmwareEvent(isStart=" + this.isStart + ", isEmergency=" + this.isEmergency + ", isNeedReloadSoundPackages=" + this.isNeedReloadSoundPackages + ", isNeedUploadCan=" + this.isNeedUploadCan + ")";
    }

    public StartUpdateFirmwareEvent(boolean z, boolean z2, boolean z3, boolean z4) {
        this.isStart = z;
        this.isEmergency = z2;
        this.isNeedReloadSoundPackages = z3;
        this.isNeedUploadCan = z4;
    }

    public final boolean isStart() {
        return this.isStart;
    }

    public final boolean isEmergency() {
        return this.isEmergency;
    }

    public final boolean isNeedReloadSoundPackages() {
        return this.isNeedReloadSoundPackages;
    }

    public final boolean isNeedUploadCan() {
        return this.isNeedUploadCan;
    }
}
