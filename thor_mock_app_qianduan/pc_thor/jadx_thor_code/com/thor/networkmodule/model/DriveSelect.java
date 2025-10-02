package com.thor.networkmodule.model;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DriveSelect.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J)\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001b"}, d2 = {"Lcom/thor/networkmodule/model/DriveSelect;", "", TtmlNode.ATTR_ID, "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "rank", "(ILjava/lang/String;I)V", "getId", "()I", "setId", "(I)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getRank", "setRank", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DriveSelect {

    @SerializedName(TtmlNode.ATTR_ID)
    private int id;

    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @SerializedName("rank")
    private int rank;

    public DriveSelect() {
        this(0, null, 0, 7, null);
    }

    public static /* synthetic */ DriveSelect copy$default(DriveSelect driveSelect, int i, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = driveSelect.id;
        }
        if ((i3 & 2) != 0) {
            str = driveSelect.name;
        }
        if ((i3 & 4) != 0) {
            i2 = driveSelect.rank;
        }
        return driveSelect.copy(i, str, i2);
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
    public final int getRank() {
        return this.rank;
    }

    public final DriveSelect copy(int id, String name, int rank) {
        return new DriveSelect(id, name, rank);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DriveSelect)) {
            return false;
        }
        DriveSelect driveSelect = (DriveSelect) other;
        return this.id == driveSelect.id && Intrinsics.areEqual(this.name, driveSelect.name) && this.rank == driveSelect.rank;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.name;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.rank);
    }

    public String toString() {
        return "DriveSelect(id=" + this.id + ", name=" + this.name + ", rank=" + this.rank + ")";
    }

    public DriveSelect(int i, String str, int i2) {
        this.id = i;
        this.name = str;
        this.rank = i2;
    }

    public /* synthetic */ DriveSelect(int i, String str, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? null : str, (i3 & 4) != 0 ? 0 : i2);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final int getRank() {
        return this.rank;
    }

    public final void setRank(int i) {
        this.rank = i;
    }
}
