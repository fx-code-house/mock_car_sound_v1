package com.thor.networkmodule.model.car;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarSeries.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J3\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0006HÖ\u0001R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lcom/thor/networkmodule/model/car/CarSeries;", "", TtmlNode.ATTR_ID, "", "modelId", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "generationId", "(IILjava/lang/String;I)V", "getGenerationId", "()I", "setGenerationId", "(I)V", "getId", "setId", "getModelId", "setModelId", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarSeries {

    @SerializedName("id_car_generation")
    private int generationId;

    @SerializedName("id_car_serie")
    private int id;

    @SerializedName("id_car_model")
    private int modelId;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    public CarSeries() {
        this(0, 0, null, 0, 15, null);
    }

    public static /* synthetic */ CarSeries copy$default(CarSeries carSeries, int i, int i2, String str, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = carSeries.id;
        }
        if ((i4 & 2) != 0) {
            i2 = carSeries.modelId;
        }
        if ((i4 & 4) != 0) {
            str = carSeries.name;
        }
        if ((i4 & 8) != 0) {
            i3 = carSeries.generationId;
        }
        return carSeries.copy(i, i2, str, i3);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final int getModelId() {
        return this.modelId;
    }

    /* renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component4, reason: from getter */
    public final int getGenerationId() {
        return this.generationId;
    }

    public final CarSeries copy(int id, int modelId, String name, int generationId) {
        return new CarSeries(id, modelId, name, generationId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CarSeries)) {
            return false;
        }
        CarSeries carSeries = (CarSeries) other;
        return this.id == carSeries.id && this.modelId == carSeries.modelId && Intrinsics.areEqual(this.name, carSeries.name) && this.generationId == carSeries.generationId;
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.id) * 31) + Integer.hashCode(this.modelId)) * 31;
        String str = this.name;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.generationId);
    }

    public String toString() {
        return "CarSeries(id=" + this.id + ", modelId=" + this.modelId + ", name=" + this.name + ", generationId=" + this.generationId + ")";
    }

    public CarSeries(int i, int i2, String str, int i3) {
        this.id = i;
        this.modelId = i2;
        this.name = str;
        this.generationId = i3;
    }

    public /* synthetic */ CarSeries(int i, int i2, String str, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? null : str, (i4 & 8) != 0 ? 0 : i3);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final int getModelId() {
        return this.modelId;
    }

    public final void setModelId(int i) {
        this.modelId = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final int getGenerationId() {
        return this.generationId;
    }

    public final void setGenerationId(int i) {
        this.generationId = i;
    }
}
