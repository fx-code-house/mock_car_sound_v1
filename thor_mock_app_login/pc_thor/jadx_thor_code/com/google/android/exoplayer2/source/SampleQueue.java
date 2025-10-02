package com.google.android.exoplayer2.source;

import android.os.Looper;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Consumer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
public class SampleQueue implements TrackOutput {
    static final int SAMPLE_CAPACITY_INCREMENT = 1000;
    private static final String TAG = "SampleQueue";
    private int absoluteFirstIndex;
    private DrmSession currentDrmSession;
    private Format downstreamFormat;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private boolean isLastSampleQueued;
    private int length;
    private boolean loggedUnexpectedNonSyncSample;
    private boolean pendingSplice;
    private final Looper playbackLooper;
    private int readPosition;
    private int relativeFirstIndex;
    private final SampleDataQueue sampleDataQueue;
    private long sampleOffsetUs;
    private Format unadjustedUpstreamFormat;
    private boolean upstreamAllSamplesAreSyncSamples;
    private Format upstreamFormat;
    private boolean upstreamFormatAdjustmentRequired;
    private UpstreamFormatChangedListener upstreamFormatChangeListener;
    private int upstreamSourceId;
    private final SampleExtrasHolder extrasHolder = new SampleExtrasHolder();
    private int capacity = 1000;
    private int[] sourceIds = new int[1000];
    private long[] offsets = new long[1000];
    private long[] timesUs = new long[1000];
    private int[] flags = new int[1000];
    private int[] sizes = new int[1000];
    private TrackOutput.CryptoData[] cryptoDatas = new TrackOutput.CryptoData[1000];
    private final SpannedData<SharedSampleMetadata> sharedSampleMetadata = new SpannedData<>(new Consumer() { // from class: com.google.android.exoplayer2.source.SampleQueue$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.util.Consumer
        public final void accept(Object obj) {
            ((SampleQueue.SharedSampleMetadata) obj).drmSessionReference.release();
        }
    });
    private long startTimeUs = Long.MIN_VALUE;
    private long largestDiscardedTimestampUs = Long.MIN_VALUE;
    private long largestQueuedTimestampUs = Long.MIN_VALUE;
    private boolean upstreamFormatRequired = true;
    private boolean upstreamKeyframeRequired = true;

    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    public static SampleQueue createWithoutDrm(Allocator allocator) {
        return new SampleQueue(allocator, null, null, null);
    }

    public static SampleQueue createWithDrm(Allocator allocator, Looper playbackLooper, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher drmEventDispatcher) {
        return new SampleQueue(allocator, (Looper) Assertions.checkNotNull(playbackLooper), (DrmSessionManager) Assertions.checkNotNull(drmSessionManager), (DrmSessionEventListener.EventDispatcher) Assertions.checkNotNull(drmEventDispatcher));
    }

    protected SampleQueue(Allocator allocator, Looper playbackLooper, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher drmEventDispatcher) {
        this.playbackLooper = playbackLooper;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = drmEventDispatcher;
        this.sampleDataQueue = new SampleDataQueue(allocator);
    }

    public void release() {
        reset(true);
        releaseDrmSessionReferences();
    }

    public final void reset() {
        reset(false);
    }

    public void reset(boolean resetUpstreamFormat) {
        this.sampleDataQueue.reset();
        this.length = 0;
        this.absoluteFirstIndex = 0;
        this.relativeFirstIndex = 0;
        this.readPosition = 0;
        this.upstreamKeyframeRequired = true;
        this.startTimeUs = Long.MIN_VALUE;
        this.largestDiscardedTimestampUs = Long.MIN_VALUE;
        this.largestQueuedTimestampUs = Long.MIN_VALUE;
        this.isLastSampleQueued = false;
        this.sharedSampleMetadata.clear();
        if (resetUpstreamFormat) {
            this.unadjustedUpstreamFormat = null;
            this.upstreamFormat = null;
            this.upstreamFormatRequired = true;
        }
    }

    public final void setStartTimeUs(long startTimeUs) {
        this.startTimeUs = startTimeUs;
    }

    public final void sourceId(int sourceId) {
        this.upstreamSourceId = sourceId;
    }

    public final void splice() {
        this.pendingSplice = true;
    }

    public final int getWriteIndex() {
        return this.absoluteFirstIndex + this.length;
    }

    public final void discardUpstreamSamples(int discardFromIndex) {
        this.sampleDataQueue.discardUpstreamSampleBytes(discardUpstreamSampleMetadata(discardFromIndex));
    }

    public final void discardUpstreamFrom(long timeUs) {
        if (this.length == 0) {
            return;
        }
        Assertions.checkArgument(timeUs > getLargestReadTimestampUs());
        discardUpstreamSamples(this.absoluteFirstIndex + countUnreadSamplesBefore(timeUs));
    }

    public void preRelease() {
        discardToEnd();
        releaseDrmSessionReferences();
    }

    public void maybeThrowError() throws IOException {
        DrmSession drmSession = this.currentDrmSession;
        if (drmSession != null && drmSession.getState() == 1) {
            throw ((DrmSession.DrmSessionException) Assertions.checkNotNull(this.currentDrmSession.getError()));
        }
    }

    public final int getFirstIndex() {
        return this.absoluteFirstIndex;
    }

    public final int getReadIndex() {
        return this.absoluteFirstIndex + this.readPosition;
    }

    public final synchronized int peekSourceId() {
        return hasNextSample() ? this.sourceIds[getRelativeIndex(this.readPosition)] : this.upstreamSourceId;
    }

    public final synchronized Format getUpstreamFormat() {
        return this.upstreamFormatRequired ? null : this.upstreamFormat;
    }

    public final synchronized long getLargestQueuedTimestampUs() {
        return this.largestQueuedTimestampUs;
    }

    public final synchronized long getLargestReadTimestampUs() {
        return Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.readPosition));
    }

    public final synchronized boolean isLastSampleQueued() {
        return this.isLastSampleQueued;
    }

    public final synchronized long getFirstTimestampUs() {
        return this.length == 0 ? Long.MIN_VALUE : this.timesUs[this.relativeFirstIndex];
    }

    public synchronized boolean isReady(boolean loadingFinished) {
        Format format;
        boolean z = true;
        if (!hasNextSample()) {
            if (!loadingFinished && !this.isLastSampleQueued && ((format = this.upstreamFormat) == null || format == this.downstreamFormat)) {
                z = false;
            }
            return z;
        }
        if (this.sharedSampleMetadata.get(getReadIndex()).format != this.downstreamFormat) {
            return true;
        }
        return mayReadSample(getRelativeIndex(this.readPosition));
    }

    public int read(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags, boolean loadingFinished) {
        int iPeekSampleMetadata = peekSampleMetadata(formatHolder, buffer, (readFlags & 2) != 0, loadingFinished, this.extrasHolder);
        if (iPeekSampleMetadata == -4 && !buffer.isEndOfStream()) {
            boolean z = (readFlags & 1) != 0;
            if ((readFlags & 4) == 0) {
                if (z) {
                    this.sampleDataQueue.peekToBuffer(buffer, this.extrasHolder);
                } else {
                    this.sampleDataQueue.readToBuffer(buffer, this.extrasHolder);
                }
            }
            if (!z) {
                this.readPosition++;
            }
        }
        return iPeekSampleMetadata;
    }

    public final synchronized boolean seekTo(int sampleIndex) {
        rewind();
        int i = this.absoluteFirstIndex;
        if (sampleIndex >= i && sampleIndex <= this.length + i) {
            this.startTimeUs = Long.MIN_VALUE;
            this.readPosition = sampleIndex - i;
            return true;
        }
        return false;
    }

    public final synchronized boolean seekTo(long timeUs, boolean allowTimeBeyondBuffer) {
        rewind();
        int relativeIndex = getRelativeIndex(this.readPosition);
        if (hasNextSample() && timeUs >= this.timesUs[relativeIndex] && (timeUs <= this.largestQueuedTimestampUs || allowTimeBeyondBuffer)) {
            int iFindSampleBefore = findSampleBefore(relativeIndex, this.length - this.readPosition, timeUs, true);
            if (iFindSampleBefore == -1) {
                return false;
            }
            this.startTimeUs = timeUs;
            this.readPosition += iFindSampleBefore;
            return true;
        }
        return false;
    }

    public final synchronized int getSkipCount(long timeUs, boolean allowEndOfQueue) {
        int relativeIndex = getRelativeIndex(this.readPosition);
        if (hasNextSample() && timeUs >= this.timesUs[relativeIndex]) {
            if (timeUs > this.largestQueuedTimestampUs && allowEndOfQueue) {
                return this.length - this.readPosition;
            }
            int iFindSampleBefore = findSampleBefore(relativeIndex, this.length - this.readPosition, timeUs, true);
            if (iFindSampleBefore == -1) {
                return 0;
            }
            return iFindSampleBefore;
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final synchronized void skip(int r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 < 0) goto Le
            int r0 = r2.readPosition     // Catch: java.lang.Throwable -> Lc
            int r0 = r0 + r3
            int r1 = r2.length     // Catch: java.lang.Throwable -> Lc
            if (r0 > r1) goto Le
            r0 = 1
            goto Lf
        Lc:
            r3 = move-exception
            goto L19
        Le:
            r0 = 0
        Lf:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)     // Catch: java.lang.Throwable -> Lc
            int r0 = r2.readPosition     // Catch: java.lang.Throwable -> Lc
            int r0 = r0 + r3
            r2.readPosition = r0     // Catch: java.lang.Throwable -> Lc
            monitor-exit(r2)
            return
        L19:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.skip(int):void");
    }

    public final void discardTo(long timeUs, boolean toKeyframe, boolean stopAtReadPosition) {
        this.sampleDataQueue.discardDownstreamTo(discardSampleMetadataTo(timeUs, toKeyframe, stopAtReadPosition));
    }

    public final void discardToRead() {
        this.sampleDataQueue.discardDownstreamTo(discardSampleMetadataToRead());
    }

    public final void discardToEnd() {
        this.sampleDataQueue.discardDownstreamTo(discardSampleMetadataToEnd());
    }

    public final void setSampleOffsetUs(long sampleOffsetUs) {
        if (this.sampleOffsetUs != sampleOffsetUs) {
            this.sampleOffsetUs = sampleOffsetUs;
            invalidateUpstreamFormatAdjustment();
        }
    }

    public final void setUpstreamFormatChangeListener(UpstreamFormatChangedListener listener) {
        this.upstreamFormatChangeListener = listener;
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public final void format(Format unadjustedUpstreamFormat) {
        Format adjustedUpstreamFormat = getAdjustedUpstreamFormat(unadjustedUpstreamFormat);
        this.upstreamFormatAdjustmentRequired = false;
        this.unadjustedUpstreamFormat = unadjustedUpstreamFormat;
        boolean upstreamFormat = setUpstreamFormat(adjustedUpstreamFormat);
        UpstreamFormatChangedListener upstreamFormatChangedListener = this.upstreamFormatChangeListener;
        if (upstreamFormatChangedListener == null || !upstreamFormat) {
            return;
        }
        upstreamFormatChangedListener.onUpstreamFormatChanged(adjustedUpstreamFormat);
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public final int sampleData(DataReader input, int length, boolean allowEndOfInput, int sampleDataPart) throws IOException {
        return this.sampleDataQueue.sampleData(input, length, allowEndOfInput);
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public final void sampleData(ParsableByteArray data, int length, int sampleDataPart) {
        this.sampleDataQueue.sampleData(data, length);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0064  */
    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void sampleMetadata(long r12, int r14, int r15, int r16, com.google.android.exoplayer2.extractor.TrackOutput.CryptoData r17) {
        /*
            r11 = this;
            r8 = r11
            boolean r0 = r8.upstreamFormatAdjustmentRequired
            if (r0 == 0) goto L10
            com.google.android.exoplayer2.Format r0 = r8.unadjustedUpstreamFormat
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r0)
            com.google.android.exoplayer2.Format r0 = (com.google.android.exoplayer2.Format) r0
            r11.format(r0)
        L10:
            r0 = r14 & 1
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L18
            r3 = r2
            goto L19
        L18:
            r3 = r1
        L19:
            boolean r4 = r8.upstreamKeyframeRequired
            if (r4 == 0) goto L22
            if (r3 != 0) goto L20
            return
        L20:
            r8.upstreamKeyframeRequired = r1
        L22:
            long r4 = r8.sampleOffsetUs
            long r4 = r4 + r12
            boolean r6 = r8.upstreamAllSamplesAreSyncSamples
            if (r6 == 0) goto L64
            long r6 = r8.startTimeUs
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 >= 0) goto L30
            return
        L30:
            if (r0 != 0) goto L64
            boolean r0 = r8.loggedUnexpectedNonSyncSample
            if (r0 != 0) goto L60
            com.google.android.exoplayer2.Format r0 = r8.upstreamFormat
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r6 = java.lang.String.valueOf(r0)
            int r6 = r6.length()
            int r6 = r6 + 50
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r6)
            java.lang.String r6 = "Overriding unexpected non-sync sample for format: "
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.StringBuilder r0 = r6.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r6 = "SampleQueue"
            com.google.android.exoplayer2.util.Log.w(r6, r0)
            r8.loggedUnexpectedNonSyncSample = r2
        L60:
            r0 = r14 | 1
            r6 = r0
            goto L65
        L64:
            r6 = r14
        L65:
            boolean r0 = r8.pendingSplice
            if (r0 == 0) goto L76
            if (r3 == 0) goto L75
            boolean r0 = r11.attemptSplice(r4)
            if (r0 != 0) goto L72
            goto L75
        L72:
            r8.pendingSplice = r1
            goto L76
        L75:
            return
        L76:
            com.google.android.exoplayer2.source.SampleDataQueue r0 = r8.sampleDataQueue
            long r0 = r0.getTotalBytesWritten()
            r7 = r15
            long r2 = (long) r7
            long r0 = r0 - r2
            r2 = r16
            long r2 = (long) r2
            long r9 = r0 - r2
            r0 = r11
            r1 = r4
            r3 = r6
            r4 = r9
            r6 = r15
            r7 = r17
            r0.commitSample(r1, r3, r4, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.sampleMetadata(long, int, int, int, com.google.android.exoplayer2.extractor.TrackOutput$CryptoData):void");
    }

    protected final void invalidateUpstreamFormatAdjustment() {
        this.upstreamFormatAdjustmentRequired = true;
    }

    protected Format getAdjustedUpstreamFormat(Format format) {
        return (this.sampleOffsetUs == 0 || format.subsampleOffsetUs == Long.MAX_VALUE) ? format : format.buildUpon().setSubsampleOffsetUs(format.subsampleOffsetUs + this.sampleOffsetUs).build();
    }

    private synchronized void rewind() {
        this.readPosition = 0;
        this.sampleDataQueue.rewind();
    }

    private synchronized int peekSampleMetadata(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired, boolean loadingFinished, SampleExtrasHolder extrasHolder) {
        buffer.waitingForKeys = false;
        if (!hasNextSample()) {
            if (!loadingFinished && !this.isLastSampleQueued) {
                Format format = this.upstreamFormat;
                if (format == null || (!formatRequired && format == this.downstreamFormat)) {
                    return -3;
                }
                onFormatResult((Format) Assertions.checkNotNull(format), formatHolder);
                return -5;
            }
            buffer.setFlags(4);
            return -4;
        }
        Format format2 = this.sharedSampleMetadata.get(getReadIndex()).format;
        if (!formatRequired && format2 == this.downstreamFormat) {
            int relativeIndex = getRelativeIndex(this.readPosition);
            if (!mayReadSample(relativeIndex)) {
                buffer.waitingForKeys = true;
                return -3;
            }
            buffer.setFlags(this.flags[relativeIndex]);
            buffer.timeUs = this.timesUs[relativeIndex];
            if (buffer.timeUs < this.startTimeUs) {
                buffer.addFlag(Integer.MIN_VALUE);
            }
            extrasHolder.size = this.sizes[relativeIndex];
            extrasHolder.offset = this.offsets[relativeIndex];
            extrasHolder.cryptoData = this.cryptoDatas[relativeIndex];
            return -4;
        }
        onFormatResult(format2, formatHolder);
        return -5;
    }

    private synchronized boolean setUpstreamFormat(Format format) {
        this.upstreamFormatRequired = false;
        if (Util.areEqual(format, this.upstreamFormat)) {
            return false;
        }
        if (!this.sharedSampleMetadata.isEmpty() && this.sharedSampleMetadata.getEndValue().format.equals(format)) {
            this.upstreamFormat = this.sharedSampleMetadata.getEndValue().format;
        } else {
            this.upstreamFormat = format;
        }
        this.upstreamAllSamplesAreSyncSamples = MimeTypes.allSamplesAreSyncSamples(this.upstreamFormat.sampleMimeType, this.upstreamFormat.codecs);
        this.loggedUnexpectedNonSyncSample = false;
        return true;
    }

    private synchronized long discardSampleMetadataTo(long timeUs, boolean toKeyframe, boolean stopAtReadPosition) {
        int i;
        int i2 = this.length;
        if (i2 != 0) {
            long[] jArr = this.timesUs;
            int i3 = this.relativeFirstIndex;
            if (timeUs >= jArr[i3]) {
                if (stopAtReadPosition && (i = this.readPosition) != i2) {
                    i2 = i + 1;
                }
                int iFindSampleBefore = findSampleBefore(i3, i2, timeUs, toKeyframe);
                if (iFindSampleBefore == -1) {
                    return -1L;
                }
                return discardSamples(iFindSampleBefore);
            }
        }
        return -1L;
    }

    public synchronized long discardSampleMetadataToRead() {
        int i = this.readPosition;
        if (i == 0) {
            return -1L;
        }
        return discardSamples(i);
    }

    private synchronized long discardSampleMetadataToEnd() {
        int i = this.length;
        if (i == 0) {
            return -1L;
        }
        return discardSamples(i);
    }

    private void releaseDrmSessionReferences() {
        DrmSession drmSession = this.currentDrmSession;
        if (drmSession != null) {
            drmSession.release(this.drmEventDispatcher);
            this.currentDrmSession = null;
            this.downstreamFormat = null;
        }
    }

    private synchronized void commitSample(long timeUs, int sampleFlags, long offset, int size, TrackOutput.CryptoData cryptoData) {
        DrmSessionManager.DrmSessionReference drmSessionReferencePreacquireSession;
        int i = this.length;
        if (i > 0) {
            int relativeIndex = getRelativeIndex(i - 1);
            Assertions.checkArgument(this.offsets[relativeIndex] + ((long) this.sizes[relativeIndex]) <= offset);
        }
        this.isLastSampleQueued = (536870912 & sampleFlags) != 0;
        this.largestQueuedTimestampUs = Math.max(this.largestQueuedTimestampUs, timeUs);
        int relativeIndex2 = getRelativeIndex(this.length);
        this.timesUs[relativeIndex2] = timeUs;
        this.offsets[relativeIndex2] = offset;
        this.sizes[relativeIndex2] = size;
        this.flags[relativeIndex2] = sampleFlags;
        this.cryptoDatas[relativeIndex2] = cryptoData;
        this.sourceIds[relativeIndex2] = this.upstreamSourceId;
        if (this.sharedSampleMetadata.isEmpty() || !this.sharedSampleMetadata.getEndValue().format.equals(this.upstreamFormat)) {
            DrmSessionManager drmSessionManager = this.drmSessionManager;
            if (drmSessionManager != null) {
                drmSessionReferencePreacquireSession = drmSessionManager.preacquireSession((Looper) Assertions.checkNotNull(this.playbackLooper), this.drmEventDispatcher, this.upstreamFormat);
            } else {
                drmSessionReferencePreacquireSession = DrmSessionManager.DrmSessionReference.EMPTY;
            }
            this.sharedSampleMetadata.appendSpan(getWriteIndex(), new SharedSampleMetadata((Format) Assertions.checkNotNull(this.upstreamFormat), drmSessionReferencePreacquireSession));
        }
        int i2 = this.length + 1;
        this.length = i2;
        int i3 = this.capacity;
        if (i2 == i3) {
            int i4 = i3 + 1000;
            int[] iArr = new int[i4];
            long[] jArr = new long[i4];
            long[] jArr2 = new long[i4];
            int[] iArr2 = new int[i4];
            int[] iArr3 = new int[i4];
            TrackOutput.CryptoData[] cryptoDataArr = new TrackOutput.CryptoData[i4];
            int i5 = this.relativeFirstIndex;
            int i6 = i3 - i5;
            System.arraycopy(this.offsets, i5, jArr, 0, i6);
            System.arraycopy(this.timesUs, this.relativeFirstIndex, jArr2, 0, i6);
            System.arraycopy(this.flags, this.relativeFirstIndex, iArr2, 0, i6);
            System.arraycopy(this.sizes, this.relativeFirstIndex, iArr3, 0, i6);
            System.arraycopy(this.cryptoDatas, this.relativeFirstIndex, cryptoDataArr, 0, i6);
            System.arraycopy(this.sourceIds, this.relativeFirstIndex, iArr, 0, i6);
            int i7 = this.relativeFirstIndex;
            System.arraycopy(this.offsets, 0, jArr, i6, i7);
            System.arraycopy(this.timesUs, 0, jArr2, i6, i7);
            System.arraycopy(this.flags, 0, iArr2, i6, i7);
            System.arraycopy(this.sizes, 0, iArr3, i6, i7);
            System.arraycopy(this.cryptoDatas, 0, cryptoDataArr, i6, i7);
            System.arraycopy(this.sourceIds, 0, iArr, i6, i7);
            this.offsets = jArr;
            this.timesUs = jArr2;
            this.flags = iArr2;
            this.sizes = iArr3;
            this.cryptoDatas = cryptoDataArr;
            this.sourceIds = iArr;
            this.relativeFirstIndex = 0;
            this.capacity = i4;
        }
    }

    private synchronized boolean attemptSplice(long timeUs) {
        if (this.length == 0) {
            return timeUs > this.largestDiscardedTimestampUs;
        }
        if (getLargestReadTimestampUs() >= timeUs) {
            return false;
        }
        discardUpstreamSampleMetadata(this.absoluteFirstIndex + countUnreadSamplesBefore(timeUs));
        return true;
    }

    private long discardUpstreamSampleMetadata(int discardFromIndex) {
        int writeIndex = getWriteIndex() - discardFromIndex;
        boolean z = false;
        Assertions.checkArgument(writeIndex >= 0 && writeIndex <= this.length - this.readPosition);
        int i = this.length - writeIndex;
        this.length = i;
        this.largestQueuedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(i));
        if (writeIndex == 0 && this.isLastSampleQueued) {
            z = true;
        }
        this.isLastSampleQueued = z;
        this.sharedSampleMetadata.discardFrom(discardFromIndex);
        int i2 = this.length;
        if (i2 == 0) {
            return 0L;
        }
        return this.offsets[getRelativeIndex(i2 - 1)] + this.sizes[r9];
    }

    private boolean hasNextSample() {
        return this.readPosition != this.length;
    }

    private void onFormatResult(Format newFormat, FormatHolder outputFormatHolder) {
        Format format = this.downstreamFormat;
        boolean z = format == null;
        DrmInitData drmInitData = z ? null : format.drmInitData;
        this.downstreamFormat = newFormat;
        DrmInitData drmInitData2 = newFormat.drmInitData;
        DrmSessionManager drmSessionManager = this.drmSessionManager;
        outputFormatHolder.format = drmSessionManager != null ? newFormat.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(newFormat)) : newFormat;
        outputFormatHolder.drmSession = this.currentDrmSession;
        if (this.drmSessionManager == null) {
            return;
        }
        if (z || !Util.areEqual(drmInitData, drmInitData2)) {
            DrmSession drmSession = this.currentDrmSession;
            DrmSession drmSessionAcquireSession = this.drmSessionManager.acquireSession((Looper) Assertions.checkNotNull(this.playbackLooper), this.drmEventDispatcher, newFormat);
            this.currentDrmSession = drmSessionAcquireSession;
            outputFormatHolder.drmSession = drmSessionAcquireSession;
            if (drmSession != null) {
                drmSession.release(this.drmEventDispatcher);
            }
        }
    }

    private boolean mayReadSample(int relativeReadIndex) {
        DrmSession drmSession = this.currentDrmSession;
        return drmSession == null || drmSession.getState() == 4 || ((this.flags[relativeReadIndex] & 1073741824) == 0 && this.currentDrmSession.playClearSamplesWithoutKeys());
    }

    private int findSampleBefore(int relativeStartIndex, int length, long timeUs, boolean keyframe) {
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            long j = this.timesUs[relativeStartIndex];
            if (j > timeUs) {
                return i;
            }
            if (!keyframe || (this.flags[relativeStartIndex] & 1) != 0) {
                if (j == timeUs) {
                    return i2;
                }
                i = i2;
            }
            relativeStartIndex++;
            if (relativeStartIndex == this.capacity) {
                relativeStartIndex = 0;
            }
        }
        return i;
    }

    private int countUnreadSamplesBefore(long timeUs) {
        int i = this.length;
        int relativeIndex = getRelativeIndex(i - 1);
        while (i > this.readPosition && this.timesUs[relativeIndex] >= timeUs) {
            i--;
            relativeIndex--;
            if (relativeIndex == -1) {
                relativeIndex = this.capacity - 1;
            }
        }
        return i;
    }

    private long discardSamples(int discardCount) {
        this.largestDiscardedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(discardCount));
        this.length -= discardCount;
        int i = this.absoluteFirstIndex + discardCount;
        this.absoluteFirstIndex = i;
        int i2 = this.relativeFirstIndex + discardCount;
        this.relativeFirstIndex = i2;
        int i3 = this.capacity;
        if (i2 >= i3) {
            this.relativeFirstIndex = i2 - i3;
        }
        int i4 = this.readPosition - discardCount;
        this.readPosition = i4;
        if (i4 < 0) {
            this.readPosition = 0;
        }
        this.sharedSampleMetadata.discardTo(i);
        if (this.length == 0) {
            int i5 = this.relativeFirstIndex;
            if (i5 == 0) {
                i5 = this.capacity;
            }
            return this.offsets[i5 - 1] + this.sizes[r6];
        }
        return this.offsets[this.relativeFirstIndex];
    }

    private long getLargestTimestamp(int length) {
        long jMax = Long.MIN_VALUE;
        if (length == 0) {
            return Long.MIN_VALUE;
        }
        int relativeIndex = getRelativeIndex(length - 1);
        for (int i = 0; i < length; i++) {
            jMax = Math.max(jMax, this.timesUs[relativeIndex]);
            if ((this.flags[relativeIndex] & 1) != 0) {
                break;
            }
            relativeIndex--;
            if (relativeIndex == -1) {
                relativeIndex = this.capacity - 1;
            }
        }
        return jMax;
    }

    private int getRelativeIndex(int offset) {
        int i = this.relativeFirstIndex + offset;
        int i2 = this.capacity;
        return i < i2 ? i : i - i2;
    }

    static final class SampleExtrasHolder {
        public TrackOutput.CryptoData cryptoData;
        public long offset;
        public int size;

        SampleExtrasHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SharedSampleMetadata {
        public final DrmSessionManager.DrmSessionReference drmSessionReference;
        public final Format format;

        private SharedSampleMetadata(Format format, DrmSessionManager.DrmSessionReference drmSessionReference) {
            this.format = format;
            this.drmSessionReference = drmSessionReference;
        }
    }
}
