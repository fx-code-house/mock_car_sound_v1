package com.thor.businessmodule.bluetooth.model.other;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DownloadGetStatusModel.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\rJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\rJ\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0006HÆ\u0003J2\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001¢\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f¨\u0006\u001f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;", "", "packageId", "", "versionId", "idFileType", "Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "(Ljava/lang/Short;Ljava/lang/Short;Lcom/thor/businessmodule/bluetooth/model/other/FileType;)V", "getIdFileType", "()Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "setIdFileType", "(Lcom/thor/businessmodule/bluetooth/model/other/FileType;)V", "getPackageId", "()Ljava/lang/Short;", "setPackageId", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "getVersionId", "setVersionId", "component1", "component2", "component3", "copy", "(Ljava/lang/Short;Ljava/lang/Short;Lcom/thor/businessmodule/bluetooth/model/other/FileType;)Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class IdFileModel {
    private FileType idFileType;
    private Short packageId;
    private Short versionId;

    public IdFileModel() {
        this(null, null, null, 7, null);
    }

    public static /* synthetic */ IdFileModel copy$default(IdFileModel idFileModel, Short sh, Short sh2, FileType fileType, int i, Object obj) {
        if ((i & 1) != 0) {
            sh = idFileModel.packageId;
        }
        if ((i & 2) != 0) {
            sh2 = idFileModel.versionId;
        }
        if ((i & 4) != 0) {
            fileType = idFileModel.idFileType;
        }
        return idFileModel.copy(sh, sh2, fileType);
    }

    /* renamed from: component1, reason: from getter */
    public final Short getPackageId() {
        return this.packageId;
    }

    /* renamed from: component2, reason: from getter */
    public final Short getVersionId() {
        return this.versionId;
    }

    /* renamed from: component3, reason: from getter */
    public final FileType getIdFileType() {
        return this.idFileType;
    }

    public final IdFileModel copy(Short packageId, Short versionId, FileType idFileType) {
        return new IdFileModel(packageId, versionId, idFileType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IdFileModel)) {
            return false;
        }
        IdFileModel idFileModel = (IdFileModel) other;
        return Intrinsics.areEqual(this.packageId, idFileModel.packageId) && Intrinsics.areEqual(this.versionId, idFileModel.versionId) && this.idFileType == idFileModel.idFileType;
    }

    public int hashCode() {
        Short sh = this.packageId;
        int iHashCode = (sh == null ? 0 : sh.hashCode()) * 31;
        Short sh2 = this.versionId;
        int iHashCode2 = (iHashCode + (sh2 == null ? 0 : sh2.hashCode())) * 31;
        FileType fileType = this.idFileType;
        return iHashCode2 + (fileType != null ? fileType.hashCode() : 0);
    }

    public String toString() {
        return "IdFileModel(packageId=" + this.packageId + ", versionId=" + this.versionId + ", idFileType=" + this.idFileType + ")";
    }

    public IdFileModel(Short sh, Short sh2, FileType fileType) {
        this.packageId = sh;
        this.versionId = sh2;
        this.idFileType = fileType;
    }

    public /* synthetic */ IdFileModel(Short sh, Short sh2, FileType fileType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : sh, (i & 2) != 0 ? null : sh2, (i & 4) != 0 ? null : fileType);
    }

    public final Short getPackageId() {
        return this.packageId;
    }

    public final void setPackageId(Short sh) {
        this.packageId = sh;
    }

    public final Short getVersionId() {
        return this.versionId;
    }

    public final void setVersionId(Short sh) {
        this.versionId = sh;
    }

    public final FileType getIdFileType() {
        return this.idFileType;
    }

    public final void setIdFileType(FileType fileType) {
        this.idFileType = fileType;
    }
}
