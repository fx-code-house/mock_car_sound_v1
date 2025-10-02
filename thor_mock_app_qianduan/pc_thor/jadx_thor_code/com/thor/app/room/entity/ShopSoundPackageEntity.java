package com.thor.app.room.entity;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.networkmodule.model.ModeType;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShopSoundPackageEntity.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0010\b\u0001\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b¢\u0006\u0002\u0010\rJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bHÆ\u0003J[\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\u0010\b\u0003\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bHÆ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020\u0003HÖ\u0001J\t\u0010&\u001a\u00020\u0005HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR&\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000f¨\u0006'"}, d2 = {"Lcom/thor/app/room/entity/ShopSoundPackageEntity;", "", TtmlNode.ATTR_ID, "", "pkgName", "", "pkgVer", "pkgImageUrl", "pkgStatus", FirebaseAnalytics.Param.PRICE, "modeTypes", "", "Lcom/thor/networkmodule/model/ModeType;", "(ILjava/lang/String;ILjava/lang/String;IILjava/util/List;)V", "getId", "()I", "getModeTypes", "()Ljava/util/List;", "setModeTypes", "(Ljava/util/List;)V", "getPkgImageUrl", "()Ljava/lang/String;", "getPkgName", "getPkgStatus", "getPkgVer", "getPrice", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ShopSoundPackageEntity {
    private final int id;
    private List<ModeType> modeTypes;
    private final String pkgImageUrl;
    private final String pkgName;
    private final int pkgStatus;
    private final int pkgVer;
    private final int price;

    public static /* synthetic */ ShopSoundPackageEntity copy$default(ShopSoundPackageEntity shopSoundPackageEntity, int i, String str, int i2, String str2, int i3, int i4, List list, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = shopSoundPackageEntity.id;
        }
        if ((i5 & 2) != 0) {
            str = shopSoundPackageEntity.pkgName;
        }
        String str3 = str;
        if ((i5 & 4) != 0) {
            i2 = shopSoundPackageEntity.pkgVer;
        }
        int i6 = i2;
        if ((i5 & 8) != 0) {
            str2 = shopSoundPackageEntity.pkgImageUrl;
        }
        String str4 = str2;
        if ((i5 & 16) != 0) {
            i3 = shopSoundPackageEntity.pkgStatus;
        }
        int i7 = i3;
        if ((i5 & 32) != 0) {
            i4 = shopSoundPackageEntity.price;
        }
        int i8 = i4;
        if ((i5 & 64) != 0) {
            list = shopSoundPackageEntity.modeTypes;
        }
        return shopSoundPackageEntity.copy(i, str3, i6, str4, i7, i8, list);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getPkgName() {
        return this.pkgName;
    }

    /* renamed from: component3, reason: from getter */
    public final int getPkgVer() {
        return this.pkgVer;
    }

    /* renamed from: component4, reason: from getter */
    public final String getPkgImageUrl() {
        return this.pkgImageUrl;
    }

    /* renamed from: component5, reason: from getter */
    public final int getPkgStatus() {
        return this.pkgStatus;
    }

    /* renamed from: component6, reason: from getter */
    public final int getPrice() {
        return this.price;
    }

    public final List<ModeType> component7() {
        return this.modeTypes;
    }

    public final ShopSoundPackageEntity copy(int id, String pkgName, int pkgVer, String pkgImageUrl, int pkgStatus, int price, List<ModeType> modeTypes) {
        return new ShopSoundPackageEntity(id, pkgName, pkgVer, pkgImageUrl, pkgStatus, price, modeTypes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ShopSoundPackageEntity)) {
            return false;
        }
        ShopSoundPackageEntity shopSoundPackageEntity = (ShopSoundPackageEntity) other;
        return this.id == shopSoundPackageEntity.id && Intrinsics.areEqual(this.pkgName, shopSoundPackageEntity.pkgName) && this.pkgVer == shopSoundPackageEntity.pkgVer && Intrinsics.areEqual(this.pkgImageUrl, shopSoundPackageEntity.pkgImageUrl) && this.pkgStatus == shopSoundPackageEntity.pkgStatus && this.price == shopSoundPackageEntity.price && Intrinsics.areEqual(this.modeTypes, shopSoundPackageEntity.modeTypes);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.pkgName;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.pkgVer)) * 31;
        String str2 = this.pkgImageUrl;
        int iHashCode3 = (((((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.pkgStatus)) * 31) + Integer.hashCode(this.price)) * 31;
        List<ModeType> list = this.modeTypes;
        return iHashCode3 + (list != null ? list.hashCode() : 0);
    }

    public String toString() {
        return "ShopSoundPackageEntity(id=" + this.id + ", pkgName=" + this.pkgName + ", pkgVer=" + this.pkgVer + ", pkgImageUrl=" + this.pkgImageUrl + ", pkgStatus=" + this.pkgStatus + ", price=" + this.price + ", modeTypes=" + this.modeTypes + ")";
    }

    public ShopSoundPackageEntity(int i, String str, int i2, String str2, int i3, int i4, List<ModeType> list) {
        this.id = i;
        this.pkgName = str;
        this.pkgVer = i2;
        this.pkgImageUrl = str2;
        this.pkgStatus = i3;
        this.price = i4;
        this.modeTypes = list;
    }

    public /* synthetic */ ShopSoundPackageEntity(int i, String str, int i2, String str2, int i3, int i4, List list, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i5 & 2) != 0 ? "" : str, i2, (i5 & 8) != 0 ? "" : str2, i3, i4, list);
    }

    public final int getId() {
        return this.id;
    }

    public final String getPkgName() {
        return this.pkgName;
    }

    public final int getPkgVer() {
        return this.pkgVer;
    }

    public final String getPkgImageUrl() {
        return this.pkgImageUrl;
    }

    public final int getPkgStatus() {
        return this.pkgStatus;
    }

    public final int getPrice() {
        return this.price;
    }

    public final List<ModeType> getModeTypes() {
        return this.modeTypes;
    }

    public final void setModeTypes(List<ModeType> list) {
        this.modeTypes = list;
    }
}
