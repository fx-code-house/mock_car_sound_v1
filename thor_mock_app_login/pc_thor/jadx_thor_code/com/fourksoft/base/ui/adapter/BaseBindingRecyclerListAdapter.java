package com.fourksoft.base.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.ViewDataBinding;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BaseBindingRecyclerListAdapter.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J#\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u0011\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0015H\u0016R,\u0010\u0007\u001a\u001c\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00028\u00010\bX¤\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lcom/fourksoft/base/ui/adapter/BaseBindingRecyclerListAdapter;", "M", "VDB", "Landroidx/databinding/ViewDataBinding;", "Lcom/fourksoft/base/ui/adapter/BaseRecyclerListAdapter;", "Lcom/fourksoft/base/ui/adapter/BaseBindingViewHolder;", "()V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "bindViewHolder", "", "holder", "model", "(Lcom/fourksoft/base/ui/adapter/BaseBindingViewHolder;Ljava/lang/Object;)V", "onBindViewHolder", "position", "", "onCreateViewHolder", "parent", "viewType", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class BaseBindingRecyclerListAdapter<M, VDB extends ViewDataBinding> extends BaseRecyclerListAdapter<M, BaseBindingViewHolder<VDB>> {
    public abstract void bindViewHolder(BaseBindingViewHolder<VDB> holder, M model);

    protected abstract Function3<LayoutInflater, ViewGroup, Boolean, VDB> getBindingInflater();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseBindingViewHolder<VDB> onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Function3<LayoutInflater, ViewGroup, Boolean, VDB> bindingInflater = getBindingInflater();
        if (layoutInflater == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        View root = bindingInflater.invoke(layoutInflater, parent, false).getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new BaseBindingViewHolder<>(root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseBindingViewHolder<VDB> holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        bindViewHolder(holder, (BaseBindingViewHolder<VDB>) getMList().get(position));
    }
}
