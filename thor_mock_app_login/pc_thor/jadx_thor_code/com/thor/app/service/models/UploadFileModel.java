package com.thor.app.service.models;

import com.thor.businessmodule.bluetooth.model.other.FileType;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadFileModel.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u0013"}, d2 = {"Lcom/thor/app/service/models/UploadFileModel;", "", "fileType", "Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "file", "", "indexFile", "", "versionId", "packageId", "(Lcom/thor/businessmodule/bluetooth/model/other/FileType;[BIII)V", "getFile", "()[B", "getFileType", "()Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "getIndexFile", "()I", "getPackageId", "getVersionId", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadFileModel {
    private final byte[] file;
    private final FileType fileType;
    private final int indexFile;
    private final int packageId;
    private final int versionId;

    public UploadFileModel(FileType fileType, byte[] file, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        Intrinsics.checkNotNullParameter(file, "file");
        this.fileType = fileType;
        this.file = file;
        this.indexFile = i;
        this.versionId = i2;
        this.packageId = i3;
    }

    public final FileType getFileType() {
        return this.fileType;
    }

    public final byte[] getFile() {
        return this.file;
    }

    public final int getIndexFile() {
        return this.indexFile;
    }

    public final int getVersionId() {
        return this.versionId;
    }

    public final int getPackageId() {
        return this.packageId;
    }
}
