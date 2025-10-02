package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.MimeTypes;
import com.thor.app.auto.media.AutoNotificationManagerKt;

/* loaded from: classes.dex */
public final class MpegAudioUtil {
    public static final int MAX_FRAME_SIZE_BYTES = 4096;
    private static final int SAMPLES_PER_FRAME_L1 = 384;
    private static final int SAMPLES_PER_FRAME_L2 = 1152;
    private static final int SAMPLES_PER_FRAME_L3_V1 = 1152;
    private static final int SAMPLES_PER_FRAME_L3_V2 = 576;
    private static final String[] MIME_TYPE_BY_LAYER = {MimeTypes.AUDIO_MPEG_L1, MimeTypes.AUDIO_MPEG_L2, MimeTypes.AUDIO_MPEG};
    private static final int[] SAMPLING_RATE_V1 = {44100, OpusUtil.SAMPLE_RATE, 32000};
    private static final int[] BITRATE_V1_L1 = {32000, 64000, 96000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 288000, 320000, 352000, 384000, 416000, 448000};
    private static final int[] BITRATE_V2_L1 = {32000, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 144000, 160000, 176000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND};
    private static final int[] BITRATE_V1_L2 = {32000, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 320000, 384000};
    public static final int MAX_RATE_BYTES_PER_SECOND = 40000;
    private static final int[] BITRATE_V1_L3 = {32000, MAX_RATE_BYTES_PER_SECOND, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 160000, DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND, 224000, AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND, 320000};
    private static final int[] BITRATE_V2 = {8000, AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND, 24000, 32000, MAX_RATE_BYTES_PER_SECOND, OpusUtil.SAMPLE_RATE, 56000, 64000, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 96000, 112000, 128000, 144000, 160000};

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isMagicPresent(int headerData) {
        return (headerData & (-2097152)) == -2097152;
    }

    public static final class Header {
        public int bitrate;
        public int channels;
        public int frameSize;
        public String mimeType;
        public int sampleRate;
        public int samplesPerFrame;
        public int version;

        public boolean setForHeaderData(int headerData) {
            int i;
            int i2;
            int i3;
            int i4;
            if (!MpegAudioUtil.isMagicPresent(headerData) || (i = (headerData >>> 19) & 3) == 1 || (i2 = (headerData >>> 17) & 3) == 0 || (i3 = (headerData >>> 12) & 15) == 0 || i3 == 15 || (i4 = (headerData >>> 10) & 3) == 3) {
                return false;
            }
            this.version = i;
            this.mimeType = MpegAudioUtil.MIME_TYPE_BY_LAYER[3 - i2];
            int i5 = MpegAudioUtil.SAMPLING_RATE_V1[i4];
            this.sampleRate = i5;
            if (i == 2) {
                this.sampleRate = i5 / 2;
            } else if (i == 0) {
                this.sampleRate = i5 / 4;
            }
            int i6 = (headerData >>> 9) & 1;
            this.samplesPerFrame = MpegAudioUtil.getFrameSizeInSamples(i, i2);
            if (i2 == 3) {
                int i7 = i == 3 ? MpegAudioUtil.BITRATE_V1_L1[i3 - 1] : MpegAudioUtil.BITRATE_V2_L1[i3 - 1];
                this.bitrate = i7;
                this.frameSize = (((i7 * 12) / this.sampleRate) + i6) * 4;
            } else {
                int i8 = AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE;
                if (i == 3) {
                    int i9 = i2 == 2 ? MpegAudioUtil.BITRATE_V1_L2[i3 - 1] : MpegAudioUtil.BITRATE_V1_L3[i3 - 1];
                    this.bitrate = i9;
                    this.frameSize = ((i9 * AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE) / this.sampleRate) + i6;
                } else {
                    int i10 = MpegAudioUtil.BITRATE_V2[i3 - 1];
                    this.bitrate = i10;
                    if (i2 == 1) {
                        i8 = 72;
                    }
                    this.frameSize = ((i8 * i10) / this.sampleRate) + i6;
                }
            }
            this.channels = ((headerData >> 6) & 3) == 3 ? 1 : 2;
            return true;
        }
    }

    public static int getFrameSize(int headerData) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (!isMagicPresent(headerData) || (i = (headerData >>> 19) & 3) == 1 || (i2 = (headerData >>> 17) & 3) == 0 || (i3 = (headerData >>> 12) & 15) == 0 || i3 == 15 || (i4 = (headerData >>> 10) & 3) == 3) {
            return -1;
        }
        int i6 = SAMPLING_RATE_V1[i4];
        if (i == 2) {
            i6 /= 2;
        } else if (i == 0) {
            i6 /= 4;
        }
        int i7 = (headerData >>> 9) & 1;
        if (i2 == 3) {
            return ((((i == 3 ? BITRATE_V1_L1[i3 - 1] : BITRATE_V2_L1[i3 - 1]) * 12) / i6) + i7) * 4;
        }
        if (i == 3) {
            i5 = i2 == 2 ? BITRATE_V1_L2[i3 - 1] : BITRATE_V1_L3[i3 - 1];
        } else {
            i5 = BITRATE_V2[i3 - 1];
        }
        int i8 = AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE;
        if (i == 3) {
            return ((i5 * AutoNotificationManagerKt.NOTIFICATION_LARGE_ICON_SIZE) / i6) + i7;
        }
        if (i2 == 1) {
            i8 = 72;
        }
        return ((i8 * i5) / i6) + i7;
    }

    public static int parseMpegAudioFrameSampleCount(int headerData) {
        int i;
        int i2;
        if (!isMagicPresent(headerData) || (i = (headerData >>> 19) & 3) == 1 || (i2 = (headerData >>> 17) & 3) == 0) {
            return -1;
        }
        int i3 = (headerData >>> 12) & 15;
        int i4 = (headerData >>> 10) & 3;
        if (i3 == 0 || i3 == 15 || i4 == 3) {
            return -1;
        }
        return getFrameSizeInSamples(i, i2);
    }

    private MpegAudioUtil() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getFrameSizeInSamples(int version, int layer) {
        if (layer == 1) {
            if (version == 3) {
                return 1152;
            }
            return SAMPLES_PER_FRAME_L3_V2;
        }
        if (layer == 2) {
            return 1152;
        }
        if (layer == 3) {
            return SAMPLES_PER_FRAME_L1;
        }
        throw new IllegalArgumentException();
    }
}
