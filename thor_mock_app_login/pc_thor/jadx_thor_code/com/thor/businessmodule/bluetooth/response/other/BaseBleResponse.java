package com.thor.businessmodule.bluetooth.response.other;

import androidx.core.app.NotificationCompat;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.bus.events.BluetoothCommandErrorEvent;
import com.thor.businessmodule.bus.events.ResponseErrorCodeEvent;
import com.thor.businessmodule.crypto.EncryptionHelper;
import com.thor.businessmodule.crypto.EncryptionType;
import com.thor.businessmodule.crypto.EncryptionWithSize;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.UShort;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.ShortCompanionObject;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: BaseBleResponse.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u000b\b&\u0018\u0000 02\u00020\u0001:\u00010B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010$\u001a\u00020\u001fJ\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0003H\u0002J\b\u0010(\u001a\u00020&H\u0004J\b\u0010)\u001a\u00020&H&J\b\u0010*\u001a\u00020\u001fH\u0002J\b\u0010+\u001a\u00020\u001fH\u0002J\b\u0010,\u001a\u00020\u001fH\u0002J\u0006\u0010-\u001a\u00020\u001fJ\b\u0010.\u001a\u00020\u001fH\u0002J\b\u0010/\u001a\u00020\u001fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0016\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001b\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u00061"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "", "bytesInput", "", "([B)V", "bytes", "getBytes", "()[B", "setBytes", "getBytesInput", BleCommandsWorker.INPUT_DATA_COMMAND, "", "getCommand", "()S", "setCommand", "(S)V", "cryptoType", "Lcom/thor/businessmodule/crypto/EncryptionType;", "getCryptoType", "()Lcom/thor/businessmodule/crypto/EncryptionType;", "setCryptoType", "(Lcom/thor/businessmodule/crypto/EncryptionType;)V", "errorCode", "getErrorCode", "()Ljava/lang/Short;", "setErrorCode", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "errorCodeSize", "", NotificationCompat.CATEGORY_STATUS, "", "getStatus", "()Z", "setStatus", "(Z)V", "checkStatusResponse", "deCrypto", "", "message", "doHandleError", "doHandleResponse", "getMessageByteArray", "isError", "isErrorCode", "isSuccessful", "isTrash", "isUploadingCommand", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BaseBleResponse {
    private static final int PREAMBLE_TYPE_ENC_SIZE = 4;
    private byte[] bytes;
    private final byte[] bytesInput;
    private boolean status;
    private final int errorCodeSize = 6;
    private short command = 128;
    private EncryptionType cryptoType = EncryptionType.ENCRYPTION;
    private Short errorCode = Short.valueOf(ShortCompanionObject.MIN_VALUE);

    /* compiled from: BaseBleResponse.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EncryptionType.values().length];
            try {
                iArr[EncryptionType.ENCRYPTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public abstract void doHandleResponse();

    public boolean isUploadingCommand() {
        return false;
    }

    public BaseBleResponse(byte[] bArr) {
        this.bytesInput = bArr;
    }

    public final byte[] getBytesInput() {
        return this.bytesInput;
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

    public final EncryptionType getCryptoType() {
        return this.cryptoType;
    }

    public final void setCryptoType(EncryptionType encryptionType) {
        Intrinsics.checkNotNullParameter(encryptionType, "<set-?>");
        this.cryptoType = encryptionType;
    }

    public final Short getErrorCode() {
        return this.errorCode;
    }

    public final void setErrorCode(Short sh) {
        this.errorCode = sh;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final void setBytes(byte[] bArr) {
        this.bytes = bArr;
    }

    public final boolean checkStatusResponse() {
        byte[] bArr = this.bytesInput;
        if (bArr != null) {
            if (!(bArr.length == 0) && bArr.length >= 4) {
                short sTakeShort = BleHelper.takeShort(bArr[0], bArr[1]);
                Object[] objArr = new Object[2];
                objArr[0] = Short.valueOf(this.command);
                byte[] bArr2 = this.bytes;
                objArr[1] = bArr2 != null ? BleHelperKt.toHex(bArr2) : null;
                Timber.i("Commands: %s, %s", objArr);
                if (this.command != sTakeShort && isError() && this.bytesInput.length == this.errorCodeSize) {
                    doHandleError();
                    return false;
                }
                if (BleHelper.checkCrc(this.bytesInput)) {
                    return getMessageByteArray();
                }
            }
        }
        return false;
    }

    public final boolean isSuccessful() {
        return this.status;
    }

    private final boolean getMessageByteArray() {
        String hex;
        byte[] bArrCopyOfRange;
        String hex2;
        try {
            byte[] bArr = this.bytesInput;
            String str = "";
            if (bArr == null || (hex = BleHelperKt.toHex(bArr)) == null) {
                hex = "";
            }
            byte[] bArr2 = this.bytesInput;
            Intrinsics.checkNotNull(bArr2);
            Pair<EncryptionType, UShort> pairM606decodexj2QHRw = EncryptionWithSize.INSTANCE.m606decodexj2QHRw(BleHelper.m604takeUShortErzVvmY(bArr2[2], this.bytesInput[3]));
            byte[] bArrCopyOfRange2 = ArraysKt.copyOfRange(this.bytesInput, 4, (pairM606decodexj2QHRw.getSecond().getData() & 65535) + 4);
            this.cryptoType = pairM606decodexj2QHRw.getFirst();
            deCrypto(bArrCopyOfRange2);
            EventBus eventBus = EventBus.getDefault();
            byte[] bArr3 = this.bytes;
            if (bArr3 != null && (hex2 = BleHelperKt.toHex(bArr3)) != null) {
                str = hex2;
            }
            eventBus.post(new BleDataResponseLogEvent(hex, str));
            byte[] bArr4 = this.bytes;
            if (bArr4 != null && ArraysKt.first(bArr4) == Byte.MIN_VALUE) {
                byte[] bArr5 = this.bytes;
                if (bArr5 != null) {
                    Intrinsics.checkNotNull(bArr5);
                    bArrCopyOfRange = ArraysKt.copyOfRange(bArr5, 2, bArr5.length);
                } else {
                    bArrCopyOfRange = null;
                }
                if (bArrCopyOfRange != null) {
                    EventBus.getDefault().post(new ResponseErrorCodeEvent(bArrCopyOfRange));
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private final void deCrypto(byte[] message) {
        if (WhenMappings.$EnumSwitchMapping$0[this.cryptoType.ordinal()] == 1) {
            byte[] bArrCoreAesEncrypt = EncryptionHelper.INSTANCE.getCrypto().coreAesEncrypt(message);
            message = ArraysKt.copyOfRange(bArrCoreAesEncrypt, 1, bArrCoreAesEncrypt.length - bArrCoreAesEncrypt[0]);
        }
        this.bytes = message;
    }

    private final boolean isError() {
        byte[] bArr = this.bytesInput;
        if (bArr == null) {
            return true;
        }
        if ((bArr.length == 0) || bArr.length < 4) {
            return true;
        }
        return isErrorCode() && this.bytesInput.length == this.errorCodeSize;
    }

    private final boolean isErrorCode() {
        byte[] bArr = this.bytesInput;
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
        byte[] bArr = this.bytesInput;
        if (bArr != null) {
            return (bArr.length == 0) || bArr.length < 4 || BleHelper.takeShort((byte) 0, bArr[1]) != this.command;
        }
        return true;
    }

    protected final void doHandleError() {
        ArrayList arrayList = new ArrayList();
        byte[] bArr = this.bytesInput;
        Intrinsics.checkNotNull(bArr);
        arrayList.add(Byte.valueOf(bArr[2]));
        arrayList.add(Byte.valueOf(this.bytesInput[3]));
        EventBus.getDefault().post(new BluetoothCommandErrorEvent(CollectionsKt.toByteArray(arrayList), isTrash(), isUploadingCommand()));
    }
}
