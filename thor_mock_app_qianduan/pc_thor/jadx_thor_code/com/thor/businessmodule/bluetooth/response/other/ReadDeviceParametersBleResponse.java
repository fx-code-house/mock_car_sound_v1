package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.model.other.DeviceParameters;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import timber.log.Timber;

/* compiled from: ReadDeviceParametersBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/ReadDeviceParametersBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "deviceParameters", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters;", "getDeviceParameters", "()Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters;", "setDeviceParameters", "(Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters;)V", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ReadDeviceParametersBleResponse extends BaseBleResponse {
    private DeviceParameters deviceParameters;

    public ReadDeviceParametersBleResponse(byte[] bArr) {
        super(bArr);
        Timber.i("init", new Object[0]);
        setCommand((short) 9);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final DeviceParameters getDeviceParameters() {
        return this.deviceParameters;
    }

    public final void setDeviceParameters(DeviceParameters deviceParameters) {
        this.deviceParameters = deviceParameters;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            ArrayList arrayList = new ArrayList();
            short sTakeShort = BleHelper.takeShort(bytes[2], bytes[3]);
            int i = 1;
            if (1 <= sTakeShort) {
                while (true) {
                    int i2 = i * 4;
                    arrayList.add(new DeviceParameters.Parameter(BleHelper.takeShort(bytes[i2], bytes[i2 + 1]), BleHelper.takeShort(bytes[i2 + 2], bytes[i2 + 3])));
                    if (i == sTakeShort) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            this.deviceParameters = new DeviceParameters(arrayList);
        }
    }
}
