package com.thor.businessmodule.bluetooth.response.other;

import androidx.core.app.NotificationCompat;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bus.events.BluetoothCommandErrorEvent;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.ShortCompanionObject;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: BaseBleResponseOld.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u001b\u001a\u00020\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0004J\b\u0010\u001e\u001a\u00020\u001dH&J\b\u0010\u001f\u001a\u00020\u0016H\u0002J\b\u0010 \u001a\u00020\u0016H\u0002J\u0006\u0010!\u001a\u00020\u0016J\b\u0010\"\u001a\u00020\u0016H\u0002J\b\u0010#\u001a\u00020\u0016H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006$"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponseOld;", "", "bytes", "", "([B)V", "getBytes", "()[B", BleCommandsWorker.INPUT_DATA_COMMAND, "", "getCommand", "()S", "setCommand", "(S)V", "errorCode", "getErrorCode", "()Ljava/lang/Short;", "setErrorCode", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "errorCodeSize", "", NotificationCompat.CATEGORY_STATUS, "", "getStatus", "()Z", "setStatus", "(Z)V", "checkStatusResponse", "doHandleError", "", "doHandleResponse", "isError", "isErrorCode", "isSuccessful", "isTrash", "isUploadingCommand", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BaseBleResponseOld {
    private final byte[] bytes;
    private boolean status;
    private final int errorCodeSize = 6;
    private short command = 128;
    private Short errorCode = Short.valueOf(ShortCompanionObject.MIN_VALUE);

    public abstract void doHandleResponse();

    public boolean isUploadingCommand() {
        return false;
    }

    public BaseBleResponseOld(byte[] bArr) {
        this.bytes = bArr;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final void setStatus(boolean z) {
        this.status = z;
    }

    public final short getCommand() {
        return this.command;
    }

    public final void setCommand(short s) {
        this.command = s;
    }

    public final Short getErrorCode() {
        return this.errorCode;
    }

    public final void setErrorCode(Short sh) {
        this.errorCode = sh;
    }

    public final boolean checkStatusResponse() {
        byte[] bArr = this.bytes;
        if (bArr != null) {
            if (!(bArr.length == 0) && bArr.length >= 4) {
                short sTakeShort = BleHelper.takeShort(bArr[0], bArr[1]);
                Timber.i("Commands: %s, %s", Short.valueOf(this.command), Short.valueOf(sTakeShort));
                if (this.command != sTakeShort && isError() && this.bytes.length == this.errorCodeSize) {
                    doHandleError();
                    return false;
                }
                return BleHelper.INSTANCE.checkCrcOld(this.bytes);
            }
        }
        return false;
    }

    public final boolean isSuccessful() {
        return this.status;
    }

    private final boolean isError() {
        byte[] bArr = this.bytes;
        if (bArr == null) {
            return true;
        }
        if ((bArr.length == 0) || bArr.length < 4) {
            return true;
        }
        return isErrorCode() && this.bytes.length == this.errorCodeSize;
    }

    private final boolean isErrorCode() {
        byte[] bArr = this.bytes;
        if (bArr == null) {
            return true;
        }
        if ((bArr.length == 0) || bArr.length < 4) {
            return true;
        }
        short sTakeShort = BleHelper.takeShort(bArr[0], (byte) 0);
        Short sh = this.errorCode;
        return sh != null && sTakeShort == sh.shortValue();
    }

    private final boolean isTrash() {
        byte[] bArr = this.bytes;
        if (bArr != null) {
            return (bArr.length == 0) || bArr.length < 4 || BleHelper.takeShort((byte) 0, bArr[1]) != this.command;
        }
        return true;
    }

    protected final void doHandleError() {
        ArrayList arrayList = new ArrayList();
        byte[] bArr = this.bytes;
        Intrinsics.checkNotNull(bArr);
        arrayList.add(Byte.valueOf(bArr[2]));
        arrayList.add(Byte.valueOf(this.bytes[3]));
        EventBus.getDefault().post(new BluetoothCommandErrorEvent(CollectionsKt.toByteArray(arrayList), isTrash(), isUploadingCommand()));
    }
}
