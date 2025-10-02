package com.thor.businessmodule.bluetooth.model.other.settings;

import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: CanFile.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006¢\u0006\u0002\u0010\tJ\t\u0010\u000e\u001a\u00020\u0006HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/CanFile;", "", "data", "", "([B)V", "formatVersion", "", "fileId", "fileVersion", "(III)V", "getFileId", "()I", "getFileVersion", "getFormatVersion", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CanFile {
    private final int fileId;
    private final int fileVersion;
    private final int formatVersion;

    public static /* synthetic */ CanFile copy$default(CanFile canFile, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = canFile.formatVersion;
        }
        if ((i4 & 2) != 0) {
            i2 = canFile.fileId;
        }
        if ((i4 & 4) != 0) {
            i3 = canFile.fileVersion;
        }
        return canFile.copy(i, i2, i3);
    }

    /* renamed from: component1, reason: from getter */
    public final int getFormatVersion() {
        return this.formatVersion;
    }

    /* renamed from: component2, reason: from getter */
    public final int getFileId() {
        return this.fileId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getFileVersion() {
        return this.fileVersion;
    }

    public final CanFile copy(int formatVersion, int fileId, int fileVersion) {
        return new CanFile(formatVersion, fileId, fileVersion);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CanFile)) {
            return false;
        }
        CanFile canFile = (CanFile) other;
        return this.formatVersion == canFile.formatVersion && this.fileId == canFile.fileId && this.fileVersion == canFile.fileVersion;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.formatVersion) * 31) + Integer.hashCode(this.fileId)) * 31) + Integer.hashCode(this.fileVersion);
    }

    public String toString() {
        return "CanFile(formatVersion=" + this.formatVersion + ", fileId=" + this.fileId + ", fileVersion=" + this.fileVersion + ")";
    }

    public CanFile(int i, int i2, int i3) {
        this.formatVersion = i;
        this.fileId = i2;
        this.fileVersion = i3;
    }

    public final int getFormatVersion() {
        return this.formatVersion;
    }

    public final int getFileId() {
        return this.fileId;
    }

    public final int getFileVersion() {
        return this.fileVersion;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CanFile(byte[] data) {
        this(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(0, 2))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(2, 4))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(4, 6))));
        Intrinsics.checkNotNullParameter(data, "data");
    }
}
