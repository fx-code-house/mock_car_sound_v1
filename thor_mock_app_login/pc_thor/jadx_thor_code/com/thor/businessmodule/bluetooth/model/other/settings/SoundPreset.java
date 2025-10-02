package com.thor.businessmodule.bluetooth.model.other.settings;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;

/* compiled from: SoundPresets.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0018B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset;", "", "soundPacketId", "", "soundMode", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset$SoundMode;", NotificationCompat.CATEGORY_STATUS, "", "(ILcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset$SoundMode;Z)V", "getSoundMode", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset$SoundMode;", "getSoundPacketId", "()I", "getStatus", "()Z", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "", "SoundMode", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPreset {
    private final SoundMode soundMode;
    private final int soundPacketId;
    private final boolean status;

    public static /* synthetic */ SoundPreset copy$default(SoundPreset soundPreset, int i, SoundMode soundMode, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = soundPreset.soundPacketId;
        }
        if ((i2 & 2) != 0) {
            soundMode = soundPreset.soundMode;
        }
        if ((i2 & 4) != 0) {
            z = soundPreset.status;
        }
        return soundPreset.copy(i, soundMode, z);
    }

    /* renamed from: component1, reason: from getter */
    public final int getSoundPacketId() {
        return this.soundPacketId;
    }

    /* renamed from: component2, reason: from getter */
    public final SoundMode getSoundMode() {
        return this.soundMode;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getStatus() {
        return this.status;
    }

    public final SoundPreset copy(int soundPacketId, SoundMode soundMode, boolean status) {
        return new SoundPreset(soundPacketId, soundMode, status);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPreset)) {
            return false;
        }
        SoundPreset soundPreset = (SoundPreset) other;
        return this.soundPacketId == soundPreset.soundPacketId && this.soundMode == soundPreset.soundMode && this.status == soundPreset.status;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = Integer.hashCode(this.soundPacketId) * 31;
        SoundMode soundMode = this.soundMode;
        int iHashCode2 = (iHashCode + (soundMode == null ? 0 : soundMode.hashCode())) * 31;
        boolean z = this.status;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode2 + i;
    }

    public String toString() {
        return "SoundPreset(soundPacketId=" + this.soundPacketId + ", soundMode=" + this.soundMode + ", status=" + this.status + ")";
    }

    public SoundPreset(int i, SoundMode soundMode, boolean z) {
        this.soundPacketId = i;
        this.soundMode = soundMode;
        this.status = z;
    }

    public final int getSoundPacketId() {
        return this.soundPacketId;
    }

    public final SoundMode getSoundMode() {
        return this.soundMode;
    }

    public final boolean getStatus() {
        return this.status;
    }

    /* compiled from: SoundPresets.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset$SoundMode;", "", "rawValue", "", "(Ljava/lang/String;II)V", "getRawValue", "()I", "City", "Sport", "Self", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum SoundMode {
        City(1),
        Sport(2),
        Self(3);

        private final int rawValue;

        SoundMode(int i) {
            this.rawValue = i;
        }

        public final int getRawValue() {
            return this.rawValue;
        }
    }
}
