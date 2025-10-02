package com.thor.app.gui.fragments.presets.sgu;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundsUIState.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsDataLoaded;", "Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsUIState;", "data", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "(Ljava/util/List;)V", "getData", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundsDataLoaded extends SguSoundsUIState {
    private final List<SguSound> data;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SguSoundsDataLoaded copy$default(SguSoundsDataLoaded sguSoundsDataLoaded, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = sguSoundsDataLoaded.data;
        }
        return sguSoundsDataLoaded.copy(list);
    }

    public final List<SguSound> component1() {
        return this.data;
    }

    public final SguSoundsDataLoaded copy(List<SguSound> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return new SguSoundsDataLoaded(data);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SguSoundsDataLoaded) && Intrinsics.areEqual(this.data, ((SguSoundsDataLoaded) other).data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public String toString() {
        return "SguSoundsDataLoaded(data=" + this.data + ")";
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SguSoundsDataLoaded(List<SguSound> data) {
        super(null);
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    public final List<SguSound> getData() {
        return this.data;
    }
}
