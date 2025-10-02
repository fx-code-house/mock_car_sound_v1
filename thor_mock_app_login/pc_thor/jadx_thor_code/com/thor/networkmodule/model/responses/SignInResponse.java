package com.thor.networkmodule.model.responses;

import com.carsystems.thor.app.BuildConfig;
import com.google.gson.annotations.SerializedName;
import com.thor.app.settings.CarInfoPreference;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignInResponse.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b0\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000fJ\t\u0010*\u001a\u00020\u0003HÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u00103\u001a\u00020\u0003HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0083\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u000109HÖ\u0003J\u0006\u0010:\u001a\u00020\u0006J\t\u0010;\u001a\u00020\u0003HÖ\u0001J\t\u0010<\u001a\u00020\u0006HÖ\u0001R\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R \u0010\n\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0011\"\u0004\b\u001d\u0010\u0013R \u0010\b\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0015\"\u0004\b\u001f\u0010\u0017R\u001e\u0010\u000b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0011\"\u0004\b!\u0010\u0013R \u0010\f\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R \u0010\r\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0015\"\u0004\b%\u0010\u0017R \u0010\u000e\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0015\"\u0004\b'\u0010\u0017R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0011\"\u0004\b)\u0010\u0013¨\u0006="}, d2 = {"Lcom/thor/networkmodule/model/responses/SignInResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "userId", "", "carMarkId", "carMarkName", "", "carModelId", "carModelName", "carGenerationId", "carGenerationName", "carSerieId", "carSerieName", "deviceSN", "token", "(IILjava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCarGenerationId", "()I", "setCarGenerationId", "(I)V", "getCarGenerationName", "()Ljava/lang/String;", "setCarGenerationName", "(Ljava/lang/String;)V", "getCarMarkId", "setCarMarkId", "getCarMarkName", "setCarMarkName", "getCarModelId", "setCarModelId", "getCarModelName", "setCarModelName", "getCarSerieId", "setCarSerieId", "getCarSerieName", "setCarSerieName", "getDeviceSN", "setDeviceSN", "getToken", "setToken", "getUserId", "setUserId", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "getFullDeviceSN", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SignInResponse extends BaseResponse {

    @SerializedName("car_generation_id")
    private int carGenerationId;

    @SerializedName("car_generation_name")
    private String carGenerationName;

    @SerializedName("car_mark_id")
    private int carMarkId;

    @SerializedName("car_mark_name")
    private String carMarkName;

    @SerializedName("car_model_id")
    private int carModelId;

    @SerializedName(CarInfoPreference.CAR_MODEL_ID_PREF)
    private String carModelName;

    @SerializedName("car_serie_id")
    private int carSerieId;

    @SerializedName(CarInfoPreference.CAR_SERIE_ID_PREF)
    private String carSerieName;

    @SerializedName("device_sn")
    private String deviceSN;

    @SerializedName("token")
    private String token;

    @SerializedName("id_user")
    private int userId;

    public SignInResponse() {
        this(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
    }

    /* renamed from: component1, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    /* renamed from: component10, reason: from getter */
    public final String getDeviceSN() {
        return this.deviceSN;
    }

    /* renamed from: component11, reason: from getter */
    public final String getToken() {
        return this.token;
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
    public final int getCarSerieId() {
        return this.carSerieId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getCarSerieName() {
        return this.carSerieName;
    }

    public final SignInResponse copy(int userId, int carMarkId, String carMarkName, int carModelId, String carModelName, int carGenerationId, String carGenerationName, int carSerieId, String carSerieName, String deviceSN, String token) {
        return new SignInResponse(userId, carMarkId, carMarkName, carModelId, carModelName, carGenerationId, carGenerationName, carSerieId, carSerieName, deviceSN, token);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SignInResponse)) {
            return false;
        }
        SignInResponse signInResponse = (SignInResponse) other;
        return this.userId == signInResponse.userId && this.carMarkId == signInResponse.carMarkId && Intrinsics.areEqual(this.carMarkName, signInResponse.carMarkName) && this.carModelId == signInResponse.carModelId && Intrinsics.areEqual(this.carModelName, signInResponse.carModelName) && this.carGenerationId == signInResponse.carGenerationId && Intrinsics.areEqual(this.carGenerationName, signInResponse.carGenerationName) && this.carSerieId == signInResponse.carSerieId && Intrinsics.areEqual(this.carSerieName, signInResponse.carSerieName) && Intrinsics.areEqual(this.deviceSN, signInResponse.deviceSN) && Intrinsics.areEqual(this.token, signInResponse.token);
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.userId) * 31) + Integer.hashCode(this.carMarkId)) * 31;
        String str = this.carMarkName;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.carModelId)) * 31;
        String str2 = this.carModelName;
        int iHashCode3 = (((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.carGenerationId)) * 31;
        String str3 = this.carGenerationName;
        int iHashCode4 = (((iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + Integer.hashCode(this.carSerieId)) * 31;
        String str4 = this.carSerieName;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.deviceSN;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.token;
        return iHashCode6 + (str6 != null ? str6.hashCode() : 0);
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "SignInResponse(userId=" + this.userId + ", carMarkId=" + this.carMarkId + ", carMarkName=" + this.carMarkName + ", carModelId=" + this.carModelId + ", carModelName=" + this.carModelName + ", carGenerationId=" + this.carGenerationId + ", carGenerationName=" + this.carGenerationName + ", carSerieId=" + this.carSerieId + ", carSerieName=" + this.carSerieName + ", deviceSN=" + this.deviceSN + ", token=" + this.token + ")";
    }

    public /* synthetic */ SignInResponse(int i, int i2, String str, int i3, String str2, int i4, String str3, int i5, String str4, String str5, String str6, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? null : str, (i6 & 8) != 0 ? 0 : i3, (i6 & 16) != 0 ? null : str2, (i6 & 32) != 0 ? 0 : i4, (i6 & 64) != 0 ? null : str3, (i6 & 128) == 0 ? i5 : 0, (i6 & 256) != 0 ? null : str4, (i6 & 512) != 0 ? null : str5, (i6 & 1024) == 0 ? str6 : null);
    }

    public final int getUserId() {
        return this.userId;
    }

    public final void setUserId(int i) {
        this.userId = i;
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

    public final int getCarSerieId() {
        return this.carSerieId;
    }

    public final void setCarSerieId(int i) {
        this.carSerieId = i;
    }

    public final String getCarSerieName() {
        return this.carSerieName;
    }

    public final void setCarSerieName(String str) {
        this.carSerieName = str;
    }

    public final String getDeviceSN() {
        return this.deviceSN;
    }

    public final void setDeviceSN(String str) {
        this.deviceSN = str;
    }

    public final String getToken() {
        return this.token;
    }

    public final void setToken(String str) {
        this.token = str;
    }

    public SignInResponse(int i, int i2, String str, int i3, String str2, int i4, String str3, int i5, String str4, String str5, String str6) {
        this.userId = i;
        this.carMarkId = i2;
        this.carMarkName = str;
        this.carModelId = i3;
        this.carModelName = str2;
        this.carGenerationId = i4;
        this.carGenerationName = str3;
        this.carSerieId = i5;
        this.carSerieName = str4;
        this.deviceSN = str5;
        this.token = str6;
    }

    public final String getFullDeviceSN() {
        return BuildConfig.APP_PREFIX + this.deviceSN;
    }
}
