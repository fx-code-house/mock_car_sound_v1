package com.thor.app.gui.fragments.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.FragmentDemoBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoSguFragment.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class DemoSguFragment$bindingInflater$1 extends FunctionReferenceImpl implements Function3<LayoutInflater, ViewGroup, Boolean, FragmentDemoBinding> {
    public static final DemoSguFragment$bindingInflater$1 INSTANCE = new DemoSguFragment$bindingInflater$1();

    DemoSguFragment$bindingInflater$1() {
        super(3, FragmentDemoBinding.class, "inflate", "inflate(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Lcom/carsystems/thor/app/databinding/FragmentDemoBinding;", 0);
    }

    public final FragmentDemoBinding invoke(LayoutInflater p0, ViewGroup viewGroup, boolean z) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return FragmentDemoBinding.inflate(p0, viewGroup, z);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ FragmentDemoBinding invoke(LayoutInflater layoutInflater, ViewGroup viewGroup, Boolean bool) {
        return invoke(layoutInflater, viewGroup, bool.booleanValue());
    }
}
