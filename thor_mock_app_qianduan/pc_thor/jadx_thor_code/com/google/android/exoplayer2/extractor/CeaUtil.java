package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes.dex */
public final class CeaUtil {
    private static final int COUNTRY_CODE = 181;
    private static final int PAYLOAD_TYPE_CC = 4;
    private static final int PROVIDER_CODE_ATSC = 49;
    private static final int PROVIDER_CODE_DIRECTV = 47;
    private static final String TAG = "CeaUtil";
    public static final int USER_DATA_IDENTIFIER_GA94 = 1195456820;
    public static final int USER_DATA_TYPE_CODE_MPEG_CC = 3;

    public static void consume(long presentationTimeUs, ParsableByteArray seiBuffer, TrackOutput[] outputs) {
        while (true) {
            if (seiBuffer.bytesLeft() <= 1) {
                return;
            }
            int non255TerminatedValue = readNon255TerminatedValue(seiBuffer);
            int non255TerminatedValue2 = readNon255TerminatedValue(seiBuffer);
            int position = seiBuffer.getPosition() + non255TerminatedValue2;
            if (non255TerminatedValue2 == -1 || non255TerminatedValue2 > seiBuffer.bytesLeft()) {
                Log.w(TAG, "Skipping remainder of malformed SEI NAL unit.");
                position = seiBuffer.limit();
            } else if (non255TerminatedValue == 4 && non255TerminatedValue2 >= 8) {
                int unsignedByte = seiBuffer.readUnsignedByte();
                int unsignedShort = seiBuffer.readUnsignedShort();
                int i = unsignedShort == 49 ? seiBuffer.readInt() : 0;
                int unsignedByte2 = seiBuffer.readUnsignedByte();
                if (unsignedShort == 47) {
                    seiBuffer.skipBytes(1);
                }
                boolean z = unsignedByte == COUNTRY_CODE && (unsignedShort == 49 || unsignedShort == 47) && unsignedByte2 == 3;
                if (unsignedShort == 49) {
                    z &= i == 1195456820;
                }
                if (z) {
                    consumeCcData(presentationTimeUs, seiBuffer, outputs);
                }
            }
            seiBuffer.setPosition(position);
        }
    }

    public static void consumeCcData(long presentationTimeUs, ParsableByteArray ccDataBuffer, TrackOutput[] outputs) {
        int unsignedByte = ccDataBuffer.readUnsignedByte();
        if ((unsignedByte & 64) != 0) {
            ccDataBuffer.skipBytes(1);
            int i = (unsignedByte & 31) * 3;
            int position = ccDataBuffer.getPosition();
            for (TrackOutput trackOutput : outputs) {
                ccDataBuffer.setPosition(position);
                trackOutput.sampleData(ccDataBuffer, i);
                trackOutput.sampleMetadata(presentationTimeUs, 1, i, 0, null);
            }
        }
    }

    private static int readNon255TerminatedValue(ParsableByteArray buffer) {
        int i = 0;
        while (buffer.bytesLeft() != 0) {
            int unsignedByte = buffer.readUnsignedByte();
            i += unsignedByte;
            if (unsignedByte != 255) {
                return i;
            }
        }
        return -1;
    }

    private CeaUtil() {
    }
}
