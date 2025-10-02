package com.thor.networkmodule.model.firmware;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FirmwareProfileShort.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B;\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\tJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0006HÆ\u0003J?\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"HÖ\u0003J\t\u0010#\u001a\u00020\u0003HÖ\u0001J\t\u0010$\u001a\u00020\u0006HÖ\u0001J\u0019\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\b\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\r¨\u0006*"}, d2 = {"Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "Landroid/os/Parcelable;", "idFW", "", "devType", "devTypeName", "", "versionFW", "fwBinUrl", "(IILjava/lang/String;ILjava/lang/String;)V", "getDevType", "()I", "setDevType", "(I)V", "getDevTypeName", "()Ljava/lang/String;", "setDevTypeName", "(Ljava/lang/String;)V", "getFwBinUrl", "setFwBinUrl", "getIdFW", "setIdFW", "getVersionFW", "setVersionFW", "component1", "component2", "component3", "component4", "component5", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class FirmwareProfileShort implements Parcelable {
    public static final Parcelable.Creator<FirmwareProfileShort> CREATOR = new Creator();

    @SerializedName("dev_type")
    private int devType;

    @SerializedName("dev_type_name")
    private String devTypeName;

    @SerializedName("fw_bin_url")
    private String fwBinUrl;

    @SerializedName("id_FW")
    private int idFW;

    @SerializedName("version_FW")
    private int versionFW;

    /* compiled from: FirmwareProfileShort.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<FirmwareProfileShort> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final FirmwareProfileShort createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new FirmwareProfileShort(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final FirmwareProfileShort[] newArray(int i) {
            return new FirmwareProfileShort[i];
        }
    }

    public FirmwareProfileShort() {
        this(0, 0, null, 0, null, 31, null);
    }

    public static /* synthetic */ FirmwareProfileShort copy$default(FirmwareProfileShort firmwareProfileShort, int i, int i2, String str, int i3, String str2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = firmwareProfileShort.idFW;
        }
        if ((i4 & 2) != 0) {
            i2 = firmwareProfileShort.devType;
        }
        int i5 = i2;
        if ((i4 & 4) != 0) {
            str = firmwareProfileShort.devTypeName;
        }
        String str3 = str;
        if ((i4 & 8) != 0) {
            i3 = firmwareProfileShort.versionFW;
        }
        int i6 = i3;
        if ((i4 & 16) != 0) {
            str2 = firmwareProfileShort.fwBinUrl;
        }
        return firmwareProfileShort.copy(i, i5, str3, i6, str2);
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
    public final int getVersionFW() {
        return this.versionFW;
    }

    /* renamed from: component5, reason: from getter */
    public final String getFwBinUrl() {
        return this.fwBinUrl;
    }

    public final FirmwareProfileShort copy(int idFW, int devType, String devTypeName, int versionFW, String fwBinUrl) {
        return new FirmwareProfileShort(idFW, devType, devTypeName, versionFW, fwBinUrl);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FirmwareProfileShort)) {
            return false;
        }
        FirmwareProfileShort firmwareProfileShort = (FirmwareProfileShort) other;
        return this.idFW == firmwareProfileShort.idFW && this.devType == firmwareProfileShort.devType && Intrinsics.areEqual(this.devTypeName, firmwareProfileShort.devTypeName) && this.versionFW == firmwareProfileShort.versionFW && Intrinsics.areEqual(this.fwBinUrl, firmwareProfileShort.fwBinUrl);
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.idFW) * 31) + Integer.hashCode(this.devType)) * 31;
        String str = this.devTypeName;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.versionFW)) * 31;
        String str2 = this.fwBinUrl;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "FirmwareProfileShort(idFW=" + this.idFW + ", devType=" + this.devType + ", devTypeName=" + this.devTypeName + ", versionFW=" + this.versionFW + ", fwBinUrl=" + this.fwBinUrl + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.idFW);
        parcel.writeInt(this.devType);
        parcel.writeString(this.devTypeName);
        parcel.writeInt(this.versionFW);
        parcel.writeString(this.fwBinUrl);
    }

    public FirmwareProfileShort(int i, int i2, String str, int i3, String str2) {
        this.idFW = i;
        this.devType = i2;
        this.devTypeName = str;
        this.versionFW = i3;
        this.fwBinUrl = str2;
    }

    public /* synthetic */ FirmwareProfileShort(int i, int i2, String str, int i3, String str2, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? null : str, (i4 & 8) == 0 ? i3 : 0, (i4 & 16) != 0 ? null : str2);
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
}
