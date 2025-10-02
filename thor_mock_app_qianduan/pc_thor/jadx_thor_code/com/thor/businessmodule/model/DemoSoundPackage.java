package com.thor.businessmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoSoundPackage.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001c\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 .2\u00020\u0001:\u0002./BM\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tJ\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003JQ\u0010\u001e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0096\u0002J\u0006\u0010%\u001a\u00020&J\b\u0010'\u001a\u00020 H\u0016J\t\u0010(\u001a\u00020\u0003HÖ\u0001J\u0019\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020 HÖ\u0001R \u0010\b\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR \u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR \u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\r¨\u00060"}, d2 = {"Lcom/thor/businessmodule/model/DemoSoundPackage;", "Landroid/os/Parcelable;", TtmlNode.ATTR_ID, "", AppMeasurementSdk.ConditionalUserProperty.NAME, "track", "trackRules", "drawableName", "category", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCategory", "()Ljava/lang/String;", "setCategory", "(Ljava/lang/String;)V", "getDrawableName", "setDrawableName", "getId", "setId", "getName", "setName", "getTrack", "setTrack", "getTrackRules", "setTrackRules", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "describeContents", "", "equals", "", "other", "", "getCategoryType", "Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "FuelCategory", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DemoSoundPackage implements Parcelable {
    public static final String BUNDLE_NAME;

    @SerializedName("category")
    private String category;

    @SerializedName("drawable_name")
    private String drawableName;

    @SerializedName(TtmlNode.ATTR_ID)
    private String id;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @SerializedName("track")
    private String track;

    @SerializedName("track_rules")
    private String trackRules;
    public static final Parcelable.Creator<DemoSoundPackage> CREATOR = new Creator();

    /* compiled from: DemoSoundPackage.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<DemoSoundPackage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DemoSoundPackage createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new DemoSoundPackage(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DemoSoundPackage[] newArray(int i) {
            return new DemoSoundPackage[i];
        }
    }

    public DemoSoundPackage() {
        this(null, null, null, null, null, null, 63, null);
    }

    public static /* synthetic */ DemoSoundPackage copy$default(DemoSoundPackage demoSoundPackage, String str, String str2, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = demoSoundPackage.id;
        }
        if ((i & 2) != 0) {
            str2 = demoSoundPackage.name;
        }
        String str7 = str2;
        if ((i & 4) != 0) {
            str3 = demoSoundPackage.track;
        }
        String str8 = str3;
        if ((i & 8) != 0) {
            str4 = demoSoundPackage.trackRules;
        }
        String str9 = str4;
        if ((i & 16) != 0) {
            str5 = demoSoundPackage.drawableName;
        }
        String str10 = str5;
        if ((i & 32) != 0) {
            str6 = demoSoundPackage.category;
        }
        return demoSoundPackage.copy(str, str7, str8, str9, str10, str6);
    }

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String getTrack() {
        return this.track;
    }

    /* renamed from: component4, reason: from getter */
    public final String getTrackRules() {
        return this.trackRules;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDrawableName() {
        return this.drawableName;
    }

    /* renamed from: component6, reason: from getter */
    public final String getCategory() {
        return this.category;
    }

    public final DemoSoundPackage copy(String id, String name, String track, String trackRules, String drawableName, String category) {
        return new DemoSoundPackage(id, name, track, trackRules, drawableName, category);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "DemoSoundPackage(id=" + this.id + ", name=" + this.name + ", track=" + this.track + ", trackRules=" + this.trackRules + ", drawableName=" + this.drawableName + ", category=" + this.category + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.track);
        parcel.writeString(this.trackRules);
        parcel.writeString(this.drawableName);
        parcel.writeString(this.category);
    }

    public DemoSoundPackage(String str, String str2, String str3, String str4, String str5, String str6) {
        this.id = str;
        this.name = str2;
        this.track = str3;
        this.trackRules = str4;
        this.drawableName = str5;
        this.category = str6;
    }

    public /* synthetic */ DemoSoundPackage(String str, String str2, String str3, String str4, String str5, String str6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6);
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(String str) {
        this.id = str;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getTrack() {
        return this.track;
    }

    public final void setTrack(String str) {
        this.track = str;
    }

    public final String getTrackRules() {
        return this.trackRules;
    }

    public final void setTrackRules(String str) {
        this.trackRules = str;
    }

    public final String getDrawableName() {
        return this.drawableName;
    }

    public final void setDrawableName(String str) {
        this.drawableName = str;
    }

    public final String getCategory() {
        return this.category;
    }

    public final void setCategory(String str) {
        this.category = str;
    }

    /* compiled from: DemoSoundPackage.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "EV", "DIESEL", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum FuelCategory {
        EV("ev"),
        DIESEL("diesel");

        private final String value;

        FuelCategory(String str) {
            this.value = str;
        }

        public final String getValue() {
            return this.value;
        }
    }

    public final FuelCategory getCategoryType() {
        String str = this.category;
        return Intrinsics.areEqual(str, FuelCategory.DIESEL.getValue()) ? FuelCategory.DIESEL : Intrinsics.areEqual(str, FuelCategory.EV.getValue()) ? FuelCategory.EV : FuelCategory.DIESEL;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.thor.businessmodule.model.DemoSoundPackage");
        DemoSoundPackage demoSoundPackage = (DemoSoundPackage) other;
        return Intrinsics.areEqual(this.name, demoSoundPackage.name) && Intrinsics.areEqual(this.track, demoSoundPackage.track) && Intrinsics.areEqual(this.trackRules, demoSoundPackage.trackRules);
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.track;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.trackRules;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    static {
        Intrinsics.checkNotNullExpressionValue("DemoSoundPackage", "DemoSoundPackage::class.java.simpleName");
        BUNDLE_NAME = "DemoSoundPackage";
    }
}
