package com.thor.app.gui.adapters.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.ItemDemoShopSoundPackageBinding;
import com.thor.app.gui.views.demo.DemoShopSoundPackageView;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoShopSoundPackagesRvAdapter.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u0016B\u0007\b\u0016¢\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J\u0010\u0010\u0014\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoShopSoundPackagesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "Lcom/thor/app/gui/adapters/demo/DemoShopSoundPackagesRvAdapter$DemoShopSoundPackageViewHolder;", "()V", "mOnNestedScrollListener", "Landroidx/core/widget/NestedScrollView$OnScrollChangeListener;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getOnNestedScrollListener", "initNestedScrollListener", "", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setRecyclerView", "rv", "DemoShopSoundPackageViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoShopSoundPackagesRvAdapter extends BaseRecyclerListAdapter<ShopSoundPackage, DemoShopSoundPackageViewHolder> {
    private NestedScrollView.OnScrollChangeListener mOnNestedScrollListener;
    private RecyclerView mRecyclerView;

    public DemoShopSoundPackagesRvAdapter() {
        initNestedScrollListener();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DemoShopSoundPackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ItemDemoShopSoundPackageBinding itemDemoShopSoundPackageBindingInflate = ItemDemoShopSoundPackageBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemDemoShopSoundPackageBindingInflate, "inflate(inflater!!, parent, false)");
        View root = itemDemoShopSoundPackageBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new DemoShopSoundPackageViewHolder(this, root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(DemoShopSoundPackageViewHolder holder, int position) {
        DemoShopSoundPackageView demoShopSoundPackageView;
        Intrinsics.checkNotNullParameter(holder, "holder");
        ItemDemoShopSoundPackageBinding binding = holder.getBinding();
        if (binding == null || (demoShopSoundPackageView = binding.demoShopSoundPackageView) == null) {
            return;
        }
        demoShopSoundPackageView.setShopSoundPackage((ShopSoundPackage) this.mList.get(position));
    }

    public final NestedScrollView.OnScrollChangeListener getOnNestedScrollListener() {
        NestedScrollView.OnScrollChangeListener onScrollChangeListener = this.mOnNestedScrollListener;
        if (onScrollChangeListener != null) {
            return onScrollChangeListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mOnNestedScrollListener");
        return null;
    }

    public final void setRecyclerView(RecyclerView rv) {
        this.mRecyclerView = rv;
    }

    private final void initNestedScrollListener() {
        this.mOnNestedScrollListener = new NestedScrollView.OnScrollChangeListener() { // from class: com.thor.app.gui.adapters.demo.DemoShopSoundPackagesRvAdapter.initNestedScrollListener.1
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View childAt;
                Intrinsics.checkNotNullParameter(v, "v");
                int itemCount = DemoShopSoundPackagesRvAdapter.this.getItemCount() - 1;
                if (itemCount < 0) {
                    return;
                }
                int i = 0;
                while (true) {
                    RecyclerView recyclerView = DemoShopSoundPackagesRvAdapter.this.mRecyclerView;
                    if (recyclerView != null && (childAt = recyclerView.getChildAt(i)) != null) {
                        DemoShopSoundPackageView demoShopSoundPackageView = (DemoShopSoundPackageView) childAt;
                        RecyclerView recyclerView2 = DemoShopSoundPackagesRvAdapter.this.mRecyclerView;
                        Integer numValueOf = recyclerView2 != null ? Integer.valueOf(recyclerView2.getHeight()) : null;
                        Intrinsics.checkNotNull(numValueOf);
                        demoShopSoundPackageView.onNestedScrollChanged(scrollY, oldScrollY, numValueOf.intValue());
                    }
                    if (i == itemCount) {
                        return;
                    } else {
                        i++;
                    }
                }
            }
        };
    }

    /* compiled from: DemoShopSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoShopSoundPackagesRvAdapter$DemoShopSoundPackageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/demo/DemoShopSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemDemoShopSoundPackageBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemDemoShopSoundPackageBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class DemoShopSoundPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemDemoShopSoundPackageBinding binding;
        final /* synthetic */ DemoShopSoundPackagesRvAdapter this$0;

        public final ItemDemoShopSoundPackageBinding getBinding() {
            return this.binding;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DemoShopSoundPackageViewHolder(DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = demoShopSoundPackagesRvAdapter;
            this.binding = (ItemDemoShopSoundPackageBinding) DataBindingUtil.bind(itemView);
        }
    }
}
