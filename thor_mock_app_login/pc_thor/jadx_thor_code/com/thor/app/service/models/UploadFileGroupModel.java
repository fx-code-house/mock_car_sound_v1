package com.thor.app.service.models;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadFileGroupModel.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u001aB#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\bHÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\bHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lcom/thor/app/service/models/UploadFileGroupModel;", "", "typeGroupFile", "Lcom/thor/app/service/models/UploadFileGroupModel$TypeGroupFile;", "uploadListFiles", "", "Lcom/thor/app/service/models/UploadFileModel;", "pckName", "", "(Lcom/thor/app/service/models/UploadFileGroupModel$TypeGroupFile;Ljava/util/List;Ljava/lang/String;)V", "getPckName", "()Ljava/lang/String;", "getTypeGroupFile", "()Lcom/thor/app/service/models/UploadFileGroupModel$TypeGroupFile;", "getUploadListFiles", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "TypeGroupFile", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class UploadFileGroupModel {
    private final String pckName;
    private final TypeGroupFile typeGroupFile;
    private final List<UploadFileModel> uploadListFiles;

    /* compiled from: UploadFileGroupModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/thor/app/service/models/UploadFileGroupModel$TypeGroupFile;", "", "(Ljava/lang/String;I)V", "SOUND", "SGU", "FIRMWARE", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum TypeGroupFile {
        SOUND,
        SGU,
        FIRMWARE
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UploadFileGroupModel copy$default(UploadFileGroupModel uploadFileGroupModel, TypeGroupFile typeGroupFile, List list, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            typeGroupFile = uploadFileGroupModel.typeGroupFile;
        }
        if ((i & 2) != 0) {
            list = uploadFileGroupModel.uploadListFiles;
        }
        if ((i & 4) != 0) {
            str = uploadFileGroupModel.pckName;
        }
        return uploadFileGroupModel.copy(typeGroupFile, list, str);
    }

    /* renamed from: component1, reason: from getter */
    public final TypeGroupFile getTypeGroupFile() {
        return this.typeGroupFile;
    }

    public final List<UploadFileModel> component2() {
        return this.uploadListFiles;
    }

    /* renamed from: component3, reason: from getter */
    public final String getPckName() {
        return this.pckName;
    }

    public final UploadFileGroupModel copy(TypeGroupFile typeGroupFile, List<UploadFileModel> uploadListFiles, String pckName) {
        Intrinsics.checkNotNullParameter(typeGroupFile, "typeGroupFile");
        Intrinsics.checkNotNullParameter(uploadListFiles, "uploadListFiles");
        Intrinsics.checkNotNullParameter(pckName, "pckName");
        return new UploadFileGroupModel(typeGroupFile, uploadListFiles, pckName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UploadFileGroupModel)) {
            return false;
        }
        UploadFileGroupModel uploadFileGroupModel = (UploadFileGroupModel) other;
        return this.typeGroupFile == uploadFileGroupModel.typeGroupFile && Intrinsics.areEqual(this.uploadListFiles, uploadFileGroupModel.uploadListFiles) && Intrinsics.areEqual(this.pckName, uploadFileGroupModel.pckName);
    }

    public int hashCode() {
        return (((this.typeGroupFile.hashCode() * 31) + this.uploadListFiles.hashCode()) * 31) + this.pckName.hashCode();
    }

    public String toString() {
        return "UploadFileGroupModel(typeGroupFile=" + this.typeGroupFile + ", uploadListFiles=" + this.uploadListFiles + ", pckName=" + this.pckName + ")";
    }

    public UploadFileGroupModel(TypeGroupFile typeGroupFile, List<UploadFileModel> uploadListFiles, String pckName) {
        Intrinsics.checkNotNullParameter(typeGroupFile, "typeGroupFile");
        Intrinsics.checkNotNullParameter(uploadListFiles, "uploadListFiles");
        Intrinsics.checkNotNullParameter(pckName, "pckName");
        this.typeGroupFile = typeGroupFile;
        this.uploadListFiles = uploadListFiles;
        this.pckName = pckName;
    }

    public final TypeGroupFile getTypeGroupFile() {
        return this.typeGroupFile;
    }

    public final List<UploadFileModel> getUploadListFiles() {
        return this.uploadListFiles;
    }

    public final String getPckName() {
        return this.pckName;
    }
}
