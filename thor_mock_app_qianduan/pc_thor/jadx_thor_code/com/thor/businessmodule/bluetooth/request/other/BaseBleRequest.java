package com.thor.businessmodule.bluetooth.request.other;

import androidx.exifinterface.media.ExifInterface;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.crypto.EncryptionHelper;
import com.thor.businessmodule.crypto.EncryptionType;
import com.thor.businessmodule.crypto.EncryptionWithSize;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.UShort;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;

/* compiled from: BaseBleRequest.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\b\u0010\u001b\u001a\u00020\u0012H\u0016J\u0010\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0006\u0010\u001f\u001a\u00020\u0012J\b\u0010 \u001a\u00020\u0012H\u0004J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0002R\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0019\u0010\u0017\u001a\u00020\u0018X\u0082Dø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\n\u0002\u0010\u0019\u0082\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006$"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BleRequest;", BleCommandsWorker.INPUT_DATA_COMMAND, "", "(S)V", "()V", "getCommand", "()Ljava/lang/Short;", "setCommand", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "cryptoType", "Lcom/thor/businessmodule/crypto/EncryptionType;", "getCryptoType", "()Lcom/thor/businessmodule/crypto/EncryptionType;", "setCryptoType", "(Lcom/thor/businessmodule/crypto/EncryptionType;)V", "deсryptoMessage", "", "getDeсryptoMessage", "()[B", "setDeсryptoMessage", "([B)V", "preamble", "Lkotlin/UShort;", ExifInterface.LATITUDE_SOUTH, "generateData", "getBody", "getBytes", "cryptoMessage", "", "getBytesOld", "getCommandPart", "getPadByteCount", "", "messageLength", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public class BaseBleRequest implements BleRequest {
    private Short command;
    private EncryptionType cryptoType;
    private byte[] deсryptoMessage;
    private final short preamble;

    /* compiled from: BaseBleRequest.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EncryptionType.values().length];
            try {
                iArr[EncryptionType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EncryptionType.HANDSHAKE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EncryptionType.ENCRYPTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BleRequest
    public byte[] getBody() {
        return new byte[0];
    }

    public BaseBleRequest() {
        this.cryptoType = EncryptionType.ENCRYPTION;
        this.preamble = BleCommands.PREAMBLE;
    }

    public final Short getCommand() {
        return this.command;
    }

    public final void setCommand(Short sh) {
        this.command = sh;
    }

    public final EncryptionType getCryptoType() {
        return this.cryptoType;
    }

    public final void setCryptoType(EncryptionType encryptionType) {
        Intrinsics.checkNotNullParameter(encryptionType, "<set-?>");
        this.cryptoType = encryptionType;
    }

    public final byte[] getDeсryptoMessage() {
        return this.deсryptoMessage;
    }

    /* renamed from: setDeсryptoMessage, reason: contains not printable characters */
    public final void m602setDeryptoMessage(byte[] bArr) {
        this.deсryptoMessage = bArr;
    }

    public BaseBleRequest(short s) {
        this();
        this.command = Short.valueOf(s);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BleRequest
    public byte[] getBytes(boolean cryptoMessage) {
        try {
            if (!cryptoMessage) {
                return new byte[0];
            }
            byte[] bArrM603convertUShortByteArrayxj2QHRw = BleHelper.m603convertUShortByteArrayxj2QHRw(this.preamble);
            byte[] bArrGenerateData = generateData();
            byte[] bArrPlus = ArraysKt.plus(ArraysKt.plus(bArrM603convertUShortByteArrayxj2QHRw, BleHelper.m603convertUShortByteArrayxj2QHRw(EncryptionWithSize.INSTANCE.m607encodeKH5fvcE(this.cryptoType, UShort.m905constructorimpl((short) bArrGenerateData.length)))), bArrGenerateData);
            return ArraysKt.plus(bArrPlus, BleHelper.createCheckSumPart(bArrPlus));
        } catch (Exception unused) {
            return new byte[0];
        }
    }

    public final byte[] getBytesOld() {
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

    private final byte[] generateData() {
        String hex;
        ArrayList arrayList = new ArrayList();
        int i = WhenMappings.$EnumSwitchMapping$0[this.cryptoType.ordinal()];
        int i2 = 0;
        if (i == 1) {
            this.deсryptoMessage = ArraysKt.plus(getCommandPart(), getBody());
            for (byte b : getCommandPart()) {
                arrayList.add(Byte.valueOf(b));
            }
            byte[] body = getBody();
            int length = body.length;
            while (i2 < length) {
                arrayList.add(Byte.valueOf(body[i2]));
                i2++;
            }
            EventBus eventBus = EventBus.getDefault();
            byte[] bArr = this.deсryptoMessage;
            if (bArr == null || (hex = BleHelperKt.toHex(bArr)) == null) {
                hex = "";
            }
            eventBus.post(new BleDataRequestLogEvent(hex, BleHelperKt.toHex(CollectionsKt.toByteArray(arrayList))));
        } else if (i == 2) {
            byte[] body2 = getBody();
            int length2 = body2.length;
            while (i2 < length2) {
                arrayList.add(Byte.valueOf(body2[i2]));
                i2++;
            }
        } else if (i == 3) {
            ArrayList arrayList2 = new ArrayList();
            byte[] bArrPlus = ArraysKt.plus(getCommandPart(), getBody());
            this.deсryptoMessage = bArrPlus;
            int padByteCount = getPadByteCount(bArrPlus.length + 1);
            for (byte b2 : BleHelperKt.toByteArrayOneBit(padByteCount)) {
                arrayList2.add(Byte.valueOf(b2));
            }
            for (byte b3 : bArrPlus) {
                arrayList2.add(Byte.valueOf(b3));
            }
            for (int i3 = 0; i3 < padByteCount; i3++) {
                arrayList2.add((byte) -91);
            }
            byte[] bArrCoreAesEncrypt = EncryptionHelper.INSTANCE.getCrypto().coreAesEncrypt(CollectionsKt.toByteArray(arrayList2));
            int length3 = bArrCoreAesEncrypt.length;
            while (i2 < length3) {
                arrayList.add(Byte.valueOf(bArrCoreAesEncrypt[i2]));
                i2++;
            }
            EventBus.getDefault().post(new BleDataRequestLogEvent(BleHelperKt.toHex(bArrPlus), BleHelperKt.toHex(bArrCoreAesEncrypt)));
        }
        return CollectionsKt.toByteArray(arrayList);
    }

    protected final byte[] getCommandPart() {
        return BleHelper.convertShortToByteArray(this.command);
    }

    private final int getPadByteCount(int messageLength) {
        return 16 - (messageLength % 16);
    }
}
