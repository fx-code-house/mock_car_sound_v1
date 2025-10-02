package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: WriteDriveModesBleResponse.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016R*\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/WriteDriveModesBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "driveModes", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode;", "Lkotlin/collections/ArrayList;", "getDriveModes", "()Ljava/util/ArrayList;", "setDriveModes", "(Ljava/util/ArrayList;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteDriveModesBleResponse extends BaseBleResponse {
    private ArrayList<DriveMode> driveModes;

    public WriteDriveModesBleResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 53);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
        this.driveModes = new ArrayList<>();
    }

    public final ArrayList<DriveMode> getDriveModes() {
        return this.driveModes;
    }

    public final void setDriveModes(ArrayList<DriveMode> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.driveModes = arrayList;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        getBytes();
    }
}
