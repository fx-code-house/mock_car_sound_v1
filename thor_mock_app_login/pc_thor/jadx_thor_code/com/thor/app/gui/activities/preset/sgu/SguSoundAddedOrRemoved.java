package com.thor.app.gui.activities.preset.sgu;

import kotlin.Metadata;

/* compiled from: AddSguPresetUiState.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nHÖ\u0003J\t\u0010\u000b\u001a\u00020\fHÖ\u0001J\t\u0010\r\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u000f"}, d2 = {"Lcom/thor/app/gui/activities/preset/sgu/SguSoundAddedOrRemoved;", "Lcom/thor/app/gui/activities/preset/sgu/AddSguPresetUiState;", "isAdded", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundAddedOrRemoved extends AddSguPresetUiState {
    private final boolean isAdded;

    public static /* synthetic */ SguSoundAddedOrRemoved copy$default(SguSoundAddedOrRemoved sguSoundAddedOrRemoved, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = sguSoundAddedOrRemoved.isAdded;
        }
        return sguSoundAddedOrRemoved.copy(z);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsAdded() {
        return this.isAdded;
    }

    public final SguSoundAddedOrRemoved copy(boolean isAdded) {
        return new SguSoundAddedOrRemoved(isAdded);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SguSoundAddedOrRemoved) && this.isAdded == ((SguSoundAddedOrRemoved) other).isAdded;
    }

    public int hashCode() {
        boolean z = this.isAdded;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public String toString() {
        return "SguSoundAddedOrRemoved(isAdded=" + this.isAdded + ")";
    }

    public SguSoundAddedOrRemoved(boolean z) {
        super(null);
        this.isAdded = z;
    }

    public final boolean isAdded() {
        return this.isAdded;
    }
}
