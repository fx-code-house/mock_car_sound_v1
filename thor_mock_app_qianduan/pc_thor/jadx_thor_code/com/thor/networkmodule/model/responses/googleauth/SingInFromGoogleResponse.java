package com.thor.networkmodule.model.responses.googleauth;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SingInFromGoogleResponse.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J3\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u0007HÖ\u0001R \u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000b¨\u0006\u001c"}, d2 = {"Lcom/thor/networkmodule/model/responses/googleauth/SingInFromGoogleResponse;", "", "idUser", "", NotificationCompat.CATEGORY_STATUS, "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "token", "(IZLjava/lang/String;Ljava/lang/String;)V", "getError", "()Ljava/lang/String;", "setError", "(Ljava/lang/String;)V", "getIdUser", "()I", "getStatus", "()Z", "getToken", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SingInFromGoogleResponse {

    @SerializedName("error_description")
    private String error;

    @SerializedName("id_user")
    private final int idUser;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private final boolean status;

    @SerializedName("token")
    private final String token;

    public static /* synthetic */ SingInFromGoogleResponse copy$default(SingInFromGoogleResponse singInFromGoogleResponse, int i, boolean z, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = singInFromGoogleResponse.idUser;
        }
        if ((i2 & 2) != 0) {
            z = singInFromGoogleResponse.status;
        }
        if ((i2 & 4) != 0) {
            str = singInFromGoogleResponse.error;
        }
        if ((i2 & 8) != 0) {
            str2 = singInFromGoogleResponse.token;
        }
        return singInFromGoogleResponse.copy(i, z, str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getIdUser() {
        return this.idUser;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getStatus() {
        return this.status;
    }

    /* renamed from: component3, reason: from getter */
    public final String getError() {
        return this.error;
    }

    /* renamed from: component4, reason: from getter */
    public final String getToken() {
        return this.token;
    }

    public final SingInFromGoogleResponse copy(int idUser, boolean status, String error, String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        return new SingInFromGoogleResponse(idUser, status, error, token);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SingInFromGoogleResponse)) {
            return false;
        }
        SingInFromGoogleResponse singInFromGoogleResponse = (SingInFromGoogleResponse) other;
        return this.idUser == singInFromGoogleResponse.idUser && this.status == singInFromGoogleResponse.status && Intrinsics.areEqual(this.error, singInFromGoogleResponse.error) && Intrinsics.areEqual(this.token, singInFromGoogleResponse.token);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = Integer.hashCode(this.idUser) * 31;
        boolean z = this.status;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (iHashCode + i) * 31;
        String str = this.error;
        return ((i2 + (str == null ? 0 : str.hashCode())) * 31) + this.token.hashCode();
    }

    public String toString() {
        return "SingInFromGoogleResponse(idUser=" + this.idUser + ", status=" + this.status + ", error=" + this.error + ", token=" + this.token + ")";
    }

    public SingInFromGoogleResponse(int i, boolean z, String str, String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        this.idUser = i;
        this.status = z;
        this.error = str;
        this.token = token;
    }

    public /* synthetic */ SingInFromGoogleResponse(int i, boolean z, String str, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, (i2 & 4) != 0 ? null : str, str2);
    }

    public final int getIdUser() {
        return this.idUser;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final String getError() {
        return this.error;
    }

    public final void setError(String str) {
        this.error = str;
    }

    public final String getToken() {
        return this.token;
    }
}
