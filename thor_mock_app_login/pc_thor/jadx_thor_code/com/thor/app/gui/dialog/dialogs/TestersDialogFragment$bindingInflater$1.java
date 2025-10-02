package com.thor.app.gui.dialog.dialogs;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.DialogFragmentTestersBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TestersDialogFragment.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class TestersDialogFragment$bindingInflater$1 extends FunctionReferenceImpl implements Function3<LayoutInflater, ViewGroup, Boolean, DialogFragmentTestersBinding> {
    public static final TestersDialogFragment$bindingInflater$1 INSTANCE = new TestersDialogFragment$bindingInflater$1();

    TestersDialogFragment$bindingInflater$1() {
        super(3, DialogFragmentTestersBinding.class, "inflate", "inflate(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Lcom/carsystems/thor/app/databinding/DialogFragmentTestersBinding;", 0);
    }

    public final DialogFragmentTestersBinding invoke(LayoutInflater p0, ViewGroup viewGroup, boolean z) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return DialogFragmentTestersBinding.inflate(p0, viewGroup, z);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ DialogFragmentTestersBinding invoke(LayoutInflater layoutInflater, ViewGroup viewGroup, Boolean bool) {
        return invoke(layoutInflater, viewGroup, bool.booleanValue());
    }
}
