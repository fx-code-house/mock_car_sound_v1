package com.thor.app.gui.adapters.shop.sgu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.ItemSguShopSoundPackageBinding;
import com.thor.app.gui.views.sgu.SguShopSoundPackageView;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguShopSoundPackagesRvAdapter.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\n\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016¨\u0006\u000f"}, d2 = {"Lcom/thor/app/gui/adapters/shop/sgu/SguShopSoundPackagesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "Lcom/thor/app/gui/adapters/shop/sgu/SguShopSoundPackagesRvAdapter$SguShopSoundPackageViewHolder;", "()V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "SguShopSoundPackageViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguShopSoundPackagesRvAdapter extends BaseRecyclerListAdapter<SguSoundSet, SguShopSoundPackageViewHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SguShopSoundPackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        if (layoutInflater != null) {
            ItemSguShopSoundPackageBinding itemSguShopSoundPackageBindingInflate = ItemSguShopSoundPackageBinding.inflate(layoutInflater, parent, false);
            Intrinsics.checkNotNullExpressionValue(itemSguShopSoundPackageBindingInflate, "inflate(\n            req…          false\n        )");
            View root = itemSguShopSoundPackageBindingInflate.getRoot();
            Intrinsics.checkNotNullExpressionValue(root, "binding.root");
            return new SguShopSoundPackageViewHolder(this, root);
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(SguShopSoundPackageViewHolder holder, int position) {
        SguShopSoundPackageView sguShopSoundPackageView;
        Intrinsics.checkNotNullParameter(holder, "holder");
        SguSoundSet sguSoundSet = (SguSoundSet) this.mList.get(position);
        ItemSguShopSoundPackageBinding binding = holder.getBinding();
        if (binding == null || (sguShopSoundPackageView = binding.shopSoundPackageView) == null) {
            return;
        }
        sguShopSoundPackageView.setModel(sguSoundSet);
    }

    /* compiled from: SguShopSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/shop/sgu/SguShopSoundPackagesRvAdapter$SguShopSoundPackageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/shop/sgu/SguShopSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemSguShopSoundPackageBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemSguShopSoundPackageBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class SguShopSoundPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemSguShopSoundPackageBinding binding;
        final /* synthetic */ SguShopSoundPackagesRvAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SguShopSoundPackageViewHolder(SguShopSoundPackagesRvAdapter sguShopSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = sguShopSoundPackagesRvAdapter;
            this.binding = (ItemSguShopSoundPackageBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemSguShopSoundPackageBinding getBinding() {
            return this.binding;
        }
    }
}
