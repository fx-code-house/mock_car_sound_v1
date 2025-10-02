package com.thor.networkmodule.model.shop;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.utils.CurrencyUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.apache.commons.lang3.BooleanUtils;

/* compiled from: ShopSoundPackage.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b,\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 K2\u00020\u0001:\u0001KB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000¢\u0006\u0002\u0010\u0003B\u0083\u0001\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\u001c\b\u0002\u0010\u000e\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\n\u0012\u0004\u0012\u00020\u0010\u0018\u0001`\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0013¢\u0006\u0002\u0010\u0015J\t\u00102\u001a\u00020\u0005HÆ\u0003J\t\u00103\u001a\u00020\u0013HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u00105\u001a\u00020\u0005HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u00108\u001a\u00020\fHÆ\u0003J\t\u00109\u001a\u00020\u0007HÆ\u0003J\u001d\u0010:\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\n\u0012\u0004\u0012\u00020\u0010\u0018\u0001`\u0011HÆ\u0003J\t\u0010;\u001a\u00020\u0013HÆ\u0003J\u0087\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00072\u001c\b\u0002\u0010\u000e\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\n\u0012\u0004\u0012\u00020\u0010\u0018\u0001`\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013HÆ\u0001J\t\u0010=\u001a\u00020\u0005HÖ\u0001J\u0013\u0010>\u001a\u00020\u00132\b\u0010?\u001a\u0004\u0018\u00010@H\u0096\u0002J\u0006\u0010A\u001a\u00020\u0007J\b\u0010B\u001a\u00020\u0005H\u0016J\u0006\u0010C\u001a\u00020\u0013J\u0006\u0010D\u001a\u00020EJ\t\u0010F\u001a\u00020\u0007HÖ\u0001J\u0019\u0010G\u001a\u00020E2\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u0005HÖ\u0001R\u001e\u0010\r\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001e\u0010\u0012\u001a\u00020\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u001e\"\u0004\b\u001f\u0010 R\u001e\u0010\u0014\u001a\u00020\u00138\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u001e\"\u0004\b!\u0010 R2\u0010\u000e\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\n\u0012\u0004\u0012\u00020\u0010\u0018\u0001`\u00118\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R \u0010\t\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0017\"\u0004\b'\u0010\u0019R \u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0017\"\u0004\b)\u0010\u0019R \u0010\n\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0017\"\u0004\b+\u0010\u0019R\u001e\u0010\b\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001b\"\u0004\b-\u0010\u001dR\u001e\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101¨\u0006L"}, d2 = {"Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "Landroid/os/Parcelable;", "shopSoundPackage", "(Lcom/thor/networkmodule/model/shop/ShopSoundPackage;)V", TtmlNode.ATTR_ID, "", "pkgName", "", "pkgVer", "pkgImageUrl", "pkgStatus", FirebaseAnalytics.Param.PRICE, "", FirebaseAnalytics.Param.CURRENCY, "modeImages", "Ljava/util/ArrayList;", "Lcom/thor/networkmodule/model/ModeType;", "Lkotlin/collections/ArrayList;", "isLoadedOnBoard", "", "isNeedUpdate", "(ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;FLjava/lang/String;Ljava/util/ArrayList;ZZ)V", "getCurrency", "()Ljava/lang/String;", "setCurrency", "(Ljava/lang/String;)V", "getId", "()I", "setId", "(I)V", "()Z", "setLoadedOnBoard", "(Z)V", "setNeedUpdate", "getModeImages", "()Ljava/util/ArrayList;", "setModeImages", "(Ljava/util/ArrayList;)V", "getPkgImageUrl", "setPkgImageUrl", "getPkgName", "setPkgName", "getPkgStatus", "setPkgStatus", "getPkgVer", "setPkgVer", "getPrice", "()F", "setPrice", "(F)V", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "other", "", "getGooglePlayId", "hashCode", "isPurchased", "setPurchasedState", "", "toString", "writeToParcel", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ShopSoundPackage implements Parcelable {
    private static final String BUNDLE_NAME;

    @SerializedName(FirebaseAnalytics.Param.CURRENCY)
    private String currency;

    @SerializedName(TtmlNode.ATTR_ID)
    private int id;

    @SerializedName("is_loaded_on_board")
    private boolean isLoadedOnBoard;

    @SerializedName("is_need_update")
    private boolean isNeedUpdate;

    @SerializedName("mode_images")
    private ArrayList<ModeType> modeImages;

    @SerializedName("pkg_image_url")
    private String pkgImageUrl;

    @SerializedName("pkg_name")
    private String pkgName;

    @SerializedName("pkg_status")
    private String pkgStatus;

    @SerializedName("pkg_ver")
    private int pkgVer;

    @SerializedName(FirebaseAnalytics.Param.PRICE)
    private float price;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Parcelable.Creator<ShopSoundPackage> CREATOR = new Creator();

    /* compiled from: ShopSoundPackage.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ShopSoundPackage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ShopSoundPackage createFromParcel(Parcel parcel) {
            ArrayList arrayList;
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            int i = parcel.readInt();
            String string = parcel.readString();
            int i2 = parcel.readInt();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            float f = parcel.readFloat();
            String string4 = parcel.readString();
            if (parcel.readInt() == 0) {
                arrayList = null;
            } else {
                int i3 = parcel.readInt();
                arrayList = new ArrayList(i3);
                for (int i4 = 0; i4 != i3; i4++) {
                    arrayList.add(ModeType.CREATOR.createFromParcel(parcel));
                }
            }
            return new ShopSoundPackage(i, string, i2, string2, string3, f, string4, arrayList, parcel.readInt() != 0, parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ShopSoundPackage[] newArray(int i) {
            return new ShopSoundPackage[i];
        }
    }

    public ShopSoundPackage() {
        this(0, null, 0, null, null, 0.0f, null, null, false, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getIsNeedUpdate() {
        return this.isNeedUpdate;
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
    public final String getPkgStatus() {
        return this.pkgStatus;
    }

    /* renamed from: component6, reason: from getter */
    public final float getPrice() {
        return this.price;
    }

    /* renamed from: component7, reason: from getter */
    public final String getCurrency() {
        return this.currency;
    }

    public final ArrayList<ModeType> component8() {
        return this.modeImages;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean getIsLoadedOnBoard() {
        return this.isLoadedOnBoard;
    }

    public final ShopSoundPackage copy(int id, String pkgName, int pkgVer, String pkgImageUrl, String pkgStatus, float price, String currency, ArrayList<ModeType> modeImages, boolean isLoadedOnBoard, boolean isNeedUpdate) {
        Intrinsics.checkNotNullParameter(currency, "currency");
        return new ShopSoundPackage(id, pkgName, pkgVer, pkgImageUrl, pkgStatus, price, currency, modeImages, isLoadedOnBoard, isNeedUpdate);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ShopSoundPackage(id=" + this.id + ", pkgName=" + this.pkgName + ", pkgVer=" + this.pkgVer + ", pkgImageUrl=" + this.pkgImageUrl + ", pkgStatus=" + this.pkgStatus + ", price=" + this.price + ", currency=" + this.currency + ", modeImages=" + this.modeImages + ", isLoadedOnBoard=" + this.isLoadedOnBoard + ", isNeedUpdate=" + this.isNeedUpdate + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.id);
        parcel.writeString(this.pkgName);
        parcel.writeInt(this.pkgVer);
        parcel.writeString(this.pkgImageUrl);
        parcel.writeString(this.pkgStatus);
        parcel.writeFloat(this.price);
        parcel.writeString(this.currency);
        ArrayList<ModeType> arrayList = this.modeImages;
        if (arrayList == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(arrayList.size());
            Iterator<ModeType> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, flags);
            }
        }
        parcel.writeInt(this.isLoadedOnBoard ? 1 : 0);
        parcel.writeInt(this.isNeedUpdate ? 1 : 0);
    }

    public ShopSoundPackage(int i, String str, int i2, String str2, String str3, float f, String currency, ArrayList<ModeType> arrayList, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(currency, "currency");
        this.id = i;
        this.pkgName = str;
        this.pkgVer = i2;
        this.pkgImageUrl = str2;
        this.pkgStatus = str3;
        this.price = f;
        this.currency = currency;
        this.modeImages = arrayList;
        this.isLoadedOnBoard = z;
        this.isNeedUpdate = z2;
        this.currency = CurrencyUtil.INSTANCE.getCurrencySymbol(this.currency, null);
    }

    public /* synthetic */ ShopSoundPackage(int i, String str, int i2, String str2, String str3, float f, String str4, ArrayList arrayList, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? null : str, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? null : str2, (i3 & 16) != 0 ? null : str3, (i3 & 32) != 0 ? 0.0f : f, (i3 & 64) != 0 ? "₽" : str4, (i3 & 128) == 0 ? arrayList : null, (i3 & 256) != 0 ? false : z, (i3 & 512) == 0 ? z2 : false);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getPkgName() {
        return this.pkgName;
    }

    public final void setPkgName(String str) {
        this.pkgName = str;
    }

    public final int getPkgVer() {
        return this.pkgVer;
    }

    public final void setPkgVer(int i) {
        this.pkgVer = i;
    }

    public final String getPkgImageUrl() {
        return this.pkgImageUrl;
    }

    public final void setPkgImageUrl(String str) {
        this.pkgImageUrl = str;
    }

    public final String getPkgStatus() {
        return this.pkgStatus;
    }

    public final void setPkgStatus(String str) {
        this.pkgStatus = str;
    }

    public final float getPrice() {
        return this.price;
    }

    public final void setPrice(float f) {
        this.price = f;
    }

    public final String getCurrency() {
        return this.currency;
    }

    public final void setCurrency(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currency = str;
    }

    public final ArrayList<ModeType> getModeImages() {
        return this.modeImages;
    }

    public final void setModeImages(ArrayList<ModeType> arrayList) {
        this.modeImages = arrayList;
    }

    public final boolean isLoadedOnBoard() {
        return this.isLoadedOnBoard;
    }

    public final void setLoadedOnBoard(boolean z) {
        this.isLoadedOnBoard = z;
    }

    public final boolean isNeedUpdate() {
        return this.isNeedUpdate;
    }

    public final void setNeedUpdate(boolean z) {
        this.isNeedUpdate = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShopSoundPackage(ShopSoundPackage shopSoundPackage) {
        this(0, null, 0, null, null, 0.0f, null, null, false, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
        Intrinsics.checkNotNullParameter(shopSoundPackage, "shopSoundPackage");
        this.id = shopSoundPackage.id;
        this.pkgName = shopSoundPackage.pkgName;
        this.pkgVer = shopSoundPackage.pkgVer;
        this.pkgImageUrl = shopSoundPackage.pkgImageUrl;
        this.pkgStatus = shopSoundPackage.pkgStatus;
        this.price = shopSoundPackage.price;
        this.currency = shopSoundPackage.currency;
        this.modeImages = shopSoundPackage.modeImages;
        this.isLoadedOnBoard = shopSoundPackage.isLoadedOnBoard;
    }

    public final void setPurchasedState() {
        this.pkgStatus = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
    }

    public final boolean isPurchased() {
        return Intrinsics.areEqual(this.pkgStatus, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) || Intrinsics.areEqual(this.pkgStatus, BooleanUtils.TRUE);
    }

    public final String getGooglePlayId() {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("sound_package_%d", Arrays.copyOf(new Object[]{Integer.valueOf(this.id)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
        return str;
    }

    public boolean equals(Object other) {
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.thor.networkmodule.model.shop.ShopSoundPackage");
        ShopSoundPackage shopSoundPackage = (ShopSoundPackage) other;
        return this.isLoadedOnBoard == shopSoundPackage.isLoadedOnBoard && Intrinsics.areEqual(this.pkgImageUrl, shopSoundPackage.pkgImageUrl) && Intrinsics.areEqual(this.pkgStatus, shopSoundPackage.pkgStatus) && Intrinsics.areEqual(this.pkgName, shopSoundPackage.pkgName) && this.isNeedUpdate == shopSoundPackage.isNeedUpdate;
    }

    public int hashCode() {
        return this.id;
    }

    /* compiled from: ShopSoundPackage.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lcom/thor/networkmodule/model/shop/ShopSoundPackage$Companion;", "", "()V", "BUNDLE_NAME", "", "getBUNDLE_NAME", "()Ljava/lang/String;", "copy", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "soundPackage", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getBUNDLE_NAME() {
            return ShopSoundPackage.BUNDLE_NAME;
        }

        public final ShopSoundPackage copy(ShopSoundPackage soundPackage) {
            Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
            return new ShopSoundPackage(soundPackage);
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ShopSoundPackage", "ShopSoundPackage::class.java.simpleName");
        BUNDLE_NAME = "ShopSoundPackage";
    }
}
