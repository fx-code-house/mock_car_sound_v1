package com.thor.networkmodule.model.car;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarModel.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0006HÆ\u0003J)\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0006HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001b"}, d2 = {"Lcom/thor/networkmodule/model/car/CarModel;", "", TtmlNode.ATTR_ID, "", "markId", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "(IILjava/lang/String;)V", "getId", "()I", "setId", "(I)V", "getMarkId", "setMarkId", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarModel {

    @SerializedName("id_car_model")
    private int id;

    @SerializedName("id_car_mark")
    private int markId;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    public CarModel() {
        this(0, 0, null, 7, null);
    }

    public static /* synthetic */ CarModel copy$default(CarModel carModel, int i, int i2, String str, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = carModel.id;
        }
        if ((i3 & 2) != 0) {
            i2 = carModel.markId;
        }
        if ((i3 & 4) != 0) {
            str = carModel.name;
        }
        return carModel.copy(i, i2, str);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final int getMarkId() {
        return this.markId;
    }

    /* renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final CarModel copy(int id, int markId, String name) {
        return new CarModel(id, markId, name);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CarModel)) {
            return false;
        }
        CarModel carModel = (CarModel) other;
        return this.id == carModel.id && this.markId == carModel.markId && Intrinsics.areEqual(this.name, carModel.name);
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.id) * 31) + Integer.hashCode(this.markId)) * 31;
        String str = this.name;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "CarModel(id=" + this.id + ", markId=" + this.markId + ", name=" + this.name + ")";
    }

    public CarModel(int i, int i2, String str) {
        this.id = i;
        this.markId = i2;
        this.name = str;
    }

    public /* synthetic */ CarModel(int i, int i2, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? null : str);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final int getMarkId() {
        return this.markId;
    }

    public final void setMarkId(int i) {
        this.markId = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }
}
