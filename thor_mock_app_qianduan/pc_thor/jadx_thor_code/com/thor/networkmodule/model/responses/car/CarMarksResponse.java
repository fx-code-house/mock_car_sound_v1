package com.thor.networkmodule.model.responses.car;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.car.CarMark;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarMarksResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\n\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R&\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0013"}, d2 = {"Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "marks", "", "Lcom/thor/networkmodule/model/car/CarMark;", "(Ljava/util/List;)V", "getMarks", "()Ljava/util/List;", "setMarks", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarMarksResponse extends BaseResponse {

    @SerializedName(FirebaseAnalytics.Param.ITEMS)
    private List<CarMark> marks;

    /* JADX WARN: Multi-variable type inference failed */
    public CarMarksResponse() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CarMarksResponse copy$default(CarMarksResponse carMarksResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = carMarksResponse.marks;
        }
        return carMarksResponse.copy(list);
    }

    public final List<CarMark> component1() {
        return this.marks;
    }

    public final CarMarksResponse copy(List<CarMark> marks) {
        return new CarMarksResponse(marks);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CarMarksResponse) && Intrinsics.areEqual(this.marks, ((CarMarksResponse) other).marks);
    }

    public int hashCode() {
        List<CarMark> list = this.marks;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "CarMarksResponse(marks=" + this.marks + ")";
    }

    public /* synthetic */ CarMarksResponse(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public final List<CarMark> getMarks() {
        return this.marks;
    }

    public final void setMarks(List<CarMark> list) {
        this.marks = list;
    }

    public CarMarksResponse(List<CarMark> list) {
        this.marks = list;
    }
}
