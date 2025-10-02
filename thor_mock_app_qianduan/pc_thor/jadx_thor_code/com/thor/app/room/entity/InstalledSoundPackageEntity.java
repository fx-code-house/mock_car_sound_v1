package com.thor.app.room.entity;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: InstalledSoundPackageEntity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u0019"}, d2 = {"Lcom/thor/app/room/entity/InstalledSoundPackageEntity;", "", "packageId", "", "versionId", "mode", "damagePck", "", "(IIIZ)V", "getDamagePck", "()Z", "getMode", "()I", "getPackageId", "getVersionId", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class InstalledSoundPackageEntity {
    private final boolean damagePck;
    private final int mode;
    private final int packageId;
    private final int versionId;

    public static /* synthetic */ InstalledSoundPackageEntity copy$default(InstalledSoundPackageEntity installedSoundPackageEntity, int i, int i2, int i3, boolean z, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = installedSoundPackageEntity.packageId;
        }
        if ((i4 & 2) != 0) {
            i2 = installedSoundPackageEntity.versionId;
        }
        if ((i4 & 4) != 0) {
            i3 = installedSoundPackageEntity.mode;
        }
        if ((i4 & 8) != 0) {
            z = installedSoundPackageEntity.damagePck;
        }
        return installedSoundPackageEntity.copy(i, i2, i3, z);
    }

    /* renamed from: component1, reason: from getter */
    public final int getPackageId() {
        return this.packageId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getVersionId() {
        return this.versionId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getMode() {
        return this.mode;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getDamagePck() {
        return this.damagePck;
    }

    public final InstalledSoundPackageEntity copy(int packageId, int versionId, int mode, boolean damagePck) {
        return new InstalledSoundPackageEntity(packageId, versionId, mode, damagePck);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InstalledSoundPackageEntity)) {
            return false;
        }
        InstalledSoundPackageEntity installedSoundPackageEntity = (InstalledSoundPackageEntity) other;
        return this.packageId == installedSoundPackageEntity.packageId && this.versionId == installedSoundPackageEntity.versionId && this.mode == installedSoundPackageEntity.mode && this.damagePck == installedSoundPackageEntity.damagePck;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((Integer.hashCode(this.packageId) * 31) + Integer.hashCode(this.versionId)) * 31) + Integer.hashCode(this.mode)) * 31;
        boolean z = this.damagePck;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode + i;
    }

    public String toString() {
        return "InstalledSoundPackageEntity(packageId=" + this.packageId + ", versionId=" + this.versionId + ", mode=" + this.mode + ", damagePck=" + this.damagePck + ")";
    }

    public InstalledSoundPackageEntity(int i, int i2, int i3, boolean z) {
        this.packageId = i;
        this.versionId = i2;
        this.mode = i3;
        this.damagePck = z;
    }

    public /* synthetic */ InstalledSoundPackageEntity(int i, int i2, int i3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3, (i4 & 8) != 0 ? false : z);
    }

    public final int getPackageId() {
        return this.packageId;
    }

    public final int getVersionId() {
        return this.versionId;
    }

    public final int getMode() {
        return this.mode;
    }

    public final boolean getDamagePck() {
        return this.damagePck;
    }
}
