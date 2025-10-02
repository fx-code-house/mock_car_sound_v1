package com.thor.app.gui.adapters.main;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import timber.log.Timber;

/* compiled from: MainSoundPackagesRvAdapter.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class MainSoundPackagesRvAdapter$handleDriveModes$1$3 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
    MainSoundPackagesRvAdapter$handleDriveModes$1$3(Object obj) {
        super(1, obj, Timber.Companion.class, "e", "e(Ljava/lang/Throwable;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Throwable th) {
        ((Timber.Companion) this.receiver).e(th);
    }
}
