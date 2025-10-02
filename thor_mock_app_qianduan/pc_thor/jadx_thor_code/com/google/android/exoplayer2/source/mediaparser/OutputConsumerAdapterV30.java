package com.google.android.exoplayer2.source.mediaparser;

import android.media.DrmInitData;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaParser;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public final class OutputConsumerAdapterV30 implements MediaParser.OutputConsumer {
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_DURATIONS = "chunk-index-long-us-durations";
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_OFFSETS = "chunk-index-long-offsets";
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_SIZES = "chunk-index-int-sizes";
    private static final String MEDIA_FORMAT_KEY_CHUNK_INDEX_TIMES = "chunk-index-long-us-times";
    private static final String MEDIA_FORMAT_KEY_TRACK_TYPE = "track-type-string";
    private static final String TAG = "OutputConsumerAdapterV30";
    private String containerMimeType;
    private MediaParser.SeekMap dummySeekMap;
    private final boolean expectDummySeekMap;
    private ExtractorOutput extractorOutput;
    private ChunkIndex lastChunkIndex;
    private final ArrayList<TrackOutput.CryptoData> lastOutputCryptoDatas;
    private final ArrayList<MediaCodec.CryptoInfo> lastReceivedCryptoInfos;
    private MediaParser.SeekMap lastSeekMap;
    private List<Format> muxedCaptionFormats;
    private int primaryTrackIndex;
    private final Format primaryTrackManifestFormat;
    private final int primaryTrackType;
    private long sampleTimestampUpperLimitFilterUs;
    private final DataReaderAdapter scratchDataReaderAdapter;
    private boolean seekingDisabled;
    private TimestampAdjuster timestampAdjuster;
    private final ArrayList<Format> trackFormats;
    private final ArrayList<TrackOutput> trackOutputs;
    private boolean tracksEnded;
    private boolean tracksFoundCalled;
    private static final Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> SEEK_POINT_PAIR_START = Pair.create(MediaParser.SeekPoint.START, MediaParser.SeekPoint.START);
    private static final Pattern REGEX_CRYPTO_INFO_PATTERN = Pattern.compile("pattern \\(encrypt: (\\d+), skip: (\\d+)\\)");

    public OutputConsumerAdapterV30() {
        this(null, 7, false);
    }

    public OutputConsumerAdapterV30(Format primaryTrackManifestFormat, int primaryTrackType, boolean expectDummySeekMap) {
        this.expectDummySeekMap = expectDummySeekMap;
        this.primaryTrackManifestFormat = primaryTrackManifestFormat;
        this.primaryTrackType = primaryTrackType;
        this.trackOutputs = new ArrayList<>();
        this.trackFormats = new ArrayList<>();
        this.lastReceivedCryptoInfos = new ArrayList<>();
        this.lastOutputCryptoDatas = new ArrayList<>();
        this.scratchDataReaderAdapter = new DataReaderAdapter();
        this.extractorOutput = new DummyExtractorOutput();
        this.sampleTimestampUpperLimitFilterUs = C.TIME_UNSET;
        this.muxedCaptionFormats = ImmutableList.of();
    }

    public void setSampleTimestampUpperLimitFilterUs(long sampleTimestampUpperLimitFilterUs) {
        this.sampleTimestampUpperLimitFilterUs = sampleTimestampUpperLimitFilterUs;
    }

    public void setTimestampAdjuster(TimestampAdjuster timestampAdjuster) {
        this.timestampAdjuster = timestampAdjuster;
    }

    public void setExtractorOutput(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
    }

    public void setMuxedCaptionFormats(List<Format> muxedCaptionFormats) {
        this.muxedCaptionFormats = muxedCaptionFormats;
    }

    public void disableSeeking() {
        this.seekingDisabled = true;
    }

    public MediaParser.SeekMap getDummySeekMap() {
        return this.dummySeekMap;
    }

    public ChunkIndex getChunkIndex() {
        return this.lastChunkIndex;
    }

    public Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> getSeekPoints(long seekTimeUs) {
        MediaParser.SeekMap seekMap = this.lastSeekMap;
        return seekMap != null ? seekMap.getSeekPoints(seekTimeUs) : SEEK_POINT_PAIR_START;
    }

    public void setSelectedParserName(String parserName) {
        this.containerMimeType = getMimeType(parserName);
    }

    public Format[] getSampleFormats() {
        if (!this.tracksFoundCalled) {
            return null;
        }
        Format[] formatArr = new Format[this.trackFormats.size()];
        for (int i = 0; i < this.trackFormats.size(); i++) {
            formatArr[i] = (Format) Assertions.checkNotNull(this.trackFormats.get(i));
        }
        return formatArr;
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onTrackCountFound(int numberOfTracks) {
        this.tracksFoundCalled = true;
        maybeEndTracks();
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSeekMapFound(MediaParser.SeekMap seekMap) {
        SeekMap seekMapAdapter;
        if (this.expectDummySeekMap && this.dummySeekMap == null) {
            this.dummySeekMap = seekMap;
            return;
        }
        this.lastSeekMap = seekMap;
        long durationMicros = seekMap.getDurationMicros();
        ExtractorOutput extractorOutput = this.extractorOutput;
        if (this.seekingDisabled) {
            if (durationMicros == -2147483648L) {
                durationMicros = C.TIME_UNSET;
            }
            seekMapAdapter = new SeekMap.Unseekable(durationMicros);
        } else {
            seekMapAdapter = new SeekMapAdapter(seekMap);
        }
        extractorOutput.seekMap(seekMapAdapter);
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onTrackDataFound(int trackIndex, MediaParser.TrackData trackData) {
        if (maybeObtainChunkIndex(trackData.mediaFormat)) {
            return;
        }
        ensureSpaceForTrackIndex(trackIndex);
        TrackOutput trackOutput = this.trackOutputs.get(trackIndex);
        if (trackOutput == null) {
            String string = trackData.mediaFormat.getString(MEDIA_FORMAT_KEY_TRACK_TYPE);
            int trackTypeConstant = toTrackTypeConstant(string != null ? string : trackData.mediaFormat.getString("mime"));
            if (trackTypeConstant == this.primaryTrackType) {
                this.primaryTrackIndex = trackIndex;
            }
            TrackOutput trackOutputTrack = this.extractorOutput.track(trackIndex, trackTypeConstant);
            this.trackOutputs.set(trackIndex, trackOutputTrack);
            if (string != null) {
                return;
            } else {
                trackOutput = trackOutputTrack;
            }
        }
        Format exoPlayerFormat = toExoPlayerFormat(trackData);
        Format format = this.primaryTrackManifestFormat;
        trackOutput.format((format == null || trackIndex != this.primaryTrackIndex) ? exoPlayerFormat : exoPlayerFormat.withManifestFormatInfo(format));
        this.trackFormats.set(trackIndex, exoPlayerFormat);
        maybeEndTracks();
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSampleDataFound(int trackIndex, MediaParser.InputReader sampleData) throws IOException {
        ensureSpaceForTrackIndex(trackIndex);
        this.scratchDataReaderAdapter.input = sampleData;
        TrackOutput trackOutputTrack = this.trackOutputs.get(trackIndex);
        if (trackOutputTrack == null) {
            trackOutputTrack = this.extractorOutput.track(trackIndex, -1);
            this.trackOutputs.set(trackIndex, trackOutputTrack);
        }
        trackOutputTrack.sampleData((DataReader) this.scratchDataReaderAdapter, (int) sampleData.getLength(), true);
    }

    @Override // android.media.MediaParser.OutputConsumer
    public void onSampleCompleted(int trackIndex, long timeUs, int flags, int size, int offset, MediaCodec.CryptoInfo cryptoInfo) {
        long j = this.sampleTimestampUpperLimitFilterUs;
        if (j == C.TIME_UNSET || timeUs < j) {
            TimestampAdjuster timestampAdjuster = this.timestampAdjuster;
            if (timestampAdjuster != null) {
                timeUs = timestampAdjuster.adjustSampleTimestamp(timeUs);
            }
            ((TrackOutput) Assertions.checkNotNull(this.trackOutputs.get(trackIndex))).sampleMetadata(timeUs, flags, size, offset, toExoPlayerCryptoData(trackIndex, cryptoInfo));
        }
    }

    private boolean maybeObtainChunkIndex(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_SIZES);
        if (byteBuffer == null) {
            return false;
        }
        IntBuffer intBufferAsIntBuffer = byteBuffer.asIntBuffer();
        LongBuffer longBufferAsLongBuffer = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_OFFSETS))).asLongBuffer();
        LongBuffer longBufferAsLongBuffer2 = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_DURATIONS))).asLongBuffer();
        LongBuffer longBufferAsLongBuffer3 = ((ByteBuffer) Assertions.checkNotNull(mediaFormat.getByteBuffer(MEDIA_FORMAT_KEY_CHUNK_INDEX_TIMES))).asLongBuffer();
        int[] iArr = new int[intBufferAsIntBuffer.remaining()];
        long[] jArr = new long[longBufferAsLongBuffer.remaining()];
        long[] jArr2 = new long[longBufferAsLongBuffer2.remaining()];
        long[] jArr3 = new long[longBufferAsLongBuffer3.remaining()];
        intBufferAsIntBuffer.get(iArr);
        longBufferAsLongBuffer.get(jArr);
        longBufferAsLongBuffer2.get(jArr2);
        longBufferAsLongBuffer3.get(jArr3);
        ChunkIndex chunkIndex = new ChunkIndex(iArr, jArr, jArr2, jArr3);
        this.lastChunkIndex = chunkIndex;
        this.extractorOutput.seekMap(chunkIndex);
        return true;
    }

    private void ensureSpaceForTrackIndex(int trackIndex) {
        for (int size = this.trackOutputs.size(); size <= trackIndex; size++) {
            this.trackOutputs.add(null);
            this.trackFormats.add(null);
            this.lastReceivedCryptoInfos.add(null);
            this.lastOutputCryptoDatas.add(null);
        }
    }

    private TrackOutput.CryptoData toExoPlayerCryptoData(int trackIndex, MediaCodec.CryptoInfo cryptoInfo) throws NumberFormatException {
        int i;
        int i2;
        if (cryptoInfo == null) {
            return null;
        }
        if (this.lastReceivedCryptoInfos.get(trackIndex) == cryptoInfo) {
            return (TrackOutput.CryptoData) Assertions.checkNotNull(this.lastOutputCryptoDatas.get(trackIndex));
        }
        try {
            Matcher matcher = REGEX_CRYPTO_INFO_PATTERN.matcher(cryptoInfo.toString());
            matcher.find();
            i = Integer.parseInt((String) Util.castNonNull(matcher.group(1)));
            i2 = Integer.parseInt((String) Util.castNonNull(matcher.group(2)));
        } catch (RuntimeException e) {
            String strValueOf = String.valueOf(cryptoInfo);
            Log.e(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 43).append("Unexpected error while parsing CryptoInfo: ").append(strValueOf).toString(), e);
            i = 0;
            i2 = 0;
        }
        TrackOutput.CryptoData cryptoData = new TrackOutput.CryptoData(cryptoInfo.mode, cryptoInfo.key, i, i2);
        this.lastReceivedCryptoInfos.set(trackIndex, cryptoInfo);
        this.lastOutputCryptoDatas.set(trackIndex, cryptoData);
        return cryptoData;
    }

    private void maybeEndTracks() {
        if (!this.tracksFoundCalled || this.tracksEnded) {
            return;
        }
        int size = this.trackOutputs.size();
        for (int i = 0; i < size; i++) {
            if (this.trackOutputs.get(i) == null) {
                return;
            }
        }
        this.extractorOutput.endTracks();
        this.tracksEnded = true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int toTrackTypeConstant(java.lang.String r5) {
        /*
            r0 = -1
            if (r5 != 0) goto L4
            return r0
        L4:
            r5.hashCode()
            int r1 = r5.hashCode()
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r1) {
                case -450004177: goto L42;
                case -284840886: goto L36;
                case 3556653: goto L2a;
                case 93166550: goto L1f;
                case 112202875: goto L13;
                default: goto L11;
            }
        L11:
            r1 = r0
            goto L4c
        L13:
            java.lang.String r1 = "video"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L1d
            goto L11
        L1d:
            r1 = 4
            goto L4c
        L1f:
            java.lang.String r1 = "audio"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L28
            goto L11
        L28:
            r1 = r2
            goto L4c
        L2a:
            java.lang.String r1 = "text"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L34
            goto L11
        L34:
            r1 = r3
            goto L4c
        L36:
            java.lang.String r1 = "unknown"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L40
            goto L11
        L40:
            r1 = r4
            goto L4c
        L42:
            java.lang.String r1 = "metadata"
            boolean r1 = r5.equals(r1)
            if (r1 != 0) goto L4b
            goto L11
        L4b:
            r1 = 0
        L4c:
            switch(r1) {
                case 0: goto L58;
                case 1: goto L57;
                case 2: goto L56;
                case 3: goto L55;
                case 4: goto L54;
                default: goto L4f;
            }
        L4f:
            int r5 = com.google.android.exoplayer2.util.MimeTypes.getTrackType(r5)
            return r5
        L54:
            return r3
        L55:
            return r4
        L56:
            return r2
        L57:
            return r0
        L58:
            r5 = 5
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30.toTrackTypeConstant(java.lang.String):int");
    }

    private Format toExoPlayerFormat(MediaParser.TrackData trackData) {
        MediaFormat mediaFormat = trackData.mediaFormat;
        String string = mediaFormat.getString("mime");
        int integer = mediaFormat.getInteger("caption-service-number", -1);
        int i = 0;
        Format.Builder accessibilityChannel = new Format.Builder().setDrmInitData(toExoPlayerDrmInitData(mediaFormat.getString("crypto-mode-fourcc"), trackData.drmInitData)).setContainerMimeType(this.containerMimeType).setPeakBitrate(mediaFormat.getInteger("bitrate", -1)).setChannelCount(mediaFormat.getInteger("channel-count", -1)).setColorInfo(getColorInfo(mediaFormat)).setSampleMimeType(string).setCodecs(mediaFormat.getString("codecs-string")).setFrameRate(mediaFormat.getFloat("frame-rate", -1.0f)).setWidth(mediaFormat.getInteger("width", -1)).setHeight(mediaFormat.getInteger("height", -1)).setInitializationData(getInitializationData(mediaFormat)).setLanguage(mediaFormat.getString("language")).setMaxInputSize(mediaFormat.getInteger("max-input-size", -1)).setPcmEncoding(mediaFormat.getInteger("exo-pcm-encoding", -1)).setRotationDegrees(mediaFormat.getInteger("rotation-degrees", 0)).setSampleRate(mediaFormat.getInteger("sample-rate", -1)).setSelectionFlags(getSelectionFlags(mediaFormat)).setEncoderDelay(mediaFormat.getInteger("encoder-delay", 0)).setEncoderPadding(mediaFormat.getInteger("encoder-padding", 0)).setPixelWidthHeightRatio(mediaFormat.getFloat("pixel-width-height-ratio-float", 1.0f)).setSubsampleOffsetUs(mediaFormat.getLong("subsample-offset-us-long", Long.MAX_VALUE)).setAccessibilityChannel(integer);
        while (true) {
            if (i >= this.muxedCaptionFormats.size()) {
                break;
            }
            Format format = this.muxedCaptionFormats.get(i);
            if (Util.areEqual(format.sampleMimeType, string) && format.accessibilityChannel == integer) {
                accessibilityChannel.setLanguage(format.language).setRoleFlags(format.roleFlags).setSelectionFlags(format.selectionFlags).setLabel(format.label).setMetadata(format.metadata);
                break;
            }
            i++;
        }
        return accessibilityChannel.build();
    }

    private static DrmInitData toExoPlayerDrmInitData(String schemeType, android.media.DrmInitData drmInitData) {
        if (drmInitData == null) {
            return null;
        }
        int schemeInitDataCount = drmInitData.getSchemeInitDataCount();
        DrmInitData.SchemeData[] schemeDataArr = new DrmInitData.SchemeData[schemeInitDataCount];
        for (int i = 0; i < schemeInitDataCount; i++) {
            DrmInitData.SchemeInitData schemeInitDataAt = drmInitData.getSchemeInitDataAt(i);
            schemeDataArr[i] = new DrmInitData.SchemeData(schemeInitDataAt.uuid, schemeInitDataAt.mimeType, schemeInitDataAt.data);
        }
        return new com.google.android.exoplayer2.drm.DrmInitData(schemeType, schemeDataArr);
    }

    private static int getSelectionFlags(MediaFormat mediaFormat) {
        return getFlag(mediaFormat, "is-forced-subtitle", 2) | getFlag(mediaFormat, "is-autoselect", 4) | 0 | getFlag(mediaFormat, "is-default", 1);
    }

    private static int getFlag(MediaFormat mediaFormat, String key, int returnValueIfPresent) {
        if (mediaFormat.getInteger(key, 0) != 0) {
            return returnValueIfPresent;
        }
        return 0;
    }

    private static List<byte[]> getInitializationData(MediaFormat mediaFormat) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i + 1;
            ByteBuffer byteBuffer = mediaFormat.getByteBuffer(new StringBuilder(15).append("csd-").append(i).toString());
            if (byteBuffer == null) {
                return arrayList;
            }
            arrayList.add(getArray(byteBuffer));
            i = i2;
        }
    }

    private static ColorInfo getColorInfo(MediaFormat mediaFormat) {
        ByteBuffer byteBuffer = mediaFormat.getByteBuffer("hdr-static-info");
        byte[] array = byteBuffer != null ? getArray(byteBuffer) : null;
        int integer = mediaFormat.getInteger("color-transfer", -1);
        int integer2 = mediaFormat.getInteger("color-range", -1);
        int integer3 = mediaFormat.getInteger("color-standard", -1);
        if (array == null && integer == -1 && integer2 == -1 && integer3 == -1) {
            return null;
        }
        return new ColorInfo(integer3, integer2, integer, array);
    }

    private static byte[] getArray(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.get(bArr);
        return bArr;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static String getMimeType(String parserName) {
        parserName.hashCode();
        char c = 65535;
        switch (parserName.hashCode()) {
            case -2063506020:
                if (parserName.equals("android.media.mediaparser.Mp4Parser")) {
                    c = 0;
                    break;
                }
                break;
            case -1870824006:
                if (parserName.equals("android.media.mediaparser.OggParser")) {
                    c = 1;
                    break;
                }
                break;
            case -1566427438:
                if (parserName.equals("android.media.mediaparser.TsParser")) {
                    c = 2;
                    break;
                }
                break;
            case -900207883:
                if (parserName.equals("android.media.mediaparser.AdtsParser")) {
                    c = 3;
                    break;
                }
                break;
            case -589864617:
                if (parserName.equals("android.media.mediaparser.WavParser")) {
                    c = 4;
                    break;
                }
                break;
            case 52265814:
                if (parserName.equals("android.media.mediaparser.PsParser")) {
                    c = 5;
                    break;
                }
                break;
            case 116768877:
                if (parserName.equals("android.media.mediaparser.FragmentedMp4Parser")) {
                    c = 6;
                    break;
                }
                break;
            case 376876796:
                if (parserName.equals("android.media.mediaparser.Ac3Parser")) {
                    c = 7;
                    break;
                }
                break;
            case 703268017:
                if (parserName.equals("android.media.mediaparser.AmrParser")) {
                    c = '\b';
                    break;
                }
                break;
            case 768643067:
                if (parserName.equals("android.media.mediaparser.FlacParser")) {
                    c = '\t';
                    break;
                }
                break;
            case 965962719:
                if (parserName.equals("android.media.mediaparser.MatroskaParser")) {
                    c = '\n';
                    break;
                }
                break;
            case 1264380477:
                if (parserName.equals("android.media.mediaparser.Ac4Parser")) {
                    c = 11;
                    break;
                }
                break;
            case 1343957595:
                if (parserName.equals("android.media.mediaparser.Mp3Parser")) {
                    c = '\f';
                    break;
                }
                break;
            case 2063134683:
                if (parserName.equals("android.media.mediaparser.FlvParser")) {
                    c = CharUtils.CR;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 6:
                return MimeTypes.VIDEO_MP4;
            case 1:
                return MimeTypes.AUDIO_OGG;
            case 2:
                return MimeTypes.VIDEO_MP2T;
            case 3:
                return MimeTypes.AUDIO_AAC;
            case 4:
                return MimeTypes.AUDIO_RAW;
            case 5:
                return MimeTypes.VIDEO_PS;
            case 7:
                return MimeTypes.AUDIO_AC3;
            case '\b':
                return MimeTypes.AUDIO_AMR;
            case '\t':
                return MimeTypes.AUDIO_FLAC;
            case '\n':
                return MimeTypes.VIDEO_WEBM;
            case 11:
                return MimeTypes.AUDIO_AC4;
            case '\f':
                return MimeTypes.AUDIO_MPEG;
            case '\r':
                return MimeTypes.VIDEO_FLV;
            default:
                String strValueOf = String.valueOf(parserName);
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Illegal parser name: ".concat(strValueOf) : new String("Illegal parser name: "));
        }
    }

    private static final class SeekMapAdapter implements SeekMap {
        private final MediaParser.SeekMap adaptedSeekMap;

        public SeekMapAdapter(MediaParser.SeekMap adaptedSeekMap) {
            this.adaptedSeekMap = adaptedSeekMap;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return this.adaptedSeekMap.isSeekable();
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            long durationMicros = this.adaptedSeekMap.getDurationMicros();
            return durationMicros != -2147483648L ? durationMicros : C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long timeUs) {
            Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> seekPoints = this.adaptedSeekMap.getSeekPoints(timeUs);
            if (seekPoints.first == seekPoints.second) {
                return new SeekMap.SeekPoints(asExoPlayerSeekPoint((MediaParser.SeekPoint) seekPoints.first));
            }
            return new SeekMap.SeekPoints(asExoPlayerSeekPoint((MediaParser.SeekPoint) seekPoints.first), asExoPlayerSeekPoint((MediaParser.SeekPoint) seekPoints.second));
        }

        private static SeekPoint asExoPlayerSeekPoint(MediaParser.SeekPoint seekPoint) {
            return new SeekPoint(seekPoint.timeMicros, seekPoint.position);
        }
    }

    private static final class DataReaderAdapter implements DataReader {
        public MediaParser.InputReader input;

        private DataReaderAdapter() {
        }

        @Override // com.google.android.exoplayer2.upstream.DataReader
        public int read(byte[] buffer, int offset, int length) throws IOException {
            return ((MediaParser.InputReader) Util.castNonNull(this.input)).read(buffer, offset, length);
        }
    }
}
