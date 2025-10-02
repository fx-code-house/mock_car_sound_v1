package com.thor.app.gui.fragments.shop.sgu.item;

import android.view.View;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ItemSguShopSoundPackageBinding;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.xwray.groupie.viewbinding.BindableItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShopSguSoundPackageItem.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\u0018\u0010\u0014\u001a\n \u0015*\u0004\u0018\u00010\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0017H\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/thor/app/gui/fragments/shop/sgu/item/ShopSguSoundPackageItem;", "Lcom/xwray/groupie/viewbinding/BindableItem;", "Lcom/carsystems/thor/app/databinding/ItemSguShopSoundPackageBinding;", "model", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "sku", "Lcom/android/billingclient/api/SkuDetails;", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;Lcom/android/billingclient/api/SkuDetails;)V", "getModel", "()Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "getSku", "()Lcom/android/billingclient/api/SkuDetails;", "bind", "", "viewBinding", "position", "", "getId", "", "getLayout", "initializeViewBinding", "kotlin.jvm.PlatformType", "view", "Landroid/view/View;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ShopSguSoundPackageItem extends BindableItem<ItemSguShopSoundPackageBinding> {
    private final SguSoundSet model;
    private final SkuDetails sku;

    @Override // com.xwray.groupie.Item
    public int getLayout() {
        return R.layout.item_sgu_shop_sound_package;
    }

    public final SguSoundSet getModel() {
        return this.model;
    }

    public final SkuDetails getSku() {
        return this.sku;
    }

    public ShopSguSoundPackageItem(SguSoundSet model, SkuDetails skuDetails) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.model = model;
        this.sku = skuDetails;
    }

    @Override // com.xwray.groupie.viewbinding.BindableItem
    public void bind(ItemSguShopSoundPackageBinding viewBinding, int position) {
        Intrinsics.checkNotNullParameter(viewBinding, "viewBinding");
        viewBinding.shopSoundPackageView.setModel(this.model);
        viewBinding.shopSoundPackageView.setSkuDetails(this.sku);
    }

    @Override // com.xwray.groupie.Item
    public long getId() {
        return this.model.getId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xwray.groupie.viewbinding.BindableItem
    public ItemSguShopSoundPackageBinding initializeViewBinding(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return ItemSguShopSoundPackageBinding.bind(view);
    }
}
