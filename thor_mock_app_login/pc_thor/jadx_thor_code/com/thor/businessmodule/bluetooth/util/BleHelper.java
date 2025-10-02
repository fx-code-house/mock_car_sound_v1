package com.thor.businessmodule.bluetooth.util;

import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.model.other.FileType;
import com.thor.businessmodule.bluetooth.model.other.IdFileModel;
import com.thor.businessmodule.bluetooth.request.other.BaseBleRequest;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UShort;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: BleHelper.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0007J\u0010\u0010\n\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0017\u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007¢\u0006\u0002\u0010\u000eJ\u001f\u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\rH\u0007¢\u0006\u0002\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u0004H\u0007¢\u0006\u0002\u0010\u0012J\u0017\u0010\u0013\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u0004H\u0007¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0014\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0015H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\u001a\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u0004H\u0007J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u0004H\u0007J \u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!H\u0007J\u000e\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\tH\u0007J\u001c\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010*\u001a\u00020\rJ\u0018\u0010+\u001a\u00020\u00042\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-H\u0007J\u0010\u0010+\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J(\u0010/\u001a\u00020\u00152\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-H\u0007ø\u0001\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b0\u00101R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0005\b¡\u001e0\u0001\n\u0002\b\u0019\n\u0002\b!¨\u00062"}, d2 = {"Lcom/thor/businessmodule/bluetooth/util/BleHelper;", "", "()V", "DEVICE_TYPE_BLE_MODULE_ID", "", "DEVICE_TYPE_MAIN_ID", "checkCrc", "", "bytes", "", "checkCrcOld", "convertIntToByteArray", "value", "", "(Ljava/lang/Integer;)[B", "sizeByte", "(Ljava/lang/Integer;I)[B", "convertShortToByteArray", "(Ljava/lang/Short;)[B", "convertShortToByteArrayOneByte", "convertUShortByteArray", "Lkotlin/UShort;", "convertUShortByteArray-xj2QHRw", "(S)[B", "createCheckSumPart", "createCheckSumPartOld", "fetchCommandRequest", BleCommandsWorker.INPUT_DATA_COMMAND, "fetchCommandRequestOld", "generateIdFile", "packageId", "versionId", "idFileType", "Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "hexStringToByteArray", "hexString", "", "parseIdFile", "Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;", "data", "splitByteArray", "", "size", "takeShort", "first", "", "second", "takeUShort", "takeUShort-ErzVvmY", "(BB)S", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BleHelper {
    public static final short DEVICE_TYPE_BLE_MODULE_ID = 2;
    public static final short DEVICE_TYPE_MAIN_ID = 1;
    public static final BleHelper INSTANCE = new BleHelper();

    @JvmStatic
    /* renamed from: convertUShortByteArray-xj2QHRw, reason: not valid java name */
    public static final byte[] m603convertUShortByteArrayxj2QHRw(short value) {
        int i = value & 65535;
        return new byte[]{(byte) ((i >>> 8) & 255), (byte) (i & 255)};
    }

    private BleHelper() {
    }

    public final byte[] hexStringToByteArray(String hexString) {
        Intrinsics.checkNotNullParameter(hexString, "hexString");
        int length = hexString.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bArr;
    }

    @JvmStatic
    public static final byte[] generateIdFile(short packageId, short versionId, FileType idFileType) {
        Intrinsics.checkNotNullParameter(idFileType, "idFileType");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ArraysKt.toList(convertShortToByteArrayOneByte(Short.valueOf(idFileType.getId()))));
        arrayList.addAll(ArraysKt.toList(convertShortToByteArray(Short.valueOf(packageId))));
        arrayList.addAll(ArraysKt.toList(convertShortToByteArrayOneByte(Short.valueOf(versionId))));
        return CollectionsKt.toByteArray(arrayList);
    }

    @JvmStatic
    public static final IdFileModel parseIdFile(byte[] data) {
        FileType fileType;
        Intrinsics.checkNotNullParameter(data, "data");
        short s = data[0];
        short sTakeShort = takeShort(data[1], data[2]);
        short s2 = data[3];
        Short shValueOf = Short.valueOf(sTakeShort);
        Short shValueOf2 = Short.valueOf(s2);
        FileType[] fileTypeArrValues = FileType.values();
        int length = fileTypeArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                fileType = null;
                break;
            }
            fileType = fileTypeArrValues[i];
            if (fileType.getId() == s) {
                break;
            }
            i++;
        }
        return new IdFileModel(shValueOf, shValueOf2, fileType);
    }

    @JvmStatic
    public static final byte[] fetchCommandRequest(short command) {
        return new BaseBleRequest(command).getBytes(true);
    }

    @JvmStatic
    public static final byte[] fetchCommandRequestOld(short command) {
        return new BaseBleRequest(command).getBytesOld();
    }

    @JvmStatic
    public static final byte[] convertShortToByteArray(Short value) {
        if (value == null) {
            return new byte[0];
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
        byteBufferAllocate.putShort(value.shortValue());
        byte[] bArrArray = byteBufferAllocate.array();
        Intrinsics.checkNotNullExpressionValue(bArrArray, "buffer.array()");
        return bArrArray;
    }

    @JvmStatic
    public static final byte[] convertShortToByteArrayOneByte(Short value) {
        return value == null ? new byte[0] : new byte[]{(byte) value.shortValue()};
    }

    @JvmStatic
    public static final byte[] convertIntToByteArray(Integer value) {
        if (value == null) {
            return new byte[0];
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.putInt(value.intValue());
        byte[] bArrArray = byteBufferAllocate.array();
        Intrinsics.checkNotNullExpressionValue(bArrArray, "buffer.array()");
        return bArrArray;
    }

    @JvmStatic
    public static final byte[] convertIntToByteArray(Integer value, int sizeByte) {
        if (value == null) {
            return new byte[0];
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(sizeByte);
        byteBufferAllocate.putInt(value.intValue());
        byte[] bArrArray = byteBufferAllocate.array();
        Intrinsics.checkNotNullExpressionValue(bArrArray, "buffer.array()");
        return bArrArray;
    }

    @JvmStatic
    public static final short takeShort(byte first, byte second) {
        return ByteBuffer.allocate(2).put(first).put(second).getShort(0);
    }

    @JvmStatic
    /* renamed from: takeUShort-ErzVvmY, reason: not valid java name */
    public static final short m604takeUShortErzVvmY(byte first, byte second) {
        return UShort.m905constructorimpl((short) (((UByte.m642constructorimpl(first) & 255) << 8) | (UByte.m642constructorimpl(second) & 255)));
    }

    @JvmStatic
    public static final short takeShort(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Timber.i("takeShort: %s", Arrays.toString(bytes));
        return ByteBuffer.wrap(bytes).getShort();
    }

    @JvmStatic
    public static final byte[] createCheckSumPart(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        CRC16 crc16 = new CRC16();
        for (byte b : bytes) {
            crc16.update(b);
        }
        byte[] crc = ByteBuffer.allocate(2).putShort((short) crc16.value).array();
        Intrinsics.checkNotNullExpressionValue(crc, "crc");
        ArraysKt.reverse(crc);
        return crc;
    }

    @JvmStatic
    public static final byte[] createCheckSumPartOld(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        CRC16 crc16 = new CRC16();
        for (byte b : bytes) {
            crc16.update(b);
        }
        byte[] bArrArray = ByteBuffer.allocate(2).putShort((short) crc16.value).array();
        Intrinsics.checkNotNullExpressionValue(bArrArray, "allocate(2).putShort(crc….value.toShort()).array()");
        return bArrArray;
    }

    @JvmStatic
    public static final boolean checkCrc(byte[] bytes) {
        if (bytes != null) {
            if (!(bytes.length == 0) && bytes.length >= 4) {
                int length = bytes.length;
                int i = length - 2;
                byte[] body = Arrays.copyOf(bytes, i);
                Intrinsics.checkNotNullExpressionValue(body, "body");
                byte[] bArrCreateCheckSumPart = createCheckSumPart(body);
                boolean z = bArrCreateCheckSumPart[0] == bytes[i] && bArrCreateCheckSumPart[1] == bytes[length - 1];
                if (!z) {
                    Timber.d("checkCrc1 false " + BleHelperKt.toHexString(bytes), new Object[0]);
                }
                return z;
            }
        }
        return false;
    }

    public final boolean checkCrcOld(byte[] bytes) {
        if (bytes != null) {
            if (!(bytes.length == 0) && bytes.length >= 4) {
                int length = bytes.length;
                int i = length - 2;
                byte[] body = Arrays.copyOf(bytes, i);
                Intrinsics.checkNotNullExpressionValue(body, "body");
                byte[] bArrCreateCheckSumPartOld = createCheckSumPartOld(body);
                boolean z = bArrCreateCheckSumPartOld[0] == bytes[i] && bArrCreateCheckSumPartOld[1] == bytes[length - 1];
                if (!z) {
                    Timber.d("checkCrc false " + BleHelperKt.toHexString(bytes), new Object[0]);
                }
                return z;
            }
        }
        return false;
    }

    public final List<byte[]> splitByteArray(byte[] bytes, int size) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        ArrayList arrayList = new ArrayList();
        if (bytes.length <= size) {
            arrayList.add(bytes);
            return arrayList;
        }
        int i = 0;
        while (i < bytes.length) {
            int i2 = i + size;
            if (i2 < bytes.length) {
                arrayList.add(ArraysKt.copyOfRange(bytes, i, i2));
            } else {
                arrayList.add(ArraysKt.copyOfRange(bytes, i, bytes.length));
            }
            i = i2;
        }
        return arrayList;
    }
}
