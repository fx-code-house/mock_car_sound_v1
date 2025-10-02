package com.thor.app.bus.events.bluetooth.sgu;

import kotlin.Metadata;

/* compiled from: UploadSguSoundAppliedEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/bluetooth/sgu/UploadSguSoundAppliedEvent;", "", "soundId", "", "(S)V", "getSoundId", "()S", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UploadSguSoundAppliedEvent {
    private final short soundId;

    public static /* synthetic */ UploadSguSoundAppliedEvent copy$default(UploadSguSoundAppliedEvent uploadSguSoundAppliedEvent, short s, int i, Object obj) {
        if ((i & 1) != 0) {
            s = uploadSguSoundAppliedEvent.soundId;
        }
        return uploadSguSoundAppliedEvent.copy(s);
    }

    /* renamed from: component1, reason: from getter */
    public final short getSoundId() {
        return this.soundId;
    }

    public final UploadSguSoundAppliedEvent copy(short soundId) {
        return new UploadSguSoundAppliedEvent(soundId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof UploadSguSoundAppliedEvent) && this.soundId == ((UploadSguSoundAppliedEvent) other).soundId;
    }

    public int hashCode() {
        return Short.hashCode(this.soundId);
    }

    public String toString() {
        return "UploadSguSoundAppliedEvent(soundId=" + ((int) this.soundId) + ")";
    }

    public UploadSguSoundAppliedEvent(short s) {
        this.soundId = s;
    }

    public final short getSoundId() {
        return this.soundId;
    }
}
