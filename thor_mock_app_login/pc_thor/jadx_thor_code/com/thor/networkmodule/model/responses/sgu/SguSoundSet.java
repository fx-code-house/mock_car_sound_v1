package com.thor.networkmodule.model.responses.sgu;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.utils.CurrencyUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundSet.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 82\u00020\u0001:\u00018B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000¢\u0006\u0002\u0010\u0003B\u0081\u0001\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u000f¢\u0006\u0002\u0010\u0015J\t\u00102\u001a\u00020\u0005HÖ\u0001J\u0019\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u0005HÖ\u0001R\u001e\u0010\f\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR \u0010\t\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0017\"\u0004\b\u001d\u0010\u0019R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\u0013\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\"\"\u0004\b#\u0010$R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\"\"\u0004\b%\u0010$R\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R \u0010\b\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0017\"\u0004\b+\u0010\u0019R \u0010\r\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0017\"\u0004\b-\u0010\u0019R \u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0017\"\u0004\b/\u0010\u0019R\u001e\u0010\u000e\u001a\u00020\u000f8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\"\"\u0004\b1\u0010$¨\u00069"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "Landroid/os/Parcelable;", "model", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;)V", TtmlNode.ATTR_ID, "", "setName", "", "setDescription", "iapIdentifier", FirebaseAnalytics.Param.PRICE, "", FirebaseAnalytics.Param.CURRENCY, "setImageUrl", "setStatus", "", "files", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "isLoadedOnBoard", "isNeedUpdate", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;FLjava/lang/String;Ljava/lang/String;ZLjava/util/List;ZZ)V", "getCurrency", "()Ljava/lang/String;", "setCurrency", "(Ljava/lang/String;)V", "getFiles", "()Ljava/util/List;", "getIapIdentifier", "setIapIdentifier", "getId", "()I", "setId", "(I)V", "()Z", "setLoadedOnBoard", "(Z)V", "setNeedUpdate", "getPrice", "()F", "setPrice", "(F)V", "getSetDescription", "setSetDescription", "getSetImageUrl", "setSetImageUrl", "getSetName", "setSetName", "getSetStatus", "setSetStatus", "describeContents", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundSet implements Parcelable {
    public static final String BUNDLE_NAME = "SguSoundSet";

    @SerializedName(FirebaseAnalytics.Param.CURRENCY)
    private String currency;

    @SerializedName("subcategories")
    private final List<SguSound> files;

    @SerializedName("category_iap_identifier")
    private String iapIdentifier;

    @SerializedName("category_id")
    private int id;
    private boolean isLoadedOnBoard;
    private boolean isNeedUpdate;

    @SerializedName("category_price")
    private float price;

    @SerializedName("category_description")
    private String setDescription;

    @SerializedName("category_cover_url")
    private String setImageUrl;

    @SerializedName("category_name")
    private String setName;

    @SerializedName("category_buy_status")
    private boolean setStatus;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Parcelable.Creator<SguSoundSet> CREATOR = new Creator();

    /* compiled from: SguSoundSet.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SguSoundSet> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundSet createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            float f = parcel.readFloat();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            boolean z = parcel.readInt() != 0;
            int i2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 != i2; i3++) {
                arrayList.add(SguSound.CREATOR.createFromParcel(parcel));
            }
            return new SguSoundSet(i, string, string2, string3, f, string4, string5, z, arrayList, parcel.readInt() != 0, parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundSet[] newArray(int i) {
            return new SguSoundSet[i];
        }
    }

    public SguSoundSet() {
        this(0, null, null, null, 0.0f, null, null, false, null, false, false, 2047, null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.id);
        parcel.writeString(this.setName);
        parcel.writeString(this.setDescription);
        parcel.writeString(this.iapIdentifier);
        parcel.writeFloat(this.price);
        parcel.writeString(this.currency);
        parcel.writeString(this.setImageUrl);
        parcel.writeInt(this.setStatus ? 1 : 0);
        List<SguSound> list = this.files;
        parcel.writeInt(list.size());
        Iterator<SguSound> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, flags);
        }
        parcel.writeInt(this.isLoadedOnBoard ? 1 : 0);
        parcel.writeInt(this.isNeedUpdate ? 1 : 0);
    }

    public SguSoundSet(int i, String str, String str2, String str3, float f, String currency, String str4, boolean z, List<SguSound> files, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(currency, "currency");
        Intrinsics.checkNotNullParameter(files, "files");
        this.id = i;
        this.setName = str;
        this.setDescription = str2;
        this.iapIdentifier = str3;
        this.price = f;
        this.currency = currency;
        this.setImageUrl = str4;
        this.setStatus = z;
        this.files = files;
        this.isLoadedOnBoard = z2;
        this.isNeedUpdate = z3;
        this.currency = CurrencyUtil.INSTANCE.getCurrencySymbol(this.currency, null);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getSetName() {
        return this.setName;
    }

    public final void setSetName(String str) {
        this.setName = str;
    }

    public final String getSetDescription() {
        return this.setDescription;
    }

    public final void setSetDescription(String str) {
        this.setDescription = str;
    }

    public final String getIapIdentifier() {
        return this.iapIdentifier;
    }

    public final void setIapIdentifier(String str) {
        this.iapIdentifier = str;
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

    public final String getSetImageUrl() {
        return this.setImageUrl;
    }

    public final void setSetImageUrl(String str) {
        this.setImageUrl = str;
    }

    public final boolean getSetStatus() {
        return this.setStatus;
    }

    public final void setSetStatus(boolean z) {
        this.setStatus = z;
    }

    public /* synthetic */ SguSoundSet(int i, String str, String str2, String str3, float f, String str4, String str5, boolean z, List list, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : str3, (i2 & 16) != 0 ? 0.0f : f, (i2 & 32) != 0 ? "₽" : str4, (i2 & 64) == 0 ? str5 : null, (i2 & 128) != 0 ? false : z, (i2 & 256) != 0 ? CollectionsKt.emptyList() : list, (i2 & 512) != 0 ? false : z2, (i2 & 1024) == 0 ? z3 : false);
    }

    public final List<SguSound> getFiles() {
        return this.files;
    }

    /* renamed from: isLoadedOnBoard, reason: from getter */
    public final boolean getIsLoadedOnBoard() {
        return this.isLoadedOnBoard;
    }

    public final void setLoadedOnBoard(boolean z) {
        this.isLoadedOnBoard = z;
    }

    /* renamed from: isNeedUpdate, reason: from getter */
    public final boolean getIsNeedUpdate() {
        return this.isNeedUpdate;
    }

    public final void setNeedUpdate(boolean z) {
        this.isNeedUpdate = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SguSoundSet(SguSoundSet model) {
        this(0, null, null, null, 0.0f, null, null, false, null, false, false, 2047, null);
        Intrinsics.checkNotNullParameter(model, "model");
        this.id = model.id;
        this.iapIdentifier = model.iapIdentifier;
        this.setName = model.setName;
        this.setDescription = model.setDescription;
        this.setImageUrl = model.setImageUrl;
        this.setStatus = model.setStatus;
        this.price = model.price;
        this.currency = model.currency;
        this.isLoadedOnBoard = model.isLoadedOnBoard;
        this.isNeedUpdate = model.isNeedUpdate;
    }

    /* compiled from: SguSoundSet.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet$Companion;", "", "()V", "BUNDLE_NAME", "", "copy", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "model", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SguSoundSet copy(SguSoundSet model) {
            Intrinsics.checkNotNullParameter(model, "model");
            return new SguSoundSet(model);
        }
    }
}
