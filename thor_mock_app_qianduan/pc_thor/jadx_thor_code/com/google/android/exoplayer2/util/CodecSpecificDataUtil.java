package com.google.android.exoplayer2.util;

import android.util.Pair;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class CodecSpecificDataUtil {
    private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    private static final String[] HEVC_GENERAL_PROFILE_SPACE_STRINGS = {"", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C"};

    public static Pair<Integer, Integer> parseAlacAudioSpecificConfig(byte[] audioSpecificConfig) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(audioSpecificConfig);
        parsableByteArray.setPosition(9);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.setPosition(20);
        return Pair.create(Integer.valueOf(parsableByteArray.readUnsignedIntToInt()), Integer.valueOf(unsignedByte));
    }

    public static List<byte[]> buildCea708InitializationData(boolean isWideAspectRatio) {
        return Collections.singletonList(isWideAspectRatio ? new byte[]{1} : new byte[]{0});
    }

    public static boolean parseCea708InitializationData(List<byte[]> initializationData) {
        return initializationData.size() == 1 && initializationData.get(0).length == 1 && initializationData.get(0)[0] == 1;
    }

    public static String buildAvcCodecString(int profileIdc, int constraintsFlagsAndReservedZero2Bits, int levelIdc) {
        return String.format("avc1.%02X%02X%02X", Integer.valueOf(profileIdc), Integer.valueOf(constraintsFlagsAndReservedZero2Bits), Integer.valueOf(levelIdc));
    }

    public static String buildHevcCodecStringFromSps(ParsableNalUnitBitArray bitArray) {
        bitArray.skipBits(24);
        int bits = bitArray.readBits(2);
        boolean bit = bitArray.readBit();
        int bits2 = bitArray.readBits(5);
        int i = 0;
        for (int i2 = 0; i2 < 32; i2++) {
            if (bitArray.readBit()) {
                i |= 1 << i2;
            }
        }
        int i3 = 6;
        int[] iArr = new int[6];
        for (int i4 = 0; i4 < 6; i4++) {
            iArr[i4] = bitArray.readBits(8);
        }
        int bits3 = bitArray.readBits(8);
        Object[] objArr = new Object[5];
        objArr[0] = HEVC_GENERAL_PROFILE_SPACE_STRINGS[bits];
        objArr[1] = Integer.valueOf(bits2);
        objArr[2] = Integer.valueOf(i);
        objArr[3] = Character.valueOf(bit ? 'H' : 'L');
        objArr[4] = Integer.valueOf(bits3);
        StringBuilder sb = new StringBuilder(Util.formatInvariant("hvc1.%s%d.%X.%c%d", objArr));
        while (i3 > 0 && iArr[i3 - 1] == 0) {
            i3--;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            sb.append(String.format(".%02X", Integer.valueOf(iArr[i5])));
        }
        return sb.toString();
    }

    public static byte[] buildNalUnit(byte[] data, int offset, int length) {
        byte[] bArr = NAL_START_CODE;
        byte[] bArr2 = new byte[bArr.length + length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(data, offset, bArr2, bArr.length, length);
        return bArr2;
    }

    public static byte[][] splitNalUnits(byte[] data) {
        if (!isNalStartCode(data, 0)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int iFindNalStartCode = 0;
        do {
            arrayList.add(Integer.valueOf(iFindNalStartCode));
            iFindNalStartCode = findNalStartCode(data, iFindNalStartCode + NAL_START_CODE.length);
        } while (iFindNalStartCode != -1);
        byte[][] bArr = new byte[arrayList.size()][];
        int i = 0;
        while (i < arrayList.size()) {
            int iIntValue = ((Integer) arrayList.get(i)).intValue();
            int iIntValue2 = (i < arrayList.size() + (-1) ? ((Integer) arrayList.get(i + 1)).intValue() : data.length) - iIntValue;
            byte[] bArr2 = new byte[iIntValue2];
            System.arraycopy(data, iIntValue, bArr2, 0, iIntValue2);
            bArr[i] = bArr2;
            i++;
        }
        return bArr;
    }

    private static int findNalStartCode(byte[] data, int index) {
        int length = data.length - NAL_START_CODE.length;
        while (index <= length) {
            if (isNalStartCode(data, index)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private static boolean isNalStartCode(byte[] data, int index) {
        if (data.length - index <= NAL_START_CODE.length) {
            return false;
        }
        int i = 0;
        while (true) {
            byte[] bArr = NAL_START_CODE;
            if (i >= bArr.length) {
                return true;
            }
            if (data[index + i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private CodecSpecificDataUtil() {
    }
}
