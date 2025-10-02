package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.hls.HlsChunkSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
final class HlsMediaChunk extends MediaChunk {
    public static final String PRIV_TIMESTAMP_FRAME_OWNER = "com.apple.streaming.transportStreamTimestamp";
    private static final AtomicInteger uidSource = new AtomicInteger();
    public final int discontinuitySequenceNumber;
    private final DrmInitData drmInitData;
    private HlsMediaChunkExtractor extractor;
    private final HlsExtractorFactory extractorFactory;
    private boolean extractorInvalidated;
    private final boolean hasGapTag;
    private final Id3Decoder id3Decoder;
    private boolean initDataLoadRequired;
    private final DataSource initDataSource;
    private final DataSpec initDataSpec;
    private final boolean initSegmentEncrypted;
    private final boolean isMasterTimestampSource;
    private boolean isPublished;
    private volatile boolean loadCanceled;
    private boolean loadCompleted;
    private final boolean mediaSegmentEncrypted;
    private final List<Format> muxedCaptionFormats;
    private int nextLoadPosition;
    private HlsSampleStreamWrapper output;
    public final int partIndex;
    public final Uri playlistUrl;
    private final HlsMediaChunkExtractor previousExtractor;
    private ImmutableList<Integer> sampleQueueFirstSampleIndices;
    private final ParsableByteArray scratchId3Data;
    public final boolean shouldSpliceIn;
    private final TimestampAdjuster timestampAdjuster;
    public final int uid;

    public static HlsMediaChunk createInstance(HlsExtractorFactory extractorFactory, DataSource dataSource, Format format, long startOfPlaylistInPeriodUs, HlsMediaPlaylist mediaPlaylist, HlsChunkSource.SegmentBaseHolder segmentBaseHolder, Uri playlistUrl, List<Format> muxedCaptionFormats, int trackSelectionReason, Object trackSelectionData, boolean isMasterTimestampSource, TimestampAdjusterProvider timestampAdjusterProvider, HlsMediaChunk previousChunk, byte[] mediaSegmentKey, byte[] initSegmentKey, boolean shouldSpliceIn) {
        boolean z;
        DataSource dataSourceBuildDataSource;
        DataSpec dataSpec;
        boolean z2;
        Id3Decoder id3Decoder;
        ParsableByteArray parsableByteArray;
        HlsMediaChunkExtractor hlsMediaChunkExtractor;
        HlsMediaPlaylist.SegmentBase segmentBase = segmentBaseHolder.segmentBase;
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(UriUtil.resolveToUri(mediaPlaylist.baseUri, segmentBase.url)).setPosition(segmentBase.byteRangeOffset).setLength(segmentBase.byteRangeLength).setFlags(segmentBaseHolder.isPreload ? 8 : 0).build();
        boolean z3 = mediaSegmentKey != null;
        DataSource dataSourceBuildDataSource2 = buildDataSource(dataSource, mediaSegmentKey, z3 ? getEncryptionIvArray((String) Assertions.checkNotNull(segmentBase.encryptionIV)) : null);
        HlsMediaPlaylist.Segment segment = segmentBase.initializationSegment;
        if (segment != null) {
            boolean z4 = initSegmentKey != null;
            byte[] encryptionIvArray = z4 ? getEncryptionIvArray((String) Assertions.checkNotNull(segment.encryptionIV)) : null;
            z = z3;
            dataSpec = new DataSpec(UriUtil.resolveToUri(mediaPlaylist.baseUri, segment.url), segment.byteRangeOffset, segment.byteRangeLength);
            dataSourceBuildDataSource = buildDataSource(dataSource, initSegmentKey, encryptionIvArray);
            z2 = z4;
        } else {
            z = z3;
            dataSourceBuildDataSource = null;
            dataSpec = null;
            z2 = false;
        }
        long j = startOfPlaylistInPeriodUs + segmentBase.relativeStartTimeUs;
        long j2 = j + segmentBase.durationUs;
        int i = mediaPlaylist.discontinuitySequence + segmentBase.relativeDiscontinuitySequence;
        if (previousChunk != null) {
            DataSpec dataSpec2 = previousChunk.initDataSpec;
            boolean z5 = dataSpec == dataSpec2 || (dataSpec != null && dataSpec2 != null && dataSpec.uri.equals(previousChunk.initDataSpec.uri) && dataSpec.position == previousChunk.initDataSpec.position);
            boolean z6 = playlistUrl.equals(previousChunk.playlistUrl) && previousChunk.loadCompleted;
            id3Decoder = previousChunk.id3Decoder;
            parsableByteArray = previousChunk.scratchId3Data;
            hlsMediaChunkExtractor = (z5 && z6 && !previousChunk.extractorInvalidated && previousChunk.discontinuitySequenceNumber == i) ? previousChunk.extractor : null;
        } else {
            id3Decoder = new Id3Decoder();
            parsableByteArray = new ParsableByteArray(10);
            hlsMediaChunkExtractor = null;
        }
        return new HlsMediaChunk(extractorFactory, dataSourceBuildDataSource2, dataSpecBuild, format, z, dataSourceBuildDataSource, dataSpec, z2, playlistUrl, muxedCaptionFormats, trackSelectionReason, trackSelectionData, j, j2, segmentBaseHolder.mediaSequence, segmentBaseHolder.partIndex, !segmentBaseHolder.isPreload, i, segmentBase.hasGapTag, isMasterTimestampSource, timestampAdjusterProvider.getAdjuster(i), segmentBase.drmInitData, hlsMediaChunkExtractor, id3Decoder, parsableByteArray, shouldSpliceIn);
    }

    public static boolean shouldSpliceIn(HlsMediaChunk previousChunk, Uri playlistUrl, HlsMediaPlaylist mediaPlaylist, HlsChunkSource.SegmentBaseHolder segmentBaseHolder, long startOfPlaylistInPeriodUs) {
        if (previousChunk == null) {
            return false;
        }
        if (playlistUrl.equals(previousChunk.playlistUrl) && previousChunk.loadCompleted) {
            return false;
        }
        return !isIndependent(segmentBaseHolder, mediaPlaylist) || startOfPlaylistInPeriodUs + segmentBaseHolder.segmentBase.relativeStartTimeUs < previousChunk.endTimeUs;
    }

    private HlsMediaChunk(HlsExtractorFactory extractorFactory, DataSource mediaDataSource, DataSpec dataSpec, Format format, boolean mediaSegmentEncrypted, DataSource initDataSource, DataSpec initDataSpec, boolean initSegmentEncrypted, Uri playlistUrl, List<Format> muxedCaptionFormats, int trackSelectionReason, Object trackSelectionData, long startTimeUs, long endTimeUs, long chunkMediaSequence, int partIndex, boolean isPublished, int discontinuitySequenceNumber, boolean hasGapTag, boolean isMasterTimestampSource, TimestampAdjuster timestampAdjuster, DrmInitData drmInitData, HlsMediaChunkExtractor previousExtractor, Id3Decoder id3Decoder, ParsableByteArray scratchId3Data, boolean shouldSpliceIn) {
        super(mediaDataSource, dataSpec, format, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs, chunkMediaSequence);
        this.mediaSegmentEncrypted = mediaSegmentEncrypted;
        this.partIndex = partIndex;
        this.isPublished = isPublished;
        this.discontinuitySequenceNumber = discontinuitySequenceNumber;
        this.initDataSpec = initDataSpec;
        this.initDataSource = initDataSource;
        this.initDataLoadRequired = initDataSpec != null;
        this.initSegmentEncrypted = initSegmentEncrypted;
        this.playlistUrl = playlistUrl;
        this.isMasterTimestampSource = isMasterTimestampSource;
        this.timestampAdjuster = timestampAdjuster;
        this.hasGapTag = hasGapTag;
        this.extractorFactory = extractorFactory;
        this.muxedCaptionFormats = muxedCaptionFormats;
        this.drmInitData = drmInitData;
        this.previousExtractor = previousExtractor;
        this.id3Decoder = id3Decoder;
        this.scratchId3Data = scratchId3Data;
        this.shouldSpliceIn = shouldSpliceIn;
        this.sampleQueueFirstSampleIndices = ImmutableList.of();
        this.uid = uidSource.getAndIncrement();
    }

    public void init(HlsSampleStreamWrapper output, ImmutableList<Integer> sampleQueueWriteIndices) {
        this.output = output;
        this.sampleQueueFirstSampleIndices = sampleQueueWriteIndices;
    }

    public int getFirstSampleIndex(int sampleQueueIndex) {
        Assertions.checkState(!this.shouldSpliceIn);
        if (sampleQueueIndex >= this.sampleQueueFirstSampleIndices.size()) {
            return 0;
        }
        return this.sampleQueueFirstSampleIndices.get(sampleQueueIndex).intValue();
    }

    public void invalidateExtractor() {
        this.extractorInvalidated = true;
    }

    @Override // com.google.android.exoplayer2.source.chunk.MediaChunk
    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
        this.loadCanceled = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        HlsMediaChunkExtractor hlsMediaChunkExtractor;
        Assertions.checkNotNull(this.output);
        if (this.extractor == null && (hlsMediaChunkExtractor = this.previousExtractor) != null && hlsMediaChunkExtractor.isReusable()) {
            this.extractor = this.previousExtractor;
            this.initDataLoadRequired = false;
        }
        maybeLoadInitData();
        if (this.loadCanceled) {
            return;
        }
        if (!this.hasGapTag) {
            loadMedia();
        }
        this.loadCompleted = !this.loadCanceled;
    }

    public boolean isPublished() {
        return this.isPublished;
    }

    public void publish() {
        this.isPublished = true;
    }

    @RequiresNonNull({"output"})
    private void maybeLoadInitData() throws IOException {
        if (this.initDataLoadRequired) {
            Assertions.checkNotNull(this.initDataSource);
            Assertions.checkNotNull(this.initDataSpec);
            feedDataToExtractor(this.initDataSource, this.initDataSpec, this.initSegmentEncrypted);
            this.nextLoadPosition = 0;
            this.initDataLoadRequired = false;
        }
    }

    @RequiresNonNull({"output"})
    private void loadMedia() throws IOException {
        try {
            this.timestampAdjuster.sharedInitializeOrWait(this.isMasterTimestampSource, this.startTimeUs);
            feedDataToExtractor(this.dataSource, this.dataSpec, this.mediaSegmentEncrypted);
        } catch (InterruptedException unused) {
            throw new InterruptedIOException();
        }
    }

    @RequiresNonNull({"output"})
    private void feedDataToExtractor(DataSource dataSource, DataSpec dataSpec, boolean dataIsEncrypted) throws IOException {
        DataSpec dataSpecSubrange;
        long position;
        long j;
        if (dataIsEncrypted) {
            z = this.nextLoadPosition != 0;
            dataSpecSubrange = dataSpec;
        } else {
            dataSpecSubrange = dataSpec.subrange(this.nextLoadPosition);
        }
        try {
            DefaultExtractorInput defaultExtractorInputPrepareExtraction = prepareExtraction(dataSource, dataSpecSubrange);
            if (z) {
                defaultExtractorInputPrepareExtraction.skipFully(this.nextLoadPosition);
            }
            while (!this.loadCanceled && this.extractor.read(defaultExtractorInputPrepareExtraction)) {
                try {
                    try {
                    } catch (EOFException e) {
                        if ((this.trackFormat.roleFlags & 16384) != 0) {
                            this.extractor.onTruncatedSegmentParsed();
                            position = defaultExtractorInputPrepareExtraction.getPosition();
                            j = dataSpec.position;
                        } else {
                            throw e;
                        }
                    }
                } catch (Throwable th) {
                    this.nextLoadPosition = (int) (defaultExtractorInputPrepareExtraction.getPosition() - dataSpec.position);
                    throw th;
                }
            }
            position = defaultExtractorInputPrepareExtraction.getPosition();
            j = dataSpec.position;
            this.nextLoadPosition = (int) (position - j);
        } finally {
            Util.closeQuietly(dataSource);
        }
    }

    @EnsuresNonNull({"extractor"})
    @RequiresNonNull({"output"})
    private DefaultExtractorInput prepareExtraction(DataSource dataSource, DataSpec dataSpec) throws IOException {
        HlsMediaChunkExtractor hlsMediaChunkExtractorCreateExtractor;
        long jAdjustTsTimestamp;
        DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(dataSource, dataSpec.position, dataSource.open(dataSpec));
        if (this.extractor == null) {
            long jPeekId3PrivTimestamp = peekId3PrivTimestamp(defaultExtractorInput);
            defaultExtractorInput.resetPeekPosition();
            HlsMediaChunkExtractor hlsMediaChunkExtractor = this.previousExtractor;
            if (hlsMediaChunkExtractor != null) {
                hlsMediaChunkExtractorCreateExtractor = hlsMediaChunkExtractor.recreate();
            } else {
                hlsMediaChunkExtractorCreateExtractor = this.extractorFactory.createExtractor(dataSpec.uri, this.trackFormat, this.muxedCaptionFormats, this.timestampAdjuster, dataSource.getResponseHeaders(), defaultExtractorInput);
            }
            this.extractor = hlsMediaChunkExtractorCreateExtractor;
            if (hlsMediaChunkExtractorCreateExtractor.isPackedAudioExtractor()) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper = this.output;
                if (jPeekId3PrivTimestamp != C.TIME_UNSET) {
                    jAdjustTsTimestamp = this.timestampAdjuster.adjustTsTimestamp(jPeekId3PrivTimestamp);
                } else {
                    jAdjustTsTimestamp = this.startTimeUs;
                }
                hlsSampleStreamWrapper.setSampleOffsetUs(jAdjustTsTimestamp);
            } else {
                this.output.setSampleOffsetUs(0L);
            }
            this.output.onNewExtractor();
            this.extractor.init(this.output);
        }
        this.output.setDrmInitData(this.drmInitData);
        return defaultExtractorInput;
    }

    private long peekId3PrivTimestamp(ExtractorInput input) throws IOException {
        input.resetPeekPosition();
        try {
            this.scratchId3Data.reset(10);
            input.peekFully(this.scratchId3Data.getData(), 0, 10);
        } catch (EOFException unused) {
        }
        if (this.scratchId3Data.readUnsignedInt24() != 4801587) {
            return C.TIME_UNSET;
        }
        this.scratchId3Data.skipBytes(3);
        int synchSafeInt = this.scratchId3Data.readSynchSafeInt();
        int i = synchSafeInt + 10;
        if (i > this.scratchId3Data.capacity()) {
            byte[] data = this.scratchId3Data.getData();
            this.scratchId3Data.reset(i);
            System.arraycopy(data, 0, this.scratchId3Data.getData(), 0, 10);
        }
        input.peekFully(this.scratchId3Data.getData(), 10, synchSafeInt);
        Metadata metadataDecode = this.id3Decoder.decode(this.scratchId3Data.getData(), synchSafeInt);
        if (metadataDecode == null) {
            return C.TIME_UNSET;
        }
        int length = metadataDecode.length();
        for (int i2 = 0; i2 < length; i2++) {
            Metadata.Entry entry = metadataDecode.get(i2);
            if (entry instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) entry;
                if (PRIV_TIMESTAMP_FRAME_OWNER.equals(privFrame.owner)) {
                    System.arraycopy(privFrame.privateData, 0, this.scratchId3Data.getData(), 0, 8);
                    this.scratchId3Data.setPosition(0);
                    this.scratchId3Data.setLimit(8);
                    return this.scratchId3Data.readLong() & 8589934591L;
                }
            }
        }
        return C.TIME_UNSET;
    }

    private static byte[] getEncryptionIvArray(String ivString) {
        if (Ascii.toLowerCase(ivString).startsWith("0x")) {
            ivString = ivString.substring(2);
        }
        byte[] byteArray = new BigInteger(ivString, 16).toByteArray();
        byte[] bArr = new byte[16];
        int length = byteArray.length > 16 ? byteArray.length - 16 : 0;
        System.arraycopy(byteArray, length, bArr, (16 - byteArray.length) + length, byteArray.length - length);
        return bArr;
    }

    private static DataSource buildDataSource(DataSource dataSource, byte[] fullSegmentEncryptionKey, byte[] encryptionIv) {
        if (fullSegmentEncryptionKey == null) {
            return dataSource;
        }
        Assertions.checkNotNull(encryptionIv);
        return new Aes128DataSource(dataSource, fullSegmentEncryptionKey, encryptionIv);
    }

    private static boolean isIndependent(HlsChunkSource.SegmentBaseHolder segmentBaseHolder, HlsMediaPlaylist mediaPlaylist) {
        if (segmentBaseHolder.segmentBase instanceof HlsMediaPlaylist.Part) {
            return ((HlsMediaPlaylist.Part) segmentBaseHolder.segmentBase).isIndependent || (segmentBaseHolder.partIndex == 0 && mediaPlaylist.hasIndependentSegments);
        }
        return mediaPlaylist.hasIndependentSegments;
    }
}
