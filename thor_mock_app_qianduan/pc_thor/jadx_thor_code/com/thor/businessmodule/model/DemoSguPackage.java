package com.thor.businessmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoSguPackage.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 '2\u00020\u0001:\u0001'B;\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J?\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fHÖ\u0003J\t\u0010 \u001a\u00020\u001bHÖ\u0001J\t\u0010!\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001bHÖ\u0001R \u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR&\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006("}, d2 = {"Lcom/thor/businessmodule/model/DemoSguPackage;", "Landroid/os/Parcelable;", TtmlNode.ATTR_ID, "", AppMeasurementSdk.ConditionalUserProperty.NAME, "track", "", "drawableName", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getDrawableName", "()Ljava/lang/String;", "setDrawableName", "(Ljava/lang/String;)V", "getId", "setId", "getName", "setName", "getTrack", "()Ljava/util/List;", "setTrack", "(Ljava/util/List;)V", "component1", "component2", "component3", "component4", "copy", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DemoSguPackage implements Parcelable {
    public static final String BUNDLE_NAME;

    @SerializedName("drawable_name")
    private String drawableName;

    @SerializedName(TtmlNode.ATTR_ID)
    private String id;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @SerializedName("track")
    private List<String> track;
    public static final Parcelable.Creator<DemoSguPackage> CREATOR = new Creator();

    /* compiled from: DemoSguPackage.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<DemoSguPackage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DemoSguPackage createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new DemoSguPackage(parcel.readString(), parcel.readString(), parcel.createStringArrayList(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DemoSguPackage[] newArray(int i) {
            return new DemoSguPackage[i];
        }
    }

    public DemoSguPackage() {
        this(null, null, null, null, 15, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DemoSguPackage copy$default(DemoSguPackage demoSguPackage, String str, String str2, List list, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = demoSguPackage.id;
        }
        if ((i & 2) != 0) {
            str2 = demoSguPackage.name;
        }
        if ((i & 4) != 0) {
            list = demoSguPackage.track;
        }
        if ((i & 8) != 0) {
            str3 = demoSguPackage.drawableName;
        }
        return demoSguPackage.copy(str, str2, list, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final List<String> component3() {
        return this.track;
    }

    /* renamed from: component4, reason: from getter */
    public final String getDrawableName() {
        return this.drawableName;
    }

    public final DemoSguPackage copy(String id, String name, List<String> track, String drawableName) {
        return new DemoSguPackage(id, name, track, drawableName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DemoSguPackage)) {
            return false;
        }
        DemoSguPackage demoSguPackage = (DemoSguPackage) other;
        return Intrinsics.areEqual(this.id, demoSguPackage.id) && Intrinsics.areEqual(this.name, demoSguPackage.name) && Intrinsics.areEqual(this.track, demoSguPackage.track) && Intrinsics.areEqual(this.drawableName, demoSguPackage.drawableName);
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.name;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        List<String> list = this.track;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        String str3 = this.drawableName;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "DemoSguPackage(id=" + this.id + ", name=" + this.name + ", track=" + this.track + ", drawableName=" + this.drawableName + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeStringList(this.track);
        parcel.writeString(this.drawableName);
    }

    public DemoSguPackage(String str, String str2, List<String> list, String str3) {
        this.id = str;
        this.name = str2;
        this.track = list;
        this.drawableName = str3;
    }

    public /* synthetic */ DemoSguPackage(String str, String str2, List list, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : list, (i & 8) != 0 ? null : str3);
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

    public final List<String> getTrack() {
        return this.track;
    }

    public final void setTrack(List<String> list) {
        this.track = list;
    }

    public final String getDrawableName() {
        return this.drawableName;
    }

    public final void setDrawableName(String str) {
        this.drawableName = str;
    }

    static {
        Intrinsics.checkNotNullExpressionValue("DemoSguPackage", "DemoSguPackage::class.java.simpleName");
        BUNDLE_NAME = "DemoSguPackage";
    }
}
