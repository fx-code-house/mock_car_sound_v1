package com.thor.networkmodule.model.responses.versions;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CurrentAppVersion.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/CurrentAppVersion;", "", NotificationCompat.CATEGORY_STATUS, "", "versions", "Lcom/thor/networkmodule/model/responses/versions/VersionsApp;", "(ZLcom/thor/networkmodule/model/responses/versions/VersionsApp;)V", "getStatus", "()Z", "getVersions", "()Lcom/thor/networkmodule/model/responses/versions/VersionsApp;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CurrentAppVersion {
    private final boolean status;
    private final VersionsApp versions;

    public static /* synthetic */ CurrentAppVersion copy$default(CurrentAppVersion currentAppVersion, boolean z, VersionsApp versionsApp, int i, Object obj) {
        if ((i & 1) != 0) {
            z = currentAppVersion.status;
        }
        if ((i & 2) != 0) {
            versionsApp = currentAppVersion.versions;
        }
        return currentAppVersion.copy(z, versionsApp);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getStatus() {
        return this.status;
    }

    /* renamed from: component2, reason: from getter */
    public final VersionsApp getVersions() {
        return this.versions;
    }

    public final CurrentAppVersion copy(boolean status, VersionsApp versions) {
        Intrinsics.checkNotNullParameter(versions, "versions");
        return new CurrentAppVersion(status, versions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CurrentAppVersion)) {
            return false;
        }
        CurrentAppVersion currentAppVersion = (CurrentAppVersion) other;
        return this.status == currentAppVersion.status && Intrinsics.areEqual(this.versions, currentAppVersion.versions);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.status;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return (r0 * 31) + this.versions.hashCode();
    }

    public String toString() {
        return "CurrentAppVersion(status=" + this.status + ", versions=" + this.versions + ")";
    }

    public CurrentAppVersion(boolean z, VersionsApp versions) {
        Intrinsics.checkNotNullParameter(versions, "versions");
        this.status = z;
        this.versions = versions;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final VersionsApp getVersions() {
        return this.versions;
    }
}
