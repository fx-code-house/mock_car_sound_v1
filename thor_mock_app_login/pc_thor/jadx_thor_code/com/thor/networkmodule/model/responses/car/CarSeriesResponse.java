package com.thor.networkmodule.model.responses.car;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.car.CarSeries;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarSeriesResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\n\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R&\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0013"}, d2 = {"Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "series", "", "Lcom/thor/networkmodule/model/car/CarSeries;", "(Ljava/util/List;)V", "getSeries", "()Ljava/util/List;", "setSeries", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarSeriesResponse extends BaseResponse {

    @SerializedName(FirebaseAnalytics.Param.ITEMS)
    private List<CarSeries> series;

    /* JADX WARN: Multi-variable type inference failed */
    public CarSeriesResponse() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CarSeriesResponse copy$default(CarSeriesResponse carSeriesResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = carSeriesResponse.series;
        }
        return carSeriesResponse.copy(list);
    }

    public final List<CarSeries> component1() {
        return this.series;
    }

    public final CarSeriesResponse copy(List<CarSeries> series) {
        return new CarSeriesResponse(series);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CarSeriesResponse) && Intrinsics.areEqual(this.series, ((CarSeriesResponse) other).series);
    }

    public int hashCode() {
        List<CarSeries> list = this.series;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "CarSeriesResponse(series=" + this.series + ")";
    }

    public /* synthetic */ CarSeriesResponse(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public final List<CarSeries> getSeries() {
        return this.series;
    }

    public final void setSeries(List<CarSeries> list) {
        this.series = list;
    }

    public CarSeriesResponse(List<CarSeries> list) {
        this.series = list;
    }
}
