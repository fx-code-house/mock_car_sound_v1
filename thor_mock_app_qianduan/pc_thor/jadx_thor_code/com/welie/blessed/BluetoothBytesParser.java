package com.welie.blessed;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;

/* compiled from: BluetoothBytesParser.kt */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0005\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 F2\u00020\u0001:\u0001FB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007B!\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0002J(\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020%2\u0006\u0010(\u001a\u00020%H\u0002J\u000e\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\tJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020\tJ\u0016\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003J\u001e\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003J\u000e\u0010-\u001a\u00020\t2\u0006\u0010,\u001a\u00020\tJ\u0016\u0010-\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003J\u001e\u0010-\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u0003J\u0016\u0010\u0014\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003J\u000e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010.\u001a\u00020\t2\u0006\u0010,\u001a\u00020\tH\u0002J\u0018\u0010/\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0002J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\tH\u0002J\u000e\u00104\u001a\u0002052\u0006\u00106\u001a\u000207J\u000e\u00108\u001a\u0002052\u0006\u00106\u001a\u000207J\u0016\u00109\u001a\u0002052\u0006\u0010\u0005\u001a\u00020#2\u0006\u0010:\u001a\u00020\tJ&\u00109\u001a\u0002052\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010=\u001a\u0002052\u0006\u0010\u0005\u001a\u00020\t2\u0006\u0010,\u001a\u00020\tJ\u001e\u0010=\u001a\u0002052\u0006\u0010\u0005\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010>\u001a\u0002052\u0006\u0010\u0005\u001a\u00020\u0013J\u0016\u0010>\u001a\u0002052\u0006\u0010\u0005\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010?\u001a\u0002052\b\u0010\u0005\u001a\u0004\u0018\u00010\u001bJ\u0018\u0010?\u001a\u0002052\b\u0010\u0005\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\b\u001a\u00020\tJ\b\u0010@\u001a\u00020\u001bH\u0016J\u0010\u0010A\u001a\u00020\t2\u0006\u0010B\u001a\u00020%H\u0002J\u0018\u0010C\u001a\u00020\t2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0002J(\u0010C\u001a\u00020\t2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020%2\u0006\u0010(\u001a\u00020%H\u0002J\u0018\u0010D\u001a\u00020\t2\u0006\u0010E\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!¨\u0006G"}, d2 = {"Lcom/welie/blessed/BluetoothBytesParser;", "", "byteOrder", "Ljava/nio/ByteOrder;", "(Ljava/nio/ByteOrder;)V", "value", "", "([BLjava/nio/ByteOrder;)V", "offset", "", "([BILjava/nio/ByteOrder;)V", "getByteOrder", "()Ljava/nio/ByteOrder;", "setByteOrder", "dateTime", "Ljava/util/Date;", "getDateTime", "()Ljava/util/Date;", "longValue", "", "getLongValue", "()J", "getOffset", "()I", "setOffset", "(I)V", "stringValue", "", "getStringValue", "()Ljava/lang/String;", "getValue", "()[B", "setValue", "([B)V", "bytesToFloat", "", "b0", "", "b1", "b2", "b3", "getByteArray", SessionDescription.ATTR_LENGTH, "getFloatValue", "formatType", "getIntValue", "getTypeLen", "intToSignedBits", "size", "prepareArray", "", "neededLength", "setCurrentTime", "", "calendar", "Ljava/util/Calendar;", "setDateTime", "setFloatValue", "precision", "mantissa", "exponent", "setIntValue", "setLong", "setString", "toString", "unsignedByteToInt", "b", "unsignedBytesToInt", "unsignedToSigned", "unsignedValue", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothBytesParser {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int FORMAT_FLOAT = 52;
    public static final int FORMAT_SFLOAT = 50;
    public static final int FORMAT_SINT16 = 34;
    public static final int FORMAT_SINT24 = 35;
    public static final int FORMAT_SINT32 = 36;
    public static final int FORMAT_SINT8 = 33;
    public static final int FORMAT_UINT16 = 18;
    public static final int FORMAT_UINT24 = 19;
    public static final int FORMAT_UINT32 = 20;
    public static final int FORMAT_UINT8 = 17;
    private ByteOrder byteOrder;
    private int offset;
    private byte[] value;

    private final int getTypeLen(int formatType) {
        return formatType & 15;
    }

    private final int intToSignedBits(int value, int size) {
        if (value >= 0) {
            return value;
        }
        int i = 1 << (size - 1);
        return (value & (i - 1)) + i;
    }

    private final int unsignedByteToInt(byte b) {
        return b & 255;
    }

    private final int unsignedToSigned(int unsignedValue, int size) {
        int i = 1 << (size - 1);
        return (unsignedValue & i) != 0 ? (i - (unsignedValue & (i - 1))) * (-1) : unsignedValue;
    }

    public BluetoothBytesParser(byte[] value, int i, ByteOrder byteOrder) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        this.value = value;
        this.offset = i;
        this.byteOrder = byteOrder;
    }

    public final byte[] getValue() {
        return this.value;
    }

    public final void setValue(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.value = bArr;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final void setOffset(int i) {
        this.offset = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ BluetoothBytesParser(byte[] bArr, int i, ByteOrder LITTLE_ENDIAN, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        i = (i2 & 2) != 0 ? 0 : i;
        if ((i2 & 4) != 0) {
            LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
            Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN, "LITTLE_ENDIAN");
        }
        this(bArr, i, LITTLE_ENDIAN);
    }

    public final ByteOrder getByteOrder() {
        return this.byteOrder;
    }

    public final void setByteOrder(ByteOrder byteOrder) {
        Intrinsics.checkNotNullParameter(byteOrder, "<set-?>");
        this.byteOrder = byteOrder;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BluetoothBytesParser(ByteOrder byteOrder) {
        this(new byte[0], 0, byteOrder);
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BluetoothBytesParser(byte[] value, ByteOrder byteOrder) {
        this(value, 0, byteOrder);
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
    }

    public final int getIntValue(int formatType) {
        int intValue = getIntValue(formatType, this.offset, this.byteOrder);
        this.offset += getTypeLen(formatType);
        return intValue;
    }

    public final int getIntValue(int formatType, ByteOrder byteOrder) {
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        int intValue = getIntValue(formatType, this.offset, byteOrder);
        this.offset += getTypeLen(formatType);
        return intValue;
    }

    public final long getLongValue() {
        return getLongValue(this.byteOrder);
    }

    public final long getLongValue(ByteOrder byteOrder) {
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        long longValue = getLongValue(this.offset, byteOrder);
        this.offset += 8;
        return longValue;
    }

    public final long getLongValue(int offset, ByteOrder byteOrder) {
        long j;
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
            j = this.value[offset + 7] & 255;
            for (int i = 6; -1 < i; i--) {
                j = (j << 8) + (this.value[i + offset] & 255);
            }
        } else {
            j = this.value[offset] & 255;
            for (int i2 = 1; i2 < 8; i2++) {
                j = (j << 8) + (this.value[i2 + offset] & 255);
            }
        }
        return j;
    }

    public final int getIntValue(int formatType, int offset, ByteOrder byteOrder) {
        byte b;
        byte b2;
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        int typeLen = getTypeLen(formatType) + offset;
        byte[] bArr = this.value;
        if (!(typeLen <= bArr.length)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        switch (formatType) {
            case 17:
                return unsignedByteToInt(bArr[offset]);
            case 18:
                if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr2 = this.value;
                    b = bArr2[offset];
                    b2 = bArr2[offset + 1];
                } else {
                    byte[] bArr3 = this.value;
                    b = bArr3[offset + 1];
                    b2 = bArr3[offset];
                }
                return unsignedBytesToInt(b, b2);
            case 19:
                if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr4 = this.value;
                    return unsignedBytesToInt(bArr4[offset], bArr4[offset + 1], bArr4[offset + 2], (byte) 0);
                }
                byte[] bArr5 = this.value;
                return unsignedBytesToInt(bArr5[offset + 2], bArr5[offset + 1], bArr5[offset], (byte) 0);
            case 20:
                if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr6 = this.value;
                    return unsignedBytesToInt(bArr6[offset], bArr6[offset + 1], bArr6[offset + 2], bArr6[offset + 3]);
                }
                byte[] bArr7 = this.value;
                return unsignedBytesToInt(bArr7[offset + 3], bArr7[offset + 2], bArr7[offset + 1], bArr7[offset]);
            default:
                switch (formatType) {
                    case 33:
                        return unsignedToSigned(unsignedByteToInt(bArr[offset]), 8);
                    case 34:
                        if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                            byte[] bArr8 = this.value;
                            return unsignedToSigned(unsignedBytesToInt(bArr8[offset], bArr8[offset + 1]), 16);
                        }
                        byte[] bArr9 = this.value;
                        return unsignedToSigned(unsignedBytesToInt(bArr9[offset + 1], bArr9[offset]), 16);
                    case 35:
                        if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                            byte[] bArr10 = this.value;
                            return unsignedToSigned(unsignedBytesToInt(bArr10[offset], bArr10[offset + 1], bArr10[offset + 2], (byte) 0), 24);
                        }
                        byte[] bArr11 = this.value;
                        return unsignedToSigned(unsignedBytesToInt(bArr11[offset + 2], bArr11[offset + 1], bArr11[offset], (byte) 0), 24);
                    case 36:
                        if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                            byte[] bArr12 = this.value;
                            return unsignedToSigned(unsignedBytesToInt(bArr12[offset], bArr12[offset + 1], bArr12[offset + 2], bArr12[offset + 3]), 32);
                        }
                        byte[] bArr13 = this.value;
                        return unsignedToSigned(unsignedBytesToInt(bArr13[offset + 3], bArr13[offset + 2], bArr13[offset + 1], bArr13[offset]), 32);
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }

    public final float getFloatValue(int formatType) {
        float floatValue = getFloatValue(formatType, this.offset, this.byteOrder);
        this.offset += getTypeLen(formatType);
        return floatValue;
    }

    public final float getFloatValue(int formatType, ByteOrder byteOrder) {
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        float floatValue = getFloatValue(formatType, this.offset, byteOrder);
        this.offset += getTypeLen(formatType);
        return floatValue;
    }

    public final float getFloatValue(int formatType, int offset, ByteOrder byteOrder) {
        byte b;
        byte b2;
        Intrinsics.checkNotNullParameter(byteOrder, "byteOrder");
        if (!(getTypeLen(formatType) + offset <= this.value.length)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (formatType != 50) {
            if (formatType == 52) {
                if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr = this.value;
                    return bytesToFloat(bArr[offset], bArr[offset + 1], bArr[offset + 2], bArr[offset + 3]);
                }
                byte[] bArr2 = this.value;
                return bytesToFloat(bArr2[offset + 3], bArr2[offset + 2], bArr2[offset + 1], bArr2[offset]);
            }
            throw new IllegalArgumentException();
        }
        if (Intrinsics.areEqual(byteOrder, ByteOrder.LITTLE_ENDIAN)) {
            byte[] bArr3 = this.value;
            b = bArr3[offset];
            b2 = bArr3[offset + 1];
        } else {
            byte[] bArr4 = this.value;
            b = bArr4[offset + 1];
            b2 = bArr4[offset];
        }
        return bytesToFloat(b, b2);
    }

    public final String getStringValue() {
        return getStringValue(this.offset);
    }

    public final String getStringValue(int offset) {
        byte[] bArr = this.value;
        if (!(offset < bArr.length)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        int length = bArr.length - offset;
        byte[] bArr2 = new byte[length];
        int length2 = bArr.length - offset;
        for (int i = 0; i < length2; i++) {
            bArr2[i] = this.value[offset + i];
        }
        while (length > 0) {
            byte b = bArr2[length - 1];
            if (b != 0 && b != 32) {
                break;
            }
            length--;
        }
        Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
        Intrinsics.checkNotNullExpressionValue(ISO_8859_1, "ISO_8859_1");
        return new String(bArr2, 0, length, ISO_8859_1);
    }

    public final Date getDateTime() {
        Date dateTime = getDateTime(this.offset);
        this.offset += 7;
        return dateTime;
    }

    public final Date getDateTime(int offset) {
        ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN, "LITTLE_ENDIAN");
        int intValue = getIntValue(18, offset, LITTLE_ENDIAN);
        int typeLen = offset + getTypeLen(18);
        ByteOrder LITTLE_ENDIAN2 = ByteOrder.LITTLE_ENDIAN;
        Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN2, "LITTLE_ENDIAN");
        int intValue2 = getIntValue(17, typeLen, LITTLE_ENDIAN2);
        int typeLen2 = typeLen + getTypeLen(17);
        ByteOrder LITTLE_ENDIAN3 = ByteOrder.LITTLE_ENDIAN;
        Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN3, "LITTLE_ENDIAN");
        int intValue3 = getIntValue(17, typeLen2, LITTLE_ENDIAN3);
        int typeLen3 = typeLen2 + getTypeLen(17);
        ByteOrder LITTLE_ENDIAN4 = ByteOrder.LITTLE_ENDIAN;
        Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN4, "LITTLE_ENDIAN");
        int intValue4 = getIntValue(17, typeLen3, LITTLE_ENDIAN4);
        int typeLen4 = typeLen3 + getTypeLen(17);
        ByteOrder LITTLE_ENDIAN5 = ByteOrder.LITTLE_ENDIAN;
        Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN5, "LITTLE_ENDIAN");
        int intValue5 = getIntValue(17, typeLen4, LITTLE_ENDIAN5);
        int typeLen5 = typeLen4 + getTypeLen(17);
        ByteOrder LITTLE_ENDIAN6 = ByteOrder.LITTLE_ENDIAN;
        Intrinsics.checkNotNullExpressionValue(LITTLE_ENDIAN6, "LITTLE_ENDIAN");
        Date time = new GregorianCalendar(intValue, intValue2 - 1, intValue3, intValue4, intValue5, getIntValue(17, typeLen5, LITTLE_ENDIAN6)).getTime();
        Intrinsics.checkNotNullExpressionValue(time, "calendar.time");
        return time;
    }

    public final byte[] getByteArray(int length) {
        byte[] bArr = this.value;
        int i = this.offset;
        byte[] array = Arrays.copyOfRange(bArr, i, i + length);
        this.offset += length;
        Intrinsics.checkNotNullExpressionValue(array, "array");
        return array;
    }

    public final boolean setIntValue(int value, int formatType, int offset) {
        prepareArray(getTypeLen(formatType) + offset);
        switch (formatType) {
            case 17:
                this.value[offset] = (byte) (value & 255);
                return true;
            case 18:
                if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr = this.value;
                    bArr[offset] = (byte) (value & 255);
                    bArr[offset + 1] = (byte) ((value >> 8) & 255);
                    return true;
                }
                byte[] bArr2 = this.value;
                bArr2[offset] = (byte) ((value >> 8) & 255);
                bArr2[offset + 1] = (byte) (value & 255);
                return true;
            case 19:
                if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr3 = this.value;
                    int i = offset + 1;
                    bArr3[offset] = (byte) (value & 255);
                    bArr3[i] = (byte) ((value >> 8) & 255);
                    bArr3[i + 1] = (byte) ((value >> 16) & 255);
                    return true;
                }
                byte[] bArr4 = this.value;
                int i2 = offset + 1;
                bArr4[offset] = (byte) ((value >> 16) & 255);
                bArr4[i2] = (byte) ((value >> 8) & 255);
                bArr4[i2 + 1] = (byte) (value & 255);
                return true;
            case 20:
                if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                    byte[] bArr5 = this.value;
                    int i3 = offset + 1;
                    bArr5[offset] = (byte) (value & 255);
                    int i4 = i3 + 1;
                    bArr5[i3] = (byte) ((value >> 8) & 255);
                    bArr5[i4] = (byte) ((value >> 16) & 255);
                    bArr5[i4 + 1] = (byte) ((value >> 24) & 255);
                    return true;
                }
                byte[] bArr6 = this.value;
                int i5 = offset + 1;
                bArr6[offset] = (byte) ((value >> 24) & 255);
                int i6 = i5 + 1;
                bArr6[i5] = (byte) ((value >> 16) & 255);
                bArr6[i6] = (byte) ((value >> 8) & 255);
                bArr6[i6 + 1] = (byte) (value & 255);
                return true;
            default:
                switch (formatType) {
                    case 33:
                        this.value[offset] = (byte) (intToSignedBits(value, 8) & 255);
                        return true;
                    case 34:
                        int iIntToSignedBits = intToSignedBits(value, 16);
                        if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                            byte[] bArr7 = this.value;
                            bArr7[offset] = (byte) (iIntToSignedBits & 255);
                            bArr7[offset + 1] = (byte) ((iIntToSignedBits >> 8) & 255);
                            return true;
                        }
                        byte[] bArr8 = this.value;
                        bArr8[offset] = (byte) ((iIntToSignedBits >> 8) & 255);
                        bArr8[offset + 1] = (byte) (iIntToSignedBits & 255);
                        return true;
                    case 35:
                        int iIntToSignedBits2 = intToSignedBits(value, 24);
                        if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                            byte[] bArr9 = this.value;
                            int i7 = offset + 1;
                            bArr9[offset] = (byte) (iIntToSignedBits2 & 255);
                            bArr9[i7] = (byte) ((iIntToSignedBits2 >> 8) & 255);
                            bArr9[i7 + 1] = (byte) ((iIntToSignedBits2 >> 16) & 255);
                            return true;
                        }
                        byte[] bArr10 = this.value;
                        int i8 = offset + 1;
                        bArr10[offset] = (byte) ((iIntToSignedBits2 >> 16) & 255);
                        bArr10[i8] = (byte) ((iIntToSignedBits2 >> 8) & 255);
                        bArr10[i8 + 1] = (byte) (iIntToSignedBits2 & 255);
                        return true;
                    case 36:
                        int iIntToSignedBits3 = intToSignedBits(value, 32);
                        if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                            byte[] bArr11 = this.value;
                            int i9 = offset + 1;
                            bArr11[offset] = (byte) (iIntToSignedBits3 & 255);
                            int i10 = i9 + 1;
                            bArr11[i9] = (byte) ((iIntToSignedBits3 >> 8) & 255);
                            bArr11[i10] = (byte) ((iIntToSignedBits3 >> 16) & 255);
                            bArr11[i10 + 1] = (byte) ((iIntToSignedBits3 >> 24) & 255);
                            return true;
                        }
                        byte[] bArr12 = this.value;
                        int i11 = offset + 1;
                        bArr12[offset] = (byte) ((iIntToSignedBits3 >> 24) & 255);
                        int i12 = i11 + 1;
                        bArr12[i11] = (byte) ((iIntToSignedBits3 >> 16) & 255);
                        bArr12[i12] = (byte) ((iIntToSignedBits3 >> 8) & 255);
                        bArr12[i12 + 1] = (byte) (iIntToSignedBits3 & 255);
                        return true;
                    default:
                        return false;
                }
        }
    }

    public final boolean setIntValue(int value, int formatType) {
        boolean intValue = setIntValue(value, formatType, this.offset);
        if (intValue) {
            this.offset += getTypeLen(formatType);
        }
        return intValue;
    }

    public final boolean setLong(long value) {
        return setLong(value, this.offset);
    }

    public final boolean setLong(long value, int offset) {
        prepareArray(offset + 8);
        if (Intrinsics.areEqual(this.byteOrder, ByteOrder.BIG_ENDIAN)) {
            for (int i = 7; -1 < i; i--) {
                this.value[i + offset] = (byte) (value & 255);
                value >>= 8;
            }
            return true;
        }
        for (int i2 = 0; i2 < 8; i2++) {
            this.value[i2 + offset] = (byte) (value & 255);
            value >>= 8;
        }
        return true;
    }

    public final boolean setFloatValue(int mantissa, int exponent, int formatType, int offset) {
        prepareArray(getTypeLen(formatType) + offset);
        if (formatType == 50) {
            int iIntToSignedBits = intToSignedBits(mantissa, 12);
            int iIntToSignedBits2 = intToSignedBits(exponent, 4);
            if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                byte[] bArr = this.value;
                int i = offset + 1;
                bArr[offset] = (byte) (iIntToSignedBits & 255);
                byte b = (byte) ((iIntToSignedBits >> 8) & 15);
                bArr[i] = b;
                bArr[i] = (byte) (b + ((iIntToSignedBits2 & 15) << 4));
            } else {
                byte[] bArr2 = this.value;
                bArr2[offset] = (byte) ((iIntToSignedBits >> 8) & 15);
                int i2 = offset + 1;
                bArr2[offset] = (byte) (bArr2[i2] + ((iIntToSignedBits2 & 15) << 4));
                bArr2[i2] = (byte) (iIntToSignedBits & 255);
            }
        } else {
            if (formatType != 52) {
                return false;
            }
            int iIntToSignedBits3 = intToSignedBits(mantissa, 24);
            int iIntToSignedBits4 = intToSignedBits(exponent, 8);
            if (Intrinsics.areEqual(this.byteOrder, ByteOrder.LITTLE_ENDIAN)) {
                byte[] bArr3 = this.value;
                int i3 = offset + 1;
                bArr3[offset] = (byte) (iIntToSignedBits3 & 255);
                int i4 = i3 + 1;
                bArr3[i3] = (byte) ((iIntToSignedBits3 >> 8) & 255);
                int i5 = i4 + 1;
                bArr3[i4] = (byte) ((iIntToSignedBits3 >> 16) & 255);
                bArr3[i5] = (byte) (bArr3[i5] + ((byte) (iIntToSignedBits4 & 255)));
            } else {
                byte[] bArr4 = this.value;
                int i6 = offset + 1;
                bArr4[offset] = (byte) (bArr4[offset] + (iIntToSignedBits4 & 255));
                int i7 = i6 + 1;
                bArr4[i6] = (byte) ((iIntToSignedBits3 >> 16) & 255);
                bArr4[i7] = (byte) ((iIntToSignedBits3 >> 8) & 255);
                bArr4[i7 + 1] = (byte) (iIntToSignedBits3 & 255);
            }
        }
        this.offset += getTypeLen(formatType);
        return true;
    }

    public final boolean setFloatValue(float value, int precision) {
        return setFloatValue((int) (value * Math.pow(10.0d, precision)), -precision, 52, this.offset);
    }

    public final boolean setString(String value) {
        if (value == null) {
            return false;
        }
        setString(value, this.offset);
        int i = this.offset;
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        this.offset = i + bytes.length;
        return true;
    }

    public final boolean setString(String value, int offset) {
        if (value == null) {
            return false;
        }
        prepareArray(value.length() + offset);
        byte[] bytes = value.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        System.arraycopy(bytes, 0, this.value, offset, bytes.length);
        return true;
    }

    public final boolean setCurrentTime(Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "calendar");
        prepareArray(10);
        setDateTime(calendar);
        this.value[7] = (byte) (((calendar.get(7) + 5) % 7) + 1);
        this.value[8] = (byte) ((calendar.get(14) * 256) / 1000);
        this.value[9] = 1;
        return true;
    }

    public final boolean setDateTime(Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "calendar");
        prepareArray(7);
        this.value[0] = (byte) calendar.get(1);
        this.value[1] = (byte) (calendar.get(1) >> 8);
        this.value[2] = (byte) (calendar.get(2) + 1);
        this.value[3] = (byte) calendar.get(5);
        this.value[4] = (byte) calendar.get(11);
        this.value[5] = (byte) calendar.get(12);
        this.value[6] = (byte) calendar.get(13);
        return true;
    }

    private final int unsignedBytesToInt(byte b0, byte b1) {
        return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
    }

    private final int unsignedBytesToInt(byte b0, byte b1, byte b2, byte b3) {
        return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (unsignedByteToInt(b2) << 16) + (unsignedByteToInt(b3) << 24);
    }

    private final float bytesToFloat(byte b0, byte b1) {
        return (float) (unsignedToSigned(unsignedByteToInt(b0) + ((unsignedByteToInt(b1) & 15) << 8), 12) * Math.pow(10.0d, unsignedToSigned(unsignedByteToInt(b1) >> 4, 4)));
    }

    private final float bytesToFloat(byte b0, byte b1, byte b2, byte b3) {
        return (float) (unsignedToSigned(unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (unsignedByteToInt(b2) << 16), 24) * Math.pow(10.0d, b3));
    }

    private final void prepareArray(int neededLength) {
        byte[] bArr = this.value;
        if (neededLength > bArr.length) {
            byte[] bArr2 = new byte[neededLength];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.value = bArr2;
        }
    }

    public String toString() {
        return INSTANCE.bytes2String(this.value);
    }

    /* compiled from: BluetoothBytesParser.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u001f\u0010\u0012\u001a\u00020\u00112\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u0014\"\u00020\u0011¢\u0006\u0002\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/welie/blessed/BluetoothBytesParser$Companion;", "", "()V", "FORMAT_FLOAT", "", "FORMAT_SFLOAT", "FORMAT_SINT16", "FORMAT_SINT24", "FORMAT_SINT32", "FORMAT_SINT8", "FORMAT_UINT16", "FORMAT_UINT24", "FORMAT_UINT32", "FORMAT_UINT8", "bytes2String", "", "bytes", "", "mergeArrays", "arrays", "", "([[B)[B", "string2bytes", "hexString", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String bytes2String(byte[] bytes) {
            if (bytes == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format("%02x", Arrays.copyOf(new Object[]{Integer.valueOf(b & 255)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
                sb.append(str);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
            return string;
        }

        public final byte[] string2bytes(String hexString) {
            if (hexString == null) {
                return new byte[0];
            }
            int length = hexString.length() / 2;
            byte[] bArr = new byte[length];
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                String strSubstring = hexString.substring(i2, i2 + 2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                bArr[i] = (byte) Integer.parseInt(strSubstring, CharsKt.checkRadix(16));
            }
            return bArr;
        }

        public final byte[] mergeArrays(byte[]... arrays) {
            Intrinsics.checkNotNullParameter(arrays, "arrays");
            byte[][] bArr = arrays;
            int length = bArr.length;
            int length2 = 0;
            for (int i = 0; i < length; i++) {
                length2 += arrays[i].length;
            }
            byte[] bArr2 = new byte[length2];
            int length3 = bArr.length;
            int length4 = 0;
            for (int i2 = 0; i2 < length3; i2++) {
                byte[] bArr3 = arrays[i2];
                System.arraycopy(bArr3, 0, bArr2, length4, bArr3.length);
                length4 += bArr3.length;
            }
            return bArr2;
        }
    }
}
