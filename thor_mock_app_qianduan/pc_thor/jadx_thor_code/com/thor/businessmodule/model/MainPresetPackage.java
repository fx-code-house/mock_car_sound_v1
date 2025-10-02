package com.thor.businessmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainPresetPackage.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b1\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 F2\u00020\u0001:\u0001FBy\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000b¢\u0006\u0002\u0010\u0010J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\t\u0010/\u001a\u00020\u000bHÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u0010\u00103\u001a\u0004\u0018\u00010\tHÆ\u0003¢\u0006\u0002\u0010$J\t\u00104\u001a\u00020\u000bHÆ\u0003J\u0010\u00105\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001aJ\u000b\u00106\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0082\u0001\u00108\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u000bHÆ\u0001¢\u0006\u0002\u00109J\t\u0010:\u001a\u00020\u0003HÖ\u0001J\u0013\u0010;\u001a\u00020\u000b2\b\u0010<\u001a\u0004\u0018\u00010=H\u0096\u0002J\b\u0010>\u001a\u00020\u0003H\u0016J\u0006\u0010?\u001a\u00020\u000bJ\t\u0010@\u001a\u00020\u0005HÖ\u0001J\u0019\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u000f\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\r\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\f\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR \u0010\u000e\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018R \u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u0018R\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0012\"\u0004\b\"\u0010\u0014R\"\u0010\b\u001a\u0004\u0018\u00010\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010'\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0016\"\u0004\b)\u0010\u0018R\"\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b*\u0010\u001a\"\u0004\b+\u0010\u001cR\"\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b,\u0010\u001a\"\u0004\b-\u0010\u001c¨\u0006G"}, d2 = {"Lcom/thor/businessmodule/model/MainPresetPackage;", "Landroid/os/Parcelable;", "packageId", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "imageUrl", "versionId", "modeType", "", "isActivated", "", "driveSelectModeId", "driveSelectMode", "driveSelectModeStatus", "damageItem", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Short;ZLjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Z)V", "getDamageItem", "()Z", "setDamageItem", "(Z)V", "getDriveSelectMode", "()Ljava/lang/String;", "setDriveSelectMode", "(Ljava/lang/String;)V", "getDriveSelectModeId", "()Ljava/lang/Integer;", "setDriveSelectModeId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getDriveSelectModeStatus", "setDriveSelectModeStatus", "getImageUrl", "setImageUrl", "setActivated", "getModeType", "()Ljava/lang/Short;", "setModeType", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "getName", "setName", "getPackageId", "setPackageId", "getVersionId", "setVersionId", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Short;ZLjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Z)Lcom/thor/businessmodule/model/MainPresetPackage;", "describeContents", "equals", "other", "", "hashCode", "isOwnType", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class MainPresetPackage implements Parcelable {
    public static final String BUNDLE_NAME = "MainPresetPackage";
    public static final String BUNDLE_NAME_LIST = "MainPresetPackages";

    @SerializedName("damage_item")
    private boolean damageItem;

    @SerializedName("drive_select")
    private String driveSelectMode;

    @SerializedName("drive_select_id")
    private Integer driveSelectModeId;

    @SerializedName("drive_select_status")
    private String driveSelectModeStatus;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("is_activated")
    private boolean isActivated;

    @SerializedName("mode_type")
    private Short modeType;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @SerializedName("package_id")
    private Integer packageId;

    @SerializedName("version_id")
    private Integer versionId;
    public static final Parcelable.Creator<MainPresetPackage> CREATOR = new Creator();

    /* compiled from: MainPresetPackage.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<MainPresetPackage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final MainPresetPackage createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new MainPresetPackage(parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), parcel.readString(), parcel.readString(), parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), parcel.readInt() == 0 ? null : Short.valueOf((short) parcel.readInt()), parcel.readInt() != 0, parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), parcel.readString(), parcel.readString(), parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final MainPresetPackage[] newArray(int i) {
            return new MainPresetPackage[i];
        }
    }

    public MainPresetPackage() {
        this(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
    }

    /* renamed from: component1, reason: from getter */
    public final Integer getPackageId() {
        return this.packageId;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getDamageItem() {
        return this.damageItem;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String getImageUrl() {
        return this.imageUrl;
    }

    /* renamed from: component4, reason: from getter */
    public final Integer getVersionId() {
        return this.versionId;
    }

    /* renamed from: component5, reason: from getter */
    public final Short getModeType() {
        return this.modeType;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getIsActivated() {
        return this.isActivated;
    }

    /* renamed from: component7, reason: from getter */
    public final Integer getDriveSelectModeId() {
        return this.driveSelectModeId;
    }

    /* renamed from: component8, reason: from getter */
    public final String getDriveSelectMode() {
        return this.driveSelectMode;
    }

    /* renamed from: component9, reason: from getter */
    public final String getDriveSelectModeStatus() {
        return this.driveSelectModeStatus;
    }

    public final MainPresetPackage copy(Integer packageId, String name, String imageUrl, Integer versionId, Short modeType, boolean isActivated, Integer driveSelectModeId, String driveSelectMode, String driveSelectModeStatus, boolean damageItem) {
        return new MainPresetPackage(packageId, name, imageUrl, versionId, modeType, isActivated, driveSelectModeId, driveSelectMode, driveSelectModeStatus, damageItem);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "MainPresetPackage(packageId=" + this.packageId + ", name=" + this.name + ", imageUrl=" + this.imageUrl + ", versionId=" + this.versionId + ", modeType=" + this.modeType + ", isActivated=" + this.isActivated + ", driveSelectModeId=" + this.driveSelectModeId + ", driveSelectMode=" + this.driveSelectMode + ", driveSelectModeStatus=" + this.driveSelectModeStatus + ", damageItem=" + this.damageItem + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        Integer num = this.packageId;
        if (num == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(num.intValue());
        }
        parcel.writeString(this.name);
        parcel.writeString(this.imageUrl);
        Integer num2 = this.versionId;
        if (num2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(num2.intValue());
        }
        Short sh = this.modeType;
        if (sh == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(sh.shortValue());
        }
        parcel.writeInt(this.isActivated ? 1 : 0);
        Integer num3 = this.driveSelectModeId;
        if (num3 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(num3.intValue());
        }
        parcel.writeString(this.driveSelectMode);
        parcel.writeString(this.driveSelectModeStatus);
        parcel.writeInt(this.damageItem ? 1 : 0);
    }

    public MainPresetPackage(Integer num, String str, String str2, Integer num2, Short sh, boolean z, Integer num3, String str3, String str4, boolean z2) {
        this.packageId = num;
        this.name = str;
        this.imageUrl = str2;
        this.versionId = num2;
        this.modeType = sh;
        this.isActivated = z;
        this.driveSelectModeId = num3;
        this.driveSelectMode = str3;
        this.driveSelectModeStatus = str4;
        this.damageItem = z2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ MainPresetPackage(Integer num, String str, String str2, Integer num2, Short sh, boolean z, Integer num3, String str3, String str4, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? num : num, (i & 2) != 0 ? null : str, (i & 4) == 0 ? str2 : null, (i & 8) != 0 ? num : num2, (i & 16) != 0 ? (short) 0 : sh, (i & 32) != 0 ? false : z, (i & 64) == 0 ? num3 : 0, (i & 128) != 0 ? "" : str3, (i & 256) == 0 ? str4 : "", (i & 512) == 0 ? z2 : false);
    }

    public final Integer getPackageId() {
        return this.packageId;
    }

    public final void setPackageId(Integer num) {
        this.packageId = num;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getImageUrl() {
        return this.imageUrl;
    }

    public final void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public final Integer getVersionId() {
        return this.versionId;
    }

    public final void setVersionId(Integer num) {
        this.versionId = num;
    }

    public final Short getModeType() {
        return this.modeType;
    }

    public final void setModeType(Short sh) {
        this.modeType = sh;
    }

    public final boolean isActivated() {
        return this.isActivated;
    }

    public final void setActivated(boolean z) {
        this.isActivated = z;
    }

    public final Integer getDriveSelectModeId() {
        return this.driveSelectModeId;
    }

    public final void setDriveSelectModeId(Integer num) {
        this.driveSelectModeId = num;
    }

    public final String getDriveSelectMode() {
        return this.driveSelectMode;
    }

    public final void setDriveSelectMode(String str) {
        this.driveSelectMode = str;
    }

    public final String getDriveSelectModeStatus() {
        return this.driveSelectModeStatus;
    }

    public final void setDriveSelectModeStatus(String str) {
        this.driveSelectModeStatus = str;
    }

    public final boolean getDamageItem() {
        return this.damageItem;
    }

    public final void setDamageItem(boolean z) {
        this.damageItem = z;
    }

    public final boolean isOwnType() {
        Short sh = this.modeType;
        return sh != null && sh.shortValue() == 3;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.thor.businessmodule.model.MainPresetPackage");
        MainPresetPackage mainPresetPackage = (MainPresetPackage) other;
        if (!Intrinsics.areEqual(this.packageId, mainPresetPackage.packageId) || !Intrinsics.areEqual(this.name, mainPresetPackage.name)) {
            return false;
        }
        Short sh = this.modeType;
        Integer numValueOf = sh != null ? Integer.valueOf(sh.shortValue()) : null;
        Short sh2 = mainPresetPackage.modeType;
        return Intrinsics.areEqual(numValueOf, sh2 != null ? Integer.valueOf(sh2.shortValue()) : null) && Intrinsics.areEqual(this.driveSelectMode, mainPresetPackage.driveSelectMode) && Intrinsics.areEqual(this.driveSelectModeStatus, mainPresetPackage.driveSelectModeStatus) && this.isActivated == mainPresetPackage.isActivated;
    }

    public int hashCode() {
        Integer num = this.packageId;
        int iIntValue = (num != null ? num.intValue() : 0) * 31;
        String str = this.name;
        int iHashCode = (iIntValue + (str != null ? str.hashCode() : 0)) * 31;
        Short sh = this.modeType;
        return iHashCode + (sh != null ? sh.shortValue() : (short) 0);
    }
}
