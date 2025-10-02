package com.thor.networkmodule.network;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.messaging.Constants;
import kotlin.Metadata;

/* compiled from: OnLoadDataListener.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u0004H&¨\u0006\u000b"}, d2 = {"Lcom/thor/networkmodule/network/OnLoadDataListener;", ExifInterface.GPS_DIRECTION_TRUE, "", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "value", "(Ljava/lang/Object;)V", "onLoadData", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface OnLoadDataListener<T> {
    void handleError(Throwable error);

    void handleResponse(T value);

    void onLoadData();
}
