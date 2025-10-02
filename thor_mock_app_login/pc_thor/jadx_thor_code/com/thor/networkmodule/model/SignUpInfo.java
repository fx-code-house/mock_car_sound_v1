package com.thor.networkmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignUpInfo.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b2\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 B2\u00020\u0001:\u0001BB\u0081\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000fJ\u000b\u0010*\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0005HÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0005HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u00103\u001a\u00020\u0005HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0085\u0001\u00105\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\t\u00106\u001a\u00020\u0005HÖ\u0001J\u0013\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:HÖ\u0003J\t\u0010;\u001a\u00020\u0005HÖ\u0001J\t\u0010<\u001a\u00020\u0003HÖ\u0001J\u0019\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0011\"\u0004\b\u001d\u0010\u0013R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0015\"\u0004\b\u001f\u0010\u0017R\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0011\"\u0004\b!\u0010\u0013R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0015\"\u0004\b%\u0010\u0017R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0015\"\u0004\b'\u0010\u0017R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0015\"\u0004\b)\u0010\u0017¨\u0006C"}, d2 = {"Lcom/thor/networkmodule/model/SignUpInfo;", "Landroid/os/Parcelable;", "deviceSN", "", "carMarkId", "", "carMarkName", "carModelId", "carModelName", "carGenerationId", "carGenerationName", "carSeriesId", "carSeriesName", "email", "password", "(Ljava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCarGenerationId", "()I", "setCarGenerationId", "(I)V", "getCarGenerationName", "()Ljava/lang/String;", "setCarGenerationName", "(Ljava/lang/String;)V", "getCarMarkId", "setCarMarkId", "getCarMarkName", "setCarMarkName", "getCarModelId", "setCarModelId", "getCarModelName", "setCarModelName", "getCarSeriesId", "setCarSeriesId", "getCarSeriesName", "setCarSeriesName", "getDeviceSN", "setDeviceSN", "getEmail", "setEmail", "getPassword", "setPassword", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SignUpInfo implements Parcelable {
    public static final String BUNDLE_NAME;
    private int carGenerationId;
    private String carGenerationName;
    private int carMarkId;
    private String carMarkName;
    private int carModelId;
    private String carModelName;
    private int carSeriesId;
    private String carSeriesName;
    private String deviceSN;
    private String email;
    private String password;
    public static final Parcelable.Creator<SignUpInfo> CREATOR = new Creator();

    /* compiled from: SignUpInfo.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SignUpInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SignUpInfo createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new SignUpInfo(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SignUpInfo[] newArray(int i) {
            return new SignUpInfo[i];
        }
    }

    public SignUpInfo() {
        this(null, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
    }

    /* renamed from: component1, reason: from getter */
    public final String getDeviceSN() {
        return this.deviceSN;
    }

    /* renamed from: component10, reason: from getter */
    public final String getEmail() {
        return this.email;
    }

    /* renamed from: component11, reason: from getter */
    public final String getPassword() {
        return this.password;
    }

    /* renamed from: component2, reason: from getter */
    public final int getCarMarkId() {
        return this.carMarkId;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCarMarkName() {
        return this.carMarkName;
    }

    /* renamed from: component4, reason: from getter */
    public final int getCarModelId() {
        return this.carModelId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getCarModelName() {
        return this.carModelName;
    }

    /* renamed from: component6, reason: from getter */
    public final int getCarGenerationId() {
        return this.carGenerationId;
    }

    /* renamed from: component7, reason: from getter */
    public final String getCarGenerationName() {
        return this.carGenerationName;
    }

    /* renamed from: component8, reason: from getter */
    public final int getCarSeriesId() {
        return this.carSeriesId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getCarSeriesName() {
        return this.carSeriesName;
    }

    public final SignUpInfo copy(String deviceSN, int carMarkId, String carMarkName, int carModelId, String carModelName, int carGenerationId, String carGenerationName, int carSeriesId, String carSeriesName, String email, String password) {
        return new SignUpInfo(deviceSN, carMarkId, carMarkName, carModelId, carModelName, carGenerationId, carGenerationName, carSeriesId, carSeriesName, email, password);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SignUpInfo)) {
            return false;
        }
        SignUpInfo signUpInfo = (SignUpInfo) other;
        return Intrinsics.areEqual(this.deviceSN, signUpInfo.deviceSN) && this.carMarkId == signUpInfo.carMarkId && Intrinsics.areEqual(this.carMarkName, signUpInfo.carMarkName) && this.carModelId == signUpInfo.carModelId && Intrinsics.areEqual(this.carModelName, signUpInfo.carModelName) && this.carGenerationId == signUpInfo.carGenerationId && Intrinsics.areEqual(this.carGenerationName, signUpInfo.carGenerationName) && this.carSeriesId == signUpInfo.carSeriesId && Intrinsics.areEqual(this.carSeriesName, signUpInfo.carSeriesName) && Intrinsics.areEqual(this.email, signUpInfo.email) && Intrinsics.areEqual(this.password, signUpInfo.password);
    }

    public int hashCode() {
        String str = this.deviceSN;
        int iHashCode = (((str == null ? 0 : str.hashCode()) * 31) + Integer.hashCode(this.carMarkId)) * 31;
        String str2 = this.carMarkName;
        int iHashCode2 = (((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.carModelId)) * 31;
        String str3 = this.carModelName;
        int iHashCode3 = (((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + Integer.hashCode(this.carGenerationId)) * 31;
        String str4 = this.carGenerationName;
        int iHashCode4 = (((iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31) + Integer.hashCode(this.carSeriesId)) * 31;
        String str5 = this.carSeriesName;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.email;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.password;
        return iHashCode6 + (str7 != null ? str7.hashCode() : 0);
    }

    public String toString() {
        return "SignUpInfo(deviceSN=" + this.deviceSN + ", carMarkId=" + this.carMarkId + ", carMarkName=" + this.carMarkName + ", carModelId=" + this.carModelId + ", carModelName=" + this.carModelName + ", carGenerationId=" + this.carGenerationId + ", carGenerationName=" + this.carGenerationName + ", carSeriesId=" + this.carSeriesId + ", carSeriesName=" + this.carSeriesName + ", email=" + this.email + ", password=" + this.password + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeString(this.deviceSN);
        parcel.writeInt(this.carMarkId);
        parcel.writeString(this.carMarkName);
        parcel.writeInt(this.carModelId);
        parcel.writeString(this.carModelName);
        parcel.writeInt(this.carGenerationId);
        parcel.writeString(this.carGenerationName);
        parcel.writeInt(this.carSeriesId);
        parcel.writeString(this.carSeriesName);
        parcel.writeString(this.email);
        parcel.writeString(this.password);
    }

    public SignUpInfo(String str, int i, String str2, int i2, String str3, int i3, String str4, int i4, String str5, String str6, String str7) {
        this.deviceSN = str;
        this.carMarkId = i;
        this.carMarkName = str2;
        this.carModelId = i2;
        this.carModelName = str3;
        this.carGenerationId = i3;
        this.carGenerationName = str4;
        this.carSeriesId = i4;
        this.carSeriesName = str5;
        this.email = str6;
        this.password = str7;
    }

    public /* synthetic */ SignUpInfo(String str, int i, String str2, int i2, String str3, int i3, String str4, int i4, String str5, String str6, String str7, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? null : str, (i5 & 2) != 0 ? 0 : i, (i5 & 4) != 0 ? null : str2, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) != 0 ? null : str3, (i5 & 32) != 0 ? 0 : i3, (i5 & 64) != 0 ? null : str4, (i5 & 128) == 0 ? i4 : 0, (i5 & 256) != 0 ? null : str5, (i5 & 512) != 0 ? null : str6, (i5 & 1024) == 0 ? str7 : null);
    }

    public final String getDeviceSN() {
        return this.deviceSN;
    }

    public final void setDeviceSN(String str) {
        this.deviceSN = str;
    }

    public final int getCarMarkId() {
        return this.carMarkId;
    }

    public final void setCarMarkId(int i) {
        this.carMarkId = i;
    }

    public final String getCarMarkName() {
        return this.carMarkName;
    }

    public final void setCarMarkName(String str) {
        this.carMarkName = str;
    }

    public final int getCarModelId() {
        return this.carModelId;
    }

    public final void setCarModelId(int i) {
        this.carModelId = i;
    }

    public final String getCarModelName() {
        return this.carModelName;
    }

    public final void setCarModelName(String str) {
        this.carModelName = str;
    }

    public final int getCarGenerationId() {
        return this.carGenerationId;
    }

    public final void setCarGenerationId(int i) {
        this.carGenerationId = i;
    }

    public final String getCarGenerationName() {
        return this.carGenerationName;
    }

    public final void setCarGenerationName(String str) {
        this.carGenerationName = str;
    }

    public final int getCarSeriesId() {
        return this.carSeriesId;
    }

    public final void setCarSeriesId(int i) {
        this.carSeriesId = i;
    }

    public final String getCarSeriesName() {
        return this.carSeriesName;
    }

    public final void setCarSeriesName(String str) {
        this.carSeriesName = str;
    }

    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(String str) {
        this.email = str;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(String str) {
        this.password = str;
    }

    static {
        Intrinsics.checkNotNullExpressionValue("SignUpInfo", "SignUpInfo::class.java.simpleName");
        BUNDLE_NAME = "SignUpInfo";
    }
}
