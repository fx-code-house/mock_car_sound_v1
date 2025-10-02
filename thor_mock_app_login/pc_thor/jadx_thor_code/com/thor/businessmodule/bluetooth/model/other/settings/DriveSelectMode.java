package com.thor.businessmodule.bluetooth.model.other.settings;

import kotlin.Metadata;

/* compiled from: DriveSelect.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0014B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelectMode;", "", "driveModeId", "", "driveSelectMode", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelectMode$SettingsMode;", "(ILcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelectMode$SettingsMode;)V", "getDriveModeId", "()I", "getDriveSelectMode", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelectMode$SettingsMode;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "SettingsMode", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DriveSelectMode {
    private final int driveModeId;
    private final SettingsMode driveSelectMode;

    public static /* synthetic */ DriveSelectMode copy$default(DriveSelectMode driveSelectMode, int i, SettingsMode settingsMode, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = driveSelectMode.driveModeId;
        }
        if ((i2 & 2) != 0) {
            settingsMode = driveSelectMode.driveSelectMode;
        }
        return driveSelectMode.copy(i, settingsMode);
    }

    /* renamed from: component1, reason: from getter */
    public final int getDriveModeId() {
        return this.driveModeId;
    }

    /* renamed from: component2, reason: from getter */
    public final SettingsMode getDriveSelectMode() {
        return this.driveSelectMode;
    }

    public final DriveSelectMode copy(int driveModeId, SettingsMode driveSelectMode) {
        return new DriveSelectMode(driveModeId, driveSelectMode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DriveSelectMode)) {
            return false;
        }
        DriveSelectMode driveSelectMode = (DriveSelectMode) other;
        return this.driveModeId == driveSelectMode.driveModeId && this.driveSelectMode == driveSelectMode.driveSelectMode;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.driveModeId) * 31;
        SettingsMode settingsMode = this.driveSelectMode;
        return iHashCode + (settingsMode == null ? 0 : settingsMode.hashCode());
    }

    public String toString() {
        return "DriveSelectMode(driveModeId=" + this.driveModeId + ", driveSelectMode=" + this.driveSelectMode + ")";
    }

    public DriveSelectMode(int i, SettingsMode settingsMode) {
        this.driveModeId = i;
        this.driveSelectMode = settingsMode;
    }

    public final int getDriveModeId() {
        return this.driveModeId;
    }

    public final SettingsMode getDriveSelectMode() {
        return this.driveSelectMode;
    }

    /* compiled from: DriveSelect.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/DriveSelectMode$SettingsMode;", "", "rowValue", "", "(Ljava/lang/String;II)V", "getRowValue", "()I", "Wait", "ActivePreset", "StopPlaySound", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum SettingsMode {
        Wait(0),
        ActivePreset(1),
        StopPlaySound(2);

        private final int rowValue;

        SettingsMode(int i) {
            this.rowValue = i;
        }

        public final int getRowValue() {
            return this.rowValue;
        }
    }
}
