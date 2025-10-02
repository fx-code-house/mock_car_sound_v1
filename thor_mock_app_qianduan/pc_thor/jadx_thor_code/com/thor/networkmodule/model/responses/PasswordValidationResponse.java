package com.thor.networkmodule.model.responses;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PasswordValidationResponse.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0019\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\nJ\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0007HÆ\u0003J2\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001¢\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u00032\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0007HÖ\u0001R\"\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\"\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0016\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006 "}, d2 = {"Lcom/thor/networkmodule/model/responses/PasswordValidationResponse;", "", NotificationCompat.CATEGORY_STATUS, "", "code", "", "error_description", "", "(Ljava/lang/Boolean;Ljava/lang/Integer;Ljava/lang/String;)V", "getCode", "()Ljava/lang/Integer;", "setCode", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getError_description", "()Ljava/lang/String;", "setError_description", "(Ljava/lang/String;)V", "getStatus", "()Ljava/lang/Boolean;", "setStatus", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "component1", "component2", "component3", "copy", "(Ljava/lang/Boolean;Ljava/lang/Integer;Ljava/lang/String;)Lcom/thor/networkmodule/model/responses/PasswordValidationResponse;", "equals", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class PasswordValidationResponse {

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("error_description")
    @Expose
    private String error_description;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    @Expose
    private Boolean status;

    public PasswordValidationResponse() {
        this(null, null, null, 7, null);
    }

    public static /* synthetic */ PasswordValidationResponse copy$default(PasswordValidationResponse passwordValidationResponse, Boolean bool, Integer num, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            bool = passwordValidationResponse.status;
        }
        if ((i & 2) != 0) {
            num = passwordValidationResponse.code;
        }
        if ((i & 4) != 0) {
            str = passwordValidationResponse.error_description;
        }
        return passwordValidationResponse.copy(bool, num, str);
    }

    /* renamed from: component1, reason: from getter */
    public final Boolean getStatus() {
        return this.status;
    }

    /* renamed from: component2, reason: from getter */
    public final Integer getCode() {
        return this.code;
    }

    /* renamed from: component3, reason: from getter */
    public final String getError_description() {
        return this.error_description;
    }

    public final PasswordValidationResponse copy(Boolean status, Integer code, String error_description) {
        return new PasswordValidationResponse(status, code, error_description);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PasswordValidationResponse)) {
            return false;
        }
        PasswordValidationResponse passwordValidationResponse = (PasswordValidationResponse) other;
        return Intrinsics.areEqual(this.status, passwordValidationResponse.status) && Intrinsics.areEqual(this.code, passwordValidationResponse.code) && Intrinsics.areEqual(this.error_description, passwordValidationResponse.error_description);
    }

    public int hashCode() {
        Boolean bool = this.status;
        int iHashCode = (bool == null ? 0 : bool.hashCode()) * 31;
        Integer num = this.code;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.error_description;
        return iHashCode2 + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        return "PasswordValidationResponse(status=" + this.status + ", code=" + this.code + ", error_description=" + this.error_description + ")";
    }

    public PasswordValidationResponse(Boolean bool, Integer num, String str) {
        this.status = bool;
        this.code = num;
        this.error_description = str;
    }

    public /* synthetic */ PasswordValidationResponse(Boolean bool, Integer num, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bool, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : str);
    }

    public final Boolean getStatus() {
        return this.status;
    }

    public final void setStatus(Boolean bool) {
        this.status = bool;
    }

    public final Integer getCode() {
        return this.code;
    }

    public final void setCode(Integer num) {
        this.code = num;
    }

    public final String getError_description() {
        return this.error_description;
    }

    public final void setError_description(String str) {
        this.error_description = str;
    }
}
