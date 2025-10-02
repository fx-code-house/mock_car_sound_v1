package com.thor.app.gui.fragments.firmwares;

import com.thor.networkmodule.model.firmware.FirmwareProfileShort;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FirmwareListFragment.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class FirmwareListFragment$initListeners$1$1$1 extends FunctionReferenceImpl implements Function1<FirmwareProfileShort, Unit> {
    FirmwareListFragment$initListeners$1$1$1(Object obj) {
        super(1, obj, FirmwareListFragment.class, "onClick", "onClick(Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(FirmwareProfileShort firmwareProfileShort) {
        invoke2(firmwareProfileShort);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(FirmwareProfileShort p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((FirmwareListFragment) this.receiver).onClick(p0);
    }
}
