package com.thor.app.gui.fragments.shop.sgu;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.FragmentSguShopBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguShopFragment.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class SguShopFragment$bindingInflater$1 extends FunctionReferenceImpl implements Function3<LayoutInflater, ViewGroup, Boolean, FragmentSguShopBinding> {
    public static final SguShopFragment$bindingInflater$1 INSTANCE = new SguShopFragment$bindingInflater$1();

    SguShopFragment$bindingInflater$1() {
        super(3, FragmentSguShopBinding.class, "inflate", "inflate(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Lcom/carsystems/thor/app/databinding/FragmentSguShopBinding;", 0);
    }

    public final FragmentSguShopBinding invoke(LayoutInflater p0, ViewGroup viewGroup, boolean z) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return FragmentSguShopBinding.inflate(p0, viewGroup, z);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ FragmentSguShopBinding invoke(LayoutInflater layoutInflater, ViewGroup viewGroup, Boolean bool) {
        return invoke(layoutInflater, viewGroup, bool.booleanValue());
    }
}
