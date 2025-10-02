package com.thor.networkmodule.model.responses.sgu;

import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.responses.BaseResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundSetDetailsResponse.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetailsResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "soundSetDetails", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetails;", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetails;)V", "getSoundSetDetails", "()Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetails;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundSetDetailsResponse extends BaseResponse {

    @SerializedName("sound_set")
    private final SguSoundSetDetails soundSetDetails;

    public static /* synthetic */ SguSoundSetDetailsResponse copy$default(SguSoundSetDetailsResponse sguSoundSetDetailsResponse, SguSoundSetDetails sguSoundSetDetails, int i, Object obj) {
        if ((i & 1) != 0) {
            sguSoundSetDetails = sguSoundSetDetailsResponse.soundSetDetails;
        }
        return sguSoundSetDetailsResponse.copy(sguSoundSetDetails);
    }

    /* renamed from: component1, reason: from getter */
    public final SguSoundSetDetails getSoundSetDetails() {
        return this.soundSetDetails;
    }

    public final SguSoundSetDetailsResponse copy(SguSoundSetDetails soundSetDetails) {
        return new SguSoundSetDetailsResponse(soundSetDetails);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SguSoundSetDetailsResponse) && Intrinsics.areEqual(this.soundSetDetails, ((SguSoundSetDetailsResponse) other).soundSetDetails);
    }

    public int hashCode() {
        SguSoundSetDetails sguSoundSetDetails = this.soundSetDetails;
        if (sguSoundSetDetails == null) {
            return 0;
        }
        return sguSoundSetDetails.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "SguSoundSetDetailsResponse(soundSetDetails=" + this.soundSetDetails + ")";
    }

    public final SguSoundSetDetails getSoundSetDetails() {
        return this.soundSetDetails;
    }

    public SguSoundSetDetailsResponse(SguSoundSetDetails sguSoundSetDetails) {
        this.soundSetDetails = sguSoundSetDetails;
    }
}
