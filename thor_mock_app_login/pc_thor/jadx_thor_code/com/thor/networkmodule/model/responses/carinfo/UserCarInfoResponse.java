package com.thor.networkmodule.model.responses.carinfo;

import com.google.gson.annotations.SerializedName;
import com.thor.app.settings.CarInfoPreference;
import com.thor.networkmodule.model.responses.BaseResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserCarInfoResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b$\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bu\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\t\u0010'\u001a\u00020\u0005HÆ\u0003Jy\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,HÖ\u0003J\t\u0010-\u001a\u00020\u0003HÖ\u0001J\t\u0010.\u001a\u00020\u0005HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0016\u0010\f\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013¨\u0006/"}, d2 = {"Lcom/thor/networkmodule/model/responses/carinfo/UserCarInfoResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "car_generation_id", "", "car_generation_name", "", "car_mark_id", "car_mark_name", "car_model_id", CarInfoPreference.CAR_MODEL_ID_PREF, "car_serie_id", CarInfoPreference.CAR_SERIE_ID_PREF, "device_sn", "id_user", "token", "(ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getCar_generation_id", "()I", "getCar_generation_name", "()Ljava/lang/String;", "getCar_mark_id", "getCar_mark_name", "getCar_model_id", "getCar_model_name", "getCar_serie_id", "getCar_serie_name", "getDevice_sn", "getId_user", "getToken", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class UserCarInfoResponse extends BaseResponse {

    @SerializedName("car_generation_id")
    private final int car_generation_id;

    @SerializedName("car_generation_name")
    private final String car_generation_name;

    @SerializedName("car_mark_id")
    private final int car_mark_id;

    @SerializedName("car_mark_name")
    private final String car_mark_name;

    @SerializedName("car_model_id")
    private final int car_model_id;

    @SerializedName(CarInfoPreference.CAR_MODEL_ID_PREF)
    private final String car_model_name;

    @SerializedName("car_serie_id")
    private final int car_serie_id;

    @SerializedName(CarInfoPreference.CAR_SERIE_ID_PREF)
    private final String car_serie_name;

    @SerializedName("device_sn")
    private final String device_sn;

    @SerializedName("id_user")
    private final int id_user;

    @SerializedName("token")
    private final String token;

    public UserCarInfoResponse() {
        this(0, null, 0, null, 0, null, 0, null, null, 0, null, 2047, null);
    }

    /* renamed from: component1, reason: from getter */
    public final int getCar_generation_id() {
        return this.car_generation_id;
    }

    /* renamed from: component10, reason: from getter */
    public final int getId_user() {
        return this.id_user;
    }

    /* renamed from: component11, reason: from getter */
    public final String getToken() {
        return this.token;
    }

    /* renamed from: component2, reason: from getter */
    public final String getCar_generation_name() {
        return this.car_generation_name;
    }

    /* renamed from: component3, reason: from getter */
    public final int getCar_mark_id() {
        return this.car_mark_id;
    }

    /* renamed from: component4, reason: from getter */
    public final String getCar_mark_name() {
        return this.car_mark_name;
    }

    /* renamed from: component5, reason: from getter */
    public final int getCar_model_id() {
        return this.car_model_id;
    }

    /* renamed from: component6, reason: from getter */
    public final String getCar_model_name() {
        return this.car_model_name;
    }

    /* renamed from: component7, reason: from getter */
    public final int getCar_serie_id() {
        return this.car_serie_id;
    }

    /* renamed from: component8, reason: from getter */
    public final String getCar_serie_name() {
        return this.car_serie_name;
    }

    /* renamed from: component9, reason: from getter */
    public final String getDevice_sn() {
        return this.device_sn;
    }

    public final UserCarInfoResponse copy(int car_generation_id, String car_generation_name, int car_mark_id, String car_mark_name, int car_model_id, String car_model_name, int car_serie_id, String car_serie_name, String device_sn, int id_user, String token) {
        Intrinsics.checkNotNullParameter(car_generation_name, "car_generation_name");
        Intrinsics.checkNotNullParameter(car_mark_name, "car_mark_name");
        Intrinsics.checkNotNullParameter(car_model_name, "car_model_name");
        Intrinsics.checkNotNullParameter(car_serie_name, "car_serie_name");
        Intrinsics.checkNotNullParameter(device_sn, "device_sn");
        return new UserCarInfoResponse(car_generation_id, car_generation_name, car_mark_id, car_mark_name, car_model_id, car_model_name, car_serie_id, car_serie_name, device_sn, id_user, token);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserCarInfoResponse)) {
            return false;
        }
        UserCarInfoResponse userCarInfoResponse = (UserCarInfoResponse) other;
        return this.car_generation_id == userCarInfoResponse.car_generation_id && Intrinsics.areEqual(this.car_generation_name, userCarInfoResponse.car_generation_name) && this.car_mark_id == userCarInfoResponse.car_mark_id && Intrinsics.areEqual(this.car_mark_name, userCarInfoResponse.car_mark_name) && this.car_model_id == userCarInfoResponse.car_model_id && Intrinsics.areEqual(this.car_model_name, userCarInfoResponse.car_model_name) && this.car_serie_id == userCarInfoResponse.car_serie_id && Intrinsics.areEqual(this.car_serie_name, userCarInfoResponse.car_serie_name) && Intrinsics.areEqual(this.device_sn, userCarInfoResponse.device_sn) && this.id_user == userCarInfoResponse.id_user && Intrinsics.areEqual(this.token, userCarInfoResponse.token);
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((((((Integer.hashCode(this.car_generation_id) * 31) + this.car_generation_name.hashCode()) * 31) + Integer.hashCode(this.car_mark_id)) * 31) + this.car_mark_name.hashCode()) * 31) + Integer.hashCode(this.car_model_id)) * 31) + this.car_model_name.hashCode()) * 31) + Integer.hashCode(this.car_serie_id)) * 31) + this.car_serie_name.hashCode()) * 31) + this.device_sn.hashCode()) * 31) + Integer.hashCode(this.id_user)) * 31;
        String str = this.token;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "UserCarInfoResponse(car_generation_id=" + this.car_generation_id + ", car_generation_name=" + this.car_generation_name + ", car_mark_id=" + this.car_mark_id + ", car_mark_name=" + this.car_mark_name + ", car_model_id=" + this.car_model_id + ", car_model_name=" + this.car_model_name + ", car_serie_id=" + this.car_serie_id + ", car_serie_name=" + this.car_serie_name + ", device_sn=" + this.device_sn + ", id_user=" + this.id_user + ", token=" + this.token + ")";
    }

    public /* synthetic */ UserCarInfoResponse(int i, String str, int i2, String str2, int i3, String str3, int i4, String str4, String str5, int i5, String str6, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? "" : str, (i6 & 4) != 0 ? 0 : i2, (i6 & 8) != 0 ? "" : str2, (i6 & 16) != 0 ? 0 : i3, (i6 & 32) != 0 ? "" : str3, (i6 & 64) != 0 ? 0 : i4, (i6 & 128) != 0 ? "" : str4, (i6 & 256) != 0 ? "" : str5, (i6 & 512) == 0 ? i5 : 0, (i6 & 1024) == 0 ? str6 : "");
    }

    public final int getCar_generation_id() {
        return this.car_generation_id;
    }

    public final String getCar_generation_name() {
        return this.car_generation_name;
    }

    public final int getCar_mark_id() {
        return this.car_mark_id;
    }

    public final String getCar_mark_name() {
        return this.car_mark_name;
    }

    public final int getCar_model_id() {
        return this.car_model_id;
    }

    public final String getCar_model_name() {
        return this.car_model_name;
    }

    public final int getCar_serie_id() {
        return this.car_serie_id;
    }

    public final String getCar_serie_name() {
        return this.car_serie_name;
    }

    public final String getDevice_sn() {
        return this.device_sn;
    }

    public final int getId_user() {
        return this.id_user;
    }

    public final String getToken() {
        return this.token;
    }

    public UserCarInfoResponse(int i, String car_generation_name, int i2, String car_mark_name, int i3, String car_model_name, int i4, String car_serie_name, String device_sn, int i5, String str) {
        Intrinsics.checkNotNullParameter(car_generation_name, "car_generation_name");
        Intrinsics.checkNotNullParameter(car_mark_name, "car_mark_name");
        Intrinsics.checkNotNullParameter(car_model_name, "car_model_name");
        Intrinsics.checkNotNullParameter(car_serie_name, "car_serie_name");
        Intrinsics.checkNotNullParameter(device_sn, "device_sn");
        this.car_generation_id = i;
        this.car_generation_name = car_generation_name;
        this.car_mark_id = i2;
        this.car_mark_name = car_mark_name;
        this.car_model_id = i3;
        this.car_model_name = car_model_name;
        this.car_serie_id = i4;
        this.car_serie_name = car_serie_name;
        this.device_sn = device_sn;
        this.id_user = i5;
        this.token = str;
    }
}
