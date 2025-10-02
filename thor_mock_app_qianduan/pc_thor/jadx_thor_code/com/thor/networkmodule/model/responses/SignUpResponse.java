package com.thor.networkmodule.model.responses;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignUpResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001R \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lcom/thor/networkmodule/model/responses/SignUpResponse;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "userId", "", "token", "", "(ILjava/lang/String;)V", "getToken", "()Ljava/lang/String;", "setToken", "(Ljava/lang/String;)V", "getUserId", "()I", "setUserId", "(I)V", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SignUpResponse extends BaseResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("id_user")
    private int userId;

    /* JADX WARN: Multi-variable type inference failed */
    public SignUpResponse() {
        this(0, null, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ SignUpResponse copy$default(SignUpResponse signUpResponse, int i, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = signUpResponse.userId;
        }
        if ((i2 & 2) != 0) {
            str = signUpResponse.token;
        }
        return signUpResponse.copy(i, str);
    }

    /* renamed from: component1, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    /* renamed from: component2, reason: from getter */
    public final String getToken() {
        return this.token;
    }

    public final SignUpResponse copy(int userId, String token) {
        return new SignUpResponse(userId, token);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SignUpResponse)) {
            return false;
        }
        SignUpResponse signUpResponse = (SignUpResponse) other;
        return this.userId == signUpResponse.userId && Intrinsics.areEqual(this.token, signUpResponse.token);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.userId) * 31;
        String str = this.token;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    @Override // com.thor.networkmodule.model.responses.BaseResponse
    public String toString() {
        return "SignUpResponse(userId=" + this.userId + ", token=" + this.token + ")";
    }

    public /* synthetic */ SignUpResponse(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : str);
    }

    public final int getUserId() {
        return this.userId;
    }

    public final void setUserId(int i) {
        this.userId = i;
    }

    public final String getToken() {
        return this.token;
    }

    public final void setToken(String str) {
        this.token = str;
    }

    public SignUpResponse(int i, String str) {
        this.userId = i;
        this.token = str;
    }
}
