package com.thor.app.databinding.model;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DriveSelectStatus.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/thor/app/databinding/model/DriveSelectStatus;", "", TtmlNode.ATTR_ID, "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "modeStatus", "rank", "(ILjava/lang/String;Ljava/lang/String;I)V", "getId", "()I", "getModeStatus", "()Ljava/lang/String;", "getName", "getRank", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class DriveSelectStatus {
    private final int id;
    private final String modeStatus;
    private final String name;
    private final int rank;

    public static /* synthetic */ DriveSelectStatus copy$default(DriveSelectStatus driveSelectStatus, int i, String str, String str2, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = driveSelectStatus.id;
        }
        if ((i3 & 2) != 0) {
            str = driveSelectStatus.name;
        }
        if ((i3 & 4) != 0) {
            str2 = driveSelectStatus.modeStatus;
        }
        if ((i3 & 8) != 0) {
            i2 = driveSelectStatus.rank;
        }
        return driveSelectStatus.copy(i, str, str2, i2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String getModeStatus() {
        return this.modeStatus;
    }

    /* renamed from: component4, reason: from getter */
    public final int getRank() {
        return this.rank;
    }

    public final DriveSelectStatus copy(int id, String name, String modeStatus, int rank) {
        Intrinsics.checkNotNullParameter(modeStatus, "modeStatus");
        return new DriveSelectStatus(id, name, modeStatus, rank);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DriveSelectStatus)) {
            return false;
        }
        DriveSelectStatus driveSelectStatus = (DriveSelectStatus) other;
        return this.id == driveSelectStatus.id && Intrinsics.areEqual(this.name, driveSelectStatus.name) && Intrinsics.areEqual(this.modeStatus, driveSelectStatus.modeStatus) && this.rank == driveSelectStatus.rank;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.name;
        return ((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.modeStatus.hashCode()) * 31) + Integer.hashCode(this.rank);
    }

    public String toString() {
        return "DriveSelectStatus(id=" + this.id + ", name=" + this.name + ", modeStatus=" + this.modeStatus + ", rank=" + this.rank + ")";
    }

    public DriveSelectStatus(int i, String str, String modeStatus, int i2) {
        Intrinsics.checkNotNullParameter(modeStatus, "modeStatus");
        this.id = i;
        this.name = str;
        this.modeStatus = modeStatus;
        this.rank = i2;
    }

    public final int getId() {
        return this.id;
    }

    public final String getModeStatus() {
        return this.modeStatus;
    }

    public final String getName() {
        return this.name;
    }

    public final int getRank() {
        return this.rank;
    }
}
