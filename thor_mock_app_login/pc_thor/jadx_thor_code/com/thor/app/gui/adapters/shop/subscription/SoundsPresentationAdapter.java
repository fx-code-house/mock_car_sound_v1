package com.thor.app.gui.adapters.shop.subscription;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ItemSubscriptionPackBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.thor.app.glide.GlideApp;
import com.thor.app.utils.extensions.StringKt;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SoundsPresentationAdapter.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\n\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016¨\u0006\u000f"}, d2 = {"Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "", "Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter$SubscriptionPackViewHolder;", "()V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "SubscriptionPackViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SoundsPresentationAdapter extends BaseRecyclerListAdapter<String, SubscriptionPackViewHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SubscriptionPackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ItemSubscriptionPackBinding itemSubscriptionPackBindingInflate = ItemSubscriptionPackBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemSubscriptionPackBindingInflate, "inflate(inflater!!, parent, false)");
        View root = itemSubscriptionPackBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new SubscriptionPackViewHolder(this, root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(SubscriptionPackViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        ItemSubscriptionPackBinding binding = holder.getBinding();
        ShapeableImageView shapeableImageView = binding != null ? binding.image : null;
        if (shapeableImageView != null) {
            Collection mList = this.mList;
            Intrinsics.checkNotNullExpressionValue(mList, "mList");
            if (!mList.isEmpty()) {
                String relativeImageUrl = (String) this.mList.get(position);
                Intrinsics.checkNotNullExpressionValue(relativeImageUrl, "relativeImageUrl");
                GlideApp.with(shapeableImageView.getContext()).load(StringKt.getFullFileUrl(relativeImageUrl)).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_thor_logo_small).into(shapeableImageView);
            }
        }
    }

    /* compiled from: SoundsPresentationAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter$SubscriptionPackViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemSubscriptionPackBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemSubscriptionPackBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class SubscriptionPackViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubscriptionPackBinding binding;
        final /* synthetic */ SoundsPresentationAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SubscriptionPackViewHolder(SoundsPresentationAdapter soundsPresentationAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = soundsPresentationAdapter;
            this.binding = (ItemSubscriptionPackBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemSubscriptionPackBinding getBinding() {
            return this.binding;
        }
    }
}
