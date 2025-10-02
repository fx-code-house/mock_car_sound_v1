package com.thor.app.service;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.crypto.EncryptionError;
import com.thor.businessmodule.crypto.EncryptionHelper;
import com.thor.businessmodule.crypto.EncryptionType;
import com.thor.businessmodule.crypto.EncryptionWithSize;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.UShort;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BleParseResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0010\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\b\u0010\u0017\u001a\u00020\u0003H\u0002J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lcom/thor/app/service/BleParseResponse;", "", "inDataBytes", "", "checkCrc", "", "([BZ)V", "cryptoType", "Lcom/thor/businessmodule/crypto/EncryptionType;", "inputCommand", "", "getInputCommand", "()Ljava/lang/Short;", "setInputCommand", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "resultBytes", "getResultBytes", "()[B", "setResultBytes", "([B)V", "deCrypto", "message", "getMessageByteArray", "parseResponse", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BleParseResponse {
    private static final int PREAMBLE_TYPE_ENC_SIZE = 4;
    private final boolean checkCrc;
    private EncryptionType cryptoType;
    private final byte[] inDataBytes;
    private Short inputCommand;
    private byte[] resultBytes;

    /* compiled from: BleParseResponse.kt */
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

    public BleParseResponse(byte[] inDataBytes, boolean z) {
        Intrinsics.checkNotNullParameter(inDataBytes, "inDataBytes");
        this.inDataBytes = inDataBytes;
        this.checkCrc = z;
        this.cryptoType = EncryptionType.NONE;
        this.resultBytes = parseResponse(z);
    }

    public /* synthetic */ BleParseResponse(byte[] bArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bArr, (i & 2) != 0 ? true : z);
    }

    public final Short getInputCommand() {
        return this.inputCommand;
    }

    public final void setInputCommand(Short sh) {
        this.inputCommand = sh;
    }

    public final byte[] getResultBytes() {
        return this.resultBytes;
    }

    public final void setResultBytes(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.resultBytes = bArr;
    }

    private final byte[] parseResponse(boolean checkCrc) throws Exception {
        byte[] bArr = this.inDataBytes;
        if ((bArr.length == 0) || bArr.length < 4) {
            throw new Exception("Invalid data");
        }
        return getMessageByteArray();
    }

    private final byte[] getMessageByteArray() throws EncryptionError {
        byte[] bArr = this.inDataBytes;
        Pair<EncryptionType, UShort> pairM606decodexj2QHRw = EncryptionWithSize.INSTANCE.m606decodexj2QHRw(BleHelper.m604takeUShortErzVvmY(bArr[2], bArr[3]));
        byte[] bArrCopyOfRange = ArraysKt.copyOfRange(this.inDataBytes, 4, (pairM606decodexj2QHRw.getSecond().getData() & 65535) + 4);
        this.cryptoType = pairM606decodexj2QHRw.getFirst();
        byte[] bArrDeCrypto = deCrypto(bArrCopyOfRange);
        this.inputCommand = Short.valueOf(BleHelper.takeShort(ArraysKt.copyOfRange(bArrDeCrypto, 0, 2)));
        return bArrDeCrypto;
    }

    private final byte[] deCrypto(byte[] message) {
        if (WhenMappings.$EnumSwitchMapping$0[this.cryptoType.ordinal()] != 1) {
            return message;
        }
        byte[] bArrCoreAesEncrypt = EncryptionHelper.INSTANCE.getCrypto().coreAesEncrypt(message);
        return ArraysKt.copyOfRange(bArrCoreAesEncrypt, 1, bArrCoreAesEncrypt.length - bArrCoreAesEncrypt[0]);
    }
}
