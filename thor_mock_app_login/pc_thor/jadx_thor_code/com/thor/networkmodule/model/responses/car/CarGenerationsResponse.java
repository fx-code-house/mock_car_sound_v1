package com.thor.networkmodule.model.responses.car;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.car.CarGeneration;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarGenerationsResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\n\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R&\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0013"}, d2 = {"Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "generations", "", "Lcom/thor/networkmodule/model/car/CarGeneration;", "(Ljava/util/List;)V", "getGenerations", "()Ljava/util/List;", "setGenerations", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarGenerationsResponse extends BaseResponse {

    @SerializedName(FirebaseAnalytics.Param.ITEMS)
    private List<CarGeneration> generations;

    /* JADX WARN: Multi-variable type inference failed */
    public CarGenerationsResponse() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CarGenerationsResponse copy$default(CarGenerationsResponse carGenerationsResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = carGenerationsResponse.generations;
        }
        return carGenerationsResponse.copy(list);
    }

    public final List<CarGeneration> component1() {
        return this.generations;
    }

    public final CarGenerationsResponse copy(List<CarGeneration> generations) {
        return new CarGenerationsResponse(generations);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CarGenerationsResponse) && Intrinsics.areEqual(this.generations, ((CarGenerationsResponse) other).generations);
    }

    public int hashCode() {
        List<CarGeneration> list = this.generations;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "CarGenerationsResponse(generations=" + this.generations + ")";
    }

    public /* synthetic */ CarGenerationsResponse(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public final List<CarGeneration> getGenerations() {
        return this.generations;
    }

    public final void setGenerations(List<CarGeneration> list) {
        this.generations = list;
    }

    public CarGenerationsResponse(List<CarGeneration> list) {
        this.generations = list;
    }
}
