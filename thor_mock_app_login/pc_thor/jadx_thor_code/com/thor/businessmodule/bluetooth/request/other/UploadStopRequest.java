package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleCommands;
import kotlin.Metadata;

/* compiled from: UploadStopRequest.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/UploadStopRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "()V", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadStopRequest extends BaseBleRequest {
    public UploadStopRequest() {
        setCommand(Short.valueOf(BleCommands.COMMAND_UPLOAD_STOP));
    }
}
