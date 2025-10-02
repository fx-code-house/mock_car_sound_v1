package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.DataChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
class HlsChunkSource {
    public static final int CHUNK_PUBLICATION_STATE_PRELOAD = 0;
    public static final int CHUNK_PUBLICATION_STATE_PUBLISHED = 1;
    public static final int CHUNK_PUBLICATION_STATE_REMOVED = 2;
    private static final int KEY_CACHE_SIZE = 4;
    private final DataSource encryptionDataSource;
    private Uri expectedPlaylistUrl;
    private final HlsExtractorFactory extractorFactory;
    private IOException fatalError;
    private boolean independentSegments;
    private boolean isTimestampMaster;
    private final DataSource mediaDataSource;
    private final List<Format> muxedCaptionFormats;
    private final Format[] playlistFormats;
    private final HlsPlaylistTracker playlistTracker;
    private final Uri[] playlistUrls;
    private boolean seenExpectedPlaylistError;
    private final TimestampAdjusterProvider timestampAdjusterProvider;
    private final TrackGroup trackGroup;
    private ExoTrackSelection trackSelection;
    private final FullSegmentEncryptionKeyCache keyCache = new FullSegmentEncryptionKeyCache(4);
    private byte[] scratchSpace = Util.EMPTY_BYTE_ARRAY;
    private long liveEdgeInPeriodTimeUs = C.TIME_UNSET;

    public static final class HlsChunkHolder {
        public Chunk chunk;
        public boolean endOfStream;
        public Uri playlistUrl;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlistUrl = null;
        }
    }

    public HlsChunkSource(HlsExtractorFactory extractorFactory, HlsPlaylistTracker playlistTracker, Uri[] playlistUrls, Format[] playlistFormats, HlsDataSourceFactory dataSourceFactory, TransferListener mediaTransferListener, TimestampAdjusterProvider timestampAdjusterProvider, List<Format> muxedCaptionFormats) {
        this.extractorFactory = extractorFactory;
        this.playlistTracker = playlistTracker;
        this.playlistUrls = playlistUrls;
        this.playlistFormats = playlistFormats;
        this.timestampAdjusterProvider = timestampAdjusterProvider;
        this.muxedCaptionFormats = muxedCaptionFormats;
        DataSource dataSourceCreateDataSource = dataSourceFactory.createDataSource(1);
        this.mediaDataSource = dataSourceCreateDataSource;
        if (mediaTransferListener != null) {
            dataSourceCreateDataSource.addTransferListener(mediaTransferListener);
        }
        this.encryptionDataSource = dataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(playlistFormats);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < playlistUrls.length; i++) {
            if ((playlistFormats[i].roleFlags & 16384) == 0) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, Ints.toArray(arrayList));
    }

    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        Uri uri = this.expectedPlaylistUrl;
        if (uri == null || !this.seenExpectedPlaylistError) {
            return;
        }
        this.playlistTracker.maybeThrowPlaylistRefreshError(uri);
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public void setTrackSelection(ExoTrackSelection trackSelection) {
        this.trackSelection = trackSelection;
    }

    public ExoTrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public void reset() {
        this.fatalError = null;
    }

    public void setIsTimestampMaster(boolean isTimestampMaster) {
        this.isTimestampMaster = isTimestampMaster;
    }

    public int getChunkPublicationState(HlsMediaChunk mediaChunk) {
        List<HlsMediaPlaylist.Part> list;
        if (mediaChunk.partIndex == -1) {
            return 1;
        }
        HlsMediaPlaylist hlsMediaPlaylist = (HlsMediaPlaylist) Assertions.checkNotNull(this.playlistTracker.getPlaylistSnapshot(this.playlistUrls[this.trackGroup.indexOf(mediaChunk.trackFormat)], false));
        int i = (int) (mediaChunk.chunkIndex - hlsMediaPlaylist.mediaSequence);
        if (i < 0) {
            return 1;
        }
        if (i < hlsMediaPlaylist.segments.size()) {
            list = hlsMediaPlaylist.segments.get(i).parts;
        } else {
            list = hlsMediaPlaylist.trailingParts;
        }
        if (mediaChunk.partIndex >= list.size()) {
            return 2;
        }
        HlsMediaPlaylist.Part part = list.get(mediaChunk.partIndex);
        if (part.isPreload) {
            return 0;
        }
        return Util.areEqual(Uri.parse(UriUtil.resolve(hlsMediaPlaylist.baseUri, part.url)), mediaChunk.dataSpec.uri) ? 1 : 2;
    }

    public void getNextChunk(long playbackPositionUs, long loadPositionUs, List<HlsMediaChunk> queue, boolean allowEndOfStream, HlsChunkHolder out) {
        HlsMediaPlaylist hlsMediaPlaylist;
        long initialStartTimeUs;
        Uri uri;
        int i;
        HlsMediaChunk hlsMediaChunk = queue.isEmpty() ? null : (HlsMediaChunk) Iterables.getLast(queue);
        int iIndexOf = hlsMediaChunk == null ? -1 : this.trackGroup.indexOf(hlsMediaChunk.trackFormat);
        long jMax = loadPositionUs - playbackPositionUs;
        long jResolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(playbackPositionUs);
        if (hlsMediaChunk != null && !this.independentSegments) {
            long durationUs = hlsMediaChunk.getDurationUs();
            jMax = Math.max(0L, jMax - durationUs);
            if (jResolveTimeToLiveEdgeUs != C.TIME_UNSET) {
                jResolveTimeToLiveEdgeUs = Math.max(0L, jResolveTimeToLiveEdgeUs - durationUs);
            }
        }
        this.trackSelection.updateSelectedTrack(playbackPositionUs, jMax, jResolveTimeToLiveEdgeUs, queue, createMediaChunkIterators(hlsMediaChunk, loadPositionUs));
        int selectedIndexInTrackGroup = this.trackSelection.getSelectedIndexInTrackGroup();
        boolean z = iIndexOf != selectedIndexInTrackGroup;
        Uri uri2 = this.playlistUrls[selectedIndexInTrackGroup];
        if (!this.playlistTracker.isSnapshotValid(uri2)) {
            out.playlistUrl = uri2;
            this.seenExpectedPlaylistError &= uri2.equals(this.expectedPlaylistUrl);
            this.expectedPlaylistUrl = uri2;
            return;
        }
        HlsMediaPlaylist playlistSnapshot = this.playlistTracker.getPlaylistSnapshot(uri2, true);
        Assertions.checkNotNull(playlistSnapshot);
        this.independentSegments = playlistSnapshot.hasIndependentSegments;
        updateLiveEdgeTimeUs(playlistSnapshot);
        long initialStartTimeUs2 = playlistSnapshot.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        Pair<Long, Integer> nextMediaSequenceAndPartIndex = getNextMediaSequenceAndPartIndex(hlsMediaChunk, z, playlistSnapshot, initialStartTimeUs2, loadPositionUs);
        long jLongValue = ((Long) nextMediaSequenceAndPartIndex.first).longValue();
        int iIntValue = ((Integer) nextMediaSequenceAndPartIndex.second).intValue();
        if (jLongValue >= playlistSnapshot.mediaSequence || hlsMediaChunk == null || !z) {
            hlsMediaPlaylist = playlistSnapshot;
            initialStartTimeUs = initialStartTimeUs2;
            uri = uri2;
            i = selectedIndexInTrackGroup;
        } else {
            Uri uri3 = this.playlistUrls[iIndexOf];
            HlsMediaPlaylist playlistSnapshot2 = this.playlistTracker.getPlaylistSnapshot(uri3, true);
            Assertions.checkNotNull(playlistSnapshot2);
            initialStartTimeUs = playlistSnapshot2.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
            Pair<Long, Integer> nextMediaSequenceAndPartIndex2 = getNextMediaSequenceAndPartIndex(hlsMediaChunk, false, playlistSnapshot2, initialStartTimeUs, loadPositionUs);
            jLongValue = ((Long) nextMediaSequenceAndPartIndex2.first).longValue();
            iIntValue = ((Integer) nextMediaSequenceAndPartIndex2.second).intValue();
            i = iIndexOf;
            uri = uri3;
            hlsMediaPlaylist = playlistSnapshot2;
        }
        if (jLongValue < hlsMediaPlaylist.mediaSequence) {
            this.fatalError = new BehindLiveWindowException();
            return;
        }
        SegmentBaseHolder nextSegmentHolder = getNextSegmentHolder(hlsMediaPlaylist, jLongValue, iIntValue);
        if (nextSegmentHolder == null) {
            if (!hlsMediaPlaylist.hasEndTag) {
                out.playlistUrl = uri;
                this.seenExpectedPlaylistError &= uri.equals(this.expectedPlaylistUrl);
                this.expectedPlaylistUrl = uri;
                return;
            } else {
                if (allowEndOfStream || hlsMediaPlaylist.segments.isEmpty()) {
                    out.endOfStream = true;
                    return;
                }
                nextSegmentHolder = new SegmentBaseHolder((HlsMediaPlaylist.SegmentBase) Iterables.getLast(hlsMediaPlaylist.segments), (hlsMediaPlaylist.mediaSequence + hlsMediaPlaylist.segments.size()) - 1, -1);
            }
        }
        this.seenExpectedPlaylistError = false;
        this.expectedPlaylistUrl = null;
        Uri fullEncryptionKeyUri = getFullEncryptionKeyUri(hlsMediaPlaylist, nextSegmentHolder.segmentBase.initializationSegment);
        out.chunk = maybeCreateEncryptionChunkFor(fullEncryptionKeyUri, i);
        if (out.chunk != null) {
            return;
        }
        Uri fullEncryptionKeyUri2 = getFullEncryptionKeyUri(hlsMediaPlaylist, nextSegmentHolder.segmentBase);
        out.chunk = maybeCreateEncryptionChunkFor(fullEncryptionKeyUri2, i);
        if (out.chunk != null) {
            return;
        }
        boolean zShouldSpliceIn = HlsMediaChunk.shouldSpliceIn(hlsMediaChunk, uri, hlsMediaPlaylist, nextSegmentHolder, initialStartTimeUs);
        if (zShouldSpliceIn && nextSegmentHolder.isPreload) {
            return;
        }
        out.chunk = HlsMediaChunk.createInstance(this.extractorFactory, this.mediaDataSource, this.playlistFormats[i], initialStartTimeUs, hlsMediaPlaylist, nextSegmentHolder, uri, this.muxedCaptionFormats, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.isTimestampMaster, this.timestampAdjusterProvider, hlsMediaChunk, this.keyCache.get(fullEncryptionKeyUri2), this.keyCache.get(fullEncryptionKeyUri), zShouldSpliceIn);
    }

    private static SegmentBaseHolder getNextSegmentHolder(HlsMediaPlaylist mediaPlaylist, long nextMediaSequence, int nextPartIndex) {
        int i = (int) (nextMediaSequence - mediaPlaylist.mediaSequence);
        if (i == mediaPlaylist.segments.size()) {
            if (nextPartIndex == -1) {
                nextPartIndex = 0;
            }
            if (nextPartIndex < mediaPlaylist.trailingParts.size()) {
                return new SegmentBaseHolder(mediaPlaylist.trailingParts.get(nextPartIndex), nextMediaSequence, nextPartIndex);
            }
            return null;
        }
        HlsMediaPlaylist.Segment segment = mediaPlaylist.segments.get(i);
        if (nextPartIndex == -1) {
            return new SegmentBaseHolder(segment, nextMediaSequence, -1);
        }
        if (nextPartIndex < segment.parts.size()) {
            return new SegmentBaseHolder(segment.parts.get(nextPartIndex), nextMediaSequence, nextPartIndex);
        }
        int i2 = i + 1;
        if (i2 < mediaPlaylist.segments.size()) {
            return new SegmentBaseHolder(mediaPlaylist.segments.get(i2), nextMediaSequence + 1, -1);
        }
        if (mediaPlaylist.trailingParts.isEmpty()) {
            return null;
        }
        return new SegmentBaseHolder(mediaPlaylist.trailingParts.get(0), nextMediaSequence + 1, 0);
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            this.keyCache.put(encryptionKeyChunk.dataSpec.uri, (byte[]) Assertions.checkNotNull(encryptionKeyChunk.getResult()));
        }
    }

    public boolean maybeExcludeTrack(Chunk chunk, long exclusionDurationMs) {
        ExoTrackSelection exoTrackSelection = this.trackSelection;
        return exoTrackSelection.blacklist(exoTrackSelection.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), exclusionDurationMs);
    }

    public boolean onPlaylistError(Uri playlistUrl, long exclusionDurationMs) {
        int iIndexOf;
        int i = 0;
        while (true) {
            Uri[] uriArr = this.playlistUrls;
            if (i >= uriArr.length) {
                i = -1;
                break;
            }
            if (uriArr[i].equals(playlistUrl)) {
                break;
            }
            i++;
        }
        if (i == -1 || (iIndexOf = this.trackSelection.indexOf(i)) == -1) {
            return true;
        }
        this.seenExpectedPlaylistError |= playlistUrl.equals(this.expectedPlaylistUrl);
        return exclusionDurationMs == C.TIME_UNSET || (this.trackSelection.blacklist(iIndexOf, exclusionDurationMs) && this.playlistTracker.excludeMediaPlaylist(playlistUrl, exclusionDurationMs));
    }

    public MediaChunkIterator[] createMediaChunkIterators(HlsMediaChunk previous, long loadPositionUs) {
        int i;
        int iIndexOf = previous == null ? -1 : this.trackGroup.indexOf(previous.trackFormat);
        int length = this.trackSelection.length();
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[length];
        boolean z = false;
        int i2 = 0;
        while (i2 < length) {
            int indexInTrackGroup = this.trackSelection.getIndexInTrackGroup(i2);
            Uri uri = this.playlistUrls[indexInTrackGroup];
            if (!this.playlistTracker.isSnapshotValid(uri)) {
                mediaChunkIteratorArr[i2] = MediaChunkIterator.EMPTY;
                i = i2;
            } else {
                HlsMediaPlaylist playlistSnapshot = this.playlistTracker.getPlaylistSnapshot(uri, z);
                Assertions.checkNotNull(playlistSnapshot);
                long initialStartTimeUs = playlistSnapshot.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
                i = i2;
                Pair<Long, Integer> nextMediaSequenceAndPartIndex = getNextMediaSequenceAndPartIndex(previous, indexInTrackGroup != iIndexOf ? true : z, playlistSnapshot, initialStartTimeUs, loadPositionUs);
                mediaChunkIteratorArr[i] = new HlsMediaPlaylistSegmentIterator(playlistSnapshot.baseUri, initialStartTimeUs, getSegmentBaseList(playlistSnapshot, ((Long) nextMediaSequenceAndPartIndex.first).longValue(), ((Integer) nextMediaSequenceAndPartIndex.second).intValue()));
            }
            i2 = i + 1;
            z = false;
        }
        return mediaChunkIteratorArr;
    }

    public int getPreferredQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        if (this.fatalError != null || this.trackSelection.length() < 2) {
            return queue.size();
        }
        return this.trackSelection.evaluateQueueSize(playbackPositionUs, queue);
    }

    public boolean shouldCancelLoad(long playbackPositionUs, Chunk loadingChunk, List<? extends MediaChunk> queue) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(playbackPositionUs, loadingChunk, queue);
    }

    static List<HlsMediaPlaylist.SegmentBase> getSegmentBaseList(HlsMediaPlaylist playlist, long mediaSequence, int partIndex) {
        int i = (int) (mediaSequence - playlist.mediaSequence);
        if (i < 0 || playlist.segments.size() < i) {
            return ImmutableList.of();
        }
        ArrayList arrayList = new ArrayList();
        if (i < playlist.segments.size()) {
            if (partIndex != -1) {
                HlsMediaPlaylist.Segment segment = playlist.segments.get(i);
                if (partIndex == 0) {
                    arrayList.add(segment);
                } else if (partIndex < segment.parts.size()) {
                    arrayList.addAll(segment.parts.subList(partIndex, segment.parts.size()));
                }
                i++;
            }
            arrayList.addAll(playlist.segments.subList(i, playlist.segments.size()));
            partIndex = 0;
        }
        if (playlist.partTargetDurationUs != C.TIME_UNSET) {
            int i2 = partIndex != -1 ? partIndex : 0;
            if (i2 < playlist.trailingParts.size()) {
                arrayList.addAll(playlist.trailingParts.subList(i2, playlist.trailingParts.size()));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public boolean obtainsChunksForPlaylist(Uri playlistUrl) {
        return Util.contains(this.playlistUrls, playlistUrl);
    }

    private Pair<Long, Integer> getNextMediaSequenceAndPartIndex(HlsMediaChunk previous, boolean switchingTrack, HlsMediaPlaylist mediaPlaylist, long startOfPlaylistInPeriodUs, long loadPositionUs) {
        List<HlsMediaPlaylist.Part> list;
        long nextChunkIndex;
        if (previous == null || switchingTrack) {
            long j = mediaPlaylist.durationUs + startOfPlaylistInPeriodUs;
            if (previous != null && !this.independentSegments) {
                loadPositionUs = previous.startTimeUs;
            }
            if (!mediaPlaylist.hasEndTag && loadPositionUs >= j) {
                return new Pair<>(Long.valueOf(mediaPlaylist.mediaSequence + mediaPlaylist.segments.size()), -1);
            }
            long j2 = loadPositionUs - startOfPlaylistInPeriodUs;
            int i = 0;
            int iBinarySearchFloor = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) mediaPlaylist.segments, Long.valueOf(j2), true, !this.playlistTracker.isLive() || previous == null);
            long j3 = iBinarySearchFloor + mediaPlaylist.mediaSequence;
            if (iBinarySearchFloor >= 0) {
                HlsMediaPlaylist.Segment segment = mediaPlaylist.segments.get(iBinarySearchFloor);
                if (j2 < segment.relativeStartTimeUs + segment.durationUs) {
                    list = segment.parts;
                } else {
                    list = mediaPlaylist.trailingParts;
                }
                while (true) {
                    if (i >= list.size()) {
                        break;
                    }
                    HlsMediaPlaylist.Part part = list.get(i);
                    if (j2 >= part.relativeStartTimeUs + part.durationUs) {
                        i++;
                    } else if (part.isIndependent) {
                        j3 += list == mediaPlaylist.trailingParts ? 1L : 0L;
                        i = i;
                    }
                }
            }
            return new Pair<>(Long.valueOf(j3), Integer.valueOf(i));
        }
        if (previous.isLoadCompleted()) {
            if (previous.partIndex == -1) {
                nextChunkIndex = previous.getNextChunkIndex();
            } else {
                nextChunkIndex = previous.chunkIndex;
            }
            return new Pair<>(Long.valueOf(nextChunkIndex), Integer.valueOf(previous.partIndex != -1 ? previous.partIndex + 1 : -1));
        }
        return new Pair<>(Long.valueOf(previous.chunkIndex), Integer.valueOf(previous.partIndex));
    }

    private long resolveTimeToLiveEdgeUs(long playbackPositionUs) {
        long j = this.liveEdgeInPeriodTimeUs;
        return (j > C.TIME_UNSET ? 1 : (j == C.TIME_UNSET ? 0 : -1)) != 0 ? j - playbackPositionUs : C.TIME_UNSET;
    }

    private void updateLiveEdgeTimeUs(HlsMediaPlaylist mediaPlaylist) {
        this.liveEdgeInPeriodTimeUs = mediaPlaylist.hasEndTag ? C.TIME_UNSET : mediaPlaylist.getEndTimeUs() - this.playlistTracker.getInitialStartTimeUs();
    }

    private Chunk maybeCreateEncryptionChunkFor(Uri keyUri, int selectedTrackIndex) {
        if (keyUri == null) {
            return null;
        }
        byte[] bArrRemove = this.keyCache.remove(keyUri);
        if (bArrRemove != null) {
            this.keyCache.put(keyUri, bArrRemove);
            return null;
        }
        return new EncryptionKeyChunk(this.encryptionDataSource, new DataSpec.Builder().setUri(keyUri).setFlags(1).build(), this.playlistFormats[selectedTrackIndex], this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.scratchSpace);
    }

    private static Uri getFullEncryptionKeyUri(HlsMediaPlaylist playlist, HlsMediaPlaylist.SegmentBase segmentBase) {
        if (segmentBase == null || segmentBase.fullSegmentEncryptionKeyUri == null) {
            return null;
        }
        return UriUtil.resolveToUri(playlist.baseUri, segmentBase.fullSegmentEncryptionKeyUri);
    }

    static final class SegmentBaseHolder {
        public final boolean isPreload;
        public final long mediaSequence;
        public final int partIndex;
        public final HlsMediaPlaylist.SegmentBase segmentBase;

        public SegmentBaseHolder(HlsMediaPlaylist.SegmentBase segmentBase, long mediaSequence, int partIndex) {
            this.segmentBase = segmentBase;
            this.mediaSequence = mediaSequence;
            this.partIndex = partIndex;
            this.isPreload = (segmentBase instanceof HlsMediaPlaylist.Part) && ((HlsMediaPlaylist.Part) segmentBase).isPreload;
        }
    }

    private static final class InitializationTrackSelection extends BaseTrackSelection {
        private int selectedIndex;

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public Object getSelectionData() {
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectionReason() {
            return 0;
        }

        public InitializationTrackSelection(TrackGroup group, int[] tracks) {
            super(group, tracks);
            this.selectedIndex = indexOf(group.getFormat(tracks[0]));
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> queue, MediaChunkIterator[] mediaChunkIterators) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.selectedIndex, jElapsedRealtime)) {
                for (int i = this.length - 1; i >= 0; i--) {
                    if (!isBlacklisted(i, jElapsedRealtime)) {
                        this.selectedIndex = i;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectedIndex() {
            return this.selectedIndex;
        }
    }

    private static final class EncryptionKeyChunk extends DataChunk {
        private byte[] result;

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, byte[] scratchSpace) {
            super(dataSource, dataSpec, 3, trackFormat, trackSelectionReason, trackSelectionData, scratchSpace);
        }

        @Override // com.google.android.exoplayer2.source.chunk.DataChunk
        protected void consume(byte[] data, int limit) {
            this.result = Arrays.copyOf(data, limit);
        }

        public byte[] getResult() {
            return this.result;
        }
    }

    static final class HlsMediaPlaylistSegmentIterator extends BaseMediaChunkIterator {
        private final String playlistBaseUri;
        private final List<HlsMediaPlaylist.SegmentBase> segmentBases;
        private final long startOfPlaylistInPeriodUs;

        public HlsMediaPlaylistSegmentIterator(String playlistBaseUri, long startOfPlaylistInPeriodUs, List<HlsMediaPlaylist.SegmentBase> segmentBases) {
            super(0L, segmentBases.size() - 1);
            this.playlistBaseUri = playlistBaseUri;
            this.startOfPlaylistInPeriodUs = startOfPlaylistInPeriodUs;
            this.segmentBases = segmentBases;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            HlsMediaPlaylist.SegmentBase segmentBase = this.segmentBases.get((int) getCurrentIndex());
            return new DataSpec(UriUtil.resolveToUri(this.playlistBaseUri, segmentBase.url), segmentBase.byteRangeOffset, segmentBase.byteRangeLength);
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.startOfPlaylistInPeriodUs + this.segmentBases.get((int) getCurrentIndex()).relativeStartTimeUs;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            checkInBounds();
            HlsMediaPlaylist.SegmentBase segmentBase = this.segmentBases.get((int) getCurrentIndex());
            return this.startOfPlaylistInPeriodUs + segmentBase.relativeStartTimeUs + segmentBase.durationUs;
        }
    }
}
