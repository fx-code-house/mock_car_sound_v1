package com.thor.app.gui.dialog.dialogs;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.DialogFragmentCarInfoBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarInfoDialogFragment.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class CarInfoDialogFragment$bindingInflater$1 extends FunctionReferenceImpl implements Function3<LayoutInflater, ViewGroup, Boolean, DialogFragmentCarInfoBinding> {
    public static final CarInfoDialogFragment$bindingInflater$1 INSTANCE = new CarInfoDialogFragment$bindingInflater$1();

    CarInfoDialogFragment$bindingInflater$1() {
        super(3, DialogFragmentCarInfoBinding.class, "inflate", "inflate(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Lcom/carsystems/thor/app/databinding/DialogFragmentCarInfoBinding;", 0);
    }

    public final DialogFragmentCarInfoBinding invoke(LayoutInflater p0, ViewGroup viewGroup, boolean z) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return DialogFragmentCarInfoBinding.inflate(p0, viewGroup, z);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ DialogFragmentCarInfoBinding invoke(LayoutInflater layoutInflater, ViewGroup viewGroup, Boolean bool) {
        return invoke(layoutInflater, viewGroup, bool.booleanValue());
    }
}
