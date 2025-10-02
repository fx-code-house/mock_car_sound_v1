package com.thor.networkmodule.model.responses;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import com.thor.app.settings.CarInfoPreference;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarInfoAuthorizeResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b)\b\u0086\b\u0018\u00002\u00020\u0001By\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0005\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0003¢\u0006\u0002\u0010\u0013J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0005HÆ\u0003J\t\u0010+\u001a\u00020\u0011HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0005HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0005HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\u0005HÆ\u0003J\t\u00104\u001a\u00020\u0003HÆ\u0003J\u0097\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0003HÆ\u0001J\u0013\u00106\u001a\u00020\u00112\b\u00107\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00108\u001a\u00020\u0005HÖ\u0001J\t\u00109\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R \u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0015\"\u0004\b!\u0010\"R\u0016\u0010\u000f\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0016\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0016\u0010\u0012\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0015¨\u0006:"}, d2 = {"Lcom/thor/networkmodule/model/responses/CarInfoAuthorizeResponse;", "", "appVersion", "", "carGenerationId", "", "carGenerationName", "carMarkId", "carMarkName", "carModelId", "carModelName", "carSerieId", "carSerieName", "deviceSn", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "idUser", NotificationCompat.CATEGORY_STATUS, "", "token", "(Ljava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;)V", "getAppVersion", "()Ljava/lang/String;", "getCarGenerationId", "()I", "getCarGenerationName", "getCarMarkId", "getCarMarkName", "getCarModelId", "getCarModelName", "getCarSerieId", "getCarSerieName", "getDeviceSn", "getError", "setError", "(Ljava/lang/String;)V", "getIdUser", "getStatus", "()Z", "getToken", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CarInfoAuthorizeResponse {

    @SerializedName("app_version")
    private final String appVersion;

    @SerializedName("car_generation_id")
    private final int carGenerationId;

    @SerializedName("car_generation_name")
    private final String carGenerationName;

    @SerializedName("car_mark_id")
    private final int carMarkId;

    @SerializedName("car_mark_name")
    private final String carMarkName;

    @SerializedName("car_model_id")
    private final int carModelId;

    @SerializedName(CarInfoPreference.CAR_MODEL_ID_PREF)
    private final String carModelName;

    @SerializedName("car_serie_id")
    private final int carSerieId;

    @SerializedName(CarInfoPreference.CAR_SERIE_ID_PREF)
    private final String carSerieName;

    @SerializedName("device_sn")
    private final String deviceSn;

    @SerializedName("error_description")
    private String error;

    @SerializedName("id_user")
    private final int idUser;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private final boolean status;

    @SerializedName("token")
    private final String token;

    /* renamed from: component1, reason: from getter */
    public final String getAppVersion() {
        return this.appVersion;
    }

    /* renamed from: component10, reason: from getter */
    public final String getDeviceSn() {
        return this.deviceSn;
    }

    /* renamed from: component11, reason: from getter */
    public final String getError() {
        return this.error;
    }

    /* renamed from: component12, reason: from getter */
    public final int getIdUser() {
        return this.idUser;
    }

    /* renamed from: component13, reason: from getter */
    public final boolean getStatus() {
        return this.status;
    }

    /* renamed from: component14, reason: from getter */
    public final String getToken() {
        return this.token;
    }

    /* renamed from: component2, reason: from getter */
    public final int getCarGenerationId() {
        return this.carGenerationId;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCarGenerationName() {
        return this.carGenerationName;
    }

    /* renamed from: component4, reason: from getter */
    public final int getCarMarkId() {
        return this.carMarkId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getCarMarkName() {
        return this.carMarkName;
    }

    /* renamed from: component6, reason: from getter */
    public final int getCarModelId() {
        return this.carModelId;
    }

    /* renamed from: component7, reason: from getter */
    public final String getCarModelName() {
        return this.carModelName;
    }

    /* renamed from: component8, reason: from getter */
    public final int getCarSerieId() {
        return this.carSerieId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getCarSerieName() {
        return this.carSerieName;
    }

    public final CarInfoAuthorizeResponse copy(String appVersion, int carGenerationId, String carGenerationName, int carMarkId, String carMarkName, int carModelId, String carModelName, int carSerieId, String carSerieName, String deviceSn, String error, int idUser, boolean status, String token) {
        Intrinsics.checkNotNullParameter(appVersion, "appVersion");
        Intrinsics.checkNotNullParameter(carGenerationName, "carGenerationName");
        Intrinsics.checkNotNullParameter(carMarkName, "carMarkName");
        Intrinsics.checkNotNullParameter(carModelName, "carModelName");
        Intrinsics.checkNotNullParameter(carSerieName, "carSerieName");
        Intrinsics.checkNotNullParameter(deviceSn, "deviceSn");
        Intrinsics.checkNotNullParameter(token, "token");
        return new CarInfoAuthorizeResponse(appVersion, carGenerationId, carGenerationName, carMarkId, carMarkName, carModelId, carModelName, carSerieId, carSerieName, deviceSn, error, idUser, status, token);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CarInfoAuthorizeResponse)) {
            return false;
        }
        CarInfoAuthorizeResponse carInfoAuthorizeResponse = (CarInfoAuthorizeResponse) other;
        return Intrinsics.areEqual(this.appVersion, carInfoAuthorizeResponse.appVersion) && this.carGenerationId == carInfoAuthorizeResponse.carGenerationId && Intrinsics.areEqual(this.carGenerationName, carInfoAuthorizeResponse.carGenerationName) && this.carMarkId == carInfoAuthorizeResponse.carMarkId && Intrinsics.areEqual(this.carMarkName, carInfoAuthorizeResponse.carMarkName) && this.carModelId == carInfoAuthorizeResponse.carModelId && Intrinsics.areEqual(this.carModelName, carInfoAuthorizeResponse.carModelName) && this.carSerieId == carInfoAuthorizeResponse.carSerieId && Intrinsics.areEqual(this.carSerieName, carInfoAuthorizeResponse.carSerieName) && Intrinsics.areEqual(this.deviceSn, carInfoAuthorizeResponse.deviceSn) && Intrinsics.areEqual(this.error, carInfoAuthorizeResponse.error) && this.idUser == carInfoAuthorizeResponse.idUser && this.status == carInfoAuthorizeResponse.status && Intrinsics.areEqual(this.token, carInfoAuthorizeResponse.token);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((((((((((((this.appVersion.hashCode() * 31) + Integer.hashCode(this.carGenerationId)) * 31) + this.carGenerationName.hashCode()) * 31) + Integer.hashCode(this.carMarkId)) * 31) + this.carMarkName.hashCode()) * 31) + Integer.hashCode(this.carModelId)) * 31) + this.carModelName.hashCode()) * 31) + Integer.hashCode(this.carSerieId)) * 31) + this.carSerieName.hashCode()) * 31) + this.deviceSn.hashCode()) * 31;
        String str = this.error;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.idUser)) * 31;
        boolean z = this.status;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return ((iHashCode2 + i) * 31) + this.token.hashCode();
    }

    public String toString() {
        return "CarInfoAuthorizeResponse(appVersion=" + this.appVersion + ", carGenerationId=" + this.carGenerationId + ", carGenerationName=" + this.carGenerationName + ", carMarkId=" + this.carMarkId + ", carMarkName=" + this.carMarkName + ", carModelId=" + this.carModelId + ", carModelName=" + this.carModelName + ", carSerieId=" + this.carSerieId + ", carSerieName=" + this.carSerieName + ", deviceSn=" + this.deviceSn + ", error=" + this.error + ", idUser=" + this.idUser + ", status=" + this.status + ", token=" + this.token + ")";
    }

    public CarInfoAuthorizeResponse(String appVersion, int i, String carGenerationName, int i2, String carMarkName, int i3, String carModelName, int i4, String carSerieName, String deviceSn, String str, int i5, boolean z, String token) {
        Intrinsics.checkNotNullParameter(appVersion, "appVersion");
        Intrinsics.checkNotNullParameter(carGenerationName, "carGenerationName");
        Intrinsics.checkNotNullParameter(carMarkName, "carMarkName");
        Intrinsics.checkNotNullParameter(carModelName, "carModelName");
        Intrinsics.checkNotNullParameter(carSerieName, "carSerieName");
        Intrinsics.checkNotNullParameter(deviceSn, "deviceSn");
        Intrinsics.checkNotNullParameter(token, "token");
        this.appVersion = appVersion;
        this.carGenerationId = i;
        this.carGenerationName = carGenerationName;
        this.carMarkId = i2;
        this.carMarkName = carMarkName;
        this.carModelId = i3;
        this.carModelName = carModelName;
        this.carSerieId = i4;
        this.carSerieName = carSerieName;
        this.deviceSn = deviceSn;
        this.error = str;
        this.idUser = i5;
        this.status = z;
        this.token = token;
    }

    public /* synthetic */ CarInfoAuthorizeResponse(String str, int i, String str2, int i2, String str3, int i3, String str4, int i4, String str5, String str6, String str7, int i5, boolean z, String str8, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, str2, i2, str3, i3, str4, i4, str5, str6, (i6 & 1024) != 0 ? null : str7, i5, z, str8);
    }

    public final String getAppVersion() {
        return this.appVersion;
    }

    public final int getCarGenerationId() {
        return this.carGenerationId;
    }

    public final String getCarGenerationName() {
        return this.carGenerationName;
    }

    public final int getCarMarkId() {
        return this.carMarkId;
    }

    public final String getCarMarkName() {
        return this.carMarkName;
    }

    public final int getCarModelId() {
        return this.carModelId;
    }

    public final String getCarModelName() {
        return this.carModelName;
    }

    public final int getCarSerieId() {
        return this.carSerieId;
    }

    public final String getCarSerieName() {
        return this.carSerieName;
    }

    public final String getDeviceSn() {
        return this.deviceSn;
    }

    public final String getError() {
        return this.error;
    }

    public final void setError(String str) {
        this.error = str;
    }

    public final int getIdUser() {
        return this.idUser;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final String getToken() {
        return this.token;
    }
}
