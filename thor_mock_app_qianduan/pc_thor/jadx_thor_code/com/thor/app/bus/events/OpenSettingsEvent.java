package com.thor.app.bus.events;

import com.thor.businessmodule.model.MainPresetPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OpenSettingsEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/OpenSettingsEvent;", "", "presetPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "(Lcom/thor/businessmodule/model/MainPresetPackage;)V", "getPresetPackage", "()Lcom/thor/businessmodule/model/MainPresetPackage;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class OpenSettingsEvent {
    private final MainPresetPackage presetPackage;

    public static /* synthetic */ OpenSettingsEvent copy$default(OpenSettingsEvent openSettingsEvent, MainPresetPackage mainPresetPackage, int i, Object obj) {
        if ((i & 1) != 0) {
            mainPresetPackage = openSettingsEvent.presetPackage;
        }
        return openSettingsEvent.copy(mainPresetPackage);
    }

    /* renamed from: component1, reason: from getter */
    public final MainPresetPackage getPresetPackage() {
        return this.presetPackage;
    }

    public final OpenSettingsEvent copy(MainPresetPackage presetPackage) {
        return new OpenSettingsEvent(presetPackage);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof OpenSettingsEvent) && Intrinsics.areEqual(this.presetPackage, ((OpenSettingsEvent) other).presetPackage);
    }

    public int hashCode() {
        MainPresetPackage mainPresetPackage = this.presetPackage;
        if (mainPresetPackage == null) {
            return 0;
        }
        return mainPresetPackage.hashCode();
    }

    public String toString() {
        return "OpenSettingsEvent(presetPackage=" + this.presetPackage + ")";
    }

    public OpenSettingsEvent(MainPresetPackage mainPresetPackage) {
        this.presetPackage = mainPresetPackage;
    }

    public final MainPresetPackage getPresetPackage() {
        return this.presetPackage;
    }
}
