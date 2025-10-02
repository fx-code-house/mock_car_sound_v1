package com.thor.networkmodule.model.firmware;

import androidx.core.app.FrameMetricsAggregator;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FirmwareProfile.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b*\b\u0086\b\u0018\u00002\u00020\u0001Be\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000eJ\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010-\u001a\u00020\u000bHÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0006HÆ\u0003Ji\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u00101\u001a\u00020\u000b2\b\u00102\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00103\u001a\u00020\u0003HÖ\u0001J\t\u00104\u001a\u00020\u0006HÖ\u0001R\u001e\u0010\f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R \u0010\r\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0016\"\u0004\b\u001a\u0010\u0018R \u0010\t\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0016\"\u0004\b\u001c\u0010\u0018R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u0012R\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0010\"\u0004\b$\u0010\u0012R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0010\"\u0004\b&\u0010\u0012¨\u00065"}, d2 = {"Lcom/thor/networkmodule/model/firmware/FirmwareProfile;", "", "idFW", "", "devType", "devTypeName", "", "versionHW", "versionFW", "fwBinUrl", NotificationCompat.CATEGORY_STATUS, "", "code", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "(IILjava/lang/String;IILjava/lang/String;ZILjava/lang/String;)V", "getCode", "()I", "setCode", "(I)V", "getDevType", "setDevType", "getDevTypeName", "()Ljava/lang/String;", "setDevTypeName", "(Ljava/lang/String;)V", "getError", "setError", "getFwBinUrl", "setFwBinUrl", "getIdFW", "setIdFW", "getStatus", "()Z", "setStatus", "(Z)V", "getVersionFW", "setVersionFW", "getVersionHW", "setVersionHW", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class FirmwareProfile {

    @SerializedName("code")
    private int code;

    @SerializedName("dev_type")
    private int devType;

    @SerializedName("dev_type_name")
    private String devTypeName;

    @SerializedName("error_description")
    private String error;

    @SerializedName("fw_bin_url")
    private String fwBinUrl;

    @SerializedName("id_FW")
    private int idFW;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private boolean status;

    @SerializedName("version_FW")
    private int versionFW;

    @SerializedName("version_HW")
    private int versionHW;

    public FirmwareProfile() {
        this(0, 0, null, 0, 0, null, false, 0, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    /* renamed from: component1, reason: from getter */
    public final int getIdFW() {
        return this.idFW;
    }

    /* renamed from: component2, reason: from getter */
    public final int getDevType() {
        return this.devType;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDevTypeName() {
        return this.devTypeName;
    }

    /* renamed from: component4, reason: from getter */
    public final int getVersionHW() {
        return this.versionHW;
    }

    /* renamed from: component5, reason: from getter */
    public final int getVersionFW() {
        return this.versionFW;
    }

    /* renamed from: component6, reason: from getter */
    public final String getFwBinUrl() {
        return this.fwBinUrl;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getStatus() {
        return this.status;
    }

    /* renamed from: component8, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    /* renamed from: component9, reason: from getter */
    public final String getError() {
        return this.error;
    }

    public final FirmwareProfile copy(int idFW, int devType, String devTypeName, int versionHW, int versionFW, String fwBinUrl, boolean status, int code, String error) {
        return new FirmwareProfile(idFW, devType, devTypeName, versionHW, versionFW, fwBinUrl, status, code, error);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FirmwareProfile)) {
            return false;
        }
        FirmwareProfile firmwareProfile = (FirmwareProfile) other;
        return this.idFW == firmwareProfile.idFW && this.devType == firmwareProfile.devType && Intrinsics.areEqual(this.devTypeName, firmwareProfile.devTypeName) && this.versionHW == firmwareProfile.versionHW && this.versionFW == firmwareProfile.versionFW && Intrinsics.areEqual(this.fwBinUrl, firmwareProfile.fwBinUrl) && this.status == firmwareProfile.status && this.code == firmwareProfile.code && Intrinsics.areEqual(this.error, firmwareProfile.error);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.idFW) * 31) + Integer.hashCode(this.devType)) * 31;
        String str = this.devTypeName;
        int iHashCode2 = (((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.versionHW)) * 31) + Integer.hashCode(this.versionFW)) * 31;
        String str2 = this.fwBinUrl;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z = this.status;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int iHashCode4 = (((iHashCode3 + i) * 31) + Integer.hashCode(this.code)) * 31;
        String str3 = this.error;
        return iHashCode4 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "FirmwareProfile(idFW=" + this.idFW + ", devType=" + this.devType + ", devTypeName=" + this.devTypeName + ", versionHW=" + this.versionHW + ", versionFW=" + this.versionFW + ", fwBinUrl=" + this.fwBinUrl + ", status=" + this.status + ", code=" + this.code + ", error=" + this.error + ")";
    }

    public FirmwareProfile(int i, int i2, String str, int i3, int i4, String str2, boolean z, int i5, String str3) {
        this.idFW = i;
        this.devType = i2;
        this.devTypeName = str;
        this.versionHW = i3;
        this.versionFW = i4;
        this.fwBinUrl = str2;
        this.status = z;
        this.code = i5;
        this.error = str3;
    }

    public /* synthetic */ FirmwareProfile(int i, int i2, String str, int i3, int i4, String str2, boolean z, int i5, String str3, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? null : str, (i6 & 8) != 0 ? 0 : i3, (i6 & 16) != 0 ? 0 : i4, (i6 & 32) != 0 ? null : str2, (i6 & 64) != 0 ? true : z, (i6 & 128) == 0 ? i5 : 0, (i6 & 256) == 0 ? str3 : null);
    }

    public final int getIdFW() {
        return this.idFW;
    }

    public final void setIdFW(int i) {
        this.idFW = i;
    }

    public final int getDevType() {
        return this.devType;
    }

    public final void setDevType(int i) {
        this.devType = i;
    }

    public final String getDevTypeName() {
        return this.devTypeName;
    }

    public final void setDevTypeName(String str) {
        this.devTypeName = str;
    }

    public final int getVersionHW() {
        return this.versionHW;
    }

    public final void setVersionHW(int i) {
        this.versionHW = i;
    }

    public final int getVersionFW() {
        return this.versionFW;
    }

    public final void setVersionFW(int i) {
        this.versionFW = i;
    }

    public final String getFwBinUrl() {
        return this.fwBinUrl;
    }

    public final void setFwBinUrl(String str) {
        this.fwBinUrl = str;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final void setStatus(boolean z) {
        this.status = z;
    }

    public final int getCode() {
        return this.code;
    }

    public final void setCode(int i) {
        this.code = i;
    }

    public final String getError() {
        return this.error;
    }

    public final void setError(String str) {
        this.error = str;
    }
}
