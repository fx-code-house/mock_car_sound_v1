package com.thor.app.room.entity;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CurrentVersionsEntity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J+\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/thor/app/room/entity/CurrentVersionsEntity;", "", TtmlNode.ATTR_ID, "", "versionOfFirmware", "", "versionOfHardware", "(ILjava/lang/String;Ljava/lang/String;)V", "getId", "()I", "getVersionOfFirmware", "()Ljava/lang/String;", "getVersionOfHardware", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CurrentVersionsEntity {
    private final int id;
    private final String versionOfFirmware;
    private final String versionOfHardware;

    public static /* synthetic */ CurrentVersionsEntity copy$default(CurrentVersionsEntity currentVersionsEntity, int i, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = currentVersionsEntity.id;
        }
        if ((i2 & 2) != 0) {
            str = currentVersionsEntity.versionOfFirmware;
        }
        if ((i2 & 4) != 0) {
            str2 = currentVersionsEntity.versionOfHardware;
        }
        return currentVersionsEntity.copy(i, str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getVersionOfFirmware() {
        return this.versionOfFirmware;
    }

    /* renamed from: component3, reason: from getter */
    public final String getVersionOfHardware() {
        return this.versionOfHardware;
    }

    public final CurrentVersionsEntity copy(int id, String versionOfFirmware, String versionOfHardware) {
        return new CurrentVersionsEntity(id, versionOfFirmware, versionOfHardware);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CurrentVersionsEntity)) {
            return false;
        }
        CurrentVersionsEntity currentVersionsEntity = (CurrentVersionsEntity) other;
        return this.id == currentVersionsEntity.id && Intrinsics.areEqual(this.versionOfFirmware, currentVersionsEntity.versionOfFirmware) && Intrinsics.areEqual(this.versionOfHardware, currentVersionsEntity.versionOfHardware);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.id) * 31;
        String str = this.versionOfFirmware;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.versionOfHardware;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "CurrentVersionsEntity(id=" + this.id + ", versionOfFirmware=" + this.versionOfFirmware + ", versionOfHardware=" + this.versionOfHardware + ")";
    }

    public CurrentVersionsEntity(int i, String str, String str2) {
        this.id = i;
        this.versionOfFirmware = str;
        this.versionOfHardware = str2;
    }

    public /* synthetic */ CurrentVersionsEntity(int i, String str, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, str, str2);
    }

    public final int getId() {
        return this.id;
    }

    public final String getVersionOfFirmware() {
        return this.versionOfFirmware;
    }

    public final String getVersionOfHardware() {
        return this.versionOfHardware;
    }
}
