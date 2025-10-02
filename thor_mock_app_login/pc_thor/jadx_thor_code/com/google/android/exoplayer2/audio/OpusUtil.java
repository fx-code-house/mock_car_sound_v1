package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.C;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OpusUtil {
    private static final int DEFAULT_SEEK_PRE_ROLL_SAMPLES = 3840;
    private static final int FULL_CODEC_INITIALIZATION_DATA_BUFFER_COUNT = 3;
    public static final int SAMPLE_RATE = 48000;

    private OpusUtil() {
    }

    public static int getChannelCount(byte[] header) {
        return header[9] & 255;
    }

    public static List<byte[]> buildInitializationData(byte[] header) {
        long jSampleCountToNanoseconds = sampleCountToNanoseconds(getPreSkipSamples(header));
        long jSampleCountToNanoseconds2 = sampleCountToNanoseconds(3840L);
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(header);
        arrayList.add(buildNativeOrderByteArray(jSampleCountToNanoseconds));
        arrayList.add(buildNativeOrderByteArray(jSampleCountToNanoseconds2));
        return arrayList;
    }

    public static int getPreSkipSamples(List<byte[]> initializationData) {
        if (initializationData.size() == 3) {
            return (int) nanosecondsToSampleCount(ByteBuffer.wrap(initializationData.get(1)).order(ByteOrder.nativeOrder()).getLong());
        }
        return getPreSkipSamples(initializationData.get(0));
    }

    public static int getSeekPreRollSamples(List<byte[]> initializationData) {
        return initializationData.size() == 3 ? (int) nanosecondsToSampleCount(ByteBuffer.wrap(initializationData.get(2)).order(ByteOrder.nativeOrder()).getLong()) : DEFAULT_SEEK_PRE_ROLL_SAMPLES;
    }

    private static int getPreSkipSamples(byte[] header) {
        return (header[10] & 255) | ((header[11] & 255) << 8);
    }

    private static byte[] buildNativeOrderByteArray(long value) {
        return ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(value).array();
    }

    private static long sampleCountToNanoseconds(long sampleCount) {
        return (sampleCount * C.NANOS_PER_SECOND) / 48000;
    }

    private static long nanosecondsToSampleCount(long nanoseconds) {
        return (nanoseconds * 48000) / C.NANOS_PER_SECOND;
    }
}
