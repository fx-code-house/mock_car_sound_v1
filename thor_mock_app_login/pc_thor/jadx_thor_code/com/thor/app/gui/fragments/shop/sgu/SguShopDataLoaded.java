package com.thor.app.gui.fragments.shop.sgu;

import com.android.billingclient.api.SkuDetails;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguShopUIState.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/thor/app/gui/fragments/shop/sgu/SguShopDataLoaded;", "Lcom/thor/app/gui/fragments/shop/sgu/SguShopUIState;", "models", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "skus", "Lcom/android/billingclient/api/SkuDetails;", "(Ljava/util/List;Ljava/util/List;)V", "getModels", "()Ljava/util/List;", "getSkus", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguShopDataLoaded extends SguShopUIState {
    private final List<SguSoundSet> models;
    private final List<SkuDetails> skus;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SguShopDataLoaded copy$default(SguShopDataLoaded sguShopDataLoaded, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = sguShopDataLoaded.models;
        }
        if ((i & 2) != 0) {
            list2 = sguShopDataLoaded.skus;
        }
        return sguShopDataLoaded.copy(list, list2);
    }

    public final List<SguSoundSet> component1() {
        return this.models;
    }

    public final List<SkuDetails> component2() {
        return this.skus;
    }

    public final SguShopDataLoaded copy(List<SguSoundSet> models, List<? extends SkuDetails> skus) {
        Intrinsics.checkNotNullParameter(models, "models");
        Intrinsics.checkNotNullParameter(skus, "skus");
        return new SguShopDataLoaded(models, skus);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguShopDataLoaded)) {
            return false;
        }
        SguShopDataLoaded sguShopDataLoaded = (SguShopDataLoaded) other;
        return Intrinsics.areEqual(this.models, sguShopDataLoaded.models) && Intrinsics.areEqual(this.skus, sguShopDataLoaded.skus);
    }

    public int hashCode() {
        return (this.models.hashCode() * 31) + this.skus.hashCode();
    }

    public String toString() {
        return "SguShopDataLoaded(models=" + this.models + ", skus=" + this.skus + ")";
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SguShopDataLoaded(List<SguSoundSet> models, List<? extends SkuDetails> skus) {
        super(null);
        Intrinsics.checkNotNullParameter(models, "models");
        Intrinsics.checkNotNullParameter(skus, "skus");
        this.models = models;
        this.skus = skus;
    }

    public final List<SguSoundSet> getModels() {
        return this.models;
    }

    public final List<SkuDetails> getSkus() {
        return this.skus;
    }
}
