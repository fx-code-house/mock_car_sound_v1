package com.thor.businessmodule.bluetooth.model.other.settings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SoundPackets.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPacket;", "", "soundPacketId", "", "soundPacketVersion", "modesMask", "Lcom/thor/businessmodule/bluetooth/model/other/settings/ModesMask;", "(IILcom/thor/businessmodule/bluetooth/model/other/settings/ModesMask;)V", "getModesMask", "()Lcom/thor/businessmodule/bluetooth/model/other/settings/ModesMask;", "getSoundPacketId", "()I", "getSoundPacketVersion", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPacket {
    private final ModesMask modesMask;
    private final int soundPacketId;
    private final int soundPacketVersion;

    public static /* synthetic */ SoundPacket copy$default(SoundPacket soundPacket, int i, int i2, ModesMask modesMask, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = soundPacket.soundPacketId;
        }
        if ((i3 & 2) != 0) {
            i2 = soundPacket.soundPacketVersion;
        }
        if ((i3 & 4) != 0) {
            modesMask = soundPacket.modesMask;
        }
        return soundPacket.copy(i, i2, modesMask);
    }

    /* renamed from: component1, reason: from getter */
    public final int getSoundPacketId() {
        return this.soundPacketId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getSoundPacketVersion() {
        return this.soundPacketVersion;
    }

    /* renamed from: component3, reason: from getter */
    public final ModesMask getModesMask() {
        return this.modesMask;
    }

    public final SoundPacket copy(int soundPacketId, int soundPacketVersion, ModesMask modesMask) {
        Intrinsics.checkNotNullParameter(modesMask, "modesMask");
        return new SoundPacket(soundPacketId, soundPacketVersion, modesMask);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPacket)) {
            return false;
        }
        SoundPacket soundPacket = (SoundPacket) other;
        return this.soundPacketId == soundPacket.soundPacketId && this.soundPacketVersion == soundPacket.soundPacketVersion && this.modesMask == soundPacket.modesMask;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.soundPacketId) * 31) + Integer.hashCode(this.soundPacketVersion)) * 31) + this.modesMask.hashCode();
    }

    public String toString() {
        return "SoundPacket(soundPacketId=" + this.soundPacketId + ", soundPacketVersion=" + this.soundPacketVersion + ", modesMask=" + this.modesMask + ")";
    }

    public SoundPacket(int i, int i2, ModesMask modesMask) {
        Intrinsics.checkNotNullParameter(modesMask, "modesMask");
        this.soundPacketId = i;
        this.soundPacketVersion = i2;
        this.modesMask = modesMask;
    }

    public final int getSoundPacketId() {
        return this.soundPacketId;
    }

    public final int getSoundPacketVersion() {
        return this.soundPacketVersion;
    }

    public final ModesMask getModesMask() {
        return this.modesMask;
    }
}
