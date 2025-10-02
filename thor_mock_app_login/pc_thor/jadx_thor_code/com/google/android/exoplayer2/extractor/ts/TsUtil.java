package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes.dex */
public final class TsUtil {
    public static boolean isStartOfTsPacket(byte[] data, int start, int limit, int searchPosition) {
        int i = 0;
        for (int i2 = -4; i2 <= 4; i2++) {
            int i3 = (i2 * TsExtractor.TS_PACKET_SIZE) + searchPosition;
            if (i3 < start || i3 >= limit || data[i3] != 71) {
                i = 0;
            } else {
                i++;
                if (i == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int findSyncBytePosition(byte[] data, int startPosition, int limitPosition) {
        while (startPosition < limitPosition && data[startPosition] != 71) {
            startPosition++;
        }
        return startPosition;
    }

    public static long readPcrFromPacket(ParsableByteArray packetBuffer, int startOfPacket, int pcrPid) {
        packetBuffer.setPosition(startOfPacket);
        if (packetBuffer.bytesLeft() < 5) {
            return C.TIME_UNSET;
        }
        int i = packetBuffer.readInt();
        if ((8388608 & i) != 0 || ((2096896 & i) >> 8) != pcrPid) {
            return C.TIME_UNSET;
        }
        if (((i & 32) != 0) && packetBuffer.readUnsignedByte() >= 7 && packetBuffer.bytesLeft() >= 7) {
            if ((packetBuffer.readUnsignedByte() & 16) == 16) {
                byte[] bArr = new byte[6];
                packetBuffer.readBytes(bArr, 0, 6);
                return readPcrValueFromPcrBytes(bArr);
            }
        }
        return C.TIME_UNSET;
    }

    private static long readPcrValueFromPcrBytes(byte[] pcrBytes) {
        return ((pcrBytes[0] & 255) << 25) | ((pcrBytes[1] & 255) << 17) | ((pcrBytes[2] & 255) << 9) | ((pcrBytes[3] & 255) << 1) | ((255 & pcrBytes[4]) >> 7);
    }

    private TsUtil() {
    }
}
