package com.thor.networkmodule.model.responses;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;

/* compiled from: BaseResponse.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0010J\b\u0010\u0016\u001a\u00020\nH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lcom/thor/networkmodule/model/responses/BaseResponse;", "", "()V", "code", "", "getCode", "()I", "setCode", "(I)V", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "getError", "()Ljava/lang/String;", "setError", "(Ljava/lang/String;)V", NotificationCompat.CATEGORY_STATUS, "", "getStatus", "()Z", "setStatus", "(Z)V", "isSuccessful", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public class BaseResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("error_description")
    private String error;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private boolean status = true;

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

    public final boolean isSuccessful() {
        return this.status;
    }

    public String toString() {
        return "status: " + this.status + ", code: " + this.code + ", error: " + this.error;
    }
}
