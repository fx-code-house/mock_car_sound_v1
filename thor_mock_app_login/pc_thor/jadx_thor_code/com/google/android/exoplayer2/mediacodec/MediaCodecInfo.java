package com.google.android.exoplayer2.mediacodec;

import android.graphics.Point;
import android.media.MediaCodecInfo;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class MediaCodecInfo {
    public static final int MAX_SUPPORTED_INSTANCES_UNKNOWN = -1;
    public static final String TAG = "MediaCodecInfo";
    public final boolean adaptive;
    public final MediaCodecInfo.CodecCapabilities capabilities;
    public final String codecMimeType;
    public final boolean hardwareAccelerated;
    private final boolean isVideo;
    public final String mimeType;
    public final String name;
    public final boolean secure;
    public final boolean softwareOnly;
    public final boolean tunneling;
    public final boolean vendor;

    public static MediaCodecInfo newInstance(String name, String mimeType, String codecMimeType, MediaCodecInfo.CodecCapabilities capabilities, boolean hardwareAccelerated, boolean softwareOnly, boolean vendor, boolean forceDisableAdaptive, boolean forceSecure) {
        return new MediaCodecInfo(name, mimeType, codecMimeType, capabilities, hardwareAccelerated, softwareOnly, vendor, (forceDisableAdaptive || capabilities == null || !isAdaptive(capabilities) || needsDisableAdaptationWorkaround(name)) ? false : true, capabilities != null && isTunneling(capabilities), forceSecure || (capabilities != null && isSecure(capabilities)));
    }

    MediaCodecInfo(String name, String mimeType, String codecMimeType, MediaCodecInfo.CodecCapabilities capabilities, boolean hardwareAccelerated, boolean softwareOnly, boolean vendor, boolean adaptive, boolean tunneling, boolean secure) {
        this.name = (String) Assertions.checkNotNull(name);
        this.mimeType = mimeType;
        this.codecMimeType = codecMimeType;
        this.capabilities = capabilities;
        this.hardwareAccelerated = hardwareAccelerated;
        this.softwareOnly = softwareOnly;
        this.vendor = vendor;
        this.adaptive = adaptive;
        this.tunneling = tunneling;
        this.secure = secure;
        this.isVideo = MimeTypes.isVideo(mimeType);
    }

    public String toString() {
        return this.name;
    }

    public MediaCodecInfo.CodecProfileLevel[] getProfileLevels() {
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.capabilities;
        return (codecCapabilities == null || codecCapabilities.profileLevels == null) ? new MediaCodecInfo.CodecProfileLevel[0] : this.capabilities.profileLevels;
    }

    public int getMaxSupportedInstances() {
        MediaCodecInfo.CodecCapabilities codecCapabilities;
        if (Util.SDK_INT < 23 || (codecCapabilities = this.capabilities) == null) {
            return -1;
        }
        return getMaxSupportedInstancesV23(codecCapabilities);
    }

    public boolean isFormatSupported(Format format) throws MediaCodecUtil.DecoderQueryException {
        if (!isCodecSupported(format)) {
            return false;
        }
        if (this.isVideo) {
            if (format.width <= 0 || format.height <= 0) {
                return true;
            }
            if (Util.SDK_INT >= 21) {
                return isVideoSizeAndRateSupportedV21(format.width, format.height, format.frameRate);
            }
            boolean z = format.width * format.height <= MediaCodecUtil.maxH264DecodableFrameSize();
            if (!z) {
                logNoSupport(new StringBuilder(40).append("legacyFrameSize, ").append(format.width).append("x").append(format.height).toString());
            }
            return z;
        }
        if (Util.SDK_INT >= 21) {
            if (format.sampleRate != -1 && !isAudioSampleRateSupportedV21(format.sampleRate)) {
                return false;
            }
            if (format.channelCount != -1 && !isAudioChannelCountSupportedV21(format.channelCount)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCodecSupported(Format format) {
        String mediaMimeType;
        if (format.codecs == null || this.mimeType == null || (mediaMimeType = MimeTypes.getMediaMimeType(format.codecs)) == null) {
            return true;
        }
        if (!this.mimeType.equals(mediaMimeType)) {
            String str = format.codecs;
            logNoSupport(new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(mediaMimeType).length()).append("codec.mime ").append(str).append(", ").append(mediaMimeType).toString());
            return false;
        }
        Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
        if (codecProfileAndLevel == null) {
            return true;
        }
        int iIntValue = ((Integer) codecProfileAndLevel.first).intValue();
        int iIntValue2 = ((Integer) codecProfileAndLevel.second).intValue();
        if (!this.isVideo && iIntValue != 42) {
            return true;
        }
        MediaCodecInfo.CodecProfileLevel[] profileLevels = getProfileLevels();
        if (Util.SDK_INT <= 23 && MimeTypes.VIDEO_VP9.equals(this.mimeType) && profileLevels.length == 0) {
            profileLevels = estimateLegacyVp9ProfileLevels(this.capabilities);
        }
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : profileLevels) {
            if (codecProfileLevel.profile == iIntValue && codecProfileLevel.level >= iIntValue2) {
                return true;
            }
        }
        String str2 = format.codecs;
        logNoSupport(new StringBuilder(String.valueOf(str2).length() + 22 + String.valueOf(mediaMimeType).length()).append("codec.profileLevel, ").append(str2).append(", ").append(mediaMimeType).toString());
        return false;
    }

    public boolean isHdr10PlusOutOfBandMetadataSupported() {
        if (Util.SDK_INT >= 29 && MimeTypes.VIDEO_VP9.equals(this.mimeType)) {
            for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : getProfileLevels()) {
                if (codecProfileLevel.profile == 16384) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSeamlessAdaptationSupported(Format format) {
        if (this.isVideo) {
            return this.adaptive;
        }
        Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
        return codecProfileAndLevel != null && ((Integer) codecProfileAndLevel.first).intValue() == 42;
    }

    @Deprecated
    public boolean isSeamlessAdaptationSupported(Format oldFormat, Format newFormat, boolean isNewFormatComplete) {
        if (!isNewFormatComplete && oldFormat.colorInfo != null && newFormat.colorInfo == null) {
            newFormat = newFormat.buildUpon().setColorInfo(oldFormat.colorInfo).build();
        }
        int i = canReuseCodec(oldFormat, newFormat).result;
        return i == 2 || i == 3;
    }

    public DecoderReuseEvaluation canReuseCodec(Format oldFormat, Format newFormat) {
        int i = !Util.areEqual(oldFormat.sampleMimeType, newFormat.sampleMimeType) ? 8 : 0;
        if (this.isVideo) {
            if (oldFormat.rotationDegrees != newFormat.rotationDegrees) {
                i |= 1024;
            }
            if (!this.adaptive && (oldFormat.width != newFormat.width || oldFormat.height != newFormat.height)) {
                i |= 512;
            }
            if (!Util.areEqual(oldFormat.colorInfo, newFormat.colorInfo)) {
                i |= 2048;
            }
            if (needsAdaptationReconfigureWorkaround(this.name) && !oldFormat.initializationDataEquals(newFormat)) {
                i |= 2;
            }
            if (i == 0) {
                return new DecoderReuseEvaluation(this.name, oldFormat, newFormat, oldFormat.initializationDataEquals(newFormat) ? 3 : 2, 0);
            }
        } else {
            if (oldFormat.channelCount != newFormat.channelCount) {
                i |= 4096;
            }
            if (oldFormat.sampleRate != newFormat.sampleRate) {
                i |= 8192;
            }
            if (oldFormat.pcmEncoding != newFormat.pcmEncoding) {
                i |= 16384;
            }
            if (i == 0 && MimeTypes.AUDIO_AAC.equals(this.mimeType)) {
                Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(oldFormat);
                Pair<Integer, Integer> codecProfileAndLevel2 = MediaCodecUtil.getCodecProfileAndLevel(newFormat);
                if (codecProfileAndLevel != null && codecProfileAndLevel2 != null) {
                    int iIntValue = ((Integer) codecProfileAndLevel.first).intValue();
                    int iIntValue2 = ((Integer) codecProfileAndLevel2.first).intValue();
                    if (iIntValue == 42 && iIntValue2 == 42) {
                        return new DecoderReuseEvaluation(this.name, oldFormat, newFormat, 3, 0);
                    }
                }
            }
            if (!oldFormat.initializationDataEquals(newFormat)) {
                i |= 32;
            }
            if (needsAdaptationFlushWorkaround(this.mimeType)) {
                i |= 2;
            }
            if (i == 0) {
                return new DecoderReuseEvaluation(this.name, oldFormat, newFormat, 1, 0);
            }
        }
        return new DecoderReuseEvaluation(this.name, oldFormat, newFormat, 0, i);
    }

    public boolean isVideoSizeAndRateSupportedV21(int width, int height, double frameRate) {
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            logNoSupport("sizeAndRate.caps");
            return false;
        }
        MediaCodecInfo.VideoCapabilities videoCapabilities = codecCapabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            logNoSupport("sizeAndRate.vCaps");
            return false;
        }
        if (areSizeAndRateSupportedV21(videoCapabilities, width, height, frameRate)) {
            return true;
        }
        if (width >= height || !needsRotatedVerticalResolutionWorkaround(this.name) || !areSizeAndRateSupportedV21(videoCapabilities, height, width, frameRate)) {
            logNoSupport(new StringBuilder(69).append("sizeAndRate.support, ").append(width).append("x").append(height).append("x").append(frameRate).toString());
            return false;
        }
        logAssumedSupport(new StringBuilder(69).append("sizeAndRate.rotated, ").append(width).append("x").append(height).append("x").append(frameRate).toString());
        return true;
    }

    public Point alignVideoSizeV21(int width, int height) {
        MediaCodecInfo.VideoCapabilities videoCapabilities;
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null || (videoCapabilities = codecCapabilities.getVideoCapabilities()) == null) {
            return null;
        }
        return alignVideoSizeV21(videoCapabilities, width, height);
    }

    public boolean isAudioSampleRateSupportedV21(int sampleRate) {
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            logNoSupport("sampleRate.caps");
            return false;
        }
        MediaCodecInfo.AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("sampleRate.aCaps");
            return false;
        }
        if (audioCapabilities.isSampleRateSupported(sampleRate)) {
            return true;
        }
        logNoSupport(new StringBuilder(31).append("sampleRate.support, ").append(sampleRate).toString());
        return false;
    }

    public boolean isAudioChannelCountSupportedV21(int channelCount) {
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            logNoSupport("channelCount.caps");
            return false;
        }
        MediaCodecInfo.AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("channelCount.aCaps");
            return false;
        }
        if (adjustMaxInputChannelCount(this.name, this.mimeType, audioCapabilities.getMaxInputChannelCount()) >= channelCount) {
            return true;
        }
        logNoSupport(new StringBuilder(33).append("channelCount.support, ").append(channelCount).toString());
        return false;
    }

    private void logNoSupport(String message) {
        String str = this.name;
        String str2 = this.mimeType;
        String str3 = Util.DEVICE_DEBUG_INFO;
        Log.d(TAG, new StringBuilder(String.valueOf(message).length() + 20 + String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(str3).length()).append("NoSupport [").append(message).append("] [").append(str).append(", ").append(str2).append("] [").append(str3).append("]").toString());
    }

    private void logAssumedSupport(String message) {
        String str = this.name;
        String str2 = this.mimeType;
        String str3 = Util.DEVICE_DEBUG_INFO;
        Log.d(TAG, new StringBuilder(String.valueOf(message).length() + 25 + String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(str3).length()).append("AssumedSupport [").append(message).append("] [").append(str).append(", ").append(str2).append("] [").append(str3).append("]").toString());
    }

    private static int adjustMaxInputChannelCount(String name, String mimeType, int maxChannelCount) {
        int i;
        if (maxChannelCount > 1 || ((Util.SDK_INT >= 26 && maxChannelCount > 0) || MimeTypes.AUDIO_MPEG.equals(mimeType) || MimeTypes.AUDIO_AMR_NB.equals(mimeType) || MimeTypes.AUDIO_AMR_WB.equals(mimeType) || MimeTypes.AUDIO_AAC.equals(mimeType) || MimeTypes.AUDIO_VORBIS.equals(mimeType) || MimeTypes.AUDIO_OPUS.equals(mimeType) || MimeTypes.AUDIO_RAW.equals(mimeType) || MimeTypes.AUDIO_FLAC.equals(mimeType) || MimeTypes.AUDIO_ALAW.equals(mimeType) || MimeTypes.AUDIO_MLAW.equals(mimeType) || MimeTypes.AUDIO_MSGSM.equals(mimeType))) {
            return maxChannelCount;
        }
        if (MimeTypes.AUDIO_AC3.equals(mimeType)) {
            i = 6;
        } else {
            i = MimeTypes.AUDIO_E_AC3.equals(mimeType) ? 16 : 30;
        }
        Log.w(TAG, new StringBuilder(String.valueOf(name).length() + 59).append("AssumedMaxChannelAdjustment: ").append(name).append(", [").append(maxChannelCount).append(" to ").append(i).append("]").toString());
        return i;
    }

    private static boolean isAdaptive(MediaCodecInfo.CodecCapabilities capabilities) {
        return Util.SDK_INT >= 19 && isAdaptiveV19(capabilities);
    }

    private static boolean isAdaptiveV19(MediaCodecInfo.CodecCapabilities capabilities) {
        return capabilities.isFeatureSupported("adaptive-playback");
    }

    private static boolean isTunneling(MediaCodecInfo.CodecCapabilities capabilities) {
        return Util.SDK_INT >= 21 && isTunnelingV21(capabilities);
    }

    private static boolean isTunnelingV21(MediaCodecInfo.CodecCapabilities capabilities) {
        return capabilities.isFeatureSupported("tunneled-playback");
    }

    private static boolean isSecure(MediaCodecInfo.CodecCapabilities capabilities) {
        return Util.SDK_INT >= 21 && isSecureV21(capabilities);
    }

    private static boolean isSecureV21(MediaCodecInfo.CodecCapabilities capabilities) {
        return capabilities.isFeatureSupported("secure-playback");
    }

    private static boolean areSizeAndRateSupportedV21(MediaCodecInfo.VideoCapabilities capabilities, int width, int height, double frameRate) {
        Point pointAlignVideoSizeV21 = alignVideoSizeV21(capabilities, width, height);
        int i = pointAlignVideoSizeV21.x;
        int i2 = pointAlignVideoSizeV21.y;
        if (frameRate == -1.0d || frameRate < 1.0d) {
            return capabilities.isSizeSupported(i, i2);
        }
        return capabilities.areSizeAndRateSupported(i, i2, Math.floor(frameRate));
    }

    private static Point alignVideoSizeV21(MediaCodecInfo.VideoCapabilities capabilities, int width, int height) {
        int widthAlignment = capabilities.getWidthAlignment();
        int heightAlignment = capabilities.getHeightAlignment();
        return new Point(Util.ceilDivide(width, widthAlignment) * widthAlignment, Util.ceilDivide(height, heightAlignment) * heightAlignment);
    }

    private static int getMaxSupportedInstancesV23(MediaCodecInfo.CodecCapabilities capabilities) {
        return capabilities.getMaxSupportedInstances();
    }

    private static MediaCodecInfo.CodecProfileLevel[] estimateLegacyVp9ProfileLevels(MediaCodecInfo.CodecCapabilities capabilities) {
        MediaCodecInfo.VideoCapabilities videoCapabilities;
        int iIntValue = (capabilities == null || (videoCapabilities = capabilities.getVideoCapabilities()) == null) ? 0 : ((Integer) videoCapabilities.getBitrateRange().getUpper()).intValue();
        int i = iIntValue >= 180000000 ? 1024 : iIntValue >= 120000000 ? 512 : iIntValue >= 60000000 ? 256 : iIntValue >= 30000000 ? 128 : iIntValue >= 18000000 ? 64 : iIntValue >= 12000000 ? 32 : iIntValue >= 7200000 ? 16 : iIntValue >= 3600000 ? 8 : iIntValue >= 1800000 ? 4 : iIntValue >= 800000 ? 2 : 1;
        MediaCodecInfo.CodecProfileLevel codecProfileLevel = new MediaCodecInfo.CodecProfileLevel();
        codecProfileLevel.profile = 1;
        codecProfileLevel.level = i;
        return new MediaCodecInfo.CodecProfileLevel[]{codecProfileLevel};
    }

    private static boolean needsDisableAdaptationWorkaround(String name) {
        return Util.SDK_INT <= 22 && ("ODROID-XU3".equals(Util.MODEL) || "Nexus 10".equals(Util.MODEL)) && ("OMX.Exynos.AVC.Decoder".equals(name) || "OMX.Exynos.AVC.Decoder.secure".equals(name));
    }

    private static boolean needsAdaptationReconfigureWorkaround(String name) {
        return Util.MODEL.startsWith("SM-T230") && "OMX.MARVELL.VIDEO.HW.CODA7542DECODER".equals(name);
    }

    private static boolean needsAdaptationFlushWorkaround(String mimeType) {
        return MimeTypes.AUDIO_OPUS.equals(mimeType);
    }

    private static final boolean needsRotatedVerticalResolutionWorkaround(String name) {
        return ("OMX.MTK.VIDEO.DECODER.HEVC".equals(name) && "mcv5a".equals(Util.DEVICE)) ? false : true;
    }
}
