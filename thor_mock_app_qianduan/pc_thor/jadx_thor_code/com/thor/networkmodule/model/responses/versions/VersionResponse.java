package com.thor.networkmodule.model.responses.versions;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VersionResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001R \u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/VersionResponse;", "", NotificationCompat.CATEGORY_STATUS, "", "versions", "Lcom/thor/networkmodule/model/responses/versions/Versions;", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "(ZLcom/thor/networkmodule/model/responses/versions/Versions;Ljava/lang/String;)V", "getError", "()Ljava/lang/String;", "setError", "(Ljava/lang/String;)V", "getStatus", "()Z", "getVersions", "()Lcom/thor/networkmodule/model/responses/versions/Versions;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class VersionResponse {

    @SerializedName("error_description")
    private String error;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private final boolean status;

    @SerializedName("versions")
    private final Versions versions;

    public static /* synthetic */ VersionResponse copy$default(VersionResponse versionResponse, boolean z, Versions versions, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = versionResponse.status;
        }
        if ((i & 2) != 0) {
            versions = versionResponse.versions;
        }
        if ((i & 4) != 0) {
            str = versionResponse.error;
        }
        return versionResponse.copy(z, versions, str);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getStatus() {
        return this.status;
    }

    /* renamed from: component2, reason: from getter */
    public final Versions getVersions() {
        return this.versions;
    }

    /* renamed from: component3, reason: from getter */
    public final String getError() {
        return this.error;
    }

    public final VersionResponse copy(boolean status, Versions versions, String error) {
        Intrinsics.checkNotNullParameter(versions, "versions");
        return new VersionResponse(status, versions, error);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VersionResponse)) {
            return false;
        }
        VersionResponse versionResponse = (VersionResponse) other;
        return this.status == versionResponse.status && Intrinsics.areEqual(this.versions, versionResponse.versions) && Intrinsics.areEqual(this.error, versionResponse.error);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    public int hashCode() {
        boolean z = this.status;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int iHashCode = ((r0 * 31) + this.versions.hashCode()) * 31;
        String str = this.error;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "VersionResponse(status=" + this.status + ", versions=" + this.versions + ", error=" + this.error + ")";
    }

    public VersionResponse(boolean z, Versions versions, String str) {
        Intrinsics.checkNotNullParameter(versions, "versions");
        this.status = z;
        this.versions = versions;
        this.error = str;
    }

    public /* synthetic */ VersionResponse(boolean z, Versions versions, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, versions, (i & 4) != 0 ? null : str);
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final Versions getVersions() {
        return this.versions;
    }

    public final String getError() {
        return this.error;
    }

    public final void setError(String str) {
        this.error = str;
    }
}
