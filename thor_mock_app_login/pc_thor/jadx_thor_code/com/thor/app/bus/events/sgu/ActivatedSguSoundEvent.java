package com.thor.app.bus.events.sgu;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ActivatedSguSoundEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/sgu/ActivatedSguSoundEvent;", "", "sound", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "(Lcom/thor/networkmodule/model/responses/sgu/SguSound;)V", "getSound", "()Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class ActivatedSguSoundEvent {
    private final SguSound sound;

    /* JADX WARN: Multi-variable type inference failed */
    public ActivatedSguSoundEvent() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ ActivatedSguSoundEvent copy$default(ActivatedSguSoundEvent activatedSguSoundEvent, SguSound sguSound, int i, Object obj) {
        if ((i & 1) != 0) {
            sguSound = activatedSguSoundEvent.sound;
        }
        return activatedSguSoundEvent.copy(sguSound);
    }

    /* renamed from: component1, reason: from getter */
    public final SguSound getSound() {
        return this.sound;
    }

    public final ActivatedSguSoundEvent copy(SguSound sound) {
        return new ActivatedSguSoundEvent(sound);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ActivatedSguSoundEvent) && Intrinsics.areEqual(this.sound, ((ActivatedSguSoundEvent) other).sound);
    }

    public int hashCode() {
        SguSound sguSound = this.sound;
        if (sguSound == null) {
            return 0;
        }
        return sguSound.hashCode();
    }

    public String toString() {
        return "ActivatedSguSoundEvent(sound=" + this.sound + ")";
    }

    public ActivatedSguSoundEvent(SguSound sguSound) {
        this.sound = sguSound;
    }

    public /* synthetic */ ActivatedSguSoundEvent(SguSound sguSound, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : sguSound);
    }

    public final SguSound getSound() {
        return this.sound;
    }
}
