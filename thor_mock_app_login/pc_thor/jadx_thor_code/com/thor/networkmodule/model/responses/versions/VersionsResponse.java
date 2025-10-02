package com.thor.networkmodule.model.responses.versions;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VersionsResponse.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/VersionsResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "versions", "", "Lcom/thor/networkmodule/model/responses/versions/VersionsResponse$Version;", "(Ljava/util/List;)V", "getVersions", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Version", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class VersionsResponse extends BaseResponse {

    @SerializedName("versions")
    private final List<Version> versions;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ VersionsResponse copy$default(VersionsResponse versionsResponse, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = versionsResponse.versions;
        }
        return versionsResponse.copy(list);
    }

    public final List<Version> component1() {
        return this.versions;
    }

    public final VersionsResponse copy(List<Version> versions) {
        Intrinsics.checkNotNullParameter(versions, "versions");
        return new VersionsResponse(versions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof VersionsResponse) && Intrinsics.areEqual(this.versions, ((VersionsResponse) other).versions);
    }

    public int hashCode() {
        return this.versions.hashCode();
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "VersionsResponse(versions=" + this.versions + ")";
    }

    public final List<Version> getVersions() {
        return this.versions;
    }

    /* compiled from: VersionsResponse.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/VersionsResponse$Version;", "", "versionAndroid", "", "versionSoftware", NotificationCompat.CATEGORY_STATUS, "", "(Ljava/lang/String;Ljava/lang/String;Z)V", "getStatus", "()Z", "getVersionAndroid", "()Ljava/lang/String;", "getVersionSoftware", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Version {

        @SerializedName(NotificationCompat.CATEGORY_STATUS)
        private final boolean status;

        @SerializedName("version_android")
        private final String versionAndroid;

        @SerializedName("version_software")
        private final String versionSoftware;

        public static /* synthetic */ Version copy$default(Version version, String str, String str2, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = version.versionAndroid;
            }
            if ((i & 2) != 0) {
                str2 = version.versionSoftware;
            }
            if ((i & 4) != 0) {
                z = version.status;
            }
            return version.copy(str, str2, z);
        }

        /* renamed from: component1, reason: from getter */
        public final String getVersionAndroid() {
            return this.versionAndroid;
        }

        /* renamed from: component2, reason: from getter */
        public final String getVersionSoftware() {
            return this.versionSoftware;
        }

        /* renamed from: component3, reason: from getter */
        public final boolean getStatus() {
            return this.status;
        }

        public final Version copy(String versionAndroid, String versionSoftware, boolean status) {
            Intrinsics.checkNotNullParameter(versionAndroid, "versionAndroid");
            Intrinsics.checkNotNullParameter(versionSoftware, "versionSoftware");
            return new Version(versionAndroid, versionSoftware, status);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Version)) {
                return false;
            }
            Version version = (Version) other;
            return Intrinsics.areEqual(this.versionAndroid, version.versionAndroid) && Intrinsics.areEqual(this.versionSoftware, version.versionSoftware) && this.status == version.status;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            int iHashCode = ((this.versionAndroid.hashCode() * 31) + this.versionSoftware.hashCode()) * 31;
            boolean z = this.status;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return iHashCode + i;
        }

        public String toString() {
            return "Version(versionAndroid=" + this.versionAndroid + ", versionSoftware=" + this.versionSoftware + ", status=" + this.status + ")";
        }

        public Version(String versionAndroid, String versionSoftware, boolean z) {
            Intrinsics.checkNotNullParameter(versionAndroid, "versionAndroid");
            Intrinsics.checkNotNullParameter(versionSoftware, "versionSoftware");
            this.versionAndroid = versionAndroid;
            this.versionSoftware = versionSoftware;
            this.status = z;
        }

        public final String getVersionAndroid() {
            return this.versionAndroid;
        }

        public final String getVersionSoftware() {
            return this.versionSoftware;
        }

        public final boolean getStatus() {
            return this.status;
        }
    }

    public VersionsResponse(List<Version> versions) {
        Intrinsics.checkNotNullParameter(versions, "versions");
        this.versions = versions;
    }
}
