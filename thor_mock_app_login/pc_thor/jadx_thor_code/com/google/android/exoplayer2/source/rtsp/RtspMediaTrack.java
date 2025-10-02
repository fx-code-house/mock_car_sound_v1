package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.util.Base64;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/* loaded from: classes.dex */
final class RtspMediaTrack {
    private static final String AAC_CODECS_PREFIX = "mp4a.40.";
    private static final String GENERIC_CONTROL_ATTR = "*";
    private static final String H264_CODECS_PREFIX = "avc1.";
    private static final String PARAMETER_PROFILE_LEVEL_ID = "profile-level-id";
    private static final String PARAMETER_SPROP_PARAMS = "sprop-parameter-sets";
    public final RtpPayloadFormat payloadFormat;
    public final Uri uri;

    public RtspMediaTrack(MediaDescription mediaDescription, Uri sessionUri) {
        Assertions.checkArgument(mediaDescription.attributes.containsKey(SessionDescription.ATTR_CONTROL));
        this.payloadFormat = generatePayloadFormat(mediaDescription);
        this.uri = extractTrackUri(sessionUri, (String) Util.castNonNull(mediaDescription.attributes.get(SessionDescription.ATTR_CONTROL)));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RtspMediaTrack rtspMediaTrack = (RtspMediaTrack) o;
        return this.payloadFormat.equals(rtspMediaTrack.payloadFormat) && this.uri.equals(rtspMediaTrack.uri);
    }

    public int hashCode() {
        return ((217 + this.payloadFormat.hashCode()) * 31) + this.uri.hashCode();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat generatePayloadFormat(com.google.android.exoplayer2.source.rtsp.MediaDescription r11) {
        /*
            com.google.android.exoplayer2.Format$Builder r0 = new com.google.android.exoplayer2.Format$Builder
            r0.<init>()
            int r1 = r11.bitrate
            if (r1 <= 0) goto Le
            int r1 = r11.bitrate
            r0.setAverageBitrate(r1)
        Le:
            com.google.common.collect.ImmutableMap<java.lang.String, java.lang.String> r1 = r11.attributes
            java.lang.String r2 = "rtpmap"
            boolean r1 = r1.containsKey(r2)
            com.google.android.exoplayer2.util.Assertions.checkArgument(r1)
            com.google.common.collect.ImmutableMap<java.lang.String, java.lang.String> r1 = r11.attributes
            java.lang.Object r1 = r1.get(r2)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r1 = com.google.android.exoplayer2.util.Util.castNonNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = " "
            java.lang.String[] r1 = com.google.android.exoplayer2.util.Util.split(r1, r2)
            int r1 = r1.length
            r2 = 2
            r3 = 0
            r4 = 1
            if (r1 != r2) goto L36
            r1 = r4
            goto L37
        L36:
            r1 = r3
        L37:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r1)
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r1 = r11.rtpMapAttribute
            int r1 = r1.payloadType
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r5 = r11.rtpMapAttribute
            java.lang.String r5 = r5.mediaEncoding
            java.lang.String r5 = com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat.getMimeTypeFromRtpMediaType(r5)
            r0.setSampleMimeType(r5)
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r6 = r11.rtpMapAttribute
            int r6 = r6.clockRate
            java.lang.String r7 = "audio"
            java.lang.String r8 = r11.mediaType
            boolean r7 = r7.equals(r8)
            r8 = -1
            if (r7 == 0) goto L68
            com.google.android.exoplayer2.source.rtsp.MediaDescription$RtpMapAttribute r7 = r11.rtpMapAttribute
            int r7 = r7.encodingParameters
            int r7 = inferChannelCount(r7, r5)
            com.google.android.exoplayer2.Format$Builder r9 = r0.setSampleRate(r6)
            r9.setChannelCount(r7)
            goto L69
        L68:
            r7 = r8
        L69:
            com.google.common.collect.ImmutableMap r11 = r11.getFmtpParametersAsMap()
            int r9 = r5.hashCode()
            r10 = -53558318(0xfffffffffccec3d2, float:-8.588679E36)
            if (r9 == r10) goto L95
            r10 = 187078296(0xb269698, float:3.208373E-32)
            if (r9 == r10) goto L8c
            r2 = 1331836730(0x4f62373a, float:3.7952701E9)
            if (r9 == r2) goto L81
            goto L9f
        L81:
            java.lang.String r2 = "video/avc"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L9f
            r2 = r4
            goto La0
        L8c:
            java.lang.String r9 = "audio/ac3"
            boolean r5 = r5.equals(r9)
            if (r5 == 0) goto L9f
            goto La0
        L95:
            java.lang.String r2 = "audio/mp4a-latm"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L9f
            r2 = r3
            goto La0
        L9f:
            r2 = r8
        La0:
            if (r2 == 0) goto Lb1
            if (r2 == r4) goto La5
            goto Lc4
        La5:
            boolean r2 = r11.isEmpty()
            r2 = r2 ^ r4
            com.google.android.exoplayer2.util.Assertions.checkArgument(r2)
            processH264FmtpAttribute(r0, r11)
            goto Lc4
        Lb1:
            if (r7 == r8) goto Lb5
            r2 = r4
            goto Lb6
        Lb5:
            r2 = r3
        Lb6:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r2)
            boolean r2 = r11.isEmpty()
            r2 = r2 ^ r4
            com.google.android.exoplayer2.util.Assertions.checkArgument(r2)
            processAacFmtpAttribute(r0, r11, r7, r6)
        Lc4:
            if (r6 <= 0) goto Lc8
            r2 = r4
            goto Lc9
        Lc8:
            r2 = r3
        Lc9:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r2)
            r2 = 96
            if (r1 < r2) goto Ld1
            r3 = r4
        Ld1:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r3)
            com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat r2 = new com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat
            com.google.android.exoplayer2.Format r0 = r0.build()
            r2.<init>(r0, r1, r6, r11)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.rtsp.RtspMediaTrack.generatePayloadFormat(com.google.android.exoplayer2.source.rtsp.MediaDescription):com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat");
    }

    private static int inferChannelCount(int encodingParameter, String mimeType) {
        return encodingParameter != -1 ? encodingParameter : mimeType.equals(MimeTypes.AUDIO_AC3) ? 6 : 1;
    }

    private static void processAacFmtpAttribute(Format.Builder formatBuilder, ImmutableMap<String, String> fmtpAttributes, int channelCount, int sampleRate) {
        Assertions.checkArgument(fmtpAttributes.containsKey(PARAMETER_PROFILE_LEVEL_ID));
        String strValueOf = String.valueOf((String) Assertions.checkNotNull(fmtpAttributes.get(PARAMETER_PROFILE_LEVEL_ID)));
        formatBuilder.setCodecs(strValueOf.length() != 0 ? AAC_CODECS_PREFIX.concat(strValueOf) : new String(AAC_CODECS_PREFIX));
        formatBuilder.setInitializationData(ImmutableList.of(AacUtil.buildAacLcAudioSpecificConfig(sampleRate, channelCount)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void processH264FmtpAttribute(Format.Builder formatBuilder, ImmutableMap<String, String> fmtpAttributes) {
        Assertions.checkArgument(fmtpAttributes.containsKey(PARAMETER_SPROP_PARAMS));
        String[] strArrSplit = Util.split((String) Assertions.checkNotNull(fmtpAttributes.get(PARAMETER_SPROP_PARAMS)), ",");
        Assertions.checkArgument(strArrSplit.length == 2);
        ImmutableList immutableListOf = ImmutableList.of(getH264InitializationDataFromParameterSet(strArrSplit[0]), getH264InitializationDataFromParameterSet(strArrSplit[1]));
        formatBuilder.setInitializationData(immutableListOf);
        byte[] bArr = (byte[]) immutableListOf.get(0);
        NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        formatBuilder.setPixelWidthHeightRatio(spsNalUnit.pixelWidthAspectRatio);
        formatBuilder.setHeight(spsNalUnit.height);
        formatBuilder.setWidth(spsNalUnit.width);
        String str = fmtpAttributes.get(PARAMETER_PROFILE_LEVEL_ID);
        if (str != null) {
            String strValueOf = String.valueOf(str);
            formatBuilder.setCodecs(strValueOf.length() != 0 ? H264_CODECS_PREFIX.concat(strValueOf) : new String(H264_CODECS_PREFIX));
        } else {
            formatBuilder.setCodecs(CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc));
        }
    }

    private static byte[] getH264InitializationDataFromParameterSet(String parameterSet) {
        byte[] bArrDecode = Base64.decode(parameterSet, 0);
        byte[] bArr = new byte[bArrDecode.length + NalUnitUtil.NAL_START_CODE.length];
        System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, bArr, 0, NalUnitUtil.NAL_START_CODE.length);
        System.arraycopy(bArrDecode, 0, bArr, NalUnitUtil.NAL_START_CODE.length, bArrDecode.length);
        return bArr;
    }

    private static Uri extractTrackUri(Uri sessionUri, String controlAttributeString) {
        Uri uri = Uri.parse(controlAttributeString);
        return uri.isAbsolute() ? uri : controlAttributeString.equals(GENERIC_CONTROL_ATTR) ? sessionUri : sessionUri.buildUpon().appendEncodedPath(controlAttributeString).build();
    }
}
