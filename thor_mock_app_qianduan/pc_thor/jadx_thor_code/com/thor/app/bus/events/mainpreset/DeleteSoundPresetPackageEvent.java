package com.thor.app.bus.events.mainpreset;

import com.thor.businessmodule.model.MainPresetPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeleteSoundPresetPackageEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/mainpreset/DeleteSoundPresetPackageEvent;", "", "mainPresetPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "(Lcom/thor/businessmodule/model/MainPresetPackage;)V", "getMainPresetPackage", "()Lcom/thor/businessmodule/model/MainPresetPackage;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class DeleteSoundPresetPackageEvent {
    private final MainPresetPackage mainPresetPackage;

    public static /* synthetic */ DeleteSoundPresetPackageEvent copy$default(DeleteSoundPresetPackageEvent deleteSoundPresetPackageEvent, MainPresetPackage mainPresetPackage, int i, Object obj) {
        if ((i & 1) != 0) {
            mainPresetPackage = deleteSoundPresetPackageEvent.mainPresetPackage;
        }
        return deleteSoundPresetPackageEvent.copy(mainPresetPackage);
    }

    /* renamed from: component1, reason: from getter */
    public final MainPresetPackage getMainPresetPackage() {
        return this.mainPresetPackage;
    }

    public final DeleteSoundPresetPackageEvent copy(MainPresetPackage mainPresetPackage) {
        Intrinsics.checkNotNullParameter(mainPresetPackage, "mainPresetPackage");
        return new DeleteSoundPresetPackageEvent(mainPresetPackage);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DeleteSoundPresetPackageEvent) && Intrinsics.areEqual(this.mainPresetPackage, ((DeleteSoundPresetPackageEvent) other).mainPresetPackage);
    }

    public int hashCode() {
        return this.mainPresetPackage.hashCode();
    }

    public String toString() {
        return "DeleteSoundPresetPackageEvent(mainPresetPackage=" + this.mainPresetPackage + ")";
    }

    public DeleteSoundPresetPackageEvent(MainPresetPackage mainPresetPackage) {
        Intrinsics.checkNotNullParameter(mainPresetPackage, "mainPresetPackage");
        this.mainPresetPackage = mainPresetPackage;
    }

    public final MainPresetPackage getMainPresetPackage() {
        return this.mainPresetPackage;
    }
}
