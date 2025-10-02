package com.thor.networkmodule.model;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CanFile.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J=\u0010\u001d\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\u0005HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006#"}, d2 = {"Lcom/thor/networkmodule/model/CanFile;", "", "fileUrl", "", "versionFile", "", "canConnectionInfo", "nativeControlInfo", "driveSelectInfo", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCanConnectionInfo", "()Ljava/lang/String;", "setCanConnectionInfo", "(Ljava/lang/String;)V", "getDriveSelectInfo", "setDriveSelectInfo", "getFileUrl", "setFileUrl", "getNativeControlInfo", "setNativeControlInfo", "getVersionFile", "()I", "setVersionFile", "(I)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CanFile {

    @SerializedName("comment_CanConnectionPoint")
    private String canConnectionInfo;

    @SerializedName("comment_DriveSelect")
    private String driveSelectInfo;

    @SerializedName("file")
    private String fileUrl;

    @SerializedName("comment_NativeControl")
    private String nativeControlInfo;

    @SerializedName("version")
    private int versionFile;

    public CanFile() {
        this(null, 0, null, null, null, 31, null);
    }

    public static /* synthetic */ CanFile copy$default(CanFile canFile, String str, int i, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = canFile.fileUrl;
        }
        if ((i2 & 2) != 0) {
            i = canFile.versionFile;
        }
        int i3 = i;
        if ((i2 & 4) != 0) {
            str2 = canFile.canConnectionInfo;
        }
        String str5 = str2;
        if ((i2 & 8) != 0) {
            str3 = canFile.nativeControlInfo;
        }
        String str6 = str3;
        if ((i2 & 16) != 0) {
            str4 = canFile.driveSelectInfo;
        }
        return canFile.copy(str, i3, str5, str6, str4);
    }

    /* renamed from: component1, reason: from getter */
    public final String getFileUrl() {
        return this.fileUrl;
    }

    /* renamed from: component2, reason: from getter */
    public final int getVersionFile() {
        return this.versionFile;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCanConnectionInfo() {
        return this.canConnectionInfo;
    }

    /* renamed from: component4, reason: from getter */
    public final String getNativeControlInfo() {
        return this.nativeControlInfo;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDriveSelectInfo() {
        return this.driveSelectInfo;
    }

    public final CanFile copy(String fileUrl, int versionFile, String canConnectionInfo, String nativeControlInfo, String driveSelectInfo) {
        Intrinsics.checkNotNullParameter(canConnectionInfo, "canConnectionInfo");
        Intrinsics.checkNotNullParameter(nativeControlInfo, "nativeControlInfo");
        Intrinsics.checkNotNullParameter(driveSelectInfo, "driveSelectInfo");
        return new CanFile(fileUrl, versionFile, canConnectionInfo, nativeControlInfo, driveSelectInfo);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CanFile)) {
            return false;
        }
        CanFile canFile = (CanFile) other;
        return Intrinsics.areEqual(this.fileUrl, canFile.fileUrl) && this.versionFile == canFile.versionFile && Intrinsics.areEqual(this.canConnectionInfo, canFile.canConnectionInfo) && Intrinsics.areEqual(this.nativeControlInfo, canFile.nativeControlInfo) && Intrinsics.areEqual(this.driveSelectInfo, canFile.driveSelectInfo);
    }

    public int hashCode() {
        String str = this.fileUrl;
        return ((((((((str == null ? 0 : str.hashCode()) * 31) + Integer.hashCode(this.versionFile)) * 31) + this.canConnectionInfo.hashCode()) * 31) + this.nativeControlInfo.hashCode()) * 31) + this.driveSelectInfo.hashCode();
    }

    public String toString() {
        return "CanFile(fileUrl=" + this.fileUrl + ", versionFile=" + this.versionFile + ", canConnectionInfo=" + this.canConnectionInfo + ", nativeControlInfo=" + this.nativeControlInfo + ", driveSelectInfo=" + this.driveSelectInfo + ")";
    }

    public CanFile(String str, int i, String canConnectionInfo, String nativeControlInfo, String driveSelectInfo) {
        Intrinsics.checkNotNullParameter(canConnectionInfo, "canConnectionInfo");
        Intrinsics.checkNotNullParameter(nativeControlInfo, "nativeControlInfo");
        Intrinsics.checkNotNullParameter(driveSelectInfo, "driveSelectInfo");
        this.fileUrl = str;
        this.versionFile = i;
        this.canConnectionInfo = canConnectionInfo;
        this.nativeControlInfo = nativeControlInfo;
        this.driveSelectInfo = driveSelectInfo;
    }

    public /* synthetic */ CanFile(String str, int i, String str2, String str3, String str4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : str, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) == 0 ? str4 : "");
    }

    public final String getFileUrl() {
        return this.fileUrl;
    }

    public final void setFileUrl(String str) {
        this.fileUrl = str;
    }

    public final int getVersionFile() {
        return this.versionFile;
    }

    public final void setVersionFile(int i) {
        this.versionFile = i;
    }

    public final String getCanConnectionInfo() {
        return this.canConnectionInfo;
    }

    public final void setCanConnectionInfo(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.canConnectionInfo = str;
    }

    public final String getNativeControlInfo() {
        return this.nativeControlInfo;
    }

    public final void setNativeControlInfo(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nativeControlInfo = str;
    }

    public final String getDriveSelectInfo() {
        return this.driveSelectInfo;
    }

    public final void setDriveSelectInfo(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.driveSelectInfo = str;
    }
}
