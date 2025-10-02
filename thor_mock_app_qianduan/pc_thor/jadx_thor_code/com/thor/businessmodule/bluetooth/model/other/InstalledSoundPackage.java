package com.thor.businessmodule.bluetooth.model.other;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;

/* compiled from: InstalledSoundPackage.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "", "packageId", "", "versionId", "mode", "(SSS)V", "getMode", "()S", "getPackageId", "getVersionId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class InstalledSoundPackage {

    @SerializedName("mode")
    private final short mode;

    @SerializedName("package_id")
    private final short packageId;

    @SerializedName("version_id")
    private final short versionId;

    public static /* synthetic */ InstalledSoundPackage copy$default(InstalledSoundPackage installedSoundPackage, short s, short s2, short s3, int i, Object obj) {
        if ((i & 1) != 0) {
            s = installedSoundPackage.packageId;
        }
        if ((i & 2) != 0) {
            s2 = installedSoundPackage.versionId;
        }
        if ((i & 4) != 0) {
            s3 = installedSoundPackage.mode;
        }
        return installedSoundPackage.copy(s, s2, s3);
    }

    /* renamed from: component1, reason: from getter */
    public final short getPackageId() {
        return this.packageId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getVersionId() {
        return this.versionId;
    }

    /* renamed from: component3, reason: from getter */
    public final short getMode() {
        return this.mode;
    }

    public final InstalledSoundPackage copy(short packageId, short versionId, short mode) {
        return new InstalledSoundPackage(packageId, versionId, mode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InstalledSoundPackage)) {
            return false;
        }
        InstalledSoundPackage installedSoundPackage = (InstalledSoundPackage) other;
        return this.packageId == installedSoundPackage.packageId && this.versionId == installedSoundPackage.versionId && this.mode == installedSoundPackage.mode;
    }

    public int hashCode() {
        return (((Short.hashCode(this.packageId) * 31) + Short.hashCode(this.versionId)) * 31) + Short.hashCode(this.mode);
    }

    public String toString() {
        return "InstalledSoundPackage(packageId=" + ((int) this.packageId) + ", versionId=" + ((int) this.versionId) + ", mode=" + ((int) this.mode) + ")";
    }

    public InstalledSoundPackage(short s, short s2, short s3) {
        this.packageId = s;
        this.versionId = s2;
        this.mode = s3;
    }

    public final short getPackageId() {
        return this.packageId;
    }

    public final short getVersionId() {
        return this.versionId;
    }

    public final short getMode() {
        return this.mode;
    }
}
