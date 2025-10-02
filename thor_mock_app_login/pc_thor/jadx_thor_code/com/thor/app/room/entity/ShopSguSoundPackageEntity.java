package com.thor.app.room.entity;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShopSguSoundPackageEntity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J?\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u0005HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001c"}, d2 = {"Lcom/thor/app/room/entity/ShopSguSoundPackageEntity;", "", TtmlNode.ATTR_ID, "", "setName", "", "setImageUrl", "setStatus", FirebaseAnalytics.Param.PRICE, "(ILjava/lang/String;Ljava/lang/String;II)V", "getId", "()I", "getPrice", "getSetImageUrl", "()Ljava/lang/String;", "getSetName", "getSetStatus", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ShopSguSoundPackageEntity {
    private final int id;
    private final int price;
    private final String setImageUrl;
    private final String setName;
    private final int setStatus;

    public static /* synthetic */ ShopSguSoundPackageEntity copy$default(ShopSguSoundPackageEntity shopSguSoundPackageEntity, int i, String str, String str2, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = shopSguSoundPackageEntity.id;
        }
        if ((i4 & 2) != 0) {
            str = shopSguSoundPackageEntity.setName;
        }
        String str3 = str;
        if ((i4 & 4) != 0) {
            str2 = shopSguSoundPackageEntity.setImageUrl;
        }
        String str4 = str2;
        if ((i4 & 8) != 0) {
            i2 = shopSguSoundPackageEntity.setStatus;
        }
        int i5 = i2;
        if ((i4 & 16) != 0) {
            i3 = shopSguSoundPackageEntity.price;
        }
        return shopSguSoundPackageEntity.copy(i, str3, str4, i5, i3);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getSetName() {
        return this.setName;
    }

    /* renamed from: component3, reason: from getter */
    public final String getSetImageUrl() {
        return this.setImageUrl;
    }

    /* renamed from: component4, reason: from getter */
    public final int getSetStatus() {
        return this.setStatus;
    }

    /* renamed from: component5, reason: from getter */
    public final int getPrice() {
        return this.price;
    }

    public final ShopSguSoundPackageEntity copy(int id, String setName, String setImageUrl, int setStatus, int price) {
        return new ShopSguSoundPackageEntity(id, setName, setImageUrl, setStatus, price);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ShopSguSoundPackageEntity)) {
            return false;
        }
        ShopSguSoundPackageEntity shopSguSoundPackageEntity = (ShopSguSoundPackageEntity) other;
        return this.id == shopSguSoundPackageEntity.id && Intrinsics.areEqual(this.setName, shopSguSoundPackageEntity.setName) && Intrinsics.areEqual(this.setImageUrl, shopSguSoundPackageEntity.setImageUrl) && this.setStatus == shopSguSoundPackageEntity.setStatus && this.price == shopSguSoundPackageEntity.price;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.setName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.setImageUrl;
        return ((((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + Integer.hashCode(this.setStatus)) * 31) + Integer.hashCode(this.price);
    }

    public String toString() {
        return "ShopSguSoundPackageEntity(id=" + this.id + ", setName=" + this.setName + ", setImageUrl=" + this.setImageUrl + ", setStatus=" + this.setStatus + ", price=" + this.price + ")";
    }

    public ShopSguSoundPackageEntity(int i, String str, String str2, int i2, int i3) {
        this.id = i;
        this.setName = str;
        this.setImageUrl = str2;
        this.setStatus = i2;
        this.price = i3;
    }

    public /* synthetic */ ShopSguSoundPackageEntity(int i, String str, String str2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i4 & 2) != 0 ? "" : str, (i4 & 4) != 0 ? "" : str2, i2, i3);
    }

    public final int getId() {
        return this.id;
    }

    public final String getSetName() {
        return this.setName;
    }

    public final String getSetImageUrl() {
        return this.setImageUrl;
    }

    public final int getSetStatus() {
        return this.setStatus;
    }

    public final int getPrice() {
        return this.price;
    }
}
