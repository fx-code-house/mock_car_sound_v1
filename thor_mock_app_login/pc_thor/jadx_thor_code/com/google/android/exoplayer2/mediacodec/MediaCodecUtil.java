package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharUtils;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes.dex */
public final class MediaCodecUtil {
    private static final String CODEC_ID_AV01 = "av01";
    private static final String CODEC_ID_AVC1 = "avc1";
    private static final String CODEC_ID_AVC2 = "avc2";
    private static final String CODEC_ID_HEV1 = "hev1";
    private static final String CODEC_ID_HVC1 = "hvc1";
    private static final String CODEC_ID_MP4A = "mp4a";
    private static final String CODEC_ID_VP09 = "vp09";
    private static final String TAG = "MediaCodecUtil";
    private static final Pattern PROFILE_PATTERN = Pattern.compile("^\\D?(\\d+)$");
    private static final HashMap<CodecKey, List<MediaCodecInfo>> decoderInfosCache = new HashMap<>();
    private static int maxH264DecodableFrameSize = -1;

    private interface MediaCodecListCompat {
        int getCodecCount();

        android.media.MediaCodecInfo getCodecInfoAt(int index);

        boolean isFeatureRequired(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities);

        boolean isFeatureSupported(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities);

        boolean secureDecodersExplicit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface ScoreProvider<T> {
        int getScore(T t);
    }

    private static int av1LevelNumberToConst(int levelNumber) {
        switch (levelNumber) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 16;
            case 5:
                return 32;
            case 6:
                return 64;
            case 7:
                return 128;
            case 8:
                return 256;
            case 9:
                return 512;
            case 10:
                return 1024;
            case 11:
                return 2048;
            case 12:
                return 4096;
            case 13:
                return 8192;
            case 14:
                return 16384;
            case 15:
                return 32768;
            case 16:
                return 65536;
            case 17:
                return 131072;
            case 18:
                return 262144;
            case 19:
                return 524288;
            case 20:
                return 1048576;
            case 21:
                return 2097152;
            case 22:
                return 4194304;
            case 23:
                return 8388608;
            default:
                return -1;
        }
    }

    private static int avcLevelNumberToConst(int levelNumber) {
        switch (levelNumber) {
            case 10:
                return 1;
            case 11:
                return 4;
            case 12:
                return 8;
            case 13:
                return 16;
            default:
                switch (levelNumber) {
                    case 20:
                        return 32;
                    case 21:
                        return 64;
                    case 22:
                        return 128;
                    default:
                        switch (levelNumber) {
                            case 30:
                                return 256;
                            case 31:
                                return 512;
                            case 32:
                                return 1024;
                            default:
                                switch (levelNumber) {
                                    case 40:
                                        return 2048;
                                    case 41:
                                        return 4096;
                                    case 42:
                                        return 8192;
                                    default:
                                        switch (levelNumber) {
                                            case 50:
                                                return 16384;
                                            case 51:
                                                return 32768;
                                            case 52:
                                                return 65536;
                                            default:
                                                return -1;
                                        }
                                }
                        }
                }
        }
    }

    private static int avcLevelToMaxFrameSize(int avcLevel) {
        if (avcLevel == 1 || avcLevel == 2) {
            return 25344;
        }
        switch (avcLevel) {
            case 8:
            case 16:
            case 32:
                return 101376;
            case 64:
                return 202752;
            case 128:
            case 256:
                return 414720;
            case 512:
                return 921600;
            case 1024:
                return 1310720;
            case 2048:
            case 4096:
                return 2097152;
            case 8192:
                return 2228224;
            case 16384:
                return 5652480;
            case 32768:
            case 65536:
                return 9437184;
            case 131072:
            case 262144:
            case 524288:
                return 35651584;
            default:
                return -1;
        }
    }

    private static int avcProfileNumberToConst(int profileNumber) {
        if (profileNumber == 66) {
            return 1;
        }
        if (profileNumber == 77) {
            return 2;
        }
        if (profileNumber == 88) {
            return 4;
        }
        if (profileNumber == 100) {
            return 8;
        }
        if (profileNumber == 110) {
            return 16;
        }
        if (profileNumber != 122) {
            return profileNumber != 244 ? -1 : 64;
        }
        return 32;
    }

    private static int mp4aAudioObjectTypeToProfile(int profileNumber) {
        int i = 17;
        if (profileNumber != 17) {
            i = 20;
            if (profileNumber != 20) {
                i = 23;
                if (profileNumber != 23) {
                    i = 29;
                    if (profileNumber != 29) {
                        i = 39;
                        if (profileNumber != 39) {
                            i = 42;
                            if (profileNumber != 42) {
                                switch (profileNumber) {
                                    case 1:
                                        return 1;
                                    case 2:
                                        return 2;
                                    case 3:
                                        return 3;
                                    case 4:
                                        return 4;
                                    case 5:
                                        return 5;
                                    case 6:
                                        return 6;
                                    default:
                                        return -1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return i;
    }

    private static int vp9LevelNumberToConst(int levelNumber) {
        if (levelNumber == 10) {
            return 1;
        }
        if (levelNumber == 11) {
            return 2;
        }
        if (levelNumber == 20) {
            return 4;
        }
        if (levelNumber == 21) {
            return 8;
        }
        if (levelNumber == 30) {
            return 16;
        }
        if (levelNumber == 31) {
            return 32;
        }
        if (levelNumber == 40) {
            return 64;
        }
        if (levelNumber == 41) {
            return 128;
        }
        if (levelNumber == 50) {
            return 256;
        }
        if (levelNumber == 51) {
            return 512;
        }
        switch (levelNumber) {
            case 60:
                return 2048;
            case 61:
                return 4096;
            case 62:
                return 8192;
            default:
                return -1;
        }
    }

    private static int vp9ProfileNumberToConst(int profileNumber) {
        if (profileNumber == 0) {
            return 1;
        }
        if (profileNumber == 1) {
            return 2;
        }
        if (profileNumber != 2) {
            return profileNumber != 3 ? -1 : 8;
        }
        return 4;
    }

    public static class DecoderQueryException extends Exception {
        private DecoderQueryException(Throwable cause) {
            super("Failed to query underlying media codecs", cause);
        }
    }

    private MediaCodecUtil() {
    }

    public static void warmDecoderInfoCache(String mimeType, boolean secure, boolean tunneling) {
        try {
            getDecoderInfos(mimeType, secure, tunneling);
        } catch (DecoderQueryException e) {
            Log.e(TAG, "Codec warming failed", e);
        }
    }

    public static synchronized void clearDecoderInfoCache() {
        decoderInfosCache.clear();
    }

    public static MediaCodecInfo getDecryptOnlyDecoderInfo() throws DecoderQueryException {
        return getDecoderInfo(MimeTypes.AUDIO_RAW, false, false);
    }

    public static MediaCodecInfo getDecoderInfo(String mimeType, boolean secure, boolean tunneling) throws DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mimeType, secure, tunneling);
        if (decoderInfos.isEmpty()) {
            return null;
        }
        return decoderInfos.get(0);
    }

    public static synchronized List<MediaCodecInfo> getDecoderInfos(String mimeType, boolean secure, boolean tunneling) throws DecoderQueryException {
        MediaCodecListCompat mediaCodecListCompatV16;
        CodecKey codecKey = new CodecKey(mimeType, secure, tunneling);
        HashMap<CodecKey, List<MediaCodecInfo>> map = decoderInfosCache;
        List<MediaCodecInfo> list = map.get(codecKey);
        if (list != null) {
            return list;
        }
        if (Util.SDK_INT >= 21) {
            mediaCodecListCompatV16 = new MediaCodecListCompatV21(secure, tunneling);
        } else {
            mediaCodecListCompatV16 = new MediaCodecListCompatV16();
        }
        ArrayList<MediaCodecInfo> decoderInfosInternal = getDecoderInfosInternal(codecKey, mediaCodecListCompatV16);
        if (secure && decoderInfosInternal.isEmpty() && 21 <= Util.SDK_INT && Util.SDK_INT <= 23) {
            decoderInfosInternal = getDecoderInfosInternal(codecKey, new MediaCodecListCompatV16());
            if (!decoderInfosInternal.isEmpty()) {
                String str = decoderInfosInternal.get(0).name;
                Log.w(TAG, new StringBuilder(String.valueOf(mimeType).length() + 63 + String.valueOf(str).length()).append("MediaCodecList API didn't list secure decoder for: ").append(mimeType).append(". Assuming: ").append(str).toString());
            }
        }
        applyWorkarounds(mimeType, decoderInfosInternal);
        List<MediaCodecInfo> listUnmodifiableList = Collections.unmodifiableList(decoderInfosInternal);
        map.put(codecKey, listUnmodifiableList);
        return listUnmodifiableList;
    }

    public static List<MediaCodecInfo> getDecoderInfosSortedByFormatSupport(List<MediaCodecInfo> decoderInfos, final Format format) {
        ArrayList arrayList = new ArrayList(decoderInfos);
        sortByScore(arrayList, new ScoreProvider() { // from class: com.google.android.exoplayer2.mediacodec.MediaCodecUtil$$ExternalSyntheticLambda3
            @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.ScoreProvider
            public final int getScore(Object obj) {
                return MediaCodecUtil.lambda$getDecoderInfosSortedByFormatSupport$0(format, (MediaCodecInfo) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ int lambda$getDecoderInfosSortedByFormatSupport$0(Format format, MediaCodecInfo mediaCodecInfo) {
        try {
            return mediaCodecInfo.isFormatSupported(format) ? 1 : 0;
        } catch (DecoderQueryException unused) {
            return -1;
        }
    }

    public static int maxH264DecodableFrameSize() throws DecoderQueryException {
        if (maxH264DecodableFrameSize == -1) {
            int iMax = 0;
            MediaCodecInfo decoderInfo = getDecoderInfo(MimeTypes.VIDEO_H264, false, false);
            if (decoderInfo != null) {
                MediaCodecInfo.CodecProfileLevel[] profileLevels = decoderInfo.getProfileLevels();
                int length = profileLevels.length;
                int iMax2 = 0;
                while (iMax < length) {
                    iMax2 = Math.max(avcLevelToMaxFrameSize(profileLevels[iMax].level), iMax2);
                    iMax++;
                }
                iMax = Math.max(iMax2, Util.SDK_INT >= 21 ? 345600 : 172800);
            }
            maxH264DecodableFrameSize = iMax;
        }
        return maxH264DecodableFrameSize;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> getCodecProfileAndLevel(com.google.android.exoplayer2.Format r6) {
        /*
            java.lang.String r0 = r6.codecs
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            java.lang.String r0 = r6.codecs
            java.lang.String r2 = "\\."
            java.lang.String[] r0 = r0.split(r2)
            java.lang.String r2 = "video/dolby-vision"
            java.lang.String r3 = r6.sampleMimeType
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L20
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getDolbyVisionProfileAndLevel(r6, r0)
            return r6
        L20:
            r2 = 0
            r3 = r0[r2]
            r3.hashCode()
            int r4 = r3.hashCode()
            r5 = -1
            switch(r4) {
                case 3004662: goto L73;
                case 3006243: goto L68;
                case 3006244: goto L5d;
                case 3199032: goto L52;
                case 3214780: goto L47;
                case 3356560: goto L3c;
                case 3624515: goto L30;
                default: goto L2e;
            }
        L2e:
            r2 = r5
            goto L7c
        L30:
            java.lang.String r2 = "vp09"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L3a
            goto L2e
        L3a:
            r2 = 6
            goto L7c
        L3c:
            java.lang.String r2 = "mp4a"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L45
            goto L2e
        L45:
            r2 = 5
            goto L7c
        L47:
            java.lang.String r2 = "hvc1"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L50
            goto L2e
        L50:
            r2 = 4
            goto L7c
        L52:
            java.lang.String r2 = "hev1"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L5b
            goto L2e
        L5b:
            r2 = 3
            goto L7c
        L5d:
            java.lang.String r2 = "avc2"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L66
            goto L2e
        L66:
            r2 = 2
            goto L7c
        L68:
            java.lang.String r2 = "avc1"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L71
            goto L2e
        L71:
            r2 = 1
            goto L7c
        L73:
            java.lang.String r4 = "av01"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L7c
            goto L2e
        L7c:
            switch(r2) {
                case 0: goto L9c;
                case 1: goto L95;
                case 2: goto L95;
                case 3: goto L8e;
                case 4: goto L8e;
                case 5: goto L87;
                case 6: goto L80;
                default: goto L7f;
            }
        L7f:
            return r1
        L80:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getVp9ProfileAndLevel(r6, r0)
            return r6
        L87:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getAacCodecProfileAndLevel(r6, r0)
            return r6
        L8e:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getHevcProfileAndLevel(r6, r0)
            return r6
        L95:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getAvcProfileAndLevel(r6, r0)
            return r6
        L9c:
            java.lang.String r1 = r6.codecs
            com.google.android.exoplayer2.video.ColorInfo r6 = r6.colorInfo
            android.util.Pair r6 = getAv1ProfileAndLevel(r1, r0, r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getCodecProfileAndLevel(com.google.android.exoplayer2.Format):android.util.Pair");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.util.ArrayList<com.google.android.exoplayer2.mediacodec.MediaCodecInfo> getDecoderInfosInternal(com.google.android.exoplayer2.mediacodec.MediaCodecUtil.CodecKey r24, com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat r25) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 358
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getDecoderInfosInternal(com.google.android.exoplayer2.mediacodec.MediaCodecUtil$CodecKey, com.google.android.exoplayer2.mediacodec.MediaCodecUtil$MediaCodecListCompat):java.util.ArrayList");
    }

    private static String getCodecMimeType(android.media.MediaCodecInfo info, String name, String mimeType) {
        for (String str : info.getSupportedTypes()) {
            if (str.equalsIgnoreCase(mimeType)) {
                return str;
            }
        }
        if (mimeType.equals(MimeTypes.VIDEO_DOLBY_VISION)) {
            if ("OMX.MS.HEVCDV.Decoder".equals(name)) {
                return "video/hevcdv";
            }
            if ("OMX.RTK.video.decoder".equals(name) || "OMX.realtek.video.decoder.tunneled".equals(name)) {
                return "video/dv_hevc";
            }
            return null;
        }
        if (mimeType.equals(MimeTypes.AUDIO_ALAC) && "OMX.lge.alac.decoder".equals(name)) {
            return "audio/x-lg-alac";
        }
        if (mimeType.equals(MimeTypes.AUDIO_FLAC) && "OMX.lge.flac.decoder".equals(name)) {
            return "audio/x-lg-flac";
        }
        return null;
    }

    private static boolean isCodecUsableDecoder(android.media.MediaCodecInfo info, String name, boolean secureDecodersExplicit, String mimeType) {
        if (info.isEncoder() || (!secureDecodersExplicit && name.endsWith(".secure"))) {
            return false;
        }
        if (Util.SDK_INT < 21 && ("CIPAACDecoder".equals(name) || "CIPMP3Decoder".equals(name) || "CIPVorbisDecoder".equals(name) || "CIPAMRNBDecoder".equals(name) || "AACDecoder".equals(name) || "MP3Decoder".equals(name))) {
            return false;
        }
        if (Util.SDK_INT < 18 && "OMX.MTK.AUDIO.DECODER.AAC".equals(name) && ("a70".equals(Util.DEVICE) || ("Xiaomi".equals(Util.MANUFACTURER) && Util.DEVICE.startsWith("HM")))) {
            return false;
        }
        if (Util.SDK_INT == 16 && "OMX.qcom.audio.decoder.mp3".equals(name) && ("dlxu".equals(Util.DEVICE) || "protou".equals(Util.DEVICE) || "ville".equals(Util.DEVICE) || "villeplus".equals(Util.DEVICE) || "villec2".equals(Util.DEVICE) || Util.DEVICE.startsWith("gee") || "C6602".equals(Util.DEVICE) || "C6603".equals(Util.DEVICE) || "C6606".equals(Util.DEVICE) || "C6616".equals(Util.DEVICE) || "L36h".equals(Util.DEVICE) || "SO-02E".equals(Util.DEVICE))) {
            return false;
        }
        if (Util.SDK_INT == 16 && "OMX.qcom.audio.decoder.aac".equals(name) && ("C1504".equals(Util.DEVICE) || "C1505".equals(Util.DEVICE) || "C1604".equals(Util.DEVICE) || "C1605".equals(Util.DEVICE))) {
            return false;
        }
        if (Util.SDK_INT < 24 && (("OMX.SEC.aac.dec".equals(name) || "OMX.Exynos.AAC.Decoder".equals(name)) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("zerolte") || Util.DEVICE.startsWith("zenlte") || "SC-05G".equals(Util.DEVICE) || "marinelteatt".equals(Util.DEVICE) || "404SC".equals(Util.DEVICE) || "SC-04G".equals(Util.DEVICE) || "SCV31".equals(Util.DEVICE)))) {
            return false;
        }
        if (Util.SDK_INT <= 19 && "OMX.SEC.vp8.dec".equals(name) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("d2") || Util.DEVICE.startsWith("serrano") || Util.DEVICE.startsWith("jflte") || Util.DEVICE.startsWith("santos") || Util.DEVICE.startsWith("t0"))) {
            return false;
        }
        if (Util.SDK_INT <= 19 && Util.DEVICE.startsWith("jflte") && "OMX.qcom.video.decoder.vp8".equals(name)) {
            return false;
        }
        return (MimeTypes.AUDIO_E_AC3_JOC.equals(mimeType) && "OMX.MTK.AUDIO.DECODER.DSPAC3".equals(name)) ? false : true;
    }

    private static void applyWorkarounds(String mimeType, List<MediaCodecInfo> decoderInfos) {
        if (MimeTypes.AUDIO_RAW.equals(mimeType)) {
            if (Util.SDK_INT < 26 && Util.DEVICE.equals("R9") && decoderInfos.size() == 1 && decoderInfos.get(0).name.equals("OMX.MTK.AUDIO.DECODER.RAW")) {
                decoderInfos.add(MediaCodecInfo.newInstance("OMX.google.raw.decoder", MimeTypes.AUDIO_RAW, MimeTypes.AUDIO_RAW, null, false, true, false, false, false));
            }
            sortByScore(decoderInfos, new ScoreProvider() { // from class: com.google.android.exoplayer2.mediacodec.MediaCodecUtil$$ExternalSyntheticLambda1
                @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.ScoreProvider
                public final int getScore(Object obj) {
                    return MediaCodecUtil.lambda$applyWorkarounds$1((MediaCodecInfo) obj);
                }
            });
        }
        if (Util.SDK_INT < 21 && decoderInfos.size() > 1) {
            String str = decoderInfos.get(0).name;
            if ("OMX.SEC.mp3.dec".equals(str) || "OMX.SEC.MP3.Decoder".equals(str) || "OMX.brcm.audio.mp3.decoder".equals(str)) {
                sortByScore(decoderInfos, new ScoreProvider() { // from class: com.google.android.exoplayer2.mediacodec.MediaCodecUtil$$ExternalSyntheticLambda2
                    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.ScoreProvider
                    public final int getScore(Object obj) {
                        return MediaCodecUtil.lambda$applyWorkarounds$2((MediaCodecInfo) obj);
                    }
                });
            }
        }
        if (Util.SDK_INT >= 30 || decoderInfos.size() <= 1 || !"OMX.qti.audio.decoder.flac".equals(decoderInfos.get(0).name)) {
            return;
        }
        decoderInfos.add(decoderInfos.remove(0));
    }

    static /* synthetic */ int lambda$applyWorkarounds$1(MediaCodecInfo mediaCodecInfo) {
        String str = mediaCodecInfo.name;
        if (str.startsWith("OMX.google") || str.startsWith("c2.android")) {
            return 1;
        }
        return (Util.SDK_INT >= 26 || !str.equals("OMX.MTK.AUDIO.DECODER.RAW")) ? 0 : -1;
    }

    static /* synthetic */ int lambda$applyWorkarounds$2(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.name.startsWith("OMX.google") ? 1 : 0;
    }

    private static boolean isAlias(android.media.MediaCodecInfo info) {
        return Util.SDK_INT >= 29 && isAliasV29(info);
    }

    private static boolean isAliasV29(android.media.MediaCodecInfo info) {
        return info.isAlias();
    }

    private static boolean isHardwareAccelerated(android.media.MediaCodecInfo codecInfo) {
        if (Util.SDK_INT >= 29) {
            return isHardwareAcceleratedV29(codecInfo);
        }
        return !isSoftwareOnly(codecInfo);
    }

    private static boolean isHardwareAcceleratedV29(android.media.MediaCodecInfo codecInfo) {
        return codecInfo.isHardwareAccelerated();
    }

    private static boolean isSoftwareOnly(android.media.MediaCodecInfo codecInfo) {
        if (Util.SDK_INT >= 29) {
            return isSoftwareOnlyV29(codecInfo);
        }
        String lowerCase = Ascii.toLowerCase(codecInfo.getName());
        if (lowerCase.startsWith("arc.")) {
            return false;
        }
        return lowerCase.startsWith("omx.google.") || lowerCase.startsWith("omx.ffmpeg.") || (lowerCase.startsWith("omx.sec.") && lowerCase.contains(".sw.")) || lowerCase.equals("omx.qcom.video.decoder.hevcswvdec") || lowerCase.startsWith("c2.android.") || lowerCase.startsWith("c2.google.") || !(lowerCase.startsWith("omx.") || lowerCase.startsWith("c2."));
    }

    private static boolean isSoftwareOnlyV29(android.media.MediaCodecInfo codecInfo) {
        return codecInfo.isSoftwareOnly();
    }

    private static boolean isVendor(android.media.MediaCodecInfo codecInfo) {
        if (Util.SDK_INT >= 29) {
            return isVendorV29(codecInfo);
        }
        String lowerCase = Ascii.toLowerCase(codecInfo.getName());
        return (lowerCase.startsWith("omx.google.") || lowerCase.startsWith("c2.android.") || lowerCase.startsWith("c2.google.")) ? false : true;
    }

    private static boolean isVendorV29(android.media.MediaCodecInfo codecInfo) {
        return codecInfo.isVendor();
    }

    private static Pair<Integer, Integer> getDolbyVisionProfileAndLevel(String codec, String[] parts) {
        if (parts.length < 3) {
            String strValueOf = String.valueOf(codec);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed Dolby Vision codec string: ".concat(strValueOf) : new String("Ignoring malformed Dolby Vision codec string: "));
            return null;
        }
        Matcher matcher = PROFILE_PATTERN.matcher(parts[1]);
        if (!matcher.matches()) {
            String strValueOf2 = String.valueOf(codec);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed Dolby Vision codec string: ".concat(strValueOf2) : new String("Ignoring malformed Dolby Vision codec string: "));
            return null;
        }
        String strGroup = matcher.group(1);
        Integer numDolbyVisionStringToProfile = dolbyVisionStringToProfile(strGroup);
        if (numDolbyVisionStringToProfile == null) {
            String strValueOf3 = String.valueOf(strGroup);
            Log.w(TAG, strValueOf3.length() != 0 ? "Unknown Dolby Vision profile string: ".concat(strValueOf3) : new String("Unknown Dolby Vision profile string: "));
            return null;
        }
        String str = parts[2];
        Integer numDolbyVisionStringToLevel = dolbyVisionStringToLevel(str);
        if (numDolbyVisionStringToLevel == null) {
            String strValueOf4 = String.valueOf(str);
            Log.w(TAG, strValueOf4.length() != 0 ? "Unknown Dolby Vision level string: ".concat(strValueOf4) : new String("Unknown Dolby Vision level string: "));
            return null;
        }
        return new Pair<>(numDolbyVisionStringToProfile, numDolbyVisionStringToLevel);
    }

    private static Pair<Integer, Integer> getHevcProfileAndLevel(String codec, String[] parts) {
        if (parts.length < 4) {
            String strValueOf = String.valueOf(codec);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed HEVC codec string: ".concat(strValueOf) : new String("Ignoring malformed HEVC codec string: "));
            return null;
        }
        int i = 1;
        Matcher matcher = PROFILE_PATTERN.matcher(parts[1]);
        if (!matcher.matches()) {
            String strValueOf2 = String.valueOf(codec);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed HEVC codec string: ".concat(strValueOf2) : new String("Ignoring malformed HEVC codec string: "));
            return null;
        }
        String strGroup = matcher.group(1);
        if (!IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(strGroup)) {
            if (!"2".equals(strGroup)) {
                String strValueOf3 = String.valueOf(strGroup);
                Log.w(TAG, strValueOf3.length() != 0 ? "Unknown HEVC profile string: ".concat(strValueOf3) : new String("Unknown HEVC profile string: "));
                return null;
            }
            i = 2;
        }
        String str = parts[3];
        Integer numHevcCodecStringToProfileLevel = hevcCodecStringToProfileLevel(str);
        if (numHevcCodecStringToProfileLevel == null) {
            String strValueOf4 = String.valueOf(str);
            Log.w(TAG, strValueOf4.length() != 0 ? "Unknown HEVC level string: ".concat(strValueOf4) : new String("Unknown HEVC level string: "));
            return null;
        }
        return new Pair<>(Integer.valueOf(i), numHevcCodecStringToProfileLevel);
    }

    private static Pair<Integer, Integer> getAvcProfileAndLevel(String codec, String[] parts) throws NumberFormatException {
        int i;
        int i2;
        if (parts.length < 2) {
            String strValueOf = String.valueOf(codec);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(strValueOf) : new String("Ignoring malformed AVC codec string: "));
            return null;
        }
        try {
            if (parts[1].length() == 6) {
                i2 = Integer.parseInt(parts[1].substring(0, 2), 16);
                i = Integer.parseInt(parts[1].substring(4), 16);
            } else if (parts.length >= 3) {
                int i3 = Integer.parseInt(parts[1]);
                i = Integer.parseInt(parts[2]);
                i2 = i3;
            } else {
                String strValueOf2 = String.valueOf(codec);
                Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(strValueOf2) : new String("Ignoring malformed AVC codec string: "));
                return null;
            }
            int iAvcProfileNumberToConst = avcProfileNumberToConst(i2);
            if (iAvcProfileNumberToConst == -1) {
                Log.w(TAG, new StringBuilder(32).append("Unknown AVC profile: ").append(i2).toString());
                return null;
            }
            int iAvcLevelNumberToConst = avcLevelNumberToConst(i);
            if (iAvcLevelNumberToConst == -1) {
                Log.w(TAG, new StringBuilder(30).append("Unknown AVC level: ").append(i).toString());
                return null;
            }
            return new Pair<>(Integer.valueOf(iAvcProfileNumberToConst), Integer.valueOf(iAvcLevelNumberToConst));
        } catch (NumberFormatException unused) {
            String strValueOf3 = String.valueOf(codec);
            Log.w(TAG, strValueOf3.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(strValueOf3) : new String("Ignoring malformed AVC codec string: "));
            return null;
        }
    }

    private static Pair<Integer, Integer> getVp9ProfileAndLevel(String codec, String[] parts) throws NumberFormatException {
        if (parts.length < 3) {
            String strValueOf = String.valueOf(codec);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed VP9 codec string: ".concat(strValueOf) : new String("Ignoring malformed VP9 codec string: "));
            return null;
        }
        try {
            int i = Integer.parseInt(parts[1]);
            int i2 = Integer.parseInt(parts[2]);
            int iVp9ProfileNumberToConst = vp9ProfileNumberToConst(i);
            if (iVp9ProfileNumberToConst == -1) {
                Log.w(TAG, new StringBuilder(32).append("Unknown VP9 profile: ").append(i).toString());
                return null;
            }
            int iVp9LevelNumberToConst = vp9LevelNumberToConst(i2);
            if (iVp9LevelNumberToConst == -1) {
                Log.w(TAG, new StringBuilder(30).append("Unknown VP9 level: ").append(i2).toString());
                return null;
            }
            return new Pair<>(Integer.valueOf(iVp9ProfileNumberToConst), Integer.valueOf(iVp9LevelNumberToConst));
        } catch (NumberFormatException unused) {
            String strValueOf2 = String.valueOf(codec);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed VP9 codec string: ".concat(strValueOf2) : new String("Ignoring malformed VP9 codec string: "));
            return null;
        }
    }

    private static Pair<Integer, Integer> getAv1ProfileAndLevel(String codec, String[] parts, ColorInfo colorInfo) throws NumberFormatException {
        if (parts.length < 4) {
            String strValueOf = String.valueOf(codec);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed AV1 codec string: ".concat(strValueOf) : new String("Ignoring malformed AV1 codec string: "));
            return null;
        }
        int i = 1;
        try {
            int i2 = Integer.parseInt(parts[1]);
            int i3 = Integer.parseInt(parts[2].substring(0, 2));
            int i4 = Integer.parseInt(parts[3]);
            if (i2 != 0) {
                Log.w(TAG, new StringBuilder(32).append("Unknown AV1 profile: ").append(i2).toString());
                return null;
            }
            if (i4 != 8 && i4 != 10) {
                Log.w(TAG, new StringBuilder(34).append("Unknown AV1 bit depth: ").append(i4).toString());
                return null;
            }
            if (i4 != 8) {
                i = (colorInfo == null || !(colorInfo.hdrStaticInfo != null || colorInfo.colorTransfer == 7 || colorInfo.colorTransfer == 6)) ? 2 : 4096;
            }
            int iAv1LevelNumberToConst = av1LevelNumberToConst(i3);
            if (iAv1LevelNumberToConst == -1) {
                Log.w(TAG, new StringBuilder(30).append("Unknown AV1 level: ").append(i3).toString());
                return null;
            }
            return new Pair<>(Integer.valueOf(i), Integer.valueOf(iAv1LevelNumberToConst));
        } catch (NumberFormatException unused) {
            String strValueOf2 = String.valueOf(codec);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed AV1 codec string: ".concat(strValueOf2) : new String("Ignoring malformed AV1 codec string: "));
            return null;
        }
    }

    private static Pair<Integer, Integer> getAacCodecProfileAndLevel(String codec, String[] parts) {
        int iMp4aAudioObjectTypeToProfile;
        if (parts.length != 3) {
            String strValueOf = String.valueOf(codec);
            Log.w(TAG, strValueOf.length() != 0 ? "Ignoring malformed MP4A codec string: ".concat(strValueOf) : new String("Ignoring malformed MP4A codec string: "));
            return null;
        }
        try {
            if (MimeTypes.AUDIO_AAC.equals(MimeTypes.getMimeTypeFromMp4ObjectType(Integer.parseInt(parts[1], 16))) && (iMp4aAudioObjectTypeToProfile = mp4aAudioObjectTypeToProfile(Integer.parseInt(parts[2]))) != -1) {
                return new Pair<>(Integer.valueOf(iMp4aAudioObjectTypeToProfile), 0);
            }
        } catch (NumberFormatException unused) {
            String strValueOf2 = String.valueOf(codec);
            Log.w(TAG, strValueOf2.length() != 0 ? "Ignoring malformed MP4A codec string: ".concat(strValueOf2) : new String("Ignoring malformed MP4A codec string: "));
        }
        return null;
    }

    static /* synthetic */ int lambda$sortByScore$3(ScoreProvider scoreProvider, Object obj, Object obj2) {
        return scoreProvider.getScore(obj2) - scoreProvider.getScore(obj);
    }

    private static <T> void sortByScore(List<T> list, final ScoreProvider<T> scoreProvider) {
        Collections.sort(list, new Comparator() { // from class: com.google.android.exoplayer2.mediacodec.MediaCodecUtil$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MediaCodecUtil.lambda$sortByScore$3(scoreProvider, obj, obj2);
            }
        });
    }

    private static final class MediaCodecListCompatV21 implements MediaCodecListCompat {
        private final int codecKind;
        private android.media.MediaCodecInfo[] mediaCodecInfos;

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public boolean secureDecodersExplicit() {
            return true;
        }

        public MediaCodecListCompatV21(boolean includeSecure, boolean includeTunneling) {
            this.codecKind = (includeSecure || includeTunneling) ? 1 : 0;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public int getCodecCount() {
            ensureMediaCodecInfosInitialized();
            return this.mediaCodecInfos.length;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public android.media.MediaCodecInfo getCodecInfoAt(int index) {
            ensureMediaCodecInfosInitialized();
            return this.mediaCodecInfos[index];
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public boolean isFeatureSupported(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return capabilities.isFeatureSupported(feature);
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public boolean isFeatureRequired(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return capabilities.isFeatureRequired(feature);
        }

        @EnsuresNonNull({"mediaCodecInfos"})
        private void ensureMediaCodecInfosInitialized() {
            if (this.mediaCodecInfos == null) {
                this.mediaCodecInfos = new MediaCodecList(this.codecKind).getCodecInfos();
            }
        }
    }

    private static final class MediaCodecListCompatV16 implements MediaCodecListCompat {
        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public boolean isFeatureRequired(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return false;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public boolean secureDecodersExplicit() {
            return false;
        }

        private MediaCodecListCompatV16() {
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public int getCodecCount() {
            return MediaCodecList.getCodecCount();
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public android.media.MediaCodecInfo getCodecInfoAt(int index) {
            return MediaCodecList.getCodecInfoAt(index);
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat
        public boolean isFeatureSupported(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return "secure-playback".equals(feature) && MimeTypes.VIDEO_H264.equals(mimeType);
        }
    }

    private static final class CodecKey {
        public final String mimeType;
        public final boolean secure;
        public final boolean tunneling;

        public CodecKey(String mimeType, boolean secure, boolean tunneling) {
            this.mimeType = mimeType;
            this.secure = secure;
            this.tunneling = tunneling;
        }

        public int hashCode() {
            return ((((this.mimeType.hashCode() + 31) * 31) + (this.secure ? 1231 : 1237)) * 31) + (this.tunneling ? 1231 : 1237);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != CodecKey.class) {
                return false;
            }
            CodecKey codecKey = (CodecKey) obj;
            return TextUtils.equals(this.mimeType, codecKey.mimeType) && this.secure == codecKey.secure && this.tunneling == codecKey.tunneling;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static Integer hevcCodecStringToProfileLevel(String codecString) {
        if (codecString == null) {
            return null;
        }
        codecString.hashCode();
        char c = 65535;
        switch (codecString.hashCode()) {
            case 70821:
                if (codecString.equals("H30")) {
                    c = 0;
                    break;
                }
                break;
            case 70914:
                if (codecString.equals("H60")) {
                    c = 1;
                    break;
                }
                break;
            case 70917:
                if (codecString.equals("H63")) {
                    c = 2;
                    break;
                }
                break;
            case 71007:
                if (codecString.equals("H90")) {
                    c = 3;
                    break;
                }
                break;
            case 71010:
                if (codecString.equals("H93")) {
                    c = 4;
                    break;
                }
                break;
            case 74665:
                if (codecString.equals("L30")) {
                    c = 5;
                    break;
                }
                break;
            case 74758:
                if (codecString.equals("L60")) {
                    c = 6;
                    break;
                }
                break;
            case 74761:
                if (codecString.equals("L63")) {
                    c = 7;
                    break;
                }
                break;
            case 74851:
                if (codecString.equals("L90")) {
                    c = '\b';
                    break;
                }
                break;
            case 74854:
                if (codecString.equals("L93")) {
                    c = '\t';
                    break;
                }
                break;
            case 2193639:
                if (codecString.equals("H120")) {
                    c = '\n';
                    break;
                }
                break;
            case 2193642:
                if (codecString.equals("H123")) {
                    c = 11;
                    break;
                }
                break;
            case 2193732:
                if (codecString.equals("H150")) {
                    c = '\f';
                    break;
                }
                break;
            case 2193735:
                if (codecString.equals("H153")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 2193738:
                if (codecString.equals("H156")) {
                    c = 14;
                    break;
                }
                break;
            case 2193825:
                if (codecString.equals("H180")) {
                    c = 15;
                    break;
                }
                break;
            case 2193828:
                if (codecString.equals("H183")) {
                    c = 16;
                    break;
                }
                break;
            case 2193831:
                if (codecString.equals("H186")) {
                    c = 17;
                    break;
                }
                break;
            case 2312803:
                if (codecString.equals("L120")) {
                    c = 18;
                    break;
                }
                break;
            case 2312806:
                if (codecString.equals("L123")) {
                    c = 19;
                    break;
                }
                break;
            case 2312896:
                if (codecString.equals("L150")) {
                    c = 20;
                    break;
                }
                break;
            case 2312899:
                if (codecString.equals("L153")) {
                    c = 21;
                    break;
                }
                break;
            case 2312902:
                if (codecString.equals("L156")) {
                    c = 22;
                    break;
                }
                break;
            case 2312989:
                if (codecString.equals("L180")) {
                    c = 23;
                    break;
                }
                break;
            case 2312992:
                if (codecString.equals("L183")) {
                    c = 24;
                    break;
                }
                break;
            case 2312995:
                if (codecString.equals("L186")) {
                    c = 25;
                    break;
                }
                break;
        }
        switch (c) {
        }
        return null;
    }

    private static Integer dolbyVisionStringToProfile(String profileString) {
        if (profileString == null) {
            return null;
        }
        profileString.hashCode();
        switch (profileString) {
        }
        return null;
    }

    private static Integer dolbyVisionStringToLevel(String levelString) {
        if (levelString == null) {
            return null;
        }
        levelString.hashCode();
        switch (levelString) {
        }
        return null;
    }
}
