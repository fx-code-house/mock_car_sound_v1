package com.thor.app.bus.events.shop.sgu;

import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadSguSoundPackageInterruptedEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/bus/events/shop/sgu/UploadSguSoundPackageInterruptedEvent;", "", "sguSoundSet", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;)V", "getSguSoundSet", "()Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UploadSguSoundPackageInterruptedEvent {
    private final SguSoundSet sguSoundSet;

    public static /* synthetic */ UploadSguSoundPackageInterruptedEvent copy$default(UploadSguSoundPackageInterruptedEvent uploadSguSoundPackageInterruptedEvent, SguSoundSet sguSoundSet, int i, Object obj) {
        if ((i & 1) != 0) {
            sguSoundSet = uploadSguSoundPackageInterruptedEvent.sguSoundSet;
        }
        return uploadSguSoundPackageInterruptedEvent.copy(sguSoundSet);
    }

    /* renamed from: component1, reason: from getter */
    public final SguSoundSet getSguSoundSet() {
        return this.sguSoundSet;
    }

    public final UploadSguSoundPackageInterruptedEvent copy(SguSoundSet sguSoundSet) {
        Intrinsics.checkNotNullParameter(sguSoundSet, "sguSoundSet");
        return new UploadSguSoundPackageInterruptedEvent(sguSoundSet);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof UploadSguSoundPackageInterruptedEvent) && Intrinsics.areEqual(this.sguSoundSet, ((UploadSguSoundPackageInterruptedEvent) other).sguSoundSet);
    }

    public int hashCode() {
        return this.sguSoundSet.hashCode();
    }

    public String toString() {
        return "UploadSguSoundPackageInterruptedEvent(sguSoundSet=" + this.sguSoundSet + ")";
    }

    public UploadSguSoundPackageInterruptedEvent(SguSoundSet sguSoundSet) {
        Intrinsics.checkNotNullParameter(sguSoundSet, "sguSoundSet");
        this.sguSoundSet = sguSoundSet;
    }

    public final SguSoundSet getSguSoundSet() {
        return this.sguSoundSet;
    }
}
