package com.thor.networkmodule.model.responses.car;

import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.responses.BaseResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: CarFuelTypeResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0011"}, d2 = {"Lcom/thor/networkmodule/model/responses/car/CarFuelTypeResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "fuelId", "", "(I)V", "getFuelId", "()I", "setFuelId", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarFuelTypeResponse extends BaseResponse {

    @SerializedName("fuelId")
    private int fuelId;

    public CarFuelTypeResponse() {
        this(0, 1, null);
    }

    public static /* synthetic */ CarFuelTypeResponse copy$default(CarFuelTypeResponse carFuelTypeResponse, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = carFuelTypeResponse.fuelId;
        }
        return carFuelTypeResponse.copy(i);
    }

    /* renamed from: component1, reason: from getter */
    public final int getFuelId() {
        return this.fuelId;
    }

    public final CarFuelTypeResponse copy(int fuelId) {
        return new CarFuelTypeResponse(fuelId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CarFuelTypeResponse) && this.fuelId == ((CarFuelTypeResponse) other).fuelId;
    }

    public int hashCode() {
        return Integer.hashCode(this.fuelId);
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "CarFuelTypeResponse(fuelId=" + this.fuelId + ")";
    }

    public /* synthetic */ CarFuelTypeResponse(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }

    public final int getFuelId() {
        return this.fuelId;
    }

    public final void setFuelId(int i) {
        this.fuelId = i;
    }

    public CarFuelTypeResponse(int i) {
        this.fuelId = i;
    }
}
