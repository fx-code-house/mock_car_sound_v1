package com.thor.networkmodule.model.responses;

import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShopSoundPackagesResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\n\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R&\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0013"}, d2 = {"Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "soundItems", "", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "(Ljava/util/List;)V", "getSoundItems", "()Ljava/util/List;", "setSoundItems", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ShopSoundPackagesResponse extends BaseResponse {

    @SerializedName("sound_items")
    private List<ShopSoundPackage> soundItems;

    /* JADX WARN: Multi-variable type inference failed */
    public ShopSoundPackagesResponse() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ShopSoundPackagesResponse copy$default(ShopSoundPackagesResponse shopSoundPackagesResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = shopSoundPackagesResponse.soundItems;
        }
        return shopSoundPackagesResponse.copy(list);
    }

    public final List<ShopSoundPackage> component1() {
        return this.soundItems;
    }

    public final ShopSoundPackagesResponse copy(List<ShopSoundPackage> soundItems) {
        return new ShopSoundPackagesResponse(soundItems);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ShopSoundPackagesResponse) && Intrinsics.areEqual(this.soundItems, ((ShopSoundPackagesResponse) other).soundItems);
    }

    public int hashCode() {
        List<ShopSoundPackage> list = this.soundItems;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "ShopSoundPackagesResponse(soundItems=" + this.soundItems + ")";
    }

    public /* synthetic */ ShopSoundPackagesResponse(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public final List<ShopSoundPackage> getSoundItems() {
        return this.soundItems;
    }

    public final void setSoundItems(List<ShopSoundPackage> list) {
        this.soundItems = list;
    }

    public ShopSoundPackagesResponse(List<ShopSoundPackage> list) {
        this.soundItems = list;
    }
}
