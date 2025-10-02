package com.thor.businessmodule.bus.events;

import com.thor.businessmodule.model.DemoSoundPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChangeActiveDemoSoundPackageEvent.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bus/events/ChangeActiveDemoSoundPackageEvent;", "", "soundPackage", "Lcom/thor/businessmodule/model/DemoSoundPackage;", "(Lcom/thor/businessmodule/model/DemoSoundPackage;)V", "getSoundPackage", "()Lcom/thor/businessmodule/model/DemoSoundPackage;", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ChangeActiveDemoSoundPackageEvent {
    private final DemoSoundPackage soundPackage;

    public ChangeActiveDemoSoundPackageEvent(DemoSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        this.soundPackage = soundPackage;
    }

    public final DemoSoundPackage getSoundPackage() {
        return this.soundPackage;
    }
}
