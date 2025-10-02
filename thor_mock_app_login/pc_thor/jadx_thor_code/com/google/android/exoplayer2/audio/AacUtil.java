package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class AacUtil {
    public static final int AAC_ELD_MAX_RATE_BYTES_PER_SECOND = 8000;
    public static final int AAC_HE_AUDIO_SAMPLE_COUNT = 2048;
    public static final int AAC_HE_V2_MAX_RATE_BYTES_PER_SECOND = 7000;
    public static final int AAC_LC_AUDIO_SAMPLE_COUNT = 1024;
    public static final int AAC_LC_MAX_RATE_BYTES_PER_SECOND = 100000;
    public static final int AAC_LD_AUDIO_SAMPLE_COUNT = 512;
    public static final int AAC_XHE_AUDIO_SAMPLE_COUNT = 1024;
    public static final int AAC_XHE_MAX_RATE_BYTES_PER_SECOND = 256000;
    public static final int AUDIO_OBJECT_TYPE_AAC_ELD = 23;
    public static final int AUDIO_OBJECT_TYPE_AAC_ER_BSAC = 22;
    public static final int AUDIO_OBJECT_TYPE_AAC_LC = 2;
    public static final int AUDIO_OBJECT_TYPE_AAC_PS = 29;
    public static final int AUDIO_OBJECT_TYPE_AAC_SBR = 5;
    public static final int AUDIO_OBJECT_TYPE_AAC_XHE = 42;
    private static final int AUDIO_OBJECT_TYPE_ESCAPE = 31;
    private static final int AUDIO_SPECIFIC_CONFIG_CHANNEL_CONFIGURATION_INVALID = -1;
    private static final int AUDIO_SPECIFIC_CONFIG_FREQUENCY_INDEX_ARBITRARY = 15;
    private static final String CODECS_STRING_PREFIX = "mp4a.40.";
    private static final String TAG = "AacUtil";
    public static final int AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND = 16000;
    private static final int[] AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE = {96000, 88200, 64000, OpusUtil.SAMPLE_RATE, 44100, 32000, 24000, 22050, AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND, 12000, 11025, 8000, 7350};
    private static final int[] AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE = {0, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, 7, 8, -1, 8, -1};

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AacAudioObjectType {
    }

    public static byte[] buildAudioSpecificConfig(int audioObjectType, int sampleRateIndex, int channelConfig) {
        return new byte[]{(byte) (((audioObjectType << 3) & 248) | ((sampleRateIndex >> 1) & 7)), (byte) (((sampleRateIndex << 7) & 128) | ((channelConfig << 3) & 120))};
    }

    public static int getEncodingForAudioObjectType(int audioObjectType) {
        if (audioObjectType == 2) {
            return 10;
        }
        if (audioObjectType == 5) {
            return 11;
        }
        if (audioObjectType == 29) {
            return 12;
        }
        if (audioObjectType == 42) {
            return 16;
        }
        if (audioObjectType != 22) {
            return audioObjectType != 23 ? 0 : 15;
        }
        return 1073741824;
    }

    public static final class Config {
        public final int channelCount;
        public final String codecs;
        public final int sampleRateHz;

        private Config(int sampleRateHz, int channelCount, String codecs) {
            this.sampleRateHz = sampleRateHz;
            this.channelCount = channelCount;
            this.codecs = codecs;
        }
    }

    public static Config parseAudioSpecificConfig(byte[] audioSpecificConfig) throws ParserException {
        return parseAudioSpecificConfig(new ParsableBitArray(audioSpecificConfig), false);
    }

    public static Config parseAudioSpecificConfig(ParsableBitArray bitArray, boolean forceReadToEnd) throws ParserException {
        int audioObjectType = getAudioObjectType(bitArray);
        int samplingFrequency = getSamplingFrequency(bitArray);
        int bits = bitArray.readBits(4);
        String string = new StringBuilder(19).append(CODECS_STRING_PREFIX).append(audioObjectType).toString();
        if (audioObjectType == 5 || audioObjectType == 29) {
            samplingFrequency = getSamplingFrequency(bitArray);
            audioObjectType = getAudioObjectType(bitArray);
            if (audioObjectType == 22) {
                bits = bitArray.readBits(4);
            }
        }
        if (forceReadToEnd) {
            if (audioObjectType != 1 && audioObjectType != 2 && audioObjectType != 3 && audioObjectType != 4 && audioObjectType != 6 && audioObjectType != 7 && audioObjectType != 17) {
                switch (audioObjectType) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        break;
                    default:
                        throw ParserException.createForUnsupportedContainerFeature(new StringBuilder(42).append("Unsupported audio object type: ").append(audioObjectType).toString());
                }
            }
            parseGaSpecificConfig(bitArray, audioObjectType, bits);
            switch (audioObjectType) {
                case 17:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    int bits2 = bitArray.readBits(2);
                    if (bits2 == 2 || bits2 == 3) {
                        throw ParserException.createForUnsupportedContainerFeature(new StringBuilder(33).append("Unsupported epConfig: ").append(bits2).toString());
                    }
            }
        }
        int i = AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE[bits];
        if (i == -1) {
            throw ParserException.createForMalformedContainer(null, null);
        }
        return new Config(samplingFrequency, i, string);
    }

    public static byte[] buildAacLcAudioSpecificConfig(int sampleRate, int channelCount) {
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            int[] iArr = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE;
            if (i3 >= iArr.length) {
                break;
            }
            if (sampleRate == iArr[i3]) {
                i2 = i3;
            }
            i3++;
        }
        int i4 = -1;
        while (true) {
            int[] iArr2 = AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE;
            if (i >= iArr2.length) {
                break;
            }
            if (channelCount == iArr2[i]) {
                i4 = i;
            }
            i++;
        }
        if (sampleRate == -1 || i4 == -1) {
            throw new IllegalArgumentException(new StringBuilder(67).append("Invalid sample rate or number of channels: ").append(sampleRate).append(", ").append(channelCount).toString());
        }
        return buildAudioSpecificConfig(2, i2, i4);
    }

    private static int getAudioObjectType(ParsableBitArray bitArray) {
        int bits = bitArray.readBits(5);
        return bits == 31 ? bitArray.readBits(6) + 32 : bits;
    }

    private static int getSamplingFrequency(ParsableBitArray bitArray) throws ParserException {
        int bits = bitArray.readBits(4);
        if (bits == 15) {
            return bitArray.readBits(24);
        }
        if (bits < 13) {
            return AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[bits];
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    private static void parseGaSpecificConfig(ParsableBitArray bitArray, int audioObjectType, int channelConfiguration) {
        if (bitArray.readBit()) {
            Log.w(TAG, "Unexpected frameLengthFlag = 1");
        }
        if (bitArray.readBit()) {
            bitArray.skipBits(14);
        }
        boolean bit = bitArray.readBit();
        if (channelConfiguration == 0) {
            throw new UnsupportedOperationException();
        }
        if (audioObjectType == 6 || audioObjectType == 20) {
            bitArray.skipBits(3);
        }
        if (bit) {
            if (audioObjectType == 22) {
                bitArray.skipBits(16);
            }
            if (audioObjectType == 17 || audioObjectType == 19 || audioObjectType == 20 || audioObjectType == 23) {
                bitArray.skipBits(3);
            }
            bitArray.skipBits(1);
        }
    }

    private AacUtil() {
    }
}
