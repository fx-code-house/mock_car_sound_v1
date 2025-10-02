package com.thor.networkmodule.model.responses.sgu;

import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundSetsResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\t\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u001e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "soundSetsItems", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "(Ljava/util/List;)V", "getSoundSetsItems", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundSetsResponse extends BaseResponse {

    @SerializedName("categories")
    private final List<SguSoundSet> soundSetsItems;

    /* JADX WARN: Multi-variable type inference failed */
    public SguSoundSetsResponse() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SguSoundSetsResponse copy$default(SguSoundSetsResponse sguSoundSetsResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = sguSoundSetsResponse.soundSetsItems;
        }
        return sguSoundSetsResponse.copy(list);
    }

    public final List<SguSoundSet> component1() {
        return this.soundSetsItems;
    }

    public final SguSoundSetsResponse copy(List<SguSoundSet> soundSetsItems) {
        return new SguSoundSetsResponse(soundSetsItems);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SguSoundSetsResponse) && Intrinsics.areEqual(this.soundSetsItems, ((SguSoundSetsResponse) other).soundSetsItems);
    }

    public int hashCode() {
        List<SguSoundSet> list = this.soundSetsItems;
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "SguSoundSetsResponse(soundSetsItems=" + this.soundSetsItems + ")";
    }

    public /* synthetic */ SguSoundSetsResponse(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public final List<SguSoundSet> getSoundSetsItems() {
        return this.soundSetsItems;
    }

    public SguSoundSetsResponse(List<SguSoundSet> list) {
        this.soundSetsItems = list;
    }
}
