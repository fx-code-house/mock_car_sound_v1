package com.thor.app.gui.adapters.shop.main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.databinding.ItemShopSoundPackageBinding;
import com.carsystems.thor.app.databinding.ItemShopSubscriptionCardBinding;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity;
import com.thor.app.gui.views.soundpackage.ShopSoundPackageView;
import com.thor.app.settings.Settings;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.basemodule.gui.adapters.BaseDiffUtilCallback;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShopSoundPackagesRvAdapter.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\u0018\u0000 )2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0003)*+B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0006\u0010\u0012\u001a\u00020\bJ\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\rJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0016\u001a\u00020\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0018\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0010H\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0002J\u000e\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u00020\u00182\b\u0010&\u001a\u0004\u0018\u00010\nJ\u0014\u0010'\u001a\u00020\u00182\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0(R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "diffUtilCallback", "Lcom/thor/basemodule/gui/adapters/BaseDiffUtilCallback;", "(Lcom/thor/basemodule/gui/adapters/BaseDiffUtilCallback;)V", "mOnNestedScrollListener", "Landroidx/core/widget/NestedScrollView$OnScrollChangeListener;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "mSkuDetailsList", "Ljava/util/ArrayList;", "Lcom/android/billingclient/api/SkuDetails;", "Lkotlin/collections/ArrayList;", "getItemViewType", "", "position", "getOnNestedScrollListener", "getPositionBySkuDetails", "skuDetails", "getSkuDetails", "shopSoundPackage", "initNestedScrollListener", "", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "openSubscriptionScreen", "activity", "Landroid/app/Activity;", "removeInstalledPackage", "packageId", "", "setRecyclerView", "rv", "setSkuDetailsList", "", "Companion", "ShopSoundPackageViewHolder", "ShopSubscriptionCardViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ShopSoundPackagesRvAdapter extends BaseRecyclerListAdapter<ShopSoundPackage, RecyclerView.ViewHolder> {
    public static final int SOUND_PACK = 10000;
    public static final int SUBSCRIPTION = 10001;
    private NestedScrollView.OnScrollChangeListener mOnNestedScrollListener;
    private RecyclerView mRecyclerView;
    private ArrayList<SkuDetails> mSkuDetailsList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShopSoundPackagesRvAdapter(BaseDiffUtilCallback<ShopSoundPackage> diffUtilCallback) {
        super(diffUtilCallback);
        Intrinsics.checkNotNullParameter(diffUtilCallback, "diffUtilCallback");
        this.mSkuDetailsList = new ArrayList<>();
        initNestedScrollListener();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        if (viewType == 10001) {
            Intrinsics.checkNotNull(layoutInflater);
            ItemShopSubscriptionCardBinding itemShopSubscriptionCardBindingInflate = ItemShopSubscriptionCardBinding.inflate(layoutInflater, parent, false);
            Intrinsics.checkNotNullExpressionValue(itemShopSubscriptionCardBindingInflate, "inflate(inflater!!, parent, false)");
            View root = itemShopSubscriptionCardBindingInflate.getRoot();
            Intrinsics.checkNotNullExpressionValue(root, "binding.root");
            return new ShopSubscriptionCardViewHolder(this, root);
        }
        Intrinsics.checkNotNull(layoutInflater);
        ItemShopSoundPackageBinding itemShopSoundPackageBindingInflate = ItemShopSoundPackageBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemShopSoundPackageBindingInflate, "inflate(inflater!!, parent, false)");
        View root2 = itemShopSoundPackageBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root2, "binding.root");
        return new ShopSoundPackageViewHolder(this, root2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View root;
        AppCompatButton appCompatButton;
        View root2;
        ShopSoundPackageView shopSoundPackageView;
        ShopSoundPackageView shopSoundPackageView2;
        ShopSoundPackageView shopSoundPackageView3;
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (holder instanceof ShopSoundPackageViewHolder) {
            ShopSoundPackageViewHolder shopSoundPackageViewHolder = (ShopSoundPackageViewHolder) holder;
            ItemShopSoundPackageBinding binding = shopSoundPackageViewHolder.getBinding();
            if (binding != null && (shopSoundPackageView3 = binding.shopSoundPackageView) != null) {
                shopSoundPackageView3.clean();
            }
            ItemShopSoundPackageBinding binding2 = shopSoundPackageViewHolder.getBinding();
            if (binding2 != null && (shopSoundPackageView2 = binding2.shopSoundPackageView) != null) {
                shopSoundPackageView2.setShopSoundPackage((ShopSoundPackage) this.mList.get(position));
            }
            ItemShopSoundPackageBinding binding3 = shopSoundPackageViewHolder.getBinding();
            if (binding3 == null || (shopSoundPackageView = binding3.shopSoundPackageView) == null) {
                return;
            }
            Object obj = this.mList.get(position);
            Intrinsics.checkNotNullExpressionValue(obj, "mList[position]");
            shopSoundPackageView.setSkuDetails(getSkuDetails((ShopSoundPackage) obj));
            return;
        }
        if (holder instanceof ShopSubscriptionCardViewHolder) {
            ShopSubscriptionCardViewHolder shopSubscriptionCardViewHolder = (ShopSubscriptionCardViewHolder) holder;
            ItemShopSubscriptionCardBinding binding4 = shopSubscriptionCardViewHolder.getBinding();
            final Context context = (binding4 == null || (root2 = binding4.getRoot()) == null) ? null : root2.getContext();
            ItemShopSubscriptionCardBinding binding5 = shopSubscriptionCardViewHolder.getBinding();
            if (binding5 != null && (appCompatButton = binding5.actionSubscription) != null) {
                appCompatButton.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.shop.main.ShopSoundPackagesRvAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ShopSoundPackagesRvAdapter.onBindViewHolder$lambda$1(context, this, view);
                    }
                });
            }
            ItemShopSubscriptionCardBinding binding6 = shopSubscriptionCardViewHolder.getBinding();
            if (binding6 == null || (root = binding6.getRoot()) == null) {
                return;
            }
            root.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.shop.main.ShopSoundPackagesRvAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ShopSoundPackagesRvAdapter.onBindViewHolder$lambda$3(context, this, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(Context context, ShopSoundPackagesRvAdapter this$0, View view) {
        Activity parentActivity;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (context == null || (parentActivity = ContextKt.getParentActivity(context)) == null) {
            return;
        }
        this$0.openSubscriptionScreen(parentActivity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$3(Context context, ShopSoundPackagesRvAdapter this$0, View view) {
        Activity parentActivity;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (context == null || (parentActivity = ContextKt.getParentActivity(context)) == null) {
            return;
        }
        this$0.openSubscriptionScreen(parentActivity);
    }

    private final void openSubscriptionScreen(Activity activity) {
        activity.startActivity(new Intent(activity, (Class<?>) SubscriptionsActivity.class), ActivityOptions.makeSceneTransitionAnimation(activity, new Pair[0]).toBundle());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED() && position == 0 && !Settings.INSTANCE.isSubscriptionActive()) ? 10001 : 10000;
    }

    public final void setSkuDetailsList(List<? extends SkuDetails> skuDetails) {
        Intrinsics.checkNotNullParameter(skuDetails, "skuDetails");
        if (this.mSkuDetailsList.isEmpty()) {
            this.mSkuDetailsList.addAll(skuDetails);
            Iterator<T> it = this.mSkuDetailsList.iterator();
            while (it.hasNext()) {
                notifyItemChanged(getPositionBySkuDetails((SkuDetails) it.next()));
            }
            return;
        }
        for (SkuDetails skuDetails2 : skuDetails) {
            int size = this.mSkuDetailsList.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                SkuDetails skuDetails3 = this.mSkuDetailsList.get(i);
                Intrinsics.checkNotNullExpressionValue(skuDetails3, "mSkuDetailsList[index]");
                SkuDetails skuDetails4 = skuDetails3;
                if (Intrinsics.areEqual(skuDetails4.getSku(), skuDetails2.getSku())) {
                    if (skuDetails4.getPriceAmountMicros() != skuDetails2.getPriceAmountMicros()) {
                        this.mSkuDetailsList.remove(i);
                        this.mSkuDetailsList.add(i, skuDetails2);
                        notifyItemChanged(getPositionBySkuDetails(skuDetails4));
                    }
                    z = true;
                } else {
                    i++;
                }
            }
            if (!z) {
                this.mSkuDetailsList.add(skuDetails2);
                notifyItemChanged(getPositionBySkuDetails(skuDetails2));
            }
        }
    }

    public final SkuDetails getSkuDetails(ShopSoundPackage shopSoundPackage) {
        Intrinsics.checkNotNullParameter(shopSoundPackage, "shopSoundPackage");
        Iterator<SkuDetails> it = this.mSkuDetailsList.iterator();
        while (it.hasNext()) {
            SkuDetails next = it.next();
            if (shopSoundPackage.getGooglePlayId().equals(next.getSku())) {
                return next;
            }
        }
        return null;
    }

    public final int getPositionBySkuDetails(SkuDetails skuDetails) {
        Intrinsics.checkNotNullParameter(skuDetails, "skuDetails");
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            if (Intrinsics.areEqual(((ShopSoundPackage) this.mList.get(i)).getGooglePlayId(), skuDetails.getSku())) {
                return i;
            }
        }
        return -1;
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

    public final void removeInstalledPackage(short packageId) {
        Collection<ShopSoundPackage> all = getAll();
        Intrinsics.checkNotNullExpressionValue(all, "all");
        int i = 0;
        for (Object obj : all) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ShopSoundPackage shopSoundPackage = (ShopSoundPackage) obj;
            if (((short) shopSoundPackage.getId()) == packageId) {
                shopSoundPackage.setLoadedOnBoard(false);
                notifyItemChanged(i);
            }
            i = i2;
        }
    }

    private final void initNestedScrollListener() {
        this.mOnNestedScrollListener = new NestedScrollView.OnScrollChangeListener() { // from class: com.thor.app.gui.adapters.shop.main.ShopSoundPackagesRvAdapter$$ExternalSyntheticLambda2
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                ShopSoundPackagesRvAdapter.initNestedScrollListener$lambda$7(this.f$0, nestedScrollView, i, i2, i3, i4);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNestedScrollListener$lambda$7(ShopSoundPackagesRvAdapter this$0, NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        View childAt;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(nestedScrollView, "<anonymous parameter 0>");
        int itemCount = this$0.getItemCount();
        for (int i5 = 1; i5 < itemCount; i5++) {
            RecyclerView recyclerView = this$0.mRecyclerView;
            if (recyclerView != null && (childAt = recyclerView.getChildAt(i5)) != null) {
                ShopSoundPackageView shopSoundPackageView = (ShopSoundPackageView) childAt;
                RecyclerView recyclerView2 = this$0.mRecyclerView;
                Integer numValueOf = recyclerView2 != null ? Integer.valueOf(recyclerView2.getHeight()) : null;
                Intrinsics.checkNotNull(numValueOf);
                shopSoundPackageView.onNestedScrollChanged(i2, i4, numValueOf.intValue());
            }
        }
    }

    /* compiled from: ShopSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter$ShopSoundPackageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemShopSoundPackageBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemShopSoundPackageBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class ShopSoundPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemShopSoundPackageBinding binding;
        final /* synthetic */ ShopSoundPackagesRvAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ShopSoundPackageViewHolder(ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = shopSoundPackagesRvAdapter;
            this.binding = (ItemShopSoundPackageBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemShopSoundPackageBinding getBinding() {
            return this.binding;
        }
    }

    /* compiled from: ShopSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter$ShopSubscriptionCardViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemShopSubscriptionCardBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemShopSubscriptionCardBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private final class ShopSubscriptionCardViewHolder extends RecyclerView.ViewHolder {
        private final ItemShopSubscriptionCardBinding binding;
        final /* synthetic */ ShopSoundPackagesRvAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ShopSubscriptionCardViewHolder(ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = shopSoundPackagesRvAdapter;
            this.binding = (ItemShopSubscriptionCardBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemShopSubscriptionCardBinding getBinding() {
            return this.binding;
        }
    }
}
