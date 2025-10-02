package com.thor.networkmodule.model.responses;

import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.CanFile;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CanConfigurationsResponse.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\t\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/responses/CanConfigurationsResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "canFile", "Lcom/thor/networkmodule/model/CanFile;", "(Lcom/thor/networkmodule/model/CanFile;)V", "getCanFile", "()Lcom/thor/networkmodule/model/CanFile;", "setCanFile", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CanConfigurationsResponse extends BaseResponse {

    @SerializedName("can")
    private CanFile canFile;

    /* JADX WARN: Multi-variable type inference failed */
    public CanConfigurationsResponse() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ CanConfigurationsResponse copy$default(CanConfigurationsResponse canConfigurationsResponse, CanFile canFile, int i, Object obj) {
        if ((i & 1) != 0) {
            canFile = canConfigurationsResponse.canFile;
        }
        return canConfigurationsResponse.copy(canFile);
    }

    /* renamed from: component1, reason: from getter */
    public final CanFile getCanFile() {
        return this.canFile;
    }

    public final CanConfigurationsResponse copy(CanFile canFile) {
        return new CanConfigurationsResponse(canFile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CanConfigurationsResponse) && Intrinsics.areEqual(this.canFile, ((CanConfigurationsResponse) other).canFile);
    }

    public int hashCode() {
        CanFile canFile = this.canFile;
        if (canFile == null) {
            return 0;
        }
        return canFile.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "CanConfigurationsResponse(canFile=" + this.canFile + ")";
    }

    public /* synthetic */ CanConfigurationsResponse(CanFile canFile, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : canFile);
    }

    public final CanFile getCanFile() {
        return this.canFile;
    }

    public final void setCanFile(CanFile canFile) {
        this.canFile = canFile;
    }

    public CanConfigurationsResponse(CanFile canFile) {
        this.canFile = canFile;
    }
}
