package com.thor.app.gui.activities.demo;

import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoShopActivity.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class DemoShopActivity$onLoadData$1$3 extends FunctionReferenceImpl implements Function1<ShopSoundPackagesResponse, Unit> {
    DemoShopActivity$onLoadData$1$3(Object obj) {
        super(1, obj, DemoShopActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
        invoke2(shopSoundPackagesResponse);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(ShopSoundPackagesResponse p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((DemoShopActivity) this.receiver).handleResponse(p0);
    }
}
