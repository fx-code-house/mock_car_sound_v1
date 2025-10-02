package com.thor.networkmodule.model.car;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarGeneration.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003JA\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010!\u001a\u00020\u0005J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\t\u0010#\u001a\u00020\u0005HÖ\u0001R \u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\b\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\r¨\u0006$"}, d2 = {"Lcom/thor/networkmodule/model/car/CarGeneration;", "", TtmlNode.ATTR_ID, "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "carModelId", "beginYear", "endYear", "(ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getBeginYear", "()Ljava/lang/String;", "setBeginYear", "(Ljava/lang/String;)V", "getCarModelId", "()I", "setCarModelId", "(I)V", "getEndYear", "setEndYear", "getId", "setId", "getName", "setName", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "getYearsInfo", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarGeneration {

    @SerializedName("year_begin")
    private String beginYear;

    @SerializedName("id_car_model")
    private int carModelId;

    @SerializedName("year_end")
    private String endYear;

    @SerializedName("id_car_generation")
    private int id;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    public CarGeneration() {
        this(0, null, 0, null, null, 31, null);
    }

    public static /* synthetic */ CarGeneration copy$default(CarGeneration carGeneration, int i, String str, int i2, String str2, String str3, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = carGeneration.id;
        }
        if ((i3 & 2) != 0) {
            str = carGeneration.name;
        }
        String str4 = str;
        if ((i3 & 4) != 0) {
            i2 = carGeneration.carModelId;
        }
        int i4 = i2;
        if ((i3 & 8) != 0) {
            str2 = carGeneration.beginYear;
        }
        String str5 = str2;
        if ((i3 & 16) != 0) {
            str3 = carGeneration.endYear;
        }
        return carGeneration.copy(i, str4, i4, str5, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final int getCarModelId() {
        return this.carModelId;
    }

    /* renamed from: component4, reason: from getter */
    public final String getBeginYear() {
        return this.beginYear;
    }

    /* renamed from: component5, reason: from getter */
    public final String getEndYear() {
        return this.endYear;
    }

    public final CarGeneration copy(int id, String name, int carModelId, String beginYear, String endYear) {
        return new CarGeneration(id, name, carModelId, beginYear, endYear);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CarGeneration)) {
            return false;
        }
        CarGeneration carGeneration = (CarGeneration) other;
        return this.id == carGeneration.id && Intrinsics.areEqual(this.name, carGeneration.name) && this.carModelId == carGeneration.carModelId && Intrinsics.areEqual(this.beginYear, carGeneration.beginYear) && Intrinsics.areEqual(this.endYear, carGeneration.endYear);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.name;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.carModelId)) * 31;
        String str2 = this.beginYear;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.endYear;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "CarGeneration(id=" + this.id + ", name=" + this.name + ", carModelId=" + this.carModelId + ", beginYear=" + this.beginYear + ", endYear=" + this.endYear + ")";
    }

    public CarGeneration(int i, String str, int i2, String str2, String str3) {
        this.id = i;
        this.name = str;
        this.carModelId = i2;
        this.beginYear = str2;
        this.endYear = str3;
    }

    public /* synthetic */ CarGeneration(int i, String str, int i2, String str2, String str3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? null : str, (i3 & 4) == 0 ? i2 : 0, (i3 & 8) != 0 ? null : str2, (i3 & 16) != 0 ? null : str3);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final int getCarModelId() {
        return this.carModelId;
    }

    public final void setCarModelId(int i) {
        this.carModelId = i;
    }

    public final String getBeginYear() {
        return this.beginYear;
    }

    public final void setBeginYear(String str) {
        this.beginYear = str;
    }

    public final String getEndYear() {
        return this.endYear;
    }

    public final void setEndYear(String str) {
        this.endYear = str;
    }

    public final String getYearsInfo() {
        return "(" + this.beginYear + " - " + this.endYear + ")";
    }
}
