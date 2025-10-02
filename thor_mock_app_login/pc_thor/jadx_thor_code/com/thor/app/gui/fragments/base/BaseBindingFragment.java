package com.thor.app.gui.fragments.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BaseBindingFragment.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00028\u00008DX\u0084\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\tR,\u0010\n\u001a\u001c\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00028\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lcom/thor/app/gui/fragments/base/BaseBindingFragment;", "VDB", "Landroidx/databinding/ViewDataBinding;", "Lcom/thor/app/gui/fragments/base/BaseFragment;", "()V", "_binding", "binding", "getBinding$annotations", "getBinding", "()Landroidx/databinding/ViewDataBinding;", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "onCreateView", "Landroid/view/View;", "inflater", TtmlNode.RUBY_CONTAINER, "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BaseBindingFragment<VDB extends ViewDataBinding> extends BaseFragment {
    private ViewDataBinding _binding;

    protected static /* synthetic */ void getBinding$annotations() {
    }

    public abstract Function3<LayoutInflater, ViewGroup, Boolean, VDB> getBindingInflater();

    protected final VDB getBinding() {
        VDB vdb = (VDB) this._binding;
        if (vdb == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        Intrinsics.checkNotNull(vdb, "null cannot be cast to non-null type VDB of com.thor.app.gui.fragments.base.BaseBindingFragment");
        return vdb;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        VDB vdbInvoke = getBindingInflater().invoke(inflater, container, false);
        this._binding = vdbInvoke;
        if (vdbInvoke != null) {
            return vdbInvoke.getRoot();
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }
}
