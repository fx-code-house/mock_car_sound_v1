package com.google.android.exoplayer2.util;

import android.media.MediaFormat;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.video.ColorInfo;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes.dex */
public final class MediaFormatUtil {
    public static final String KEY_EXO_PCM_ENCODING = "exo-pcm-encoding-int";
    public static final String KEY_EXO_PIXEL_WIDTH_HEIGHT_RATIO_FLOAT = "exo-pixel-width-height-ratio-float";
    private static final int MAX_POWER_OF_TWO_INT = 1073741824;

    public static MediaFormat createMediaFormatFromFormat(Format format) {
        MediaFormat mediaFormat = new MediaFormat();
        maybeSetInteger(mediaFormat, "bitrate", format.bitrate);
        maybeSetInteger(mediaFormat, "channel-count", format.channelCount);
        maybeSetColorInfo(mediaFormat, format.colorInfo);
        maybeSetString(mediaFormat, "mime", format.sampleMimeType);
        maybeSetString(mediaFormat, "codecs-string", format.codecs);
        maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        maybeSetInteger(mediaFormat, "width", format.width);
        maybeSetInteger(mediaFormat, "height", format.height);
        setCsdBuffers(mediaFormat, format.initializationData);
        maybeSetPcmEncoding(mediaFormat, format.pcmEncoding);
        maybeSetString(mediaFormat, "language", format.language);
        maybeSetInteger(mediaFormat, "max-input-size", format.maxInputSize);
        maybeSetInteger(mediaFormat, "sample-rate", format.sampleRate);
        maybeSetInteger(mediaFormat, "caption-service-number", format.accessibilityChannel);
        mediaFormat.setInteger("rotation-degrees", format.rotationDegrees);
        int i = format.selectionFlags;
        setBooleanAsInt(mediaFormat, "is-autoselect", i & 4);
        setBooleanAsInt(mediaFormat, "is-default", i & 1);
        setBooleanAsInt(mediaFormat, "is-forced-subtitle", i & 2);
        mediaFormat.setInteger("encoder-delay", format.encoderDelay);
        mediaFormat.setInteger("encoder-padding", format.encoderPadding);
        maybeSetPixelAspectRatio(mediaFormat, format.pixelWidthHeightRatio);
        return mediaFormat;
    }

    public static void maybeSetString(MediaFormat format, String key, String value) {
        if (value != null) {
            format.setString(key, value);
        }
    }

    public static void setCsdBuffers(MediaFormat format, List<byte[]> csdBuffers) {
        for (int i = 0; i < csdBuffers.size(); i++) {
            format.setByteBuffer(new StringBuilder(15).append("csd-").append(i).toString(), ByteBuffer.wrap(csdBuffers.get(i)));
        }
    }

    public static void maybeSetInteger(MediaFormat format, String key, int value) {
        if (value != -1) {
            format.setInteger(key, value);
        }
    }

    public static void maybeSetFloat(MediaFormat format, String key, float value) {
        if (value != -1.0f) {
            format.setFloat(key, value);
        }
    }

    public static void maybeSetByteBuffer(MediaFormat format, String key, byte[] value) {
        if (value != null) {
            format.setByteBuffer(key, ByteBuffer.wrap(value));
        }
    }

    public static void maybeSetColorInfo(MediaFormat format, ColorInfo colorInfo) {
        if (colorInfo != null) {
            maybeSetInteger(format, "color-transfer", colorInfo.colorTransfer);
            maybeSetInteger(format, "color-standard", colorInfo.colorSpace);
            maybeSetInteger(format, "color-range", colorInfo.colorRange);
            maybeSetByteBuffer(format, "hdr-static-info", colorInfo.hdrStaticInfo);
        }
    }

    private static void setBooleanAsInt(MediaFormat format, String key, int value) {
        format.setInteger(key, value != 0 ? 1 : 0);
    }

    private static void maybeSetPixelAspectRatio(MediaFormat mediaFormat, float pixelWidthHeightRatio) {
        int i;
        mediaFormat.setFloat(KEY_EXO_PIXEL_WIDTH_HEIGHT_RATIO_FLOAT, pixelWidthHeightRatio);
        int i2 = 1073741824;
        if (pixelWidthHeightRatio < 1.0f) {
            i2 = (int) (pixelWidthHeightRatio * 1073741824);
            i = 1073741824;
        } else if (pixelWidthHeightRatio > 1.0f) {
            i = (int) (1073741824 / pixelWidthHeightRatio);
        } else {
            i2 = 1;
            i = 1;
        }
        mediaFormat.setInteger("sar-width", i2);
        mediaFormat.setInteger("sar-height", i);
    }

    private static void maybeSetPcmEncoding(MediaFormat mediaFormat, int exoPcmEncoding) {
        if (exoPcmEncoding == -1) {
            return;
        }
        maybeSetInteger(mediaFormat, KEY_EXO_PCM_ENCODING, exoPcmEncoding);
        int i = 2;
        if (exoPcmEncoding != 2) {
            i = 3;
            if (exoPcmEncoding != 3) {
                i = 4;
                if (exoPcmEncoding != 4) {
                    return;
                }
            }
        }
        mediaFormat.setInteger("pcm-encoding", i);
    }

    private MediaFormatUtil() {
    }
}
