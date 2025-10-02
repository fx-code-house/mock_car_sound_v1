package com.thor.networkmodule.model.responses.sgu;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: SguSoundConfig.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundConfig;", "", "repeatCycle", "", "engineVolume", "soundVolume", "(SSS)V", "getEngineVolume", "()S", "setEngineVolume", "(S)V", "getRepeatCycle", "setRepeatCycle", "getSoundVolume", "setSoundVolume", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundConfig {
    public static final short ENDLESS_REPEAT = Short.MAX_VALUE;
    private short engineVolume;
    private short repeatCycle;
    private short soundVolume;

    public SguSoundConfig() {
        this((short) 0, (short) 0, (short) 0, 7, null);
    }

    public static /* synthetic */ SguSoundConfig copy$default(SguSoundConfig sguSoundConfig, short s, short s2, short s3, int i, Object obj) {
        if ((i & 1) != 0) {
            s = sguSoundConfig.repeatCycle;
        }
        if ((i & 2) != 0) {
            s2 = sguSoundConfig.engineVolume;
        }
        if ((i & 4) != 0) {
            s3 = sguSoundConfig.soundVolume;
        }
        return sguSoundConfig.copy(s, s2, s3);
    }

    /* renamed from: component1, reason: from getter */
    public final short getRepeatCycle() {
        return this.repeatCycle;
    }

    /* renamed from: component2, reason: from getter */
    public final short getEngineVolume() {
        return this.engineVolume;
    }

    /* renamed from: component3, reason: from getter */
    public final short getSoundVolume() {
        return this.soundVolume;
    }

    public final SguSoundConfig copy(short repeatCycle, short engineVolume, short soundVolume) {
        return new SguSoundConfig(repeatCycle, engineVolume, soundVolume);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguSoundConfig)) {
            return false;
        }
        SguSoundConfig sguSoundConfig = (SguSoundConfig) other;
        return this.repeatCycle == sguSoundConfig.repeatCycle && this.engineVolume == sguSoundConfig.engineVolume && this.soundVolume == sguSoundConfig.soundVolume;
    }

    public int hashCode() {
        return (((Short.hashCode(this.repeatCycle) * 31) + Short.hashCode(this.engineVolume)) * 31) + Short.hashCode(this.soundVolume);
    }

    public String toString() {
        return "SguSoundConfig(repeatCycle=" + ((int) this.repeatCycle) + ", engineVolume=" + ((int) this.engineVolume) + ", soundVolume=" + ((int) this.soundVolume) + ")";
    }

    public SguSoundConfig(short s, short s2, short s3) {
        this.repeatCycle = s;
        this.engineVolume = s2;
        this.soundVolume = s3;
    }

    public /* synthetic */ SguSoundConfig(short s, short s2, short s3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (short) 1 : s, (i & 2) != 0 ? (short) 50 : s2, (i & 4) != 0 ? (short) 50 : s3);
    }

    public final short getRepeatCycle() {
        return this.repeatCycle;
    }

    public final void setRepeatCycle(short s) {
        this.repeatCycle = s;
    }

    public final short getEngineVolume() {
        return this.engineVolume;
    }

    public final void setEngineVolume(short s) {
        this.engineVolume = s;
    }

    public final short getSoundVolume() {
        return this.soundVolume;
    }

    public final void setSoundVolume(short s) {
        this.soundVolume = s;
    }
}
