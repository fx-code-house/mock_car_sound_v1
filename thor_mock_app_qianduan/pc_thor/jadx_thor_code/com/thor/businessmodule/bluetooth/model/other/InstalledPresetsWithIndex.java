package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InstalledPresetsWithIndex.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetsWithIndex;", "", "packageId", "", "mode", "activity", "(SSS)V", "getActivity", "()S", "getMode", "getPackageId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class InstalledPresetsWithIndex {

    @SerializedName("activity")
    private final short activity;

    @SerializedName("mode")
    private final short mode;

    @SerializedName("package_id")
    private final short packageId;

    public static /* synthetic */ InstalledPresetsWithIndex copy$default(InstalledPresetsWithIndex installedPresetsWithIndex, short s, short s2, short s3, int i, Object obj) {
        if ((i & 1) != 0) {
            s = installedPresetsWithIndex.packageId;
        }
        if ((i & 2) != 0) {
            s2 = installedPresetsWithIndex.mode;
        }
        if ((i & 4) != 0) {
            s3 = installedPresetsWithIndex.activity;
        }
        return installedPresetsWithIndex.copy(s, s2, s3);
    }

    /* renamed from: component1, reason: from getter */
    public final short getPackageId() {
        return this.packageId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getMode() {
        return this.mode;
    }

    /* renamed from: component3, reason: from getter */
    public final short getActivity() {
        return this.activity;
    }

    public final InstalledPresetsWithIndex copy(short packageId, short mode, short activity) {
        return new InstalledPresetsWithIndex(packageId, mode, activity);
    }

    public String toString() {
        return "InstalledPresetsWithIndex(packageId=" + ((int) this.packageId) + ", mode=" + ((int) this.mode) + ", activity=" + ((int) this.activity) + ")";
    }

    public InstalledPresetsWithIndex(short s, short s2, short s3) {
        this.packageId = s;
        this.mode = s2;
        this.activity = s3;
    }

    public /* synthetic */ InstalledPresetsWithIndex(short s, short s2, short s3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(s, s2, (i & 4) != 0 ? (short) 1 : s3);
    }

    public final short getPackageId() {
        return this.packageId;
    }

    public final short getMode() {
        return this.mode;
    }

    public final short getActivity() {
        return this.activity;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.thor.businessmodule.bluetooth.model.other.InstalledPreset");
        InstalledPreset installedPreset = (InstalledPreset) other;
        return this.packageId == installedPreset.getPackageId() && this.mode == installedPreset.getMode();
    }

    public int hashCode() {
        return (this.packageId * 31) + this.mode;
    }
}
