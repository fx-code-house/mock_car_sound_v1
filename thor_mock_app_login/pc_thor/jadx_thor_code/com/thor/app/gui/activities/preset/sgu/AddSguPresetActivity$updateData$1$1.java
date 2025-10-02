package com.thor.app.gui.activities.preset.sgu;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AddSguPresetActivity.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class AddSguPresetActivity$updateData$1$1 extends FunctionReferenceImpl implements Function1<SguSound, Unit> {
    AddSguPresetActivity$updateData$1$1(Object obj) {
        super(1, obj, AddSguPresetActivity.class, "onFavSound", "onFavSound(Lcom/thor/networkmodule/model/responses/sgu/SguSound;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(SguSound sguSound) {
        invoke2(sguSound);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(SguSound p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((AddSguPresetActivity) this.receiver).onFavSound(p0);
    }
}
