package com.thor.businessmodule.bluetooth.request.other;

import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* compiled from: BaseBleRequestOld.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\b\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\fH\u0004R\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequestOld;", "Lcom/thor/businessmodule/bluetooth/request/other/BleRequest;", BleCommandsWorker.INPUT_DATA_COMMAND, "", "(S)V", "()V", "getCommand", "()Ljava/lang/Short;", "setCommand", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "getBody", "", "getBytes", "cryptoMessage", "", "getCommandPart", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public class BaseBleRequestOld implements BleRequest {
    private Short command;

    @Override // com.thor.businessmodule.bluetooth.request.other.BleRequest
    public byte[] getBody() {
        return new byte[0];
    }

    public BaseBleRequestOld() {
    }

    public final Short getCommand() {
        return this.command;
    }

    public final void setCommand(Short sh) {
        this.command = sh;
    }

    public BaseBleRequestOld(short s) {
        this();
        this.command = Short.valueOf(s);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BleRequest
    public byte[] getBytes(boolean cryptoMessage) {
        ArrayList arrayList = new ArrayList();
        for (byte b : getCommandPart()) {
            arrayList.add(Byte.valueOf(b));
        }
        for (byte b2 : getBody()) {
            arrayList.add(Byte.valueOf(b2));
        }
        ArrayList arrayList2 = arrayList;
        for (byte b3 : BleHelper.createCheckSumPartOld(CollectionsKt.toByteArray(arrayList2))) {
            arrayList.add(Byte.valueOf(b3));
        }
        return CollectionsKt.toByteArray(arrayList2);
    }

    protected final byte[] getCommandPart() {
        return BleHelper.convertShortToByteArray(this.command);
    }
}
